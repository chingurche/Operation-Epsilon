package com.mygdx.game.entities;

import java.io.Serializable;

public class EntityConfig {
    private AnimationConfig[] animationConfigs;

    public EntityConfig() {

    }

    public EntityConfig(EntityConfig config) {
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

    public AnimationConfig[] getAnimationConfigs() {
        return animationConfigs;
    }
}
