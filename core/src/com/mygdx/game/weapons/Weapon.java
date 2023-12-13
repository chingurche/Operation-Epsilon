package com.mygdx.game.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.entities.EntityConfig;
import com.mygdx.game.manager.ResourceManager;

public abstract class Weapon {
    protected WeaponConfig weaponConfig;
    protected Vector2 position;
    protected Vector2 direction;
    protected Texture texture;

    protected float damage;
    protected float maxInterval;
    protected float interval = 0;

    public abstract void input(Batch batch);

    public abstract void render(Batch batch, float delta);

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }

    public void loadConfig() {
        ResourceManager.loadTextureAsset(weaponConfig.getTexturePath());
        texture = ResourceManager.getTextureAsset(weaponConfig.getTexturePath());

        damage = weaponConfig.getDamage();
        maxInterval = weaponConfig.getMaxInterval();
    }

    public void setWeaponConfig(WeaponConfig weaponConfig) {
        this.weaponConfig = weaponConfig;
    }

    public static WeaponConfig getWeaponConfig(String path) {
        Json json = new Json();
        WeaponConfig config = json.fromJson(WeaponConfig.class, Gdx.files.internal(path));
        return config;
    }
}