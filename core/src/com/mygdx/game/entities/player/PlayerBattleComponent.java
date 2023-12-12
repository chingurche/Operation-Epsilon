package com.mygdx.game.entities.player;

import com.mygdx.game.components.BattleComponent;

public class PlayerBattleComponent extends BattleComponent {
    @Override
    public void receiveMessage(String message) {
        String[] string = message.split(MESSAGE_TOKEN);

        if (string.length == 0) {
            return;
        }

        if (string.length == 2) {

        }
    }
}
