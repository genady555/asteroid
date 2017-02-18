package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.model.*;
import com.mygdx.view.GameScreen;

public class GdxGame extends Game {

    //final static public int WIDTH = 128; //размер в
    //final static public int HEIGHT = 72;
    final public int STATE_GAME = 1;
    final public int STATE_GAMEOVER = 0;

    private SpriteBatch batch;
    private GameScreen screenGame;
    private WorldSpace world;
    private InputController input;


//---------------------------------------------------------------------------------

    public InputController getInput() { return input; }

    @Override
    public void create () {
        batch = new SpriteBatch();
        input = new InputController();
        Gdx.input.setInputProcessor(input);
        world = new WorldSpace(this);
        screenGame = new GameScreen(this, world);
        setScreen(screenGame);
    }

    public SpriteBatch getBatch() { return batch;}
	
	@Override
	public void dispose () {
		batch.dispose();
		screenGame.dispose();
	}

}
