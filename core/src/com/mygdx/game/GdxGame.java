package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.model.*;
import com.mygdx.view.GameScreen;

public class GdxGame extends Game {

    final static public int WIDTH = 1280; //размер в пикселях
    final static public int HEIGHT = 720;
    final public int STATE_GAME = 1;
    final public int STATE_GAMEOVER = 0;

    private SpriteBatch batch;
    private int state, level;
    private long lastTime = System.nanoTime();
    private long counterTime;
    private int fps, counterFps;
    private GameScreen screenGame;
    private WorldSpace world;


//---------------------------------------------------------------------------------

    @Override
    public void create () {
        batch = new SpriteBatch();
        world = new WorldSpace(this);
        screenGame = new GameScreen(this, world);
        start();
    }

    public void start() {
        level = 1;
        state = STATE_GAME;
        world.start(level);
        setScreen(screenGame);
    }

    public void stop() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //super.render();
    }

    public void levelUp() {
        level++;
        world.start(level);
    }

    public SpriteBatch getBatch() { return batch;}

	@Override
	public void render () {
        switch (state){
            case STATE_GAMEOVER:
                stop();
                if(Gdx.input.isKeyPressed(Input.Keys.Y)) {
                    start();
                }
                else return;
        }
        update();
        super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		screenGame.dispose();
		//world.dispose();
	}
        
    public void update() {
        fps();
        world.update();
        for (Bullet bullet : world.getHero().getBullets()) {
            if (!bullet.isActive())
                continue;
            for (Asteroid asteroid : world.getAsteroids()) {
                if (!asteroid.isActive())
                    continue;
                if (asteroid.getRectangle().overlaps(bullet.getRectangle())) {
                    if (asteroid.damage(bullet.getDamage())) {
                        levelUp();
                        return;
                    }
                    bullet.destroy();
                    break;
                }
            }
        }
        for (Asteroid asteroid : world.getAsteroids()) {
            if(!asteroid.isActive())
                continue;
            if(world.getHero().getRectangle().overlaps(asteroid.getRectangle())) {
                if(world.getHero().damage(asteroid.getDamage())){
                    System.out.println("Game over!");
                    System.out.println("Хочешь еще сыграть? Y/N");
                    state = STATE_GAMEOVER;
                }
                asteroid.create();
            }
        }
    }

    public void fps() {
        long currentTime = System.nanoTime();
        long dTime = currentTime - lastTime;
        lastTime = currentTime;
        counterTime += dTime;
        counterFps++;
        if(counterTime >= 1E9) {
            fps = counterFps;
            counterFps = 0;
            counterTime = 0;
            System.out.println("Скорость: " + world.getHero().getSpeed());
            //System.out.println(world.getHero().getBody().getLinearVelocity());
            //System.out.println("FPS: " + fps + " " + Gdx.graphics.getFramesPerSecond());
        }
    }

}
