package com.mygdx.game.components;

public interface Component {
    String MESSAGE_TOKEN = ":::::";

    enum MESSAGE {
        CURRENT_POSITION
    }

    void receiveMessage(String message);
}