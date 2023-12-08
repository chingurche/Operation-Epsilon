package com.mygdx.game.components;

import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.entities.Entity;

public abstract class InputComponent extends ComponentSubject implements Component {
    public abstract void update(Entity entity, float delta);
}
