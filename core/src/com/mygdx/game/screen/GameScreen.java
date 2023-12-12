package com.mygdx.game.screen;

import static com.mygdx.game.utils.StaticValues.PPM;
import static com.mygdx.game.utils.StaticValues.gameTime;
import static com.mygdx.game.utils.StaticValues.screenSize;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.audio.AudioObserver;
import com.mygdx.game.components.Component;
import com.mygdx.game.components.ComponentObserver;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityFactory;
import com.mygdx.game.map.RoomExit;
import com.mygdx.game.physics.PhysicsBodyData;
import com.mygdx.game.physics.PhysicsContactListener;
import com.mygdx.game.physics.PhysicsManager;
import com.mygdx.game.map.GameStage;
import com.mygdx.game.physics.info.ExitInfo;
import com.mygdx.game.utils.Joystick;

public class GameScreen extends BaseScreen implements ComponentObserver {
    private final float cameraViewport = 6;
    private OrthographicCamera camera;
    private OrthographicCamera hudCamera;
    private World world;
    private PhysicsContactListener physicsContactListener;
    private Box2DDebugRenderer box2DDebugRenderer = new Box2DDebugRenderer();
    private Json json;

    private GameStage gameStage;
    private Joystick joystick;
    private Entity player;

    public GameScreen(MyGdxGame gdxGame, Batch batch, Batch hudBatch) {
        super(gdxGame, batch, hudBatch);

        json = new Json();

        world = new World(new Vector2(0, 0), false);
        box2DDebugRenderer.SHAPE_STATIC.set(0, 0, 255, 255);
        box2DDebugRenderer.SHAPE_AWAKE.set(0, 0, 255, 255);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenSize.x / cameraViewport, screenSize.y / cameraViewport);

        hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(true, screenSize.x, screenSize.y);

        player = EntityFactory.getInstance().getEntity(EntityFactory.EntityType.PLAYER, world);
        player.sendMessage(Component.MESSAGE.INIT_POSITION, json.toJson(new Vector2(16, 8)));

        gameStage = new GameStage(world);
        gameStage.setPlayer(player);
        createWorld();

        joystick = new Joystick(300, screenSize.y - 300, 200);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void onNotify(String value, ComponentEvent event) {

    }

    @Override
    public void show() {
        notify(AudioObserver.AudioCommand.MUSIC_LOAD, AudioObserver.AudioTypeEvent.MENU_THEME);
        notify(AudioObserver.AudioCommand.MUSIC_PLAY_LOOP, AudioObserver.AudioTypeEvent.MENU_THEME);
    }

    private void update(float delta) {
        world.step(1 / 60f, 6, 2);
        camera.position.set(new Vector3(128, 64, 0));
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        hudBatch.setProjectionMatrix(hudCamera.combined);
        gameStage.setMapRendererView(camera);
        gameTime += delta;
    }

    @Override
    public void render(float delta) {
        this.update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameStage.render(batch, delta);

        player.update(batch, delta);

        gameStage.renderEffects(hudBatch, delta);

        box2DDebugRenderer.render(world, camera.combined.scl(PPM));

        joystick.render(hudBatch);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        joystick.start(screenX, screenY);
        player.sendMessage(Component.MESSAGE.ENTITY_DIRECTION, json.toJson(joystick.getDirection()));
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        joystick.dragged(screenX, screenY);
        player.sendMessage(Component.MESSAGE.ENTITY_DIRECTION, json.toJson(joystick.getDirection()));
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        joystick.end();
        player.sendMessage(Component.MESSAGE.ENTITY_DIRECTION, json.toJson(Vector2.Zero));
        return true;
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
        ExitInfo exitInfo1 = new ExitInfo(RoomExit.Direction.DOWN_LEFT, gameStage);
        exit1.setUserData(new PhysicsBodyData(PhysicsBodyData.DataType.ROOM_TRIGGER, exitInfo1));
        exit1.setTransform(0, 0, 45);

        exit2.createFixture(PhysicsManager.createRectangleShape(0.5f, 0.5f), 1000);
        ExitInfo exitInfo2 = new ExitInfo(RoomExit.Direction.UP_LEFT, gameStage);
        exit2.setUserData(new PhysicsBodyData(PhysicsBodyData.DataType.ROOM_TRIGGER, exitInfo2));
        exit2.setTransform(0, 16f, 45);

        exit3.createFixture(PhysicsManager.createRectangleShape(0.5f, 0.5f), 1000);
        ExitInfo exitInfo3 = new ExitInfo(RoomExit.Direction.DOWN_RIGHT, gameStage);
        exit3.setUserData(new PhysicsBodyData(PhysicsBodyData.DataType.ROOM_TRIGGER, exitInfo3));
        exit3.setTransform(32f, 0, 45);

        exit4.createFixture(PhysicsManager.createRectangleShape(0.5f, 0.5f), 1000);
        ExitInfo exitInfo4 = new ExitInfo(RoomExit.Direction.UP_RIGHT, gameStage);
        exit4.setUserData(new PhysicsBodyData(PhysicsBodyData.DataType.ROOM_TRIGGER, exitInfo4));
        exit4.setTransform(32f, 16f, 45);
    }
}