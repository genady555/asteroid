package com.mygdx.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.mygdx.game.GdxGame;
import com.mygdx.model.Asteroid;
import com.mygdx.model.WorldSpace;

/**
 * Created by Rrr on 15.02.2017.
 */
public class GameScreen implements Screen{

    final private GdxGame game;
    final private WorldSpace world;

    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;

    public GameScreen(GdxGame game, WorldSpace world) {
        this.game = game;
        this.world = world;
        renderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WorldSpace.WIDTH, WorldSpace.HEIGHT);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //world.update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.getBatch().setProjectionMatrix(camera.combined);
        game.getBatch().begin();
        world.getBackground().render();
        world.getHero().render();
        for (Asteroid asteroid : world.getAsteroids())
            asteroid.render();
        game.getBatch().end();
        renderer.render(world.getPhysics(), camera.combined);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
