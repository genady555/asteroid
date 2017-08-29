/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GdxGame;
import com.mygdx.view.GameScreen;

/**
 *
 * @author Rrr
 */

public class Bullet extends Subject {

    final static Texture texture = new Texture("bullet20.png");

    static PolygonShape poly;
    static int count;

    private Hero hero;
    private float speed;
    private float damage;


    public Bullet(Hero hero) {
        super(hero.world, texture);
        this.hero = hero;
        if(poly == null) {
            poly = new PolygonShape();
            poly.setAsBox(0.8f * getWidth() / 2, 0.6f * getHeight() / 2);
        }
        active = false;
    }
    
    public void create(){
        active = true;
        createBody(poly, BodyDef.BodyType.DynamicBody, 0);
        body.setBullet(false);
        damage = hero.bulletDef.damage;
        speed = hero.bulletDef.speed;
        setPosition(hero.getPosition().x + (float)Math.cos(hero.getTurn()) * (hero.getWidth()/2 + getWidth()/2),
                hero.getPosition().y + (float)Math.sin(hero.getTurn()) * (hero.getWidth()/2 + getHeight()/2),
                hero.getTurn());
        setSpeed();
        count++;
    }

    void setSpeed() {
        Vector2 v = new Vector2(speed, 0);
        v.setAngleRad(getTurn());
        v.add(hero.getSpeed());
        body.setLinearVelocity(v);
    }

    public boolean destroy() {
        if(!active) return false;
        super.destroy();
        count--;
        return false;
    }

    public float getDamage(){
        return damage;
    }
    
    public void update(){
        if(!active) return;
        if(delete) {
            destroy();
            return;
        }
        if (getX() > (GameScreen.WIDTH + getWidth()/2) ||
                getX() < -getWidth()/2 || getY() < -getHeight()/2 || getY() > (GameScreen.HEIGHT + getHeight()/2))
            destroy();

    }
}

class BulletDef {

    final static int NONE = 0;
    final static int STANDARD = 1;
    final static int ADVANCED = 2;
    final static int BURSTING = 3;

    static private BulletDef[] def;

    public int type;
    public float damage;
    public float speed;
    public float rate;

    private static void create() {
        if(def == null) {
            def = new BulletDef[4];
            def[0] = new BulletDef();
            def[1] = new BulletDef(STANDARD, 10, 10, 3);
            def[2] = new BulletDef(ADVANCED, 15, 15, 5);
            def[3]= new BulletDef(BURSTING, 200, 7, 2);
        }
    }

    public BulletDef() {
        create();
    }

    private BulletDef(int type, float damage, float speed, float rate) {
        this.type = type;
        this.damage = damage;
        this.speed = speed;
        this.rate = rate;
    }

    public BulletDef(int type) {
        create();
        change(type);
    }

    public void change(int type) {
        this.type = type;
        damage = def[type].damage;
        speed = def[type].speed;
        rate = def[type].rate;
    }
}
