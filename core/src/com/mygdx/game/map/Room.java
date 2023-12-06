package com.mygdx.game.map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entities.Entity;

import java.util.Arrays;
import java.util.List;

public class Room {
    private TiledMap room;
    private Vector2 position;
    private Array<RoomExit> exites;

    private Array<Entity> enemyEntities;

    public Room(Vector2 position) {
        this.position = position;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Array<RoomExit> getExites() {
        return exites;
    }

    public RoomExit.Direction getRandomEmptyDirection() {
        List<RoomExit.Direction> directions = Arrays.asList(RoomExit.Direction.values());

        for (RoomExit exit : exites) {
            for (RoomExit.Direction direction : directions) {
                if (direction == exit.getDirection()) {
                    directions.remove(direction);
                }
            }
        }

        return directions.get((int) (Math.random() * directions.size()));
    }

    public void updateEnemyEntities(Batch batch, float delta) {
        for (int i = 0; i < enemyEntities.size; i++) {
            enemyEntities.get(i).update(batch, delta);
        }
    }
}
