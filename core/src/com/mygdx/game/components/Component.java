package com.mygdx.game.components;

public interface Component {
    String MESSAGE_TOKEN = ":::::";

    void receiveMessage(String message);
}