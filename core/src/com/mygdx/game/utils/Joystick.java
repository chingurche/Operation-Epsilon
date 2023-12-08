package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.manager.ResourceManager;

public class Joystick {
    private Texture joystickTexture, handleTexture;

    public Joystick() {
        ResourceManager.loadTextureAsset("joystick/JoystickSplitted.png");
        joystickTexture = ResourceManager.getTextureAsset("joystick/JoystickSplitted.png");
        ResourceManager.loadTextureAsset("joystick/SmallHandleFilledGrey.png");
        handleTexture = ResourceManager.getTextureAsset("joystick/SmallHandleFilledGrey.png");
    }
}
