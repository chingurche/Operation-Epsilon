package com.mygdx.game.components;

public interface Component {
    String MESSAGE_TOKEN = ":::::";

    enum MESSAGE {
        CURRENT_POSITION,
        ENTITY_DIRECTION,
        LOAD_ANIMATIONS
    }

    void receiveMessage(String message);
}