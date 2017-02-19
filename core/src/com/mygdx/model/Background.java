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
    
    private final int STARS_COUNT = 300;
    private Star[] stars;
    static Texture texture = new Texture("staticback.jpg");;
    
    public Background() {
        super(texture);
        stars = new Star[STARS_COUNT];
        for (int i = 0; i < STARS_COUNT; i++)
            stars[i] = new Star();
    }
    
    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        for (int i = 0; i < STARS_COUNT; i++)
            stars[i].render(batch);
    }
    
    public void update(){
        for (int i = 0; i < STARS_COUNT; i++) {
            stars[i].update();
        }
    }
    
}

//------------------------------------------------------------------------------------
    class Star extends MySprite {

        final static float SPEED = 2f;
        final static Texture texture = new Texture("star12.tga");;

        public Star() {
            super(texture);
            speed = (float)Math.random() * SPEED;
            setScale(speed/SPEED);
            angleMove = 180;
            setX((float)Math.random() * GameScreen.WIDTH);
            setY((float)Math.random() * GameScreen.HEIGHT);
        }
        
        public void update(){
            move();
            if (getX() < 0) {
                setX(GameScreen.WIDTH);
                setY((float)Math.random() * GameScreen.HEIGHT);
            } else if(getX() > GameScreen.WIDTH){
                setX(0);
                setY((float)Math.random() * GameScreen.HEIGHT);
            }
        }
        
    }
//----------------------------------------------------------------------------------------
