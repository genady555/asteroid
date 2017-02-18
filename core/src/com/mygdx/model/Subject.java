package com.mygdx.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Rrr on 17.02.2017.
 */
public class Subject {

    static public int count;

    protected MySprite sprite;
    protected boolean active;
    protected Body body;
    protected float density = 1f;
    protected float massa;


}
