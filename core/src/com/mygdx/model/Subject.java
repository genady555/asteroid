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
    protected Body body;;
    protected World world;
    protected Fixture fixture;
    protected float scale = 1;
    protected float angleMove;

    public Subject(World world) {
        this.world = world;
        count++;
    }

    public Subject(World world, Texture texture) {
        this(world);
        sprite = new MySprite(texture);
    }

    public Subject(World world, String filename) {
        this(world);
        sprite = new MySprite(new Texture(filename));
    }


    protected void createBody(Shape shape, BodyDef.BodyType type, float density, float x, float y) {
        BodyDef bDef = new BodyDef();
        bDef.type = type;
        bDef.active = active;
        bDef.fixedRotation = false;
        bDef.position.set(x, y);
        body = world.createBody(bDef);
        fixture = body.createFixture(shape, density);
        fixture.setUserData(sprite);
        sprite.setCenter(x, y);
    }

    public void setPosition(float x, float y) {
        body.setTransform(x, y, body.getAngle());
    }

    public void setPosition(float x, float y, float angle) {
        body.setTransform(x, y, (float)Math.toRadians(angle));
    }

    public void setSpeed(float vx, float vy) {
        body.setLinearVelocity(vx, vy);
    }

    public float getSpeed() {
        return body.getLinearVelocity().len();
    }

    public void setTurn(float angle) {
        body.setTransform(body.getPosition(), (float)Math.toRadians(angle));
    }

    public float getX() {
        return body.getPosition().x;
    }

    public float getY() {
        return body.getPosition().y;
    }

    public float getTurn(){
        return (float)Math.toDegrees(body.getAngle());
    }

    public void render(SpriteBatch batch) {
        if(!active) return;
        sprite.setCenter(body.getPosition().x, body.getPosition().y);
        sprite.setRotation(getTurn());
        sprite.draw(batch);
    }

    public boolean destroy() {
        active = false;
        count--;
        body.setActive(false);
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
