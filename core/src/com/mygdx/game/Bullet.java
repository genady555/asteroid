/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

/**
 *
 * @author Rrr
 */
public class Bullet extends Sprite {

    final static float SPEED = 500f;
    final static int DAMAGE = 10;

    static int count;
    static int damage = DAMAGE;

    public Bullet(Texture texture) {
        super(texture);
        active = false;
    }
    
    public boolean isActive(){
        return active;
    }
    
    public void create(float x, float y, float speed, float angle){
        this.x = x;
        this.y = y;
        angleMove = angle;
        angleRotate = angleMove;
        rectangle.x = x;
        rectangle.y = y;
        active = true;
        this.speed = SPEED + speed;
        count++;
        //System.out.println("Пуля+ " + count);
    }

    public void destroy() {
        active = false;
        count--;
        //System.out.println("Пуля- " + count);
    }

    public float getDamage(){
        return DAMAGE * damage;
    }
    
    public void update(){
            move();
            if (x > GdxGame.WIDTH || x < -width || y < -height || y > GdxGame.HEIGHT)
                destroy();
     }
         
    
    
}
