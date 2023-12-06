package com.mygdx.game.map;

import com.badlogic.gdx.maps.tiled.TiledMap;

public class MapManager {
    private Map currentMap;
    public TiledMap getCurrentTiledMap() {
        return currentMap.getCurrentTiledMap();
    }
}
