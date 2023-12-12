package com.mygdx.game.entities.player;

import com.mygdx.game.components.BattleComponent;
import com.mygdx.game.weapons.Weapon;

public class PlayerBattleComponent extends BattleComponent {
    private Weapon weapon;

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
