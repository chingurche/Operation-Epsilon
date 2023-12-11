package com.mygdx.game.components;

import static com.mygdx.game.utils.StaticValues.gameTime;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityConfig;
import com.mygdx.game.manager.ResourceManager;

import java.util.Hashtable;

public abstract class GraphicsComponent extends ComponentSubject implements Component {
    public enum Direction {
        FORWARD,
        LEFT,
        RIGHT,
        BACK
    }

    protected Json json;

    private Hashtable<Entity.AnimationType, Animation<Texture>> animations = new Hashtable<>();
    protected Entity.State currentState;
    private Animation<Texture> currentAnimation;
    protected Texture currentFrame;
    private Direction direction = Direction.FORWARD;
    protected Vector2 currentPosition;

    protected GraphicsComponent() {
        currentPosition = new Vector2(100, 100);
        json = new Json();
    }

    protected void setDirection(Vector2 direction) {
        if (direction.x > 0  && direction.y > 0) {
            if (direction.x > direction.y) {
                this.direction = Direction.RIGHT;
            }
            else {
                this.direction = Direction.BACK;
            }
        }
        else if (direction.x < 0  && direction.y > 0) {
            if (Math.abs(direction.x) > direction.y) {
                this.direction = Direction.LEFT;
            }
            else {
                this.direction = Direction.BACK;
            }
        }
        else if (direction.x > 0  && direction.y < 0) {
            if (direction.x > Math.abs(direction.y)) {
                this.direction = Direction.RIGHT;
            }
            else {
                this.direction = Direction.FORWARD;
            }
        }
        else if (direction.x < 0  && direction.y < 0) {
            if (Math.abs(direction.x) > Math.abs(direction.y)) {
                this.direction = Direction.LEFT;
            }
            else {
                this.direction = Direction.FORWARD;
            }
        }

        updateAnimation();
    }

    protected void updateAnimation() {
        if (currentState == Entity.State.IDLE) {
            currentAnimation = animations.get(Entity.AnimationType.IDLE);
            return;
        }

        switch (direction) {
            case FORWARD:
                currentAnimation = animations.get(Entity.AnimationType.WALKING_FORWARD);
                break;
            case LEFT:
                currentAnimation = animations.get(Entity.AnimationType.WALKING_LEFT);
                break;
            case RIGHT:
                currentAnimation = animations.get(Entity.AnimationType.WALKING_RIGHT);
                break;
            case BACK:
                currentAnimation = animations.get(Entity.AnimationType.WALKING_BACK);
                break;
        }
    }

    protected void updateFrame(float delta) {
        currentFrame = currentAnimation.getKeyFrame(gameTime);
    }

    protected void loadAnimation(EntityConfig.AnimationConfig config) {
        Array<Texture> textures = new Array<>();

        for (String path : config.getTexturePaths()) {
            ResourceManager.loadTextureAsset(path);
            textures.add(ResourceManager.getTextureAsset(path));
        }

        Animation<Texture> animation = new Animation<>(config.getStateTime(), textures);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        animations.put(config.getAnimationType(), animation);
    }

    public void setTexture(Texture texture) { currentFrame = texture; }

    public abstract void update(Entity entity, Batch batch, float delta);
}
