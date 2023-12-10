package com.mygdx.game.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.entities.Entity;

public abstract class GraphicsComponent extends ComponentSubject implements Component {
    public enum Direction {
        FORWARD,
        LEFT,
        RIGHT,
        BACK
    }

    protected Json json;
    public Texture currentFrame = null;
    private Direction direction;
    protected Vector2 currentPosition;

    protected void setDirection(Vector2 direction) {

    }

    public void setTexture(Texture texture) { currentFrame = texture; }

    protected GraphicsComponent() {
        currentPosition = new Vector2(100, 100);
        json = new Json();
    }

    public abstract void update(Entity entity, Batch batch, float delta);
}
