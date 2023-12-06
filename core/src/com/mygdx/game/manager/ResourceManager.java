package com.mygdx.game.manager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class ResourceManager {
    private static InternalFileHandleResolver filePathResolver =  new InternalFileHandleResolver();
    private static AssetManager assetManager = new AssetManager();

    public static void loadMapAsset(String mapFilenamePath) {
        if (mapFilenamePath == null || mapFilenamePath.isEmpty()) {
            return;
        }

        if (assetManager.isLoaded(mapFilenamePath)) {
            return;
        }

        assetManager.setLoader(TiledMap.class, new TmxMapLoader(filePathResolver));
        assetManager.load(mapFilenamePath, TiledMap.class);
        assetManager.finishLoadingAsset(mapFilenamePath);
    }

    public static TiledMap getMapAsset(String mapFilenamePath) {
        TiledMap map = assetManager.get(mapFilenamePath, TiledMap.class);
        return map;
    }

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
        Music music = assetManager.get(musicFilenamePath, Music.class);

        return music;
    }

    public static void loadTextureAsset(String textureFilenamePath) {
        if (textureFilenamePath == null || textureFilenamePath.isEmpty()) {
            return;
        }

        if (assetManager.isLoaded(textureFilenamePath)) {
            return;
        }

        assetManager.setLoader(Texture.class, new TextureLoader(filePathResolver));
        assetManager.load(textureFilenamePath, Texture.class);
        assetManager.finishLoadingAsset(textureFilenamePath);
    }

    public static Texture getTextureAsset(String textureFilenamePath) {
        Texture texture = null;

        if (assetManager.isLoaded(textureFilenamePath)) {
            texture = assetManager.get(textureFilenamePath, Texture.class);
        }

        return texture;
    }

    public static boolean isAssetLoaded(String fileName) {
        return assetManager.isLoaded(fileName);
    }
}
