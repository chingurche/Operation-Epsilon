package com.mygdx.game.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.components.Component;
import com.mygdx.game.entities.enemy.EnemyBattleComponent;
import com.mygdx.game.entities.player.PlayerBattleComponent;
import com.mygdx.game.entities.player.PlayerGraphicsComponent;
import com.mygdx.game.entities.player.PlayerPhysicsComponent;
import com.mygdx.game.manager.ResourceManager;
import com.mygdx.game.physics.PhysicsBodyData;

import java.util.Hashtable;

public class EntityFactory {
    private Json json;
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
        json = new Json();
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
        Body body;
        switch (entityType) {
            case PLAYER:
                entity = new Entity(new PlayerPhysicsComponent(), new PlayerGraphicsComponent(), new PlayerBattleComponent(world));

                body = ResourceManager.createBody(world);
                body.setUserData(new PhysicsBodyData(PhysicsBodyData.DataType.PLAYER_ENTITY, entity));
                entity.setBody(body);

                entity.setEntityConfig(Entity.getEntityConfig("scripts/player.json"));
                entity.sendMessage(Component.MESSAGE.LOAD_CONFIG, json.toJson(entity.getEntityConfig()));
                entity.sendMessage(Component.MESSAGE.LOAD_ANIMATIONS, json.toJson(entity.getEntityConfig()));

                return entity;
            case BASE_ENEMY:
                entity = new Entity(new PlayerPhysicsComponent(), new PlayerGraphicsComponent(), new EnemyBattleComponent());

                body = ResourceManager.createBody(world);
                body.setUserData(new PhysicsBodyData(PhysicsBodyData.DataType.ENEMY_ENTITY, entity));
                entity.setBody(body);

                entity.setEntityConfig(Entity.getEntityConfig("scripts/baseenemy.json"));
                entity.sendMessage(Component.MESSAGE.LOAD_CONFIG, json.toJson(entity.getEntityConfig()));
                entity.sendMessage(Component.MESSAGE.LOAD_ANIMATIONS, json.toJson(entity.getEntityConfig()));

                return entity;
            default:
                return null;
        }
    }
}
