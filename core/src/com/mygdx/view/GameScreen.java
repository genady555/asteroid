package com.mygdx.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.mygdx.game.GdxGame;
import com.mygdx.game.InputController;
import com.mygdx.model.Asteroid;
import com.mygdx.model.WorldSpace;

/**
 * Created by Rrr on 15.02.2017.
 */
public class GameScreen implements Screen{

    final static public int WIDTH = 22; //размер экрана в юнитах
    final static public int HEIGHT = 12;
    final public GdxGame game;

    private WorldSpace world;
    private InputController input;
    private WorldRenderer renderer;

    public GameScreen(GdxGame game) {
        this.game = game;
        input = new InputController();
        Gdx.input.setInputProcessor(input);
        world = new WorldSpace(this);
        renderer = new WorldRenderer(this, world);
    }

    public InputController getInput() { return input; }

    @Override
    public void show() {
        world.start(1);
    }

    @Override
    public void render(float delta) {
        world.update(delta);
        renderer.render();
    }

    @Override
    public void resize(int width, int height) {

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
    }
}
