package com.mygdx.game;

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
	private Texture img;

	public SpriteBatch getBatch() {
		return batch;
	}
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		resourceManager = new ResourceManager();

		gameScreen = new GameScreen(this, resourceManager);

		this.setScreen(gameScreen);
	}

	@Override
	public void dispose () {
		/*batch.dispose();
		img.dispose();*/
	}
}
