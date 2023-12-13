package com.mygdx.game.weapons;

public class WeaponConfig {
    private String texturePath;

    private float damage;
    private float maxInterval;
    private int maxMagazineSize;

    public WeaponConfig() {

    }

    public WeaponConfig(WeaponConfig config) {
        texturePath = config.texturePath;

        damage = config.getDamage();
        maxInterval = config.getMaxInterval();
        maxMagazineSize = config.getMaxMagazineSize();
    }

    public String getTexturePath() {
        return texturePath;
    }

    public float getDamage() {
        return damage;
    }

    public float getMaxInterval() {
        return maxInterval;
    }

    public int getMaxMagazineSize() {
        return maxMagazineSize;
    }
}