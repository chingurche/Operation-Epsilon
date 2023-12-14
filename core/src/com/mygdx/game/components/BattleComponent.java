package com.mygdx.game.components;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.entities.Entity;

public abstract class BattleComponent implements Component {
    protected Json json;

    protected int health;

    protected BattleComponent() {
        json = new Json();
    }

    protected void getDamage(int damage) {
        if (damage <= 0) { return; }

        health -= damage;
    }

    public int getHealth() {
        return health;
    }

    public abstract void update(Entity entity, Batch batch, float delta);
}
