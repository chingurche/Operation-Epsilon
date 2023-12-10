package com.mygdx.game.components;

public interface Component {
    String MESSAGE_TOKEN = ":::::";

    enum MESSAGE {
        CURRENT_POSITION,
        ENTITY_DIRECTION
    }

    void receiveMessage(String message);
}