package com.mygdx.game.screen;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.ComponentObserver;
import com.mygdx.game.manager.ResourceManager;

public class MenuScreen extends BaseScreen implements ComponentObserver {
    public MenuScreen(MyGdxGame gdxGame, Batch batch) {
        super(gdxGame, batch);
    }


    @Override
    public void onNotify(String value, ComponentEvent event) {

    }
}
