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
import com.mygdx.game.weapons.RangedWeapon;

public class GameScreen extends BaseScreen implements ComponentObserver {
    private final float cameraViewport = 6;
    private OrthographicCamera camera;
    private OrthographicCamera hudCamera;
    private Json json;

    private GameStage gameStage;
    private Joystick moveJoystick;
    private Joystick attackJoystick;
    private Entity player;

    public GameScreen(MyGdxGame gdxGame, Batch batch, Batch hudBatch) {
        super(gdxGame, batch, hudBatch);

        json = new Json();


        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenSize.x / cameraViewport, screenSize.y / cameraViewport);

        hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(true, screenSize.x, screenSize.y);


        gameStage = new GameStage();
        gameStage.setCamera(camera);

        player = EntityFactory.getInstance().getEntity(EntityFactory.EntityType.PLAYER, gameStage.getWorld());
        player.sendMessage(Component.MESSAGE.INIT_POSITION, json.toJson(new Vector2(16, 8)));

        gameStage.setPlayer(player);


        moveJoystick = new Joystick(300, screenSize.y - 300, 200);
        attackJoystick = new Joystick(screenSize.x - 300, screenSize.y - 300, 200);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void onNotify(String value, ComponentEvent event) {

    }

    @Override
    public void show() {
        notify(AudioObserver.AudioCommand.MUSIC_LOAD, AudioObserver.AudioTypeEvent.MENU_THEME);
        notify(AudioObserver.AudioCommand.MUSIC_PLAY_LOOP, AudioObserver.AudioTypeEvent.MENU_THEME);
        notify(AudioObserver.AudioCommand.MUSIC_LOAD, AudioObserver.AudioTypeEvent.SHOOT);
    }

    private void update(float delta) {
        camera.position.set(new Vector3(128, 64, 0));
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        hudBatch.setProjectionMatrix(hudCamera.combined);
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

        moveJoystick.render(hudBatch);
        attackJoystick.render(hudBatch);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (screenX < screenSize.x / 2) {
            moveJoystick.start(screenX, screenY);
            player.sendMessage(Component.MESSAGE.ENTITY_DIRECTION, json.toJson(moveJoystick.getDirection()));
            return true;
        }
        else {
            attackJoystick.start(screenX, screenY);
            player.sendMessage(Component.MESSAGE.ATTACK_STATUS, json.toJson(true));
            player.sendMessage(Component.MESSAGE.ATTACK_DIRECTION, json.toJson(attackJoystick.getDirection()));
            return true;
        }
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (screenX < screenSize.x / 2) {
            moveJoystick.dragged(screenX, screenY);
            player.sendMessage(Component.MESSAGE.ENTITY_DIRECTION, json.toJson(moveJoystick.getDirection()));
            return true;
        }
        else {
            attackJoystick.dragged(screenX, screenY);
            player.sendMessage(Component.MESSAGE.ATTACK_DIRECTION, json.toJson(attackJoystick.getDirection()));
            return true;
        }
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (screenX < screenSize.x / 2) {
            moveJoystick.end();
            player.sendMessage(Component.MESSAGE.ENTITY_DIRECTION, json.toJson(Vector2.Zero));
            return true;
        }
        else {
            attackJoystick.end();
            player.sendMessage(Component.MESSAGE.ATTACK_STATUS, json.toJson(false));
            return true;
        }
    }
}