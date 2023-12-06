package com.mygdx.game.screen;

import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.audio.AudioManager;
import com.mygdx.game.components.ComponentObserver;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityFactory;
import com.mygdx.game.manager.ResourceManager;
import com.mygdx.game.map.Map;
import com.mygdx.game.map.MapManager;

public class GameScreen extends BaseScreen implements ComponentObserver {
    protected OrthogonalTiledMapRenderer mapRenderer = null;
    protected MapManager mapManager;
    private MyGdxGame game;
    private Entity player;

    public GameScreen(MyGdxGame gdxGame, ResourceManager resourceManager) {
        super(gdxGame, resourceManager);
        game = gdxGame;
        mapManager = new MapManager();

        player = EntityFactory.getInstance().getEntity(EntityFactory.EntityType.PLAYER);
    }

    @Override
    public void show() {
        if (mapRenderer == null) {
            mapRenderer = new OrthogonalTiledMapRenderer(mapManager.getCurrentTiledMap(), Map.UNIT_SCALE);
        }
    }

    @Override
    public void render(float delta) {
        player.update(mapManager, mapRenderer.getBatch(), delta);
    }

    @Override
    public void onNotify(String value, ComponentEvent event) {

    }
}
