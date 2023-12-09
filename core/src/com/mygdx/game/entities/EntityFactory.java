package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.enemy.EnemyGraphicsComponent;
import com.mygdx.game.entities.enemy.EnemyPhysicsComponent;
import com.mygdx.game.entities.player.PlayerGraphicsComponent;
import com.mygdx.game.entities.player.PlayerPhysicsComponent;
import com.mygdx.game.manager.ResourceManager;

import java.util.Hashtable;

public class EntityFactory {
    private static EntityFactory instance = null;
    private Hashtable<String, EntityConfig> entities;

    public enum EntityType {
        PLAYER,
        BASE_ENEMY,
        SLOW_ENEMY,
        FAST_ENEMY,
        RANGED_ENEMY
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

    public Entity getEntity(EntityType entityType, World world) {
        Entity entity;
        switch (entityType) {
            case PLAYER:
                entity = new Entity(new PlayerPhysicsComponent(), new PlayerGraphicsComponent());
                Body body = ResourceManager.createBody(world);
                entity.setBody(body);

                ResourceManager.loadTextureAsset("textures/player/idle1.png");
                Texture texture = ResourceManager.getTextureAsset("textures/player/idle1.png");
                entity.setTexture(texture);
                return entity;
            case BASE_ENEMY:
                entity = new Entity(new EnemyPhysicsComponent(), new EnemyGraphicsComponent());
            default:
                return null;
        }
    }
}
