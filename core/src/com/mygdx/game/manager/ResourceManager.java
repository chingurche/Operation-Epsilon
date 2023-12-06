package com.mygdx.game.manager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;

public class ResourceManager {
    private static InternalFileHandleResolver filePathResolver =  new InternalFileHandleResolver();
    private static AssetManager assetManager = new AssetManager();


    public static void loadMusicAsset(String musicFilenamePath) {
        if (musicFilenamePath == null || musicFilenamePath.isEmpty()) {
            return;
        }

        if (assetManager.isLoaded(musicFilenamePath)) {
            return;
        }

        assetManager.setLoader(Music.class, new MusicLoader(filePathResolver));
        assetManager.load(musicFilenamePath, Music.class);
        assetManager.finishLoadingAsset(musicFilenamePath);
    }

    public static Music getMusicAsset(String musicFilenamePath) {
        Music music = null;

        music = assetManager.get(musicFilenamePath, Music.class);

        return music;
    }


    public static boolean isAssetLoaded(String fileName) {
        return assetManager.isLoaded(fileName);
    }
}
