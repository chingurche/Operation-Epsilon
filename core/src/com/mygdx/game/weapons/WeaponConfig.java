package com.mygdx.game.weapons;

import com.mygdx.game.audio.AudioObserver;

public class WeaponConfig {
    private String texturePath;
    private AudioObserver.AudioTypeEvent sound;
    private Weapon.Rarity rarity;

    private int damage;
    private float maxInterval;

    public WeaponConfig() {

    }

    public WeaponConfig(WeaponConfig config) {
        texturePath = config.texturePath;
        sound = config.getSound();
        rarity = config.getRarity();

        damage = config.getDamage();
        maxInterval = config.getMaxInterval();
    }

    public String getTexturePath() {
        return texturePath;
    }

    public AudioObserver.AudioTypeEvent getSound() {
            return sound;
    }

    public Weapon.Rarity getRarity() {
        return rarity;
    }

    public int getDamage() {
        return damage;
    }

    public float getMaxInterval() {
        return maxInterval;
    }
}