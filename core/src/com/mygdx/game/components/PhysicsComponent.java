package com.mygdx.game.components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.entities.Entity;

public abstract class PhysicsComponent extends ComponentSubject implements Component {
    protected Json json = new Json();
    protected Body body;

    public void setBody(Body body) {
        this.body = body;
    }

    public Body getBody() {
        return body;
    }

    public abstract void update(Entity entity, float delta);
}