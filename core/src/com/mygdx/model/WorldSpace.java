package com.mygdx.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GdxGame;
import com.mygdx.view.GameScreen;

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
    Asteroid[] asteroids;


    public WorldSpace(GameScreen screen) {
        this.screen = screen;
        physics = new World(new Vector2(0, 0), true);
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

    public Asteroid[] getAsteroids(){ return asteroids; }

    public World getPhysics() { return physics; }

    public void dispose() {
        physics.dispose();
    }

    public void pause() {
    }

    public void resume() {}

}
