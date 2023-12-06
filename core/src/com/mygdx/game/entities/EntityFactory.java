package com.mygdx.game.entities;

import com.mygdx.game.entities.player.PlayerGraphicsComponent;
import com.mygdx.game.entities.player.PlayerInputComponent;
import com.mygdx.game.entities.player.PlayerPhysicsComponent;

import java.util.Hashtable;

public class EntityFactory {
    private static EntityFactory instance = null;
    private Hashtable<String, EntityConfig> entities;

    public enum EntityType {
        PLAYER
    }

    private EntityFactory() {
        entities = new Hashtable<>();
    }

    public static EntityFactory getInstance() {
        if (instance == null) {
            instance = new EntityFactory();
        }

        return instance;
    }

    public Entity getEntity(EntityType entityType) {
        Entity entity;
        switch (entityType) {
            case PLAYER:
                entity = new Entity(new PlayerInputComponent(), new PlayerPhysicsComponent(), new PlayerGraphicsComponent());
                return entity;
            default:
                return null;
        }
    }
}
