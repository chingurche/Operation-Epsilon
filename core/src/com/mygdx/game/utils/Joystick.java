package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.manager.ResourceManager;

public class Joystick {
    private boolean active = false;
    private Circle circle;
    private Vector2 handleStartPosition;
    private Vector2 handlePosition;
    private Texture joystickTexture, handleTexture;

    public Joystick(float x, float y, float radius) {
        circle = new Circle(x, y, radius);
        handleStartPosition = new Vector2(x, y);
        handlePosition = handleStartPosition;

        ResourceManager.loadTextureAsset("joystick/JoystickSplitted.png");
        joystickTexture = ResourceManager.getTextureAsset("joystick/JoystickSplitted.png");
        ResourceManager.loadTextureAsset("joystick/SmallHandleFilledGrey.png");
        handleTexture = ResourceManager.getTextureAsset("joystick/SmallHandleFilledGrey.png");
    }

    public Vector2 getDirection() {
        Vector2 handleNewPosition = new Vector2(handlePosition);
        Vector2 direction = handleNewPosition.sub(new Vector2(circle.x, circle.y)).nor().scl(1, -1);
        return direction;
    }

    public Circle getCircle() {
        return circle;
    }

    public void start(int x, int y) {
        if (circle.contains(x, y)) {
            active = true;
            handlePosition.set(x, y);
            return;
        }
        active = false;
    }

    public void dragged(int x, int y) {
        if (active) {
            handlePosition.set(x, y);
        }
    }

    public void end() {
        handlePosition.set(circle.x, circle.y);
    }

    public void render(Batch batch) {
        batch.begin();
        batch.draw(joystickTexture, circle.x - circle.radius, circle.y - circle.radius, circle.radius * 2, circle.radius * 2);
        batch.draw(handleTexture, handlePosition.x - 50, handlePosition.y - 50, 100, 100);
        batch.end();
    }
}