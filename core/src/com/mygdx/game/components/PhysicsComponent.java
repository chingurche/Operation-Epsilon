package com.mygdx.game.components;

import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.entities.Entity;

public abstract class PhysicsComponent extends ComponentSubject implements Component {
    private Body body;



    public abstract void update(Entity entity, float delta);
}