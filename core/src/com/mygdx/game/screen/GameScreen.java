package com.mygdx.game.screen;

import static com.mygdx.game.utils.Constants.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.audio.AudioManager;
import com.mygdx.game.components.ComponentObserver;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityFactory;
import com.mygdx.game.manager.ResourceManager;
import com.mygdx.game.map.GameStage;
import com.mygdx.game.utils.Joystick;

import java.util.ArrayList;

public class GameScreen extends BaseScreen implements ComponentObserver {
    private final float cameraViewport = 3;
    private OrthographicCamera camera;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer = new Box2DDebugRenderer();
    protected OrthogonalTiledMapRenderer mapRenderer;
    //private ArrayList<GameStage> gameStages;
    private MyGdxGame game;
    private Entity player;


    private TiledMap map;


    public GameScreen(MyGdxGame gdxGame, ResourceManager resourceManager) {
        super(gdxGame, resourceManager);
        game = gdxGame;

        world = new World(new Vector2(0, 0), false);

        camera = new OrthographicCamera();
        Vector2 screenSize = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(true, screenSize.x / cameraViewport, screenSize.y / cameraViewport);

        ResourceManager.loadMapAsset("location/room2.tmx");
        map = ResourceManager.getMapAsset("location/room2.tmx");
        ResourceManager.parseMapObjects(map, world);
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        //GameStage stage = new GameStage();
        //gameStages.add(stage);

        player = EntityFactory.getInstance().getEntity(EntityFactory.EntityType.PLAYER);
    }

    @Override
    public void onNotify(String value, ComponentEvent event) {

    }

    @Override
    public void show() {

    }

    private void update() {
        world.step(1 / 60f, 6, 2);
        camera.position.set(new Vector3(256, 128, 0));
        camera.update();

        gdxGame.getBatch().setProjectionMatrix(camera.combined);
        mapRenderer.setView(camera);
    }

    @Override
    public void render(float delta) {
        this.update();

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.render();

        game.getBatch().begin();



        game.getBatch().end();

        box2DDebugRenderer.render(world, camera.combined.scl(PPM));
    }
}