package com.mygdx.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.GameState;
import com.mygdx.game.GdxGame;
import com.mygdx.game.InputController;
import com.mygdx.view.GameScreen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Rrr on 15.02.2017.
 */
public class WorldSpace {

    final int ASTEROIDS_LEVEL = 5;

    World  physics;
    public GdxGame game;
    Background background;
    Hero hero;
    ArrayList<Asteroid> asteroids;

    public WorldSpace(GdxGame game) {
        this.game = game;
        physics = new World(new Vector2(0, 0), false);
        physics.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                String clas1 = contact.getFixtureA().getUserData().getClass().toString();
                String clas2 = contact.getFixtureB().getUserData().getClass().toString();
                Asteroid a = null;
                Bullet b = null;
                Hero h = null;
                if(clas1.indexOf("Bullet") > 0 && clas2.indexOf("Asteroid") > 0) {
                    //System.out.println("bullet + asteroid");
                    b = (Bullet) contact.getFixtureA().getUserData();
                    b.delete = true;
                    a = (Asteroid)contact.getFixtureB().getUserData();
                    a.damage(b.getDamage());
                }
                else if(clas1.indexOf("Hero") > 0 && clas2.indexOf("Asteroid") > 0) {
                    h = (Hero) contact.getFixtureA().getUserData();
                    a = (Asteroid) contact.getFixtureB().getUserData();
                    h.damage(a);
                }
                    //System.out.println("asteroid + hero");
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        });
        background = new Background();
        hero = new Hero(this);
        asteroids = new ArrayList<Asteroid>();
    }

    public void start() {
        game.state.state= GameState.State.START;
        hero.start();
        //System.out.println("Уровень: " + game.state.level);
        Asteroid.count = ASTEROIDS_LEVEL * game.state.level;
        for (int i = 0; i < ASTEROIDS_LEVEL; i++)
            asteroids.get(i).create();
    }

    public void levelUp() {
        game.state.level++;
        for (int i = 0; i < ASTEROIDS_LEVEL; i++)
            asteroids.add(new Asteroid(this));
        start();
    }

    public void update(float delta) {
        background.update();
        if(game.state.state == GameState.State.PLAY) {
            physics.step(delta, 4, 4);
            hero.update();
            for (int i = 0; i < ASTEROIDS_LEVEL; i++)
                if (asteroids.get(i).update()) levelUp();
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
