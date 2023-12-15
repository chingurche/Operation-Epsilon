package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.manager.ResourceManager;

public class Button {
    private boolean isPressed = false;

    private Rectangle rect;
    private Texture texture;
    private Texture texturePressed;

    public Button(Rectangle rect, String path1, String path2) {
        this.rect = rect;
        ResourceManager.loadTextureAsset(path1);
        texture = ResourceManager.getTextureAsset(path1);
        ResourceManager.loadTextureAsset(path2);
        texturePressed = ResourceManager.getTextureAsset(path2);
    }

    public void render(Batch batch) {
        batch.begin();
        batch.draw(isPressed ? texturePressed : texture,
                rect.x, rect.y, rect.width, rect.height);
        batch.end();
    }

    public void start(int x, int y) {
        if (rect.contains(x, y)) {
            isPressed = true;
        }
    }

    public boolean end(int x, int y) {
        if (rect.contains(x, y) && isPressed) {
            isPressed = false;
            return true;
        }
        isPressed = false;
        return false;
    }
}
