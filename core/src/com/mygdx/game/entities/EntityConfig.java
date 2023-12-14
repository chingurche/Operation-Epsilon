package com.mygdx.game.entities;

import java.io.Serializable;

public class EntityConfig {
    private int maxHealthPoints;
    private float speed;
    private AnimationConfig[] animationConfigs;

    public EntityConfig() {

    }

    public EntityConfig(EntityConfig config) {
        maxHealthPoints = config.getMaxHealthPoints();
        speed = config.getSpeed();
        animationConfigs = config.getAnimationConfigs();
    }


    public static class AnimationConfig {
        private Entity.AnimationType animationType;
        private float stateTime;
        private String[] texturePaths;

        public AnimationConfig() {

        }

        public AnimationConfig(AnimationConfig config) {
            animationType = config.getAnimationType();
            stateTime = config.getStateTime();
            texturePaths = config.getTexturePaths();
        }

        public Entity.AnimationType getAnimationType() {
            return animationType;
        }

        public float getStateTime() {
            return stateTime;
        }

        public String[] getTexturePaths() {
            return texturePaths;
        }
    }

    public float getSpeed() {
        return speed;
    }

    public int getMaxHealthPoints() {
        return maxHealthPoints;
    }

    public AnimationConfig[] getAnimationConfigs() {
        return animationConfigs;
    }
}
