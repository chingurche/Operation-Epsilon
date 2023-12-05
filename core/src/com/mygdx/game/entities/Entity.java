package com.mygdx.game.entities;

import com.mygdx.game.components.GraphicsComponent;
import com.mygdx.game.components.InputComponent;
import com.mygdx.game.components.PhysicsComponent;

public abstract class Entity {
    private InputComponent inputComponent;
    private GraphicsComponent graphicsComponent;
    private PhysicsComponent physicsComponent;
}
