/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author Rrr
 */
class Sprite {
    
    static protected SpriteBatch batch = new SpriteBatch();;
    static int count;

    protected Texture texture;
    protected Rectangle rectangle;
    protected float x, y, speed, accel, resist, angleMove, angleRotate, size;
    protected int width, height;
    protected boolean active;


    //public static void setBatch(SpriteBatch batch){
    //    Sprite.batch = batch;
    //}

//--------------------------------------------------------------------
    public Sprite() {
        count++;
        active = true;
        size = 1f;
    }

    public Sprite(Texture texture){
        this();
        this.texture = texture;
        width = texture.getWidth();
        height = texture.getHeight();
        rectangle = new Rectangle(x, y, width, height);
    }
    
    public Sprite(String filename) {
        this();
        texture = new Texture(filename);
        width = texture.getWidth();
        height = texture.getHeight();
        rectangle = new Rectangle(x, y, width, height);
    }

    public boolean isActive() { return  active; }

    public Rectangle getRectangle(){
        return rectangle;
    }

    public void move() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        speed = speed + (accel  - resist) * deltaTime;
        if (speed < 0) speed = 0;
        x = x + speed * deltaTime * (float)Math.cos(Math.toRadians(angleMove));
        y = y + speed * deltaTime * (float)Math.sin(Math.toRadians(angleMove));
        rectangle.x = x;
        rectangle.y = y;
    }

    public void renderSimple(){
        if(active)
            batch.draw(texture, x, y);
    }

    public void render(){
        if(active)
            batch.draw(texture, x, y, width/2, height/2, width, height, size, size, angleRotate, 0, 0, width, height, false, false);
    }
       
}
