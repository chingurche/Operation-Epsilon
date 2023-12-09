package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.manager.ResourceManager;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.screen.MenuScreen;

public class MyGdxGame extends Game {
	private SpriteBatch batch;
	private ResourceManager resourceManager;
	public MenuScreen menuScreen;
	public GameScreen gameScreen;

	public SpriteBatch getBatch() {
		return batch;
	}
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		resourceManager = new ResourceManager();

		//menuScreen = new MenuScreen(this, resourceManager);
		gameScreen = new GameScreen(this, batch);

		this.setScreen(gameScreen);
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
