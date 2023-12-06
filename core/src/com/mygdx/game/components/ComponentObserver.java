package com.mygdx.game.components;

import com.mygdx.game.entities.Entity;

public interface ComponentObserver {
    enum ComponentEvent {

    }

    void onNotify(String value, ComponentEvent event);
}
