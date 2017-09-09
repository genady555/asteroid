package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * Created by genady on 18.02.2017.
 */
public class InputController implements InputProcessor {

    private boolean accelerate;
    private boolean brake;
    private boolean left;
    private boolean right;
    private boolean fire;
    private GdxGame game;

    public InputController(GdxGame game){
        this.game = game;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(game.state.state == GameState.State.START)
            game.state.state = GameState.State.PLAY;
        else if(game.state.state == GameState.State.GAME_OVER) {
            game.state = new GameState();
        }
        else if(game.state.state == GameState.State.PAUSE){
            if(keycode == Input.Keys.P)
                game.state.state = GameState.State.PLAY;
        }
        else if(game.state.state == GameState.State.PLAY) {
            switch (keycode) {
                case Input.Keys.UP: {
                    accelerate = true;
                    break;
                }
                case Input.Keys.DOWN: {
                    brake = true;
                    break;
                }
                case Input.Keys.LEFT: {
                    left = true;
                    break;
                }
                case Input.Keys.RIGHT: {
                    right = true;
                    break;
                }
                case Input.Keys.Q: {
                    fire = true;
                    break;
                }
                case Input.Keys.P: {
                    game.state.state = GameState.State.PAUSE;
                    break;
                }
            }

        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.UP: {
                accelerate = false;
                break;
            }
            case Input.Keys.DOWN: {
                brake = false;
                break;
            }
            case Input.Keys.LEFT: {
                left = false;
                break;
            }
            case Input.Keys.RIGHT: {
                right = false;
                break;
            }
            case Input.Keys.Q: {
                fire = false;
                break;
            }
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public boolean isAccelerate() {
        return accelerate;
    }

    public boolean isBrake() {
        return brake;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isFire() {
        return fire;
    }
}
