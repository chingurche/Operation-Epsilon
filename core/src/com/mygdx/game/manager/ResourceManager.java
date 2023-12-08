package com.mygdx.game.manager;

import static com.mygdx.game.utils.Constants.PPM;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class ResourceManager {
    private static InternalFileHandleResolver filePathResolver =  new InternalFileHandleResolver();
    public static AssetManager assetManager = new AssetManager();

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

    public static void parseMapObjects(TiledMap tiledMap, World world) {
        MapObjects mapObjects = tiledMap.getLayers().get("objects").getObjects();

        for (MapObject mapObject : mapObjects) {
            try {
                createBody((PolygonMapObject) mapObject, world);
            }
            catch (ClassCastException e) {
                continue;
            }
        }
    }

    private static void createBody(PolygonMapObject polygonMapObject, World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bodyDef);
        Shape shape = createShape(polygonMapObject);
        body.createFixture(shape, 1000);
        shape.dispose();
    }

    private static Shape createShape(PolygonMapObject polygonMapObject) {
        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < worldVertices.length; i++) {
            worldVertices[i] = new Vector2(vertices[i * 2] / PPM, vertices[i * 2 + 1] / PPM);
        }

        PolygonShape shape = new PolygonShape();
        shape.set(worldVertices);
        return shape;
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

    public static boolean update() {
        return assetManager.update();
    }
}
