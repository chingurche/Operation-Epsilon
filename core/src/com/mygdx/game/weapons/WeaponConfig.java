package com.mygdx.game.weapons;

public class WeaponConfig {
    private float interval;

    public WeaponConfig(WeaponConfig config) {
        interval = config.getInterval();
    }

    public float getInterval() {
        return interval;
    }
}
