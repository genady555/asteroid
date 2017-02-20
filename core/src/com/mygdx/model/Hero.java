/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.GdxGame;
import com.mygdx.view.GameScreen;

import javax.sound.midi.Soundbank;


/**
 *
 * @author Rrr
 */
public class Hero extends Subject {

    final static Texture texture = new Texture("ship80x60.tga");;
    final int BULLETS_COUNT = 100;
    final float DENSITY = 100f;

    Bullet[] bullets = new Bullet[BULLETS_COUNT];
    private float maxSpeed;
    private float drive, rotate;
    private float rateFire;
    private long timeFire, pauseFire;
    private float hp;
    private int shield = 10;


//--------------------------------------------------------------------------------

    public Hero(World world) {
        super(world, texture);
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(0.8f*(sprite.getWidth()/2), 0.8f*(sprite.getHeight()/2));
        createBody(poly, BodyDef.BodyType.DynamicBody, DENSITY, 0, 0);
        //System.out.println(fixture.getShape());
        body.setLinearDamping(1f);
        body.setAngularDamping(2f);
        drive = 500f;
        rotate = 2.5f * (float)(Math.PI/180); //в радианах
        maxSpeed = drive/5;
        rateFire = 5;
        pauseFire = (long)(1000f / rateFire);
        timeFire = System.currentTimeMillis();
        for (int i = 0; i < bullets.length; i++)
            bullets[i] = new Bullet();
    }

    public Body getBody() { return  body; }

    public void start() {
        setSpeed(0, 0);
        setPosition(1, 6, 0);
        hp = 100f;
        for (Bullet bullet : bullets) bullet.destroy();
    }

    public Bullet[] getBullets() { return bullets; }
    
    public void update(){
        float x = body.getPosition().x;
        float y = body.getPosition().y;
        boolean flag = false;
        if (x < -getWidth()) {
            flag = true;
            x = GameScreen.WIDTH;
        }
        if (x > GameScreen.WIDTH) {
            x = -getWidth();
            flag = true;
        }
        if (y < -getHeight()) {
            y = GameScreen.HEIGHT;
            flag = true;
        }
        if (y > GameScreen.HEIGHT) {
            y = -getHeight();
            flag = true;
        }
        if(flag) body.setTransform(x, y, body.getAngle());
        for (Bullet bullet : bullets)
            bullet.update();
    }

    public void accelerate() {
        float ax = drive * (float)Math.cos(body.getAngle());
        float ay = drive * (float)Math.sin(body.getAngle());
        body.applyForceToCenter(ax, ay, true);
    }

    public void brake() {
        float ax = drive * (float)Math.cos(body.getAngle() - Math.PI);
        float ay = drive * (float)Math.sin(body.getAngle() - Math.PI);
        //body.applyLinearImpulse(ax, ay, body.getPosition().x, body.getPosition().y, true);
        body.applyForceToCenter(ax, ay, true);
    }

    public void turnLeft() {
        //body.setTransform(body.getPosition().x, body.getPosition().y, body.getAngle() + rotate);
        body.applyAngularImpulse(10f, true);
    }

    public void turnRight() {

        //body.setTransform(body.getPosition().x, body.getPosition().y, body.getAngle() - rotate);
        body.applyAngularImpulse(-10f, true);
    }

    public void fire() {
        long time = System.currentTimeMillis();
        long dt = time - timeFire;
        if(dt >= pauseFire){
            for (Bullet bullet : bullets) {
                if (bullet.isActive()) continue;
                bullet.create(body.getPosition().x, body.getPosition().y, getSpeed(), getTurn());
                break;
            }
            timeFire = time;
        }

    }

    public float getWidth() { return sprite.getWidth(); }

    public float getHeight() { return sprite.getHeight(); }

    public void showHp() {
        System.out.println("Здоровье: " + (int)hp);
    }

    public boolean damage(float value) {
        System.out.println("астеройд damage: " + value);
        hp = hp - value + shield*value/100f;
        showHp();
        return (int)hp <= 0;
    }

    public void render(SpriteBatch batch) {
        super.render(batch);
        for (Bullet bullet : bullets) bullet.render(batch);
    }

}
