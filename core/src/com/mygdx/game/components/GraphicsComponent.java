package com.mygdx.game.components;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.map.MapManager;

public abstract class GraphicsComponent extends ComponentSubject implements Component {
    protected TextureRegion currentFrame = null;
    protected Vector2 currentPosition;

    public abstract void update(Entity entity, MapManager mapManager, Batch batch, float delta);
}
