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
import com.mygdx.view.GameScreen;


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

//--------------------------------------------------------------------

    public MySprite(Texture texture){
        super(texture);
        setSize(getWidth()/GameScreen.UNIT_SIZE, getHeight()/GameScreen.UNIT_SIZE);
        setOriginCenter();
    }

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
        setPosition(x, y);
    }

    public void moveTo(float x, float y) {
        float dx = x - getX();
        float dy = y - getY();
        if(dy == 0) angleMove = 0;
        else angleMove = (float)(Math.toDegrees(Math.atan(dx/dy)));
        move();
    }

    public void render(SpriteBatch batch){
        if(active) draw(batch);
    }

    public void dispose() {
        getTexture().dispose();
    }
       
}
