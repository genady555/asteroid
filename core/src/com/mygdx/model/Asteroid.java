/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.view.GameScreen;

import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author Rrr
 */
public class  Asteroid extends Subject {

    final static int SHIELD = 10;

    final static int DAMAGE = 25;
    final static float DENSITY = 1000f;
    final static float SPEED = 2.5f;
    final static float MIN_SCALE = 0.25f;
    final static Texture texture = new Texture("asteroid60.tga");;

    static int count;
    static float RADIUS;
    static WorldSpace world;

    private float hp;
    private float scale;
    private int shield;

    private CircleShape circle;

    public Asteroid(WorldSpace world) {
        super(world.physics, texture);
        if(Asteroid.world == null) Asteroid.world = world;
        if(RADIUS == 0) RADIUS = 0.8f*sprite.getWidth()/2f;
        circle = new CircleShape();
        create();
    }

    public void create() {
        scale = MIN_SCALE + (float)Math.random() * 2f;
        float x = GameScreen.WIDTH + (float)Math.random() * (GameScreen.WIDTH * world.level);
        float y = (float)Math.random() * GameScreen.HEIGHT;
        float speed = SPEED + (float)Math.random() * SPEED*world.level;
        if(Math.random() > 0.9)
            speed = SPEED * world.level * 3f;
        sprite.setScale(scale);
        circle.setRadius(RADIUS*scale);
        createBody(circle, BodyDef.BodyType.DynamicBody, DENSITY);
        setActive(true);
        setPosition(x, y);
        setSpeed(-speed, 0);
        int sign;
        if(Math.random() > 0.5) sign = 1;
        else sign = -1;
        body.setAngularVelocity(speed*sign);
        hp = 100f * scale;
        shield = SHIELD * world.level;
        if(shield > 90) shield = 90;
        //System.out.println("Масса астеройда: " + body.getMass());
        //System.out.println("Размер: " + getSize());

    }

    public boolean destroy() {
        if(!active) return false;
        count--;
        super.destroy();
        System.out.println("Астеройдов осталось: " + count);
        return count == 0;
    }

    public void damage(float value) {
        hp = hp - value + shield*value/100f;
        System.out.println("Астероид hp: " + hp);
        if((int)hp <= 0) delete = true;
    }

    public float getDamage() {

        return DAMAGE *scale;
    }

    public float getSize() {
        return fixture.getShape().getRadius();
    }

    public boolean update() {
        if(!active) return false;
        if(delete) return destroy();
        if (getX() < - getSize() || getY() < -getSize() || getY() > GameScreen.HEIGHT + getSize()) {
            body.destroyFixture(fixture);
            super.destroy();
            create();
        }
        return false;
    }
    
    
}
