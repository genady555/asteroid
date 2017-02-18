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

    World  physics;
    //GdxGame game;
    Background background;
    Hero hero;
    Asteroid[] asteroids;


    public WorldSpace(GdxGame game) {
        //this.game = game;
        MySprite.setBatch(game.getBatch());
        physics = new World(new Vector2(0, 0), false);
        background = new Background();
        hero = new Hero(physics);
    }

    public void start(int level) {
        hero.start();
        System.out.println("Уровень: " + level);
        Asteroid.count = 0;
        asteroids = new Asteroid[ASTEROIDS + level*5];
        for (int i = 0; i < asteroids.length; i++)
            asteroids[i] = new Asteroid(level);
    }

    public void update() {
        background.update();
        hero.update();
        for (Asteroid asteroid : asteroids) asteroid.update();
        physics.step(1f/60f, 1, 1);
    }

    public Background getBackground() { return background; }

    public Hero getHero() { return hero; }

    public Asteroid[] getAsteroids(){ return asteroids; }

    public World getPhysics() { return physics; }

int i;

}
