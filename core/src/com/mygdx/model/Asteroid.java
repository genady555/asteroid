/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.model;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.GdxGame;

/**
 *
 * @author Rrr
 */
public class  Asteroid extends MySprite {

    final static int SHIELD = 5;
    final static int DAMAGE = 25;
    final static float ACCEL = 5f;
    final static float SPEED = 100f;
    final static float SIZE = 0.25f;
    final static Texture texture = new Texture("asteroid60.tga");;

    static int count;

    private float hp;
    private int shield;
    private int level;

    public Asteroid(int level) {
        super(texture);
        this.level = level;
        angleMove = 180;
        count++;
        shield = SHIELD * level;
        if(shield > 90) shield = 90;
        create();
    }

    public void create() {
        float scale = 0.25f + (float)Math.random() * SIZE * level;
        setScale(scale);
        hp = 100f * scale;
        accel = ACCEL + (float)Math.random() * ACCEL * level;
        speed = SPEED + (float)Math.random() * SPEED * level / 5;
        if(Math.random() > 0.9)
            speed = SPEED * level * 2f;
        setX(GdxGame.WIDTH + (float)Math.random() * GdxGame.WIDTH);
        setY((float)Math.random() * (GdxGame.HEIGHT - getHeight()));
    }

    public boolean destroy() {
        active = false;
        count--;
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

    public void update(){
        rotate(speed/50);
        move();
        if (getX() < - getWidth())
            create();
    }
    
    
}
