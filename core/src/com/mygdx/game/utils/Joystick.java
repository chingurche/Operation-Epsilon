package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.manager.ResourceManager;

public class Joystick {
    private boolean active = false;
    private Circle circle;
    private Vector2 handlePosition;
    private Vector2 direction;
    private Texture joystickTexture, handleTexture;

    public Joystick(float x, float y, float radius) {
        circle = new Circle(x, y, radius);
        handlePosition = new Vector2(x, y);

        ResourceManager.loadTextureAsset("joystick/JoystickSplitted.png");
        joystickTexture = ResourceManager.getTextureAsset("joystick/JoystickSplitted.png");
        ResourceManager.loadTextureAsset("joystick/SmallHandleFilledGrey.png");
        handleTexture = ResourceManager.getTextureAsset("joystick/SmallHandleFilledGrey.png");
    }

    public Vector2 getDirection() {
        direction = handlePosition.sub(new Vector2(circle.x, circle.y)).nor();
        return direction;
    }

    public void start(int x, int y) {
        if (circle.contains(x, y)) {
            active = true;
            handlePosition.set(x, y);
        }
        active = false;
    }

    public void dragged(int x, int y) {
        if (active) {
            handlePosition.set(x, y);
        }
    }

    public void end() {
        handlePosition.set(0, 0);
    }

    public void render(Batch batch) {
        batch.begin();
        batch.draw(joystickTexture, circle.x - circle.radius * 2, circle.y - circle.radius * 2);
        batch.draw(handleTexture, handlePosition.x + 25, handlePosition.y + 25, 100, 100);
        batch.end();
    }
}