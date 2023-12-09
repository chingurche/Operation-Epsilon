package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.components.Component;
import com.mygdx.game.components.GraphicsComponent;
import com.mygdx.game.components.PhysicsComponent;

public class Entity {
    private Json json;
    private EntityConfig entityConfig;
    private Array<Component> components;
    private GraphicsComponent graphicsComponent;
    private PhysicsComponent physicsComponent;

    public void setBody(Body body) {
        physicsComponent.setBody(body);
    }

    public void setTexture(Texture texture) { graphicsComponent.setTexture(texture); }

    public Entity(PhysicsComponent physicsComponent, GraphicsComponent graphicsComponent) {
        entityConfig = new EntityConfig();
        json = new Json();

        components = new Array<>(5);

        this.physicsComponent = physicsComponent;
        this.graphicsComponent = graphicsComponent;

        components.add(physicsComponent);
        components.add(graphicsComponent);
    }


    public void update(Batch batch, float delta) {
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
