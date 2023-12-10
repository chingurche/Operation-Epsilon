package com.mygdx.game.entities.enemy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.components.GraphicsComponent;
import com.mygdx.game.entities.Entity;

public class EnemyGraphicsComponent extends GraphicsComponent {
    @Override
    public void receiveMessage(String message) {
        String[] string = message.split(MESSAGE_TOKEN);

        if (string.length == 0) {
            return;
        }

        if (string.length == 2) {

        }
    }

    @Override
    public void update(Entity entity, Batch batch, float delta) {
        batch.begin();
        batch.draw(currentFrame, currentPosition.x, currentPosition.y, 1, 1);
        batch.end();
    }
}
