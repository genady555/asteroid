package com.mygdx.view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.mygdx.game.GdxGame;
import com.mygdx.model.Asteroid;
import com.mygdx.model.WorldSpace;

/**
 * Created by genady on 19.02.2017.
 */
public class WorldRenderer {

    final WorldSpace world;

    private SpriteBatch batch;
    public OrthographicCamera camera;
    public OrthographicCamera camera_origin;
    private Box2DDebugRenderer debugRenderer;

    private long lastTime;
    private long counterTime;


    public WorldRenderer(WorldSpace world) {
        this.world = world;
        batch = new SpriteBatch();
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        camera_origin = new OrthographicCamera();
        camera_origin.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera_origin.position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0);
        lastTime = System.currentTimeMillis();
        //camera.setToOrtho(false, screen.WIDTH, screen.HEIGHT);
        //camera.position.set(screen.WIDTH/2, screen.HEIGHT/2, 0);
    }

    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        world.getBackground().render(batch);
        world.getHero().render(batch);
        for (Asteroid asteroid : world.getAsteroids())
            asteroid.render(batch);
        batch.setProjectionMatrix(camera_origin.combined);
        GdxGame.font.draw(batch, "First font! фыва фыва жождлолдо джллож лол жо ", 1, 100);
        batch.end();
        debugRenderer.render(world.getPhysics(), camera.combined);
        //debug(1000);
    }

    public void debug(float time) {
        long currentTime = System.currentTimeMillis();
        long dTime = currentTime - lastTime;
        lastTime = currentTime;
        counterTime += dTime;
        if(counterTime >= time) {
            counterTime = 0;
            //System.out.println(world.getHero().getBody().getLinearVelocity());
            System.out.println("FPS: " + Gdx.graphics.getFramesPerSecond());
        }
    }

    public void dispose(){
        debugRenderer.dispose();
        batch.dispose();
    }
}
