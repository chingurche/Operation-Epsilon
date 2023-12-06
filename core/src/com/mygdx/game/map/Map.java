package com.mygdx.game.map;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.audio.AudioObserver;
import com.mygdx.game.audio.AudioSubject;

public class Map implements AudioSubject {
    private Array<AudioObserver> observers;
    public static final float UNIT_SCALE  = 1/16f;

    protected TiledMap currentMap = null;

    @Override
    public void addObserver(AudioObserver audioObserver) {
        observers.add(audioObserver);
    }

    @Override
    public void removeObserver(AudioObserver audioObserver) {
        observers.removeValue(audioObserver, true);
    }

    @Override
    public void removeAllObservers() {
        observers.removeAll(observers, true);
    }

    @Override
    public void notify(AudioObserver.AudioCommand command, AudioObserver.AudioTypeEvent event) {
        for(AudioObserver observer: observers) {
            observer.onNotify(command, event);
        }
    }

    public TiledMap getCurrentTiledMap() {
        return currentMap;
    }
}
