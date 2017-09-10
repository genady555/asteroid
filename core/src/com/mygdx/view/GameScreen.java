package com.mygdx.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.mygdx.game.GameState;
import com.mygdx.game.GdxGame;
import com.mygdx.game.InputController;
import com.mygdx.model.Asteroid;
import com.mygdx.model.WorldSpace;

/**
 * Created by Rrr on 15.02.2017.
 */
public class GameScreen implements Screen{

    final static public int UNIT_SIZE = 60; //размер юнита в пикселях
    final static public float WIDTH_MIN = 10;
    final static public float HEIGHT_MIN = 5;
    final public GdxGame game;

    SpriteBatch batch;
    public static float WIDTH = 20f; //размер экрана в юнитах
    public static float HEIGHT = 10f;

    private WorldSpace world;
    private WorldRenderer renderer;
    static public InputController input;

    public GameScreen(GdxGame game) {
        this.game = game;
        batch = new SpriteBatch();
        input = new InputController(game);
        Gdx.input.setInputProcessor(input);
        Gdx.graphics.setWindowedMode((int)(WIDTH*UNIT_SIZE), (int)(HEIGHT*UNIT_SIZE));
        world = new WorldSpace(game);
        renderer = new WorldRenderer(world);
    }

    private void setSize(int w, int h){
        WIDTH = w/UNIT_SIZE;
        HEIGHT = h/UNIT_SIZE;
        if(WIDTH < WIDTH_MIN) WIDTH = WIDTH_MIN;
        if(HEIGHT < HEIGHT_MIN) HEIGHT = HEIGHT_MIN;
    }

    @Override
    public void show() {
        world.levelUp();
    }

    @Override
    public void render(float delta) {
        world.update(delta);
        renderer.render(batch);
    }

    @Override
    public void resize(int w, int h) {
        setSize(w, h);
        renderer.camera.setToOrtho(false, WIDTH, HEIGHT);
        //renderer.camera.position.set(WIDTH/2, HEIGHT/2, 0);
        renderer.camera.update();
        game.state.resize();
        world.getBackground().resize();
    }

    @Override
    public void pause() {
        world.pause();
    }

    @Override
    public void resume() {
        world.resume();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        world.dispose();
        renderer.dispose();
        batch.dispose();
    }
}
