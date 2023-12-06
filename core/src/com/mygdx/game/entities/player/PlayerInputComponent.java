package com.mygdx.game.entities.player;

import com.mygdx.game.components.InputComponent;
import com.mygdx.game.entities.Entity;

public class PlayerInputComponent extends InputComponent {
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
    public void update(Entity entity, float delta) {

    }
}
