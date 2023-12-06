package com.mygdx.game.components;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.map.MapManager;

public abstract class PhysicsComponent extends ComponentSubject implements Component {
    public abstract void update(Entity entity, MapManager mapManager, float delta);
}