package com.mygdx.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Rrr on 17.02.2017.
 */
public class Subject {

    static public int count;

    protected MySprite sprite;
    protected boolean active = true;
    protected Body body;
    protected World world;
    protected Fixture fixture;
    protected boolean delete;

    public Subject(World world) {
        this.world = world;
    }

    public Subject(World world, Texture texture) {
        this(world);
        sprite = new MySprite(texture);
    }

    public Subject(World world, String filename) {
        this(world);
        sprite = new MySprite(new Texture(filename));
    }

    public Subject(World world, Texture texture, Shape shape, BodyDef.BodyType type, float density) {
        this(world, texture);
        createBody(shape, type, density);
    }


    protected void createBody(Shape shape, BodyDef.BodyType type, float density) {
        BodyDef bDef = new BodyDef();
        bDef.type = type;
        bDef.active = active;
        bDef.fixedRotation = false;
        body = world.createBody(bDef);
        fixture = body.createFixture(shape, density);
        fixture.setUserData(this);
        count++;
    }

    public Vector2 getPosition() { return body.getPosition(); }

    public void setPosition(Vector2 position, float angle) {
        body.setTransform(position.x, position.y, angle);
    }

    public void setPosition(float x, float y) {
        body.setTransform(x, y, body.getAngle());
    }

    public void setPosition(float x, float y, float angle) {
        body.setTransform(x, y, angle);
    }

    public void setSpeed(float vx, float vy) {
        body.setLinearVelocity(vx, vy);
    }

    public void setSpeed(Vector2 speed) { body.setLinearVelocity(speed); }

    public Vector2 getSpeed() { return body.getLinearVelocity(); }

    public float getWidth() { return sprite.getWidth(); }

    public float getHeight() { return sprite.getHeight(); }

    public void setTurn(float angle) {
        body.setTransform(getPosition(), angle);
    }

    public float getX() {
        return body.getPosition().x;
    }

    public float getY() {
        return body.getPosition().y;
    }

    public float getTurn(){
        return body.getAngle();
    }

    public void render(SpriteBatch batch) {
        if(!active) return;
        sprite.setCenter(getX(), getY());
        sprite.setRotation((float)Math.toDegrees(getTurn()));
        sprite.draw(batch);
    }

    public boolean destroy() {
        setActive(false);
        count--;
        delete = false;
        world.destroyBody(body);
        return count == 0;
    }

    public void setActive(boolean active) {
        this.active = active;
        body.setActive(active);
    }

    public boolean isActive() { return active; }

    public Rectangle getRectangle() {
        return sprite.getRectangle();
    }



}
