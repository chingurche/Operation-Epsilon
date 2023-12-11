package com.mygdx.game.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.manager.ResourceManager;

public class RoomExit {

    public enum Direction {
        UP_LEFT,
        UP_RIGHT,
        DOWN_LEFT,
        DOWN_RIGHT;

        public Direction getOpposite() {
            if (this == UP_LEFT) {
                return DOWN_RIGHT;
            } else if (this == UP_RIGHT) {
                return DOWN_LEFT;
            } else if (this == DOWN_LEFT) {
                return UP_RIGHT;
            } else {
                return UP_LEFT;
            }
        }
    }

    private final Direction direction;
    private final Room room;
    private final Texture texture;

    public RoomExit(Direction direction, Room room) {
        this.direction = direction;
        this.room = room;
        ResourceManager.loadTextureAsset("textures/exites/exit1.png");
        texture = ResourceManager.getTextureAsset("textures/exites/exit1.png");
    }

    public void render(Batch batch) {
        batch.begin();
        switch (direction) {
            case UP_LEFT:
                batch.draw(texture, -32, 112, 48, 48);
                break;
            case UP_RIGHT:
                batch.draw(texture, 288, 112, -48, 48);
                break;
            case DOWN_LEFT:
                batch.draw(texture, -32, 16, 48, -48);
                break;
            case DOWN_RIGHT:
                batch.draw(texture, 288, 16, -48, -48);
                break;
        }
        batch.end();
    }

    public Direction getDirection() {
        return direction;
    }

    public Room getNextRoom() {
        return room;
    }

    public static Vector2 toVector2(Direction direction) {
        switch (direction) {
            case UP_LEFT:
                return new Vector2(1, 0);
            case UP_RIGHT:
                return new Vector2(0, 1);
            case DOWN_LEFT:
                return new Vector2(0, -1);
            case DOWN_RIGHT:
                return new Vector2(-1, 0);
        }
        return null;
    }
}
