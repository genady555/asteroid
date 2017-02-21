package com.mygdx.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GdxGame;
import com.mygdx.view.GameScreen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Rrr on 15.02.2017.
 */
public class WorldSpace {

    final int ASTEROIDS = 10;
    int level;

    World  physics;
    GameScreen screen;
    Background background;
    Hero hero;
    ArrayList<Asteroid> asteroids;


    public WorldSpace(GameScreen screen) {
        this.screen = screen;
        physics = new World(new Vector2(0, 0), true);
        background = new Background();
        hero = new Hero(physics);
        asteroids = new ArrayList<Asteroid>();
    }

    public void start(int level) {
        this.level = level;
        hero.start();
        System.out.println("Уровень: " + level);
        Asteroid.count = ASTEROIDS;
        for (int i = 0; i < Asteroid.count; i++)
            asteroids.add(new Asteroid(level, physics));
        asteroids.get(0).create();
    }

    public void levelUp() {
        level++;
        start(level);
    }

    public void update(float delta) {
        background.update();
        input();
        hero.update();
        for (int i = 0; i < asteroids.size(); i++)
            if(asteroids.get(i).update())
                for (int j = 0; j < asteroids.size(); j++)
                    if(asteroids.get(j).isActive()) continue;
                    else {
                        asteroids.get(j).create();
                        break;
                    }

        physics.step(1f/60f, 4, 4);
        //check();
    }

    void input() {
        if(screen.getInput().isAccelerate())
            hero.accelerate();
        if(screen.getInput().isBrake())
            hero.brake();
        if(screen.getInput().isLeft())
            hero.turnLeft();
        if(screen.getInput().isRight())
            hero.turnRight();
        if(screen.getInput().isFire())
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

    public ArrayList<Asteroid> getAsteroids(){ return asteroids; }

    public World getPhysics() { return physics; }

    public void dispose() {
        physics.dispose();
    }

    public void pause() {
    }

    public void resume() {}

}
