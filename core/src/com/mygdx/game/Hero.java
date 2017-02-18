/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import javax.sound.midi.Soundbank;


/**
 *
 * @author Rrr
 */
public class Hero extends Sprite{

    private float maxSpeed;
    private float drive, rotate;
    private float rateFire;
    private long timeFire, pauseFire;
    private float hp = 100f;
    private int shield = 100;
    
    public Hero(String filename) {
        super(filename);
        drive = 200f;
        resist = 50f;
        rotate = drive/50;
        maxSpeed = drive*5;
        rateFire = 3;
        pauseFire = (long)(1000f / rateFire);
        timeFire = System.currentTimeMillis();
    }

    public void restart() {
        x = 100;
        y = 100;
        speed = 0;
        accel = 0;
        angleMove = 0;
        hp = 100;
    }
    
    public void update(){
        input();
        if (speed > maxSpeed){
            speed = maxSpeed;
            accel = 0;
        }
        move();
        if (x < -width)
            x = GdxGame.WIDTH;
        if (x > GdxGame.WIDTH)
            x = -width;
        if (y < -height)
            y = GdxGame.HEIGHT;
        if (y > GdxGame.HEIGHT)
            y = -height;
    }

    private void input() {
        accel = 0;
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
            fire();
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            accel = drive;
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            accel = -drive;
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            angleMove -= rotate;
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            angleMove += rotate;
    }
    
    public void fire() {
        long time = System.currentTimeMillis();
        long dt = time - timeFire;
        if(dt >= pauseFire){
            for (Bullet bullet : GdxGame.bullets) {
                if (bullet.isActive()) continue;
                bullet.create(x + 50, y + 10, speed, angleMove);
                break;
            }
            timeFire = time;
        }

    }

    public void showHp() {
        System.out.println("Здоровье: " + (int)hp);
    }

    public boolean damage(float value) {
        System.out.println("Cтолкновение с астеройдом!");
        hp = hp - value / shield;
        showHp();
        return (int)hp <= 0;
    }

    public void render() {
        angleRotate = angleMove;
        super.render();
    }

}
