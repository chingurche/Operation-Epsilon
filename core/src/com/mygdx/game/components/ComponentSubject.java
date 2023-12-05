package com.mygdx.game.components;

import com.badlogic.gdx.utils.Array;

public class ComponentSubject {
    private Array<ComponentObserver> observers;

    public void addObserver(ComponentObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ComponentObserver observer) {
        observers.removeValue(observer, true);
    }

    public void removeAllObserver() {
        for(ComponentObserver observer: observers) {
            observers.removeValue(observer, true);
        }
    }

    protected void notify()
}
