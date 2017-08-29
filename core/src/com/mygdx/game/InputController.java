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
    private int up, down;


    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
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
