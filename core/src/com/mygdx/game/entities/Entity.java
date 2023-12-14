package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.audio.AudioManager;
import com.mygdx.game.audio.AudioObserver;
import com.mygdx.game.components.BattleComponent;
import com.mygdx.game.components.Component;
import com.mygdx.game.components.GraphicsComponent;
import com.mygdx.game.components.PhysicsComponent;

public class Entity {
    public enum AnimationType {
        IDLE,
        WALKING_FORWARD,
        WALKING_LEFT,
        WALKING_RIGHT,
        WALKING_BACK,
        OTHER
    }

    public enum State {
        IDLE,
        WALKING
    }

    private Json json;
    private World world;
    private EntityConfig entityConfig;
    private Array<Component> components;
    private GraphicsComponent graphicsComponent;
    private PhysicsComponent physicsComponent;
    private BattleComponent battleComponent;

    public Entity(PhysicsComponent physicsComponent, GraphicsComponent graphicsComponent, BattleComponent battleComponent) {
        json = new Json();

        components = new Array<>(5);

        this.physicsComponent = physicsComponent;
        this.graphicsComponent = graphicsComponent;
        this.battleComponent = battleComponent;

        components.add(physicsComponent);
        components.add(graphicsComponent);
        components.add(battleComponent);
    }

    public void setBody(Body body) {
        physicsComponent.setBody(body);
        world = body.getWorld();
    }

    public Body getBody() {
        return physicsComponent.getBody();
    }

    public World getWorld() {
        return world;
    }

    public void setEntityConfig(EntityConfig config) {
        entityConfig = config;
    }

    public EntityConfig getEntityConfig() {
        return entityConfig;
    }

    public boolean isDead() {
        return battleComponent.getHealth() <= 0;
    }

    public void update(Batch batch, float delta) {
        graphicsComponent.update(this, batch, delta);
        physicsComponent.update(this, delta);
        battleComponent.update(this, batch, delta);
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

    public void destroy() {
        physicsComponent.getBody().getWorld().destroyBody(physicsComponent.getBody());
        AudioManager.getInstance().onNotify(AudioObserver.AudioCommand.MUSIC_STOP, AudioObserver.AudioTypeEvent.KILLED_ENEMY);
        AudioManager.getInstance().onNotify(AudioObserver.AudioCommand.MUSIC_PLAY_ONCE, AudioObserver.AudioTypeEvent.KILLED_ENEMY);
    }

    public static EntityConfig getEntityConfig(String path) {
        Json json = new Json();
        EntityConfig config = json.fromJson(EntityConfig.class, Gdx.files.internal(path));
        return config;
    }
}
