package com.mygdx.game.physics;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.physics.info.ExitInfo;
import com.mygdx.game.weapons.RangedWeapon;

public class PhysicsBodyData {
    public enum DataType {
        ROOM_TRIGGER,
        BULLET,
        PLAYER_ENTITY,
        ENEMY_ENTITY
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
                if (other == null) { return; }
                if (other.getDataType() == DataType.ROOM_TRIGGER) {
                    ExitInfo exitInfo = (ExitInfo) other.getData();
                    exitInfo.getGameStage().changeRoom(exitInfo.getDirection());
                }
                break;
            case BULLET:
                RangedWeapon.Bullet bullet = (RangedWeapon.Bullet) data;
        }
    }

    public Object getData() {
        return data;
    }

    public DataType getDataType() {
        return dataType;
    }
}
