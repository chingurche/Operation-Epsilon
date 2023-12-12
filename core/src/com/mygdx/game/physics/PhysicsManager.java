package com.mygdx.game.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class PhysicsManager {
    public static PolygonShape createRectangleShape(float width, float height) {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);
        return shape;
    }
}
