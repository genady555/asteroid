package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.model.*;
import com.mygdx.view.GameScreen;

public class GdxGame extends Game {

//---------------------------------------------------------------------------------

    @Override
    public void create () {
        setScreen(new GameScreen(this));
    }

}
