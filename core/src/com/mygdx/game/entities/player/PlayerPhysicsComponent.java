package com.mygdx.game.entities.player;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.Component;
import com.mygdx.game.components.PhysicsComponent;
import com.mygdx.game.entities.Entity;

public class PlayerPhysicsComponent extends PhysicsComponent {
    private Vector2 direction = new Vector2(0, 0);
    private float speed = 800;

    @Override
    public void receiveMessage(String message) {
        String[] string = message.split(MESSAGE_TOKEN);

        if (string.length == 0) {
            return;
        }

        if (string.length == 2) {
            if (string[0].equalsIgnoreCase(MESSAGE.ENTITY_DIRECTION.toString())) {
                direction = json.fromJson(Vector2.class, string[1]);
            }
        }
    }

    @Override
    public void update(Entity entity, float delta) {
        body.setLinearVelocity(new Vector2(direction).scl(speed * delta));
        entity.sendMessage(MESSAGE.CURRENT_POSITION, json.toJson(body.getPosition()));
    }
}