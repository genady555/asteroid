/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GdxGame;
import com.mygdx.view.GameScreen;

/**
 *
 * @author Rrr
 */
public class Background extends MySprite {
    
    private final float STARS_DENSITY = 3;
    private Star[] stars;
    static Texture texture = new Texture("bg.png");;
    private int starsCount;
    
    public Background() {
        super(texture);
    }

    public void resize() {
        starsCount = (int)(STARS_DENSITY*GameScreen.WIDTH*GameScreen.HEIGHT);
        stars = new Star[starsCount];
        for (int i = 0; i < starsCount; i++)
            stars[i] = new Star();
    }
    
    @Override
    public void render(SpriteBatch batch) {
        //super.render(batch);
        for (int i = 0; i < starsCount; i++)
            stars[i].render(batch);
    }
    
    public void update(){
        for (int i = 0; i < starsCount; i++) {
            stars[i].update();
        }
    }
    
}

//------------------------------------------------------------------------------------
    class Star extends MySprite {

        final static float SPEED = 3f;
        final static Texture texture = new Texture("star12.tga");;

        public Star() {
            super(texture);
            angleMove = 180;
            setX((float)Math.random() * GameScreen.WIDTH);
            create();
        }

        void create() {
            speed = (float)Math.random() * SPEED;
            setScale(speed/SPEED);
            setY((float)Math.random() * GameScreen.HEIGHT);
        }
        
        public void update(){
            move();
            if (getX() < 0) {
                setX(GameScreen.WIDTH);
                create();
            }
        }
        
    }
//----------------------------------------------------------------------------------------
