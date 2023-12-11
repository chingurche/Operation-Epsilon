package com.mygdx.game.entities.enemy;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.PhysicsComponent;
import com.mygdx.game.entities.Entity;

public class EnemyPhysicsComponent extends PhysicsComponent {


    @Override
    public void receiveMessage(String message) {
        String[] string = message.split(MESSAGE_TOKEN);

        if (string.length == 0) {
            return;
        }

        if (string.length == 2) {
            if (string[0].equalsIgnoreCase(MESSAGE.INIT_POSITION.toString())) {
                body.setTransform(json.fromJson(Vector2.class, string[1]), 0);
            }
        }
    }

    @Override
    public void update(Entity entity, float delta) {

    }
}
