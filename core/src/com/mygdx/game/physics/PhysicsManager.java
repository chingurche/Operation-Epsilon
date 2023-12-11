package com.mygdx.game.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class PhysicsManager {
    public static PolygonShape createRectangleShape(float width, float height) {
        Vector2[] vertices = new Vector2[] {
                new Vector2(width/2, height/2),
                new Vector2(-width/2, height/2),
                new Vector2(-width/2, -height/2),
                new Vector2(width/2, -height/2)
        };

        PolygonShape shape = new PolygonShape();
        shape.set(vertices);
        return shape;
    }
}
