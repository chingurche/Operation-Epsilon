package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.components.Component;
import com.mygdx.game.components.GraphicsComponent;
import com.mygdx.game.components.InputComponent;
import com.mygdx.game.components.PhysicsComponent;

import java.lang.ref.SoftReference;

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

    public void sendMessage(Component.MESSAGE type, String ... args) {
        String message = type.toString();

        for (String string : args) {
            message += Component.MESSAGE_TOKEN + string;
        }

        for (Component component : components) {
            component.receiveMessage(message);
        }
    }
}
