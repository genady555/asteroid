/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.GdxGame;

import javax.sound.midi.Soundbank;


/**
 *
 * @author Rrr
 */
public class Hero extends MySprite{

    final static Texture texture = new Texture("ship80x60.tga");;
    final int BULLETS_COUNT = 100;
    final float DENSITY = 1f;

    Bullet[] bullets = new Bullet[BULLETS_COUNT];

    private float maxSpeed;
    private float drive, rotate;
    private float rateFire;
    private long timeFire, pauseFire;
    private float hp;
    private int shield = 10;
    private Body body;
    private Fixture fixture;


//--------------------------------------------------------------------------------

    public Hero(World world) {
        super(texture);
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.active = true;
        def.allowSleep = false;
        def.fixedRotation = false;
        def.linearDamping = 0.5f;
        body = world.createBody(def);
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(getWidth()/2, getHeight()/2);
        fixture = body.createFixture(poly, DENSITY);
        poly.dispose();
        drive = 100f;
        rotate = 2.5f;
        maxSpeed = drive*5;
        rateFire = 3;
        pauseFire = (long)(1000f / rateFire);
        timeFire = System.currentTimeMillis();
        for (int i = 0; i < bullets.length; i++)
            bullets[i] = new Bullet();
    }

    public Body getBody() { return  body; }

    public float getSpeed() {
        return body.getLinearVelocity().len();
    }

    public void start() {
        body.setLinearVelocity(0, 0);
        body.setTransform(5, 35,0);
        speed = 0;
        accel = 0;
        angleMove = 0;
        hp = 100f;
        for (Bullet bullet : bullets) bullet.destroy();
    }

    public Bullet[] getBullets() { return bullets; }
    
    public void update(){
        float x = body.getPosition().x;
        float y = body.getPosition().y;
        if (x < -getWidth())
            x = WorldSpace.WIDTH;
        if (x > WorldSpace.WIDTH)
            x = -getWidth();
        if (y < -getHeight())
            y = WorldSpace.HEIGHT;
        if (y > WorldSpace.HEIGHT)
            y = -getHeight();
        body.setTransform(x, y, (float)Math.toRadians(angleMove));
        setPosition(x, y);
        for (Bullet bullet : bullets)
            bullet.update();
    }

    public void accelerate() {
        float ax = drive * (float)Math.cos(Math.toRadians(angleMove));
        float ay = drive * (float)Math.sin(Math.toRadians(angleMove));
        body.applyLinearImpulse(ax, ay, body.getPosition().x, body.getPosition().y, false);
    }

    public void brake() {
        float ax = drive * (float)Math.cos(Math.toRadians(angleMove - 180));
        float ay = drive * (float)Math.sin(Math.toRadians(angleMove - 180));
        body.applyLinearImpulse(ax, ay, body.getPosition().x, body.getPosition().y, false);
    }

    public void turnLeft() { angleMove += rotate; }

    public void turnRight() { angleMove -= rotate; }

    public void fire() {
        long time = System.currentTimeMillis();
        long dt = time - timeFire;
        if(dt >= pauseFire){
            for (Bullet bullet : bullets) {
                if (bullet.isActive()) continue;
                bullet.create(body.getPosition().x, body.getPosition().y, body.getLinearVelocity().len(), angleMove);
                break;
            }
            timeFire = time;
        }

    }

    public void showHp() {
        System.out.println("Здоровье: " + (int)hp);
    }

    public boolean damage(float value) {
        System.out.println("астеройд damage: " + value);
        hp = hp - value + shield*value/100f;
        showHp();
        return (int)hp <= 0;
    }

    public void render() {
        setRotation(angleMove);
        setCenter(body.getPosition().x, body.getPosition().y);
        super.render();
        for (Bullet bullet : bullets) bullet.render();
    }

}
