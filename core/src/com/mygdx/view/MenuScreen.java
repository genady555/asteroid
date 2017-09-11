package com.mygdx.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GdxGame;

/**
 * Created by genady on 10.09.2017.
 */
public class MenuScreen implements Screen {

    private GdxGame game;
    private Stage stage;
    private Table table;
    private TextButton start, options, exit;
    private TextButton.TextButtonStyle buttonStyle;


    public MenuScreen(GdxGame game) {
        this.game = game;
        stage = new Stage();
        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = GdxGame.createFont("fonts/input.ttf", 50, Color.CORAL);
        start = new TextButton("Старт", buttonStyle);
        options = new TextButton("Настройки", buttonStyle);
        exit = new TextButton("Выход", buttonStyle);
        start.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(game.gameScreen);
            }
        });
        table = new Table();
        table.setFillParent(true);
        table.add(start);
        table.row();
        table.add(options);
        table.row();
        table.add(exit);
        stage.addActor(table);
        stage.setDebugAll(true);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().setScreenSize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        //dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
