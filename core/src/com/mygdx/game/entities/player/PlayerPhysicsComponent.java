package com.mygdx.game.entities.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Transform;
import com.mygdx.game.components.Component;
import com.mygdx.game.components.PhysicsComponent;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityConfig;

public class PlayerPhysicsComponent extends PhysicsComponent {
    private Vector2 direction = new Vector2(0, 0);
    private float speed;

    private boolean initNewPosition;
    private Vector2 newPosition;

    @Override
    public void receiveMessage(String message) {
        String[] string = message.split(MESSAGE_TOKEN);

        if (string.length == 0) {
            return;
        }

        if (string.length == 2) {
            if (string[0].equalsIgnoreCase(MESSAGE.INIT_POSITION.toString())) {
                newPosition = json.fromJson(Vector2.class, string[1]);
                initNewPosition = true;
            } else if (string[0].equalsIgnoreCase(MESSAGE.ENTITY_DIRECTION.toString())) {
                direction = json.fromJson(Vector2.class, string[1]);
            } else if (string[0].equalsIgnoreCase(MESSAGE.LOAD_CONFIG.toString())) {
                EntityConfig config = json.fromJson(EntityConfig.class, string[1]);

                speed = config.getSpeed();
            }
        }
    }

    @Override
    public void update(Entity entity, float delta) {
        if (direction.equals(Vector2.Zero)) {
            entity.sendMessage(MESSAGE.CURRENT_STATE, json.toJson(Entity.State.IDLE));
        }
        else {
            entity.sendMessage(MESSAGE.CURRENT_STATE, json.toJson(Entity.State.WALKING));
        }

        body.setLinearVelocity(new Vector2(direction).scl(speed * delta));
        if (initNewPosition) {
            body.setTransform(newPosition, 0);
            initNewPosition = false;
        }
        entity.sendMessage(MESSAGE.CURRENT_POSITION, json.toJson(body.getPosition()));
    }
}