package com.mygdx.game.map;

import com.badlogic.gdx.math.Vector2;

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

    public RoomExit(Direction direction, Room room) {
        this.direction = direction;
        this.room = room;
    }

    public Direction getDirection() {
        return direction;
    }

    public Room getNextRoom() {
        return room;
    }

    public static Vector2 toVector2(Direction drctn) {
        switch (drctn) {
            case UP_LEFT:
                return Vector2.X;
            case UP_RIGHT:
                return Vector2.Y;
            case DOWN_LEFT:
                return Vector2.X.scl(-1f);
            case DOWN_RIGHT:
                return Vector2.Y.scl(-1f);
        }
        return null;
    }
}
