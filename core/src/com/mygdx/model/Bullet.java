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
public class Bullet extends MySprite {

    final static float SPEED = 500f;
    final static float DAMAGE = 100f;
    final static Texture texture = new Texture("bullet20.png");

    static int count;
    static float damage = DAMAGE;

    public Bullet() {
        super(texture);
        active = false;
    }
    
    public boolean isActive(){
        return active;
    }
    
    public void create(float x, float y, float speed, float angle){
        setCenter(x, y);
        angleMove = angle;
        setRotation(angleMove);
        active = true;
        this.speed = SPEED + speed;
        count++;
        //System.out.println("Пуля+ " + count);
    }

    public void destroy() {
        if (!active) return;
        active = false;
        count--;
        //System.out.println("Пуля- " + count);
    }

    public float getDamage(){
        return damage;
    }
    
    public void update(){
        if(!active) return;
        move();
        if (getX() > GdxGame.WIDTH || getY() < -getWidth() || getY() < -getHeight() || getY() > GdxGame.HEIGHT)
            destroy();
     }
}
