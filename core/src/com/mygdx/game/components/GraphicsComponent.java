package com.mygdx.game.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Entity;

public abstract class GraphicsComponent extends ComponentSubject implements Component {
    public Texture currentFrame = null;
    protected Vector2 currentPosition;

    protected GraphicsComponent() {
        currentPosition = new Vector2(100, 100);
    }

    public abstract void update(Entity entity, Batch batch, float delta);
}
