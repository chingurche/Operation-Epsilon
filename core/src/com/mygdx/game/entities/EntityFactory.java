package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.components.Component;
import com.mygdx.game.entities.enemy.EnemyGraphicsComponent;
import com.mygdx.game.entities.enemy.EnemyPhysicsComponent;
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
        switch (entityType) {
            case PLAYER:
                entity = new Entity(new PlayerPhysicsComponent(), new PlayerGraphicsComponent(), new PlayerBattleComponent(world));

                Body body = ResourceManager.createBody(world);
                body.setUserData(new PhysicsBodyData(PhysicsBodyData.DataType.PLAYER_ENTITY, entity));
                entity.setBody(body);

                entity.setEntityConfig(Entity.getEntityConfig("scripts/player.json"));
                entity.sendMessage(Component.MESSAGE.LOAD_ANIMATIONS, json.toJson(entity.getEntityConfig()));

                return entity;
            case BASE_ENEMY:
                entity = new Entity(new EnemyPhysicsComponent(), new PlayerGraphicsComponent(), new PlayerBattleComponent(world));

            default:
                return null;
        }
    }
}
