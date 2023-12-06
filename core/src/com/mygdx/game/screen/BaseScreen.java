package com.mygdx.game.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.audio.AudioManager;
import com.mygdx.game.audio.AudioSubject;
import com.mygdx.game.audio.AudioObserver;
import com.mygdx.game.manager.ResourceManager;

public class BaseScreen implements Screen, AudioSubject {
    protected final MyGdxGame gdxGame;
    protected ResourceManager resourceManager;
    protected OrthographicCamera camera;
    protected Viewport viewport;
    protected Stage stage;

    private Array<AudioObserver> observers;

    public BaseScreen(MyGdxGame gdxGame, ResourceManager resourceManager) {
        this.gdxGame = gdxGame;
        this.resourceManager = resourceManager;

        observers = new Array<>();
        this.addObserver(AudioManager.getInstance());
    }

    @Override
    public void addObserver(AudioObserver audioObserver) {
        observers.add(audioObserver);
    }

    @Override
    public void removeObserver(AudioObserver audioObserver) {
        observers.removeValue(audioObserver, true);
    }

    @Override
    public void removeAllObservers() {
        observers.removeAll(observers, true);
    }

    @Override
    public void notify(AudioObserver.AudioCommand command, AudioObserver.AudioTypeEvent event) {
        for(AudioObserver observer: observers){
            observer.onNotify(command, event);
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
