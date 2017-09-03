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
import com.mygdx.game.InputController;
import com.mygdx.view.GameScreen;
import org.omg.CORBA.DATA_CONVERSION;
import sun.nio.cs.ext.MacThai;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;

/**
 *
 * @author Rrr
 */
public class Hero extends Subject {

    final static Texture texture = new Texture("ship80x60.tga");
    final int BULLETS_COUNT = 100;
    final float DENSITY = 300f;
    final float DRIVE = 1200;
    final int SHIELD = 100;
    final float ROTATE = 5;
    final float WIDTH = 1f;
    final float HEIGHT = 0.7f;
    final public WorldSpace world;

    ArrayList<Bullet> bullets;
    BulletDef bulletDef;
    private int bulletsMax = BULLETS_COUNT;
    private float maxSpeed;
    private float drive = DRIVE;
    private float rotate = ROTATE;
    private long timeFire, pauseFire;
    private float hp;
    private int shield = SHIELD;
    private InputController input;
    private MySprite hpBar;

    private int gunCount = 1;


//--------------------------------------------------------------------------------

    public Hero(WorldSpace world) {
        super(world.getPhysics(), texture);
        this.world = world;
        hpBar = new MySprite(new Texture("hp.png"));
        input = new InputController();
        Gdx.input.setInputProcessor(input);
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(WIDTH/2, HEIGHT/2);
        createBody(poly, BodyDef.BodyType.DynamicBody, DENSITY);
        poly.dispose();
        body.setLinearDamping(0.2f);
        body.setAngularDamping(2.5f);
        maxSpeed = drive/100;
        timeFire = System.currentTimeMillis();
        bullets = new ArrayList<>();
        bulletDef = new BulletDef(BulletDef.STANDARD);
        for (int i = 0; i < bulletsMax; i++)
            bullets.add(new Bullet(this));
    }

    public Body getBody() { return  body; }

    public void start() {
        pauseFire = (long)(1000f / bulletDef.rate);
        setSpeed(0, 0);
        setPosition(1, GameScreen.HEIGHT/2, 0);
        hp = 100f;
        for (Bullet bullet : bullets) bullet.destroy();
    }

    public ArrayList<Bullet> getBullets() { return bullets; }
    
    public void update(){
        if(delete) {
            delete = false;
            for(Asteroid asteroid : world.getAsteroids())
                asteroid.destroy();
            world.start();
        }
        input();
        float x = getX();
        float y = getY();
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
        if(flag) setPosition(x, y);
        for (Bullet bullet : bullets)
            bullet.update();
    }

    void input() {
        if(input.isAccelerate())
            accelerate();
        if(input.isBrake())
            brake();
        if(input.isLeft())
            turnLeft();
        if(input.isRight())
            turnRight();
        if(input.isFire())
            fire();
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
        body.applyAngularImpulse(rotate, true);
    }

    public void turnRight() {

        //body.setTransform(body.getPosition().x, body.getPosition().y, body.getAngle() - rotate);
        body.applyAngularImpulse(-rotate, true);
    }

    public void fire() {
        long time = System.currentTimeMillis();
        long dt = time - timeFire;
        if(dt >= pauseFire) {
            if(gunCount == 1)
                for (Bullet bullet : bullets) {
                    if (bullet.isActive()) continue;
                    bullet.create(0);
                    break;
                }
            else if(gunCount == 2) {
                for (Bullet bullet : bullets) {
                    if (bullet.isActive()) continue;
                    bullet.create(-0.1f);
                    break;
                }
                for (Bullet bullet : bullets) {
                    if (bullet.isActive()) continue;
                    bullet.create(0.1f);
                    break;
                }
            }
            else if(gunCount == 3) {
                for (Bullet bullet : bullets) {
                    if (bullet.isActive()) continue;
                    bullet.create(-0.1f);
                    break;
                }
                for (Bullet bullet : bullets) {
                    if (bullet.isActive()) continue;
                    bullet.create(0);
                    break;
                }
                for (Bullet bullet : bullets) {
                    if (bullet.isActive()) continue;
                    bullet.create(0.1f);
                    break;
                }
            }
            else if (gunCount >= 5) {
                float delta = (float)Math.PI*2/gunCount;
                for(float d=0;d<(float)Math.PI*2;d+=delta)
                    for (Bullet bullet : bullets) {
                        if (bullet.isActive()) continue;
                        bullet.create(d);
                        break;
                    }
            }
            timeFire = time;
        }

    }

    public void damage(Asteroid a) {
        //float d = value/shield;
        //System.out.println("астеройд damage: " + (int)d);
        //hp = hp - value + shield*value/100f;
        Vector2 va = a.getSpeed();
        Vector2 vh = getSpeed();
        Vector2 v = new Vector2(vh.x - va.x, vh.y - va.y);
        float speed = v.len();
        System.out.println("Скорость столкновения: " + speed);
        float d = a.body.getMass()*speed/shield;
        System.out.println("Повреждение: " + d);
        hp = hp - d;
        if((int)hp <= 0) {
            System.out.println("Меня убили!");
            delete = true;
        }
    }

    //public float getDamage() {
    //    return damage;
    //}

    public void render(SpriteBatch batch) {
        super.render(batch);
        hpBar.setScale(hp/100, 1f);
        hpBar.setCenter(getX() + (float)Math.cos(getTurn() + Math.PI/2)*0.35f,
                getY() + (float)Math.sin(getTurn() + Math.PI/2)*0.35f);
        hpBar.setRotation(sprite.getRotation());
        hpBar.render(batch);
        for (Bullet bullet : bullets) bullet.render(batch);
    }

}
