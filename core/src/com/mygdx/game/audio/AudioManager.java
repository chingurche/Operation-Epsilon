package com.mygdx.game.audio;

import com.badlogic.gdx.audio.Music;
import com.mygdx.game.manager.ResourceManager;
import com.mygdx.game.manager.PreferenceManager;

import java.util.Hashtable;

public class AudioManager implements AudioObserver {

    private static AudioManager instance = null;

    private Music currentMusic;

    private Hashtable<String, Music> queuedMusic;

    private AudioManager() {
        queuedMusic = new Hashtable<>();
    }

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }

        return instance;
    }

    private void checkMusicEnabled(Music music) {
        if (!PreferenceManager.getInstance().isMusicEnabled()) {
            music.stop();
        } else {
            music.play();
        }
    }

    public void setCurrentMusic(Music currentMusic) {
        this.currentMusic = currentMusic;
    }

    @Override
    public void onNotify(AudioCommand command, AudioTypeEvent event) {
        switch (command) {
            case MUSIC_LOAD:
                ResourceManager.loadMusicAsset(event.getValue());
                break;
            case MUSIC_PLAY_ONCE:
                playMusic(false, event.getValue());
                break;
            case MUSIC_PLAY_LOOP:
                playMusic(true, event.getValue());
                break;
            case MUSIC_STOP:
                Music music = queuedMusic.get(event.getValue());
                if (music != null) {
                    music.stop();
                }
                break;
            case MUSIC_STOP_ALL:
                for (Music musicStop : queuedMusic.values()) {
                    musicStop.stop();
                }
                break;
            default:
                break;
        }
    }

    private void playMusic(boolean isLooping, String fullFilePath) {
        Music music = queuedMusic.get(fullFilePath);
        if (music != null) {
            music.setLooping(isLooping);
            music.setVolume(PreferenceManager.getMusicVolume());
            checkMusicEnabled(music);
            setCurrentMusic(music);
        } else if (ResourceManager.isAssetLoaded(fullFilePath)) {
            music = ResourceManager.getMusicAsset(fullFilePath);
            music.setLooping(isLooping);
            music.setVolume(PreferenceManager.getMusicVolume());
            checkMusicEnabled(music);
            queuedMusic.put(fullFilePath, music);
            setCurrentMusic(music);
        }
    }


}
