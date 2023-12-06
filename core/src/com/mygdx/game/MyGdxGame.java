package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.manager.ResourceManager;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.screen.MenuScreen;

public class MyGdxGame extends Game {
	private SpriteBatch batch;
	private ResourceManager resourceManager;
	private MenuScreen menuScreen;
	private GameScreen gameScreen;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		resourceManager = new ResourceManager();

		menuScreen = new MenuScreen(this, resourceManager);

		this.setScreen(menuScreen);
	}

	@Override
	public void render() {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(img, 1200, 0);
		batch.end();
	}

	@Override
	public void dispose () {
		/*batch.dispose();
		img.dispose();*/
	}
}
