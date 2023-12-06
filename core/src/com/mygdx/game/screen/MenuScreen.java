package com.mygdx.game.screen;

import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.ComponentObserver;
import com.mygdx.game.manager.ResourceManager;

public class MenuScreen extends BaseScreen implements ComponentObserver {
    public MenuScreen(MyGdxGame gdxGame, ResourceManager resourceManager) {
        super(gdxGame, resourceManager);
    }


    @Override
    public void onNotify(String value, ComponentEvent event) {

    }
}
