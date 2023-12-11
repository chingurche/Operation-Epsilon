package com.mygdx.game.components;

public interface Component {
    String MESSAGE_TOKEN = ":::::";

    enum MESSAGE {
        INIT_POSITION,
        CURRENT_POSITION,
        CURRENT_STATE,
        ENTITY_DIRECTION,
        LOAD_ANIMATIONS
    }

    void receiveMessage(String message);
}