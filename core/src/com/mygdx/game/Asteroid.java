/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author Rrr
 */
public class  Asteroid extends Sprite {

    final static float SHIELD = 50;
    final static int DAMAGE = 10;
    final static float ACCEL = 5f;
    final static float SPEED = 100f;
    final static float SIZE = 0.25f;
    final static Texture texture = new Texture("asteroid60.tga");;

    static int count;

    private float hp = 100;
    private float shield;

    public Asteroid() {
        super(texture);
        angleMove = 180;
        count++;
        shield = GdxGame.level * SHIELD;
        create();
    }

    public void create() {
        size = SIZE + (float)Math.random() * SIZE * GdxGame.level;
        hp = 100 * size;
        rectangle.width = width*size;
        rectangle.height = height*size;
        accel = ACCEL + (float)Math.random() * ACCEL * GdxGame.level;
        speed = SPEED + (float)Math.random() * SPEED * GdxGame.level;
        if(Math.random() > 0.9)
            speed = SPEED * GdxGame.level * 2f;
        x = GdxGame.WIDTH + (float)Math.random() * GdxGame.WIDTH;
        y = (float)Math.random() * (GdxGame.HEIGHT - height);
    }

    public boolean destroy() {
        active = false;
        count--;
        System.out.println("Астеройдов: " + count);
        return count == 0;
    }

    public boolean damage(float value) {
        hp = hp - value / shield;
        System.out.println("Астероид hp: " + hp);
        if((int)hp <= 0) return destroy();
        return false;
    }

    public Rectangle getRectangle(){
        return rectangle;
    }

    public float getDamage() {
        return DAMAGE * speed * size;
    }

    public void update(){
        angleRotate += speed/50;
        move();
        if (x < -width)
            create();
    }
    
    
}
