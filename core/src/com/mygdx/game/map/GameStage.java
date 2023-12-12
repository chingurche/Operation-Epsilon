package com.mygdx.game.map;

import static com.mygdx.game.utils.StaticValues.gameTime;
import static com.mygdx.game.utils.StaticValues.screenSize;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.components.Component;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityConfig;
import com.mygdx.game.manager.ResourceManager;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;

public class GameStage {
    private Json json;
    private World world;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Animation<Texture> sandStormAnimation;

    private Array<Room> rooms = new Array<>();
    private Room currentRoom;
    private Entity player;

    public GameStage(World world) {
        json = new Json();

        this.world = world;

        createRooms(16);

        loadSandStorm("scripts/sandstorm.json");
    }

    public void render(Batch batch, float delta) {
        currentRoom.render(batch, delta);
        mapRenderer.render();
    }

    public void renderEffects(Batch batch, float delta) {
        batch.begin();
        Texture sandStormTexture = sandStormAnimation.getKeyFrame(gameTime);
        batch.draw(sandStormTexture, 0, 0, screenSize.x, screenSize.y);
        batch.end();
    }

    public void setMapRendererView(OrthographicCamera camera) {
        mapRenderer.setView(camera);
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    private void createRooms(int roomsNumber) {
        rooms.add(new Room(Vector2.Zero));
        setCurrentRoom(rooms.get(0));

        for (int i = 1; i < roomsNumber; i++) {
            Room randomRoom = rooms.get((int) (Math.random() * rooms.size));
            while (randomRoom.getExites().size == 4) {
                randomRoom = rooms.get((int) (Math.random() * rooms.size));
            }

            RoomExit.Direction emptyDirection = randomRoom.getRandomEmptyDirection();
            Vector2 deltaPosition = RoomExit.toVector2(emptyDirection);
            Vector2 newPosition = new Vector2(randomRoom.getPosition()).add(deltaPosition);
            Room newRoom = new Room(newPosition);
            setExitesWithRounded(newRoom);

            rooms.add(newRoom);
        }
    }

    private Room getRoomByPosition(Vector2 position) {
        for (Room room : rooms) {
            if (room.getPosition().idt(position)) {
                return room;
            }
        }
        return null;
    }

    private void setExitesWithRounded(Room room) {
        Room room1 = getRoomByPosition(new Vector2(1, 0).add(room.getPosition()));
        if (room1 != null) {
            room.addExit(new RoomExit(RoomExit.Direction.UP_LEFT, room1));
            room1.addExit(new RoomExit(RoomExit.Direction.UP_LEFT.getOpposite(), room));
        }

        Room room2 = getRoomByPosition(new Vector2(0, 1).add(room.getPosition()));
        if (room2 != null) {
            room.addExit(new RoomExit(RoomExit.Direction.UP_RIGHT, room2));
            room2.addExit(new RoomExit(RoomExit.Direction.UP_RIGHT.getOpposite(), room));
        }

        Room room3 = getRoomByPosition(new Vector2(-1, 0).add(room.getPosition()));
        if (room3 != null) {
            room.addExit(new RoomExit(RoomExit.Direction.DOWN_RIGHT, room3));
            room3.addExit(new RoomExit(RoomExit.Direction.DOWN_RIGHT.getOpposite(), room));
        }

        Room room4 = getRoomByPosition(new Vector2(0, -1).add(room.getPosition()));
        if (room4 != null) {
            room.addExit(new RoomExit(RoomExit.Direction.DOWN_LEFT, room4));
            room4.addExit(new RoomExit(RoomExit.Direction.DOWN_LEFT.getOpposite(), room));
        }
    }

    public void changeRoom(RoomExit.Direction direction) {
        if (getRoomByPosition(RoomExit.toVector2(direction).add(getCurrentRoom().getPosition())) == null) {
            return;
        }

        switch (direction) {
            case UP_LEFT:
                player.sendMessage(Component.MESSAGE.INIT_POSITION, json.toJson(new Vector2(16.5f, 0.5f)));
                break;
            case UP_RIGHT:
                player.sendMessage(Component.MESSAGE.INIT_POSITION, json.toJson(new Vector2(0.5f, 0.5f)));
                break;
            case DOWN_LEFT:
                player.sendMessage(Component.MESSAGE.INIT_POSITION, json.toJson(new Vector2(16.5f, 8.5f)));
                break;
            case DOWN_RIGHT:
                player.sendMessage(Component.MESSAGE.INIT_POSITION, json.toJson(new Vector2(0.5f, 8.5f)));
                break;
        }

        currentRoom.destroyStaticObjects(world);
        setCurrentRoom(currentRoom.getExitByDirection(direction).getNextRoom());
    }

    private void setCurrentRoom(Room room) {
        currentRoom = room;
        currentRoom.parseStaticObjects(world);
        currentRoom.parseEntities(world);
        mapRenderer = new OrthogonalTiledMapRenderer(currentRoom.getMap());
    }

    public void setPlayer(Entity player) {
        this.player = player;
    }

    private void loadSandStorm(String configPath) {
        Json json = new Json();
        EntityConfig.AnimationConfig config = json.fromJson(EntityConfig.AnimationConfig.class, Gdx.files.internal(configPath));

        Array<Texture> textures = new Array<>();

        for (String path : config.getTexturePaths()) {
            ResourceManager.loadTextureAsset(path);
            textures.add(ResourceManager.getTextureAsset(path));
        }

        sandStormAnimation = new Animation<>(config.getStateTime(), textures);
        sandStormAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }
}
