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

/**
 *
 * @author Rrr
 */
public class  Asteroid extends Subject {

    final static int SHIELD = 10;

    final static int DAMAGE = 25;
    final static float DENSITY = 1000;
    final static float ACCEL = 5f;
    final static float SPEED = 15f;
    final static float MIN_SCALE = 0.25f;
    final static Texture texture = new Texture("asteroid60.tga");;

    static int count;
    static float RADIUS;

    private float hp;
    private int shield;
    private int level;

    public Asteroid(int level, World world) {
        super(world, texture);
        if(RADIUS == 0) RADIUS = sprite.getWidth()/2f;
        this.level = level;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.active = true;
        def.allowSleep = true;
        def.fixedRotation = false;
        def.linearDamping = 0;
        body = world.createBody(def);
        CircleShape circle = new CircleShape();
        fixture = body.createFixture(circle, DENSITY);
        circle.dispose();
        angleMove = 180;
        count++;
        create();
    }

    public void setScale(float scale) {
        fixture.getShape().setRadius(RADIUS*scale);
        sprite.setScale(scale);
    }


    public void create() {
        float scale = MIN_SCALE + (float)Math.random() * 2;
        float x = (float)Math.random() * GameScreen.WIDTH;
        float y = (float)Math.random() * GameScreen.HEIGHT;
        setScale(scale);
        setPosition(x, y);
        hp = 100f * scale;
        shield = SHIELD * level;
        if(shield > 90) shield = 90;
        //if(Math.random() > 0.9)
        //    speed = SPEED * level * 2f;

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

    public void update() {
        if (getX() < - fixture.getShape().getRadius())
            destroy();
    }
    
    
}
