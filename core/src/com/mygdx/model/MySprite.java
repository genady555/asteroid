/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author Rrr
 */
class MySprite extends Sprite{
    
    static protected SpriteBatch batch;

    protected float speed;
    protected boolean active = true;
    protected float angleMove;
    protected float accel;

    public static void setBatch(SpriteBatch batch){
        if(MySprite.batch == null)
            MySprite.batch = batch;
        else System.out.println("SpriteBatch уже был установлен!");
    }

//--------------------------------------------------------------------

    public MySprite() { super(); }

    public MySprite(Texture texture){
        super(texture);
    }
    
    //public MySprite(String filename) {
    //    super();
    //    Texture texture = new Texture(filename);
    //    setTexture(texture);
    //}

    public boolean isActive() { return  active; }

    public Rectangle getRectangle(){
        return getBoundingRectangle();
    }

    public void move() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        float x = getX();
        float y = getY();
        speed = speed + accel * deltaTime;
        x = x + speed * deltaTime * (float)Math.cos(Math.toRadians(angleMove));
        y = y + speed * deltaTime * (float)Math.sin(Math.toRadians(angleMove));
        setX(x);
        setY(y);
    }

    public void render(){
        if(active) draw(batch);
            //batch.draw(texture, x, y, width/2, height/2,
            //        width, height, size, size, angleRotate, 0, 0, width, height, false, false);
    }
       
}
