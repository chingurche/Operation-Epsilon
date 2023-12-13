package com.mygdx.game.weapons;

import com.badlogic.gdx.physics.box2d.World;

public class WeaponFactory {
    public enum WeaponType {
        AK47("scripts/weapons/ak47.json");

        private String configPath;

        WeaponType(String configPath) {
            this.configPath = configPath;
        }

        public String getValue() {
            return configPath;
        }
    }

    private static WeaponFactory instance;

    private WeaponFactory() {

    }

    public static WeaponFactory getInstance() {
        if (instance == null) {
            instance = new WeaponFactory();
        }
        return instance;
    }

    public Weapon getWeapon(WeaponType type, World world) {
        Weapon weapon;

        switch (type) {
            case AK47:
                weapon = new RangedWeapon(world);
                weapon.setWeaponConfig(Weapon.getWeaponConfig(type.getValue()));
                weapon.loadConfig();

                return weapon;
            default:
                return null;
        }
    }
}
