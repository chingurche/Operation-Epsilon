package com.mygdx.game.components;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.entities.Entity;

public abstract class BattleComponent implements Component {
    protected Json json;

    protected BattleComponent() {
        json = new Json();
    }

    public abstract void update(Entity entity, Batch batch, float delta);
}
