package com.mygdx.game.manager;

import static com.mygdx.game.utils.StaticValues.PPM;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.components.Component;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityFactory;

import java.util.ArrayList;


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

    public static Array<Body> parseStaticObjects(TiledMap tiledMap, World world) {
        MapObjects mapObjects = tiledMap.getLayers().get("objects").getObjects();
        Array<Body> worldBodies = new Array<>();

        for (MapObject mapObject : mapObjects) {
            if (mapObject instanceof PolygonMapObject && mapObject.getName() == null) {
                Body worldBody = createStaticBody((PolygonMapObject) mapObject, world);
                worldBodies.add(worldBody);
            }
        }

        return worldBodies;
    }

    public static ArrayList<Entity> parseEntities(TiledMap tiledMap, World world) {
        Json json = new Json();

        MapLayer mapLayer = tiledMap.getLayers().get("objects");
        if (mapLayer == null) {
            return new ArrayList<>();
        }

        MapObjects mapObjects = mapLayer.getObjects();
        ArrayList<Entity> entities = new ArrayList<>();

        for (MapObject mapObject : mapObjects) {
            String mapObjectName = mapObject.getName();

            if (mapObjectName == null) { continue; }

            switch (mapObjectName) {
                case "baseenemy":
                    Entity entity = EntityFactory.getInstance().getEntity(EntityFactory.EntityType.BASE_ENEMY, world);
                    Vector2 position = new Vector2(
                            mapObject.getProperties().get("x", float.class) / PPM + 1,
                            mapObject.getProperties().get("y", float.class) / PPM + 1);
                    entity.sendMessage(Component.MESSAGE.INIT_POSITION, json.toJson(position));
                    entities.add(entity);
                    break;
            }
        }

        return entities;
    }

    public static Body createBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        Body body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setPosition(Vector2.Zero);
        shape.setRadius(1);
        body.createFixture(shape, 1000);

        return body;
    }

    private static Body createStaticBody(PolygonMapObject polygonMapObject, World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bodyDef);

        world.getBodyCount();
        Shape shape = createPolygonShape(polygonMapObject);
        body.createFixture(shape, 1000);
        shape.dispose();

        return body;
    }

    private static Shape createPolygonShape(PolygonMapObject polygonMapObject) {
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
