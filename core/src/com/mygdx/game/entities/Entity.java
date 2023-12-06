package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.components.Component;
import com.mygdx.game.components.GraphicsComponent;
import com.mygdx.game.components.InputComponent;
import com.mygdx.game.components.PhysicsComponent;

public class Entity {
    private Json json;
    private EntityConfig entityConfig;
    private Array<Component> components;
    private InputComponent inputComponent;
    private GraphicsComponent graphicsComponent;
    private PhysicsComponent physicsComponent;

    public Entity(InputComponent inputComponent, PhysicsComponent physicsComponent, GraphicsComponent graphicsComponent) {
        entityConfig = new EntityConfig();
        json = new Json();

        components = new Array<>(5);

        this.inputComponent = inputComponent;
        this.physicsComponent = physicsComponent;
        this.graphicsComponent = graphicsComponent;

        components.add(inputComponent);
        components.add(physicsComponent);
        components.add(graphicsComponent);
    }


    public void update(Batch batch, float delta) {
        inputComponent.update(this, delta);
        graphicsComponent.update(this, batch, delta);
        physicsComponent.update(this, delta);
    }
}
