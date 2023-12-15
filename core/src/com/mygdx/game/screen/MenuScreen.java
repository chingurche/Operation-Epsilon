package com.mygdx.game.screen;

import static com.mygdx.game.utils.StaticValues.gameTime;
import static com.mygdx.game.utils.StaticValues.screenSize;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.audio.AudioObserver;
import com.mygdx.game.components.ComponentObserver;
import com.mygdx.game.entities.EntityConfig;
import com.mygdx.game.manager.ResourceManager;
import com.mygdx.game.utils.Button;

public class MenuScreen extends BaseScreen implements ComponentObserver {
    private Animation<Texture> backgroundAnimation;
    public Texture menu;
    private Button startButton;
    private Button exitButton;
    private OrthographicCamera hudCamera;

    private GameScreen gameScreen;

    public MenuScreen(MyGdxGame gdxGame, Batch batch, Batch hudBatch) {
        super(gdxGame, batch, hudBatch);

        hudCamera = new OrthographicCamera();
        hudCamera.setToOrtho(false, screenSize.x, screenSize.y);

        loadBackground("scripts/menubackground.json");
        ResourceManager.loadTextureAsset("textures/menu/menu.png");
        menu = ResourceManager.getTextureAsset("textures/menu/menu.png");
        startButton = new Button(new Rectangle(screenSize.x / 2 - 90, screenSize.y / 2 - 32,
                180, 64), "textures/menu/play_on.png", "textures/menu/play_off.png");
        exitButton = new Button(new Rectangle(screenSize.x / 2 - 90, screenSize.y / 2 - 232,
                180, 64), "textures/menu/quit_on.png", "textures/menu/quit_off.png");
    }


    @Override
    public void onNotify(String value, ComponentEvent event) {

    }

    @Override
    public void show() {
        super.show();

        notify(AudioObserver.AudioCommand.MUSIC_LOAD, AudioObserver.AudioTypeEvent.MENU_THEME);
        notify(AudioObserver.AudioCommand.MUSIC_PLAY_LOOP, AudioObserver.AudioTypeEvent.MENU_THEME);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void hide() {
        super.hide();

        notify(AudioObserver.AudioCommand.MUSIC_STOP_ALL, AudioObserver.AudioTypeEvent.NO_ACCESS);
    }

    private void update(float delta) {
        gameTime += delta;
        hudBatch.setProjectionMatrix(hudCamera.combined);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        hudBatch.begin();
        hudBatch.draw(backgroundAnimation.getKeyFrame(gameTime),
                screenSize.x / 2 - screenSize.y * 1.775f / 2,
                0, screenSize.y * 1.775f, screenSize.y);
        hudBatch.draw(menu, screenSize.x / 2 - 639,
                screenSize.y / 2 - 360, 1278, 720);
        hudBatch.end();

        startButton.render(hudBatch);
        exitButton.render(hudBatch);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenY = (int) screenSize.y - screenY;
        startButton.start(screenX, screenY);
        exitButton.start(screenX, screenY);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenY = (int) screenSize.y - screenY;
        if (startButton.end(screenX, screenY)) {
            gameScreen = new GameScreen(game, batch, hudBatch);
            game.setScreen(gameScreen);
        }
        if (exitButton.end(screenX, screenY)) {
            Gdx.app.exit();
        }
        return true;
    }

    private void loadBackground(String configPath) {
        Json json = new Json();
        EntityConfig.AnimationConfig config = json.fromJson(EntityConfig.AnimationConfig.class, Gdx.files.internal(configPath));

        Array<Texture> textures = new Array<>();

        for (String path : config.getTexturePaths()) {
            ResourceManager.loadTextureAsset(path);
            textures.add(ResourceManager.getTextureAsset(path));
        }

        backgroundAnimation = new Animation<>(config.getStateTime(), textures);
        backgroundAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }

}
