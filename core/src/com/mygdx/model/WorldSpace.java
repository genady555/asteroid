package com.mygdx.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GdxGame;

/**
 * Created by Rrr on 15.02.2017.
 */
public class WorldSpace {

    final static public int WIDTH = 128; //размер в метрах
    final static public int HEIGHT = 72;

    final int ASTEROIDS = 10;
    int level;

    World  physics;
    GdxGame game;
    Background background;
    Hero hero;
    Asteroid[] asteroids;


    public WorldSpace(GdxGame game) {
        this.game = game;
        MySprite.setBatch(game.getBatch());
        physics = new World(new Vector2(0, 0), false);
        background = new Background();
        hero = new Hero(physics);
    }

    public void start(int level) {
        this.level = level;
        hero.start();
        System.out.println("Уровень: " + level);
        Asteroid.count = 0;
        asteroids = new Asteroid[ASTEROIDS + level*3];
        for (int i = 0; i < asteroids.length; i++)
            asteroids[i] = new Asteroid(level, physics);
    }

    public void levelUp() {
        level++;
        start(level);
    }

    public void update(float delta) {
        background.update();
        input();
        hero.update();
        for (Asteroid asteroid : asteroids) asteroid.update();
        physics.step(1f/60f, 1, 1);
        check();
    }

    void input() {
        if(game.getInput().isAccelerate())
            hero.accelerate();
        if(game.getInput().isBrake())
            hero.brake();
        if(game.getInput().isLeft())
            hero.turnLeft();
        if(game.getInput().isRight())
            hero.turnRight();
        if(game.getInput().isFire())
            hero.fire();
    }

    public void check(){
        for (Bullet bullet : getHero().getBullets()) {
            if (!bullet.isActive())
                continue;
            for (Asteroid asteroid : getAsteroids()) {
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
        for (Asteroid asteroid : getAsteroids()) {
            if(!asteroid.isActive())
                continue;
            if(getHero().getRectangle().overlaps(asteroid.getRectangle())) {
                if(getHero().damage(asteroid.getDamage())){
                    System.out.println("Game over!");
                    for (Asteroid asteroid1 : asteroids) asteroid1.destroy();
                    start(1);
                    //state = STATE_GAMEOVER;
                }
                asteroid.destroy();
            }
        }
    }

    public Background getBackground() { return background; }

    public Hero getHero() { return hero; }

    public Asteroid[] getAsteroids(){ return asteroids; }

    public World getPhysics() { return physics; }


}
