package com.mygdx.game.map;

import static com.mygdx.game.utils.StaticValues.PPM;
import static com.mygdx.game.utils.StaticValues.gameTime;
import static com.mygdx.game.utils.StaticValues.screenSize;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.components.Component;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityConfig;
import com.mygdx.game.manager.ResourceManager;
import com.mygdx.game.physics.PhysicsBodyData;
import com.mygdx.game.physics.PhysicsContactListener;
import com.mygdx.game.physics.PhysicsManager;
import com.mygdx.game.physics.info.ExitInfo;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;

public class GameStage {
    private Json json;
    private World world;
    private PhysicsContactListener physicsContactListener;
    private Box2DDebugRenderer box2DDebugRenderer = new Box2DDebugRenderer();
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Animation<Texture> sandStormAnimation;

    private Array<Room> rooms = new Array<>();
    private Room currentRoom;
    private Entity player;

    public GameStage() {
        json = new Json();

        world = new World(new Vector2(0, 0), false);
        box2DDebugRenderer.SHAPE_STATIC.set(0, 0, 255, 255);
        box2DDebugRenderer.SHAPE_AWAKE.set(0, 0, 255, 255);

        createWorld();

        createRooms(10);

        loadSandStorm("scripts/sandstorm.json");
    }

    private void update() {
        world.step(1 / 60f, 6, 2);
        mapRenderer.setView(camera);
    }

    public void render(Batch batch, float delta) {
        update();

        currentRoom.render(batch, delta);

        mapRenderer.render();

        box2DDebugRenderer.render(world, camera.combined.scl(PPM));
    }

    public void renderEffects(Batch batch, float delta) {
        batch.begin();
        Texture sandStormTexture = sandStormAnimation.getKeyFrame(gameTime);
        batch.draw(sandStormTexture, 0, 0, screenSize.x, screenSize.y);
        batch.end();
    }

    public World getWorld() {
        return world;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    private void createRooms(int roomsNumber) {
        rooms.add(new Room(Vector2.Zero, world));
        setCurrentRoom(rooms.get(0));

        for (int i = 1; i < roomsNumber; i++) {
            Room randomRoom = rooms.get((int) (Math.random() * rooms.size));
            while (randomRoom.getExites().size == 4) {
                randomRoom = rooms.get((int) (Math.random() * rooms.size));
            }

            RoomExit.Direction emptyDirection = randomRoom.getRandomEmptyDirection();
            Vector2 deltaPosition = RoomExit.toCursedVector2(emptyDirection);
            Vector2 newPosition = new Vector2(randomRoom.getPosition()).add(deltaPosition);
            Room newRoom = new Room(newPosition, world);
            setExitesWithRounded(newRoom);

            rooms.add(newRoom);
            newRoom.setActive(false);
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
        if (getRoomByPosition(RoomExit.toCursedVector2(direction).add(getCurrentRoom().getPosition())) == null) {
            return;
        }

        switch (direction) {
            case UP_LEFT:
                player.sendMessage(Component.MESSAGE.INIT_POSITION, json.toJson(new Vector2(30.8f, 1.2f)));
                break;
            case UP_RIGHT:
                player.sendMessage(Component.MESSAGE.INIT_POSITION, json.toJson(new Vector2(1.2f, 1.2f)));
                break;
            case DOWN_LEFT:
                player.sendMessage(Component.MESSAGE.INIT_POSITION, json.toJson(new Vector2(30.8f, 14.8f)));
                break;
            case DOWN_RIGHT:
                player.sendMessage(Component.MESSAGE.INIT_POSITION, json.toJson(new Vector2(1.2f, 14.8f)));
                break;
        }

        currentRoom.setActive(false);
        setCurrentRoom(currentRoom.getExitByDirection(direction).getNextRoom());
    }

    private void setCurrentRoom(Room room) {
        currentRoom = room;
        currentRoom.setActive(true);
        mapRenderer = new OrthogonalTiledMapRenderer(currentRoom.getMap());
    }

    public void setPlayer(Entity player) {
        this.player = player;
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
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

    private void createWorld() {
        physicsContactListener = new PhysicsContactListener();
        world.setContactListener(physicsContactListener);

        //стены
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body1 = world.createBody(bodyDef);
        Body body2 = world.createBody(bodyDef);
        Body body3 = world.createBody(bodyDef);
        Body body4 = world.createBody(bodyDef);

        body1.createFixture(PhysicsManager.createRectangleShape(100, 0.5f), 1000);
        body1.setTransform(0, -0.5f, 0);
        body2.createFixture(PhysicsManager.createRectangleShape(100, 0.5f), 1000);
        body2.setTransform(0, 16.5f, 0);
        body3.createFixture(PhysicsManager.createRectangleShape(0.5f, 100), 1000);
        body3.setTransform(-0.5f, 0, 0);
        body4.createFixture(PhysicsManager.createRectangleShape(0.5f, 100), 1000);
        body4.setTransform(32.5f, 0, 0);

        //выходы
        Body exit1 = world.createBody(bodyDef);
        Body exit2 = world.createBody(bodyDef);
        Body exit3 = world.createBody(bodyDef);
        Body exit4 = world.createBody(bodyDef);

        exit1.createFixture(PhysicsManager.createRectangleShape(0.5f, 0.5f), 1000);
        ExitInfo exitInfo1 = new ExitInfo(RoomExit.Direction.DOWN_LEFT, this);
        exit1.setUserData(new PhysicsBodyData(PhysicsBodyData.DataType.ROOM_TRIGGER, exitInfo1));
        exit1.setTransform(0, 0, 45);

        exit2.createFixture(PhysicsManager.createRectangleShape(0.5f, 0.5f), 1000);
        ExitInfo exitInfo2 = new ExitInfo(RoomExit.Direction.UP_LEFT, this);
        exit2.setUserData(new PhysicsBodyData(PhysicsBodyData.DataType.ROOM_TRIGGER, exitInfo2));
        exit2.setTransform(0, 16f, 45);

        exit3.createFixture(PhysicsManager.createRectangleShape(0.5f, 0.5f), 1000);
        ExitInfo exitInfo3 = new ExitInfo(RoomExit.Direction.DOWN_RIGHT, this);
        exit3.setUserData(new PhysicsBodyData(PhysicsBodyData.DataType.ROOM_TRIGGER, exitInfo3));
        exit3.setTransform(32f, 0, 45);

        exit4.createFixture(PhysicsManager.createRectangleShape(0.5f, 0.5f), 1000);
        ExitInfo exitInfo4 = new ExitInfo(RoomExit.Direction.UP_RIGHT, this);
        exit4.setUserData(new PhysicsBodyData(PhysicsBodyData.DataType.ROOM_TRIGGER, exitInfo4));
        exit4.setTransform(32f, 16f, 45);
    }
}