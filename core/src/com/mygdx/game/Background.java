/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 *
 * @author Rrr
 */
public class Background extends Sprite {
    
    private final int STARS_COUNT = 200;
    private Star[] stars;
    
    public Background(String fileBackground, String fileStar) {
        super(fileBackground);
        stars = new Star[STARS_COUNT];
        Texture textureStar = new Texture(fileStar);
        for (int i = 0; i < STARS_COUNT; i++)
            stars[i] = new Star(textureStar);
    }
    
    @Override
    public void render() {
        renderSimple();
        for (int i = 0; i < STARS_COUNT; i++)
            stars[i].render();
    }
    
    public void update(){
        for (int i = 0; i < STARS_COUNT; i++) {
            stars[i].update();
        }
    }
    
}

//------------------------------------------------------------------------------------
    class Star extends Sprite {


        public Star(Texture texture) {
            super(texture);
            speed = 0.1f + (float)Math.random() * 5f;

            x = (float)Math.random() * GdxGame.WIDTH;
            y = (float)Math.random() * GdxGame.HEIGHT;
        }
        
        public void update(){
            x -= speed;
            if (x < 0) {
                x = GdxGame.WIDTH;
                y = (float)Math.random() * GdxGame.HEIGHT;
            } else if(x > GdxGame.WIDTH){
                x = 0;
                y = (float)Math.random() * GdxGame.HEIGHT;
            }
        }

        public void render(){
            renderSimple();
        }
        
    }
//----------------------------------------------------------------------------------------
