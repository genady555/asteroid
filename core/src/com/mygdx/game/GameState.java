package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.model.Asteroid;

/**
 * Created by genady on 09.09.2017.
 */
public class GameState {

    public enum State {PLAY, START, PAUSE, GAME_OVER}

    public int level = 0;
    public int score = 0;
    public int lives = 1;
    public State state = State.START;
    private BitmapFont font;
    private OrthographicCamera camera_origin;

    public  GameState() {
        font = GdxGame.createFont("fonts/input.ttf", 30, Color.GOLD);
        camera_origin = new OrthographicCamera();
        resize();
    }

    public void resize() {
        camera_origin.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera_origin.position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0);
        camera_origin.update();
    }

    public void showState(SpriteBatch batch){
        batch.setProjectionMatrix(camera_origin.combined);
        font.draw(batch, "Уровень: " + level + " Астеройдов: " + Asteroid.count +  " Жизней: " + lives + " Очки: " + score , 0, 50);
        batch.end();
    }

    public void showStart(SpriteBatch batch){
        batch.setProjectionMatrix(camera_origin.combined);
        font.draw(batch, "Уровень " + level, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        batch.end();
    }

    public void showGameOver(SpriteBatch batch){
        batch.setProjectionMatrix(camera_origin.combined);
        font.draw(batch, "Game Over!",Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        batch.end();
    }


}
