package com.mygdx.view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.mygdx.game.GameState;
import com.mygdx.game.GdxGame;
import com.mygdx.game.InputController;
import com.mygdx.model.Asteroid;
import com.mygdx.model.WorldSpace;

import javax.swing.plaf.nimbus.State;

/**
 * Created by genady on 19.02.2017.
 */
public class WorldRenderer {

    final WorldSpace world;

    public OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer;

    private long lastTime;
    private long counterTime;


    public WorldRenderer(WorldSpace world) {
        this.world = world;
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        lastTime = System.currentTimeMillis();
        //camera.setToOrtho(false, screen.WIDTH, screen.HEIGHT);
        //camera.position.set(screen.WIDTH/2, screen.HEIGHT/2, 0);
    }

    public void render(SpriteBatch batch) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        world.getBackground().render(batch);
        if(world.game.state.state == GameState.State.PLAY || world.game.state.state == GameState.State.PAUSE) {
            world.getHero().render(batch);
            for (Asteroid asteroid : world.getAsteroids())
                asteroid.render(batch);
            world.game.state.showState(batch);
            debugRenderer.render(world.getPhysics(), camera.combined);
        }
        else if(world.game.state.state == GameState.State.START)
            world.game.state.showStart(batch);
        else if(world.game.state.state == GameState.State.GAME_OVER)
            world.game.state.showGameOver(batch);
        //debug(1000);
    }

    public void debug(float time) {
        long currentTime = System.currentTimeMillis();
        long dTime = currentTime - lastTime;
        lastTime = currentTime;
        counterTime += dTime;
        if(counterTime >= time) {
            counterTime = 0;
            //System.out.println(world.getHero().getBody().getLinearVelocity());
            System.out.println("FPS: " + Gdx.graphics.getFramesPerSecond());
        }
    }

    public void dispose(){
        debugRenderer.dispose();
    }
}
