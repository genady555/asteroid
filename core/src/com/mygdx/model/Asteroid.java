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

    private float hp;
    private float scale;
    private int shield;
    private int level;
    private CircleShape circle;

    public Asteroid(int level, World world) {
        super(world, texture);
        if(RADIUS == 0) RADIUS = 0.8f*sprite.getWidth()/2f;
        this.level = level;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.allowSleep = true;
        def.fixedRotation = false;
        def.linearDamping = 0;
        body = world.createBody(def);
        circle = new CircleShape();
        setActive(false);
        //create();
    }

    public void createFixture(float radius) {
        circle.setRadius(radius);
        fixture = body.createFixture(circle, DENSITY);
    }


    public void create() {
        setActive(true);
        scale = MIN_SCALE + (float)Math.random() * 2f;
        float x = GameScreen.WIDTH + (float)Math.random() * (GameScreen.WIDTH * level);
        float y = (float)Math.random() * GameScreen.HEIGHT;
        float speed = SPEED + (float)Math.random() * SPEED*level;
        if(Math.random() > 0.9)
            speed = SPEED * level * 3f;
        sprite.setScale(scale);
        createFixture(scale*RADIUS);
        setPosition(x, y);
        body.setLinearVelocity(-speed, 0);
        int sign;
        if(Math.random() > 0.5) sign = 1;
        else sign = -1;
        body.setAngularVelocity(speed*sign);
        hp = 100f * scale;
        shield = SHIELD * level;
        if(shield > 90) shield = 90;
        //System.out.println("Масса астеройда: " + body.getMass());
        //System.out.println("Размер: " + getSize());

    }

    public boolean destroy() {
        if(!active) return false;
        count--;
        super.destroy();
        System.out.println("Астеройдов: " + count);
        return count == 0;
    }

    public boolean damage(float value) {
        hp = hp - value + shield*value/100f;
        System.out.println("Астероид hp: " + hp);
        if((int)hp <= 0) return destroy();
        return false;
    }

    public float getDamage() {

        return 0;//(float)DAMAGE *scale;
    }

    public float getSize() {
        return fixture.getShape().getRadius();
    }

    public boolean update() {
        if(!active) return false;
        if (getX() < - getSize() || getY() < -getSize() || getY() > GameScreen.HEIGHT + getSize()) {
            body.destroyFixture(fixture);
            create();
            return true;
        }
        return false;
    }
    
    
}
