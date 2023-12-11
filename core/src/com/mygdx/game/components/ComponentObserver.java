package com.mygdx.game.components;

public interface ComponentObserver {
    enum ComponentEvent {

    }

    void onNotify(String value, ComponentEvent event);
}
