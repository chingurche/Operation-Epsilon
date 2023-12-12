package com.mygdx.game.physics;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.physics.info.ExitInfo;

public class PhysicsBodyData {
    public enum DataType {
        ROOM_TRIGGER,
        PLAYER_ENTITY
    }

    private Object data;
    private DataType dataType;

    public PhysicsBodyData(DataType dataType, Object data) {
        this.dataType = dataType;
        this.data = data;
    }

    public void interact(PhysicsBodyData other) {
        switch (dataType) {
            case PLAYER_ENTITY:
                if (other.getDataType() == DataType.ROOM_TRIGGER) {
                    ExitInfo exitInfo = (ExitInfo) other.getData();
                    exitInfo.getGameStage().changeRoom(exitInfo.getDirection());
                }
                break;
        }
    }

    public Object getData() {
        return data;
    }

    public DataType getDataType() {
        return dataType;
    }
}
