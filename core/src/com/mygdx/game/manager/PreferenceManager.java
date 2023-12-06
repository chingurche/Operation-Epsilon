package com.mygdx.game.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class PreferenceManager {
    private static PreferenceManager instance;

    public static PreferenceManager getInstance() {
        if (instance == null) {
            instance = new PreferenceManager();
        }

        return instance;
    }

    protected static Preferences getPrefs() {
        return Gdx.app.getPreferences("gdxGame");
    }

    public static float getMusicVolume() {
        return getPrefs().getFloat("music", 0.5f);
    }

    public boolean isMusicEnabled() {
        return getPrefs().getBoolean("music.enabled", true);
    }
}
