package com.mygdx.game.components;

public interface Component {
    String MESSAGE_TOKEN = ":::::";

    enum MESSAGE {
        INIT_POSITION,
        CURRENT_POSITION,
        CURRENT_STATE,
        ENTITY_DIRECTION,
        LOAD_ANIMATIONS,
        LOAD_CONFIG,
        ATTACK_STATUS,
        ATTACK_DIRECTION,
        BATTLE_TARGET,
        GET_DAMAGE,
        RANDOM_WEAPON
    }

    void receiveMessage(String message);
}