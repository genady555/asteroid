/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;

import java.awt.*;

/**
 *
 * @author Rrr
 */
public class  Asteroid extends MySprite {

    final static int SHIELD = 5;
    final static int DAMAGE = 25;
    final static float ACCEL = 5f;
    final static float SPEED = 15f;
    final static float SIZE = 0.25f;
    final static Texture texture = new Texture("asteroid60.tga");;

    static int count;

    private float hp;
    private int shield;
    private int level;
    private Body body;
    private Fixture fixture;

    public Asteroid(int level, World world) {
        super(texture);
        this.level = level;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.active = true;
        def.allowSleep = false;
        def.fixedRotation = false;
        def.linearDamping = 0.5f;
        body = world.createBody(def);
        float scale = 0.25f + (float)Math.random() * 2;
        float x = (float)Math.random() * WorldSpace.WIDTH;
        float y = (float)Math.random() * WorldSpace.HEIGHT;
        setCenter(x, y);
        setScale(scale);
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(scale*getWidth()/2, scale*getHeight()/2);
        fixture = body.createFixture(poly, 1);
        body.setTransform(x, y, 0);
        poly.dispose();
        angleMove = 180;
        count++;
        shield = SHIELD * level;
        if(shield > 90) shield = 90;
        //create();
    }

    public void create() {
        //float scale = 0.25f + (float)Math.random() * 2;
        //setScale(scale);
        //hp = 100f * scale;
        //accel = ACCEL + (float)Math.random() * ACCEL * level;
        //speed = SPEED + (float)Math.random() * SPEED * level / 5;
        //if(Math.random() > 0.9)
        //    speed = SPEED * level * 2f;
        //float angle = (float)Math.random() * 360;


        //poly.setAsBox(getWidth()/2, getHeight()/2);

    }

    public boolean destroy() {
        if(!active) return false;
        active = false;
        count--;
        body.destroyFixture(fixture);
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
        return (float)DAMAGE * getScaleX();
    }

    public void update() {
        move();
        if (getX() < - getWidth())
            create();
    }
    
    
}
