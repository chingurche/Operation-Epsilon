package com.mygdx.game.entities.player;

import static com.mygdx.game.utils.StaticValues.PPM;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.GraphicsComponent;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityConfig;

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
            if (string[0].equalsIgnoreCase(MESSAGE.LOAD_ANIMATIONS.toString())) {
                EntityConfig config = json.fromJson(EntityConfig.class, string[1]);

                for (EntityConfig.AnimationConfig animationConfig : config.getAnimationConfigs()) {
                    loadAnimation(animationConfig);
                }

                updateAnimation();
            }
        }
    }

    @Override
    public void update(Entity entity, Batch batch, float delta) {
        updateFrame(delta);


        batch.begin();
        batch.draw(currentFrame, currentPosition.x * PPM + 48, currentPosition.y * PPM + 64, 16, 16);
        batch.end();
    }
}
