package com.mygdx.game.weapons;

import static com.mygdx.game.utils.StaticValues.screenSize;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.entities.EntityConfig;
import com.mygdx.game.manager.ResourceManager;

public abstract class Weapon {
    public enum Rarity {
        COMMON,
        UNCOMMON,
        RARE,
        EPIC,
        LEGENDARY
    }

    protected WeaponConfig weaponConfig;
    protected Texture rarityTexture;
    protected Vector2 position;
    protected Vector2 direction = new Vector2(0, 0);
    protected Texture texture;

    protected int damage;
    protected float maxInterval;
    protected float interval = 0;

    public abstract void input(Batch batch);

    public abstract void render(Batch batch, float delta);

    public void renderUI(Batch hudBatch, float delta) {
        hudBatch.begin();
        hudBatch.draw(rarityTexture, screenSize.x - 250, 250, 200, -200);
        hudBatch.draw(texture, screenSize.x - 230, 230, 160, -160);
        hudBatch.end();
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }

    public void loadConfig() {
        ResourceManager.loadTextureAsset(weaponConfig.getTexturePath());
        texture = ResourceManager.getTextureAsset(weaponConfig.getTexturePath());

        loadRarity(weaponConfig.getRarity());
        damage = weaponConfig.getDamage();
        maxInterval = weaponConfig.getMaxInterval();
    }

    private void loadRarity(Rarity rarity) {
        String path = null;
        switch (rarity) {
            case COMMON:
                path = "textures/weapons/rarity/1.png";
                break;
            case UNCOMMON:
                path = "textures/weapons/rarity/2.png";
                break;
            case RARE:
                path = "textures/weapons/rarity/3.png";
                break;
            case EPIC:
                path = "textures/weapons/rarity/4.png";
                break;
            case LEGENDARY:
                path = "textures/weapons/rarity/5.png";
                break;
        }

        ResourceManager.loadTextureAsset(path);
        rarityTexture = ResourceManager.getTextureAsset(path);
    }

    public void setWeaponConfig(WeaponConfig weaponConfig) {
        this.weaponConfig = weaponConfig;
    }

    public static WeaponConfig getWeaponConfig(String path) {
        Json json = new Json();
        WeaponConfig config = json.fromJson(WeaponConfig.class, Gdx.files.internal(path));
        return config;
    }

    public Texture[] getTextures() {
        return new Texture[] { texture, rarityTexture };
    }
}