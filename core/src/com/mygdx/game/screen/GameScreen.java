package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.audio.AudioManager;
import com.mygdx.game.components.ComponentObserver;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityFactory;
import com.mygdx.game.manager.ResourceManager;

public class GameScreen extends BaseScreen implements ComponentObserver {
    protected OrthogonalTiledMapRenderer mapRenderer = null;
    private MyGdxGame game;
    private Entity player;

    public GameScreen(MyGdxGame gdxGame, ResourceManager resourceManager) {
        super(gdxGame, resourceManager);
        game = gdxGame;



        player = EntityFactory.getInstance().getEntity(EntityFactory.EntityType.PLAYER);
    }

    @Override
    public void onNotify(String value, ComponentEvent event) {

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //mapRenderer.setView(camera);
        /*ScreenUtils.clear(1, 0, 0, 1);
        game.getBatch().begin();
        game.getBatch().draw(img, 100, 100);
        game.getBatch().end();*/
        //player.update(mapManager, mapRenderer.getBatch(), delta);
    }
}
