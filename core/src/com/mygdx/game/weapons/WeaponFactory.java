package com.mygdx.game.weapons;

public class WeaponFactory {
    private static WeaponFactory instance;

    private WeaponFactory() {

    }

    public static WeaponFactory getInstance() {
        if (instance == null) {
            instance = new WeaponFactory();
        }
        return instance;
    }
}
