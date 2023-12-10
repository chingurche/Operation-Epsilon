package com.mygdx.game.entities.player;

import static com.mygdx.game.utils.Constants.PPM;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.GraphicsComponent;
import com.mygdx.game.entities.Entity;

public class PlayerGraphicsComponent extends GraphicsComponent {

    public PlayerGraphicsComponent() {
        super();
    }

    @Override
    public void receiveMessage(String message) {
        String[] string = message.split(MESSAGE_TOKEN);

        if (string.length == 0) {
            return;
        }

        if (string.length == 2) {
            if (string[0].equalsIgnoreCase(MESSAGE.CURRENT_POSITION.toString())) {
                currentPosition = json.fromJson(Vector2.class, string[1]);
            }
            if (string[0].equalsIgnoreCase(MESSAGE.ENTITY_DIRECTION.toString())) {
                setDirection(json.fromJson(Vector2.class, string[1]));
            }
        }
    }

    @Override
    public void update(Entity entity, Batch batch, float delta) {
        batch.begin();
        batch.draw(currentFrame, currentPosition.x * PPM + 48, currentPosition.y * PPM + 64, 16, 16);
        batch.end();
    }
}
