package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityConfig;
import com.mygdx.game.manager.ResourceManager;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.screen.MenuScreen;

public class MyGdxGame extends Game {
	private SpriteBatch batch;
	private SpriteBatch hudBatch;
	private ResourceManager resourceManager;
	public MenuScreen menuScreen;
	public GameScreen gameScreen;

	public SpriteBatch getBatch() {
		return batch;
	}
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		hudBatch = new SpriteBatch();
		resourceManager = new ResourceManager();

		menuScreen = new MenuScreen(this, batch, hudBatch);
		//gameScreen = new GameScreen(this, batch, hudBatch);

		setMainMenu();
	}

	public void setMainMenu() {
		this.setScreen(menuScreen);
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
