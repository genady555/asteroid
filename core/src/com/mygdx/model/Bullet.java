/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GdxGame;
import com.mygdx.view.GameScreen;

/**
 *
 * @author Rrr
 */

public class Bullet extends Subject {

    final static float SPEED = 5f;
    final static Texture texture = new Texture("bullet20.png");

    static int count;

    private Hero hero;
    float speed = SPEED;

    public Bullet(Hero hero) {
        super(hero.world, texture);
        this.hero = hero;
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(0.8f*getWidth()/2, 0.7f*getHeight()/2);
        createBody(poly, BodyDef.BodyType.KinematicBody, 0);
        poly.dispose();
        body.setBullet(true);
        setActive(false);
    }
    
    public void create(){
        setPosition(hero.getPosition().x + (float)Math.cos(hero.getTurn()) * (hero.getWidth()/2 + getWidth()/2),
                hero.getPosition().y + (float)Math.sin(hero.getTurn()) * (hero.getWidth()/2 + getHeight()/2),
                hero.getTurn());
        setActive(true);
        setTurn(hero.getTurn());
        setSpeed();
        count++;
        System.out.println("Скорость пули: " + getSpeedLenght());
        System.out.println("Угол: " + Math.toDegrees(getTurn()));
    }

    void setSpeed() {
        Vector2 v = new Vector2(hero.getSpeed());
        v.setLength(v.len() + 100f);
        System.out.println(v.len());
        body.setLinearVelocity(v);
    }

    public boolean destroy() {
        if(!active) return false;
        super.destroy();
        count--;
        return false;
    }

    public float getDamage(){
        return hero.getDamage();
    }
    
    public void update(){
        if(!active) return;
        if (getX() > GameScreen.WIDTH + getWidth()/2 || getX() < -getWidth()/2 || getY() < -getHeight()/2 || getY() > GameScreen.HEIGHT + getHeight()/2)
            destroy();
    }
}
