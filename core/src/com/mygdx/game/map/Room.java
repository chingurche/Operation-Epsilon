package com.mygdx.game.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.manager.ResourceManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Room {
    private TiledMap map;
    private final Vector2 position;
    private Array<RoomExit> exites = new Array<>();


    private Texture floor;
    private Texture walls;

    private Array<Body> staticBodies = new Array<>();
    private Array<Entity> entities = new Array<>();

    public Room(Vector2 position, World world) {
        String randomName = "location/room" + (int) (Math.random() * 5 + 1) + ".tmx";
        ResourceManager.loadMapAsset(randomName);
        map = ResourceManager.getMapAsset(randomName);

        String randomWallsName = "textures/walls/walls" + (int) (Math.random() * 8 + 1) + ".png";
        ResourceManager.loadTextureAsset(randomWallsName);
        walls = ResourceManager.getTextureAsset(randomWallsName);
        String randomFloorName = "textures/floor/sand" + (int) (Math.random() * 10 + 1) + ".png";
        ResourceManager.loadTextureAsset(randomFloorName);
        floor = ResourceManager.getTextureAsset(randomFloorName);

        parseStaticObjects(world);

        this.position = position;
    }

    public void setActive(boolean flag) {
        for (Body body : staticBodies) {
            body.setActive(flag);
        }
    }

    public void render(Batch batch, float delta) {
        batch.begin();
        batch.draw(floor, -32, -32);
        batch.draw(walls, -32, -32);
        batch.end();

        for (RoomExit exit : exites) {
            exit.render(batch);
        }

        updateEntities(batch, delta);
    }

    public Vector2 getPosition() {
        return position;
    }

    public TiledMap getMap() {
        return map;
    }

    public void addExit(RoomExit exit) {
        exites.add(exit);
    }

    public Array<RoomExit> getExites() {
        return exites;
    }
    public RoomExit getExitByDirection(RoomExit.Direction direction) {
        for (RoomExit exit : exites) {
            if (exit.getDirection() == direction) {
                return exit;
            }
        }

        return null;
    }

    public RoomExit.Direction getRandomEmptyDirection() {
        List<RoomExit.Direction> emptyDirections = new ArrayList<>();
        emptyDirections.add(RoomExit.Direction.DOWN_LEFT);
        emptyDirections.add(RoomExit.Direction.DOWN_RIGHT);
        emptyDirections.add(RoomExit.Direction.UP_LEFT);
        emptyDirections.add(RoomExit.Direction.UP_RIGHT);

        for (RoomExit exit : exites) {
            emptyDirections.remove(exit.getDirection());
        }

        return emptyDirections.get((int) (Math.random() * emptyDirections.size()));
    }

    public void parseStaticObjects(World world) {
        staticBodies = ResourceManager.parseStaticObjects(map, world);
    }

    public void parseEntities(World world) {

    }

    private void updateEntities(Batch batch, float delta) {
        for (int i = 0; i < entities.size; i++) {
            entities.get(i).update(batch, delta);
        }
    }
}
