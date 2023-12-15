package com.mygdx.game.physics;

import com.badlogic.gdx.utils.Json;
import com.mygdx.game.components.Component;
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

    private Json json;

    private Object data;
    private DataType dataType;

    public PhysicsBodyData(DataType dataType, Object data) {
        json = new Json();

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
                } else if (other.getDataType() == DataType.ENEMY_ENTITY) {
                    Entity player = (Entity) data;
                    player.sendMessage(Component.MESSAGE.GET_DAMAGE, "skibididodopdopeses");
                }
                break;
            case BULLET:
                if (other != null && other.getDataType() == DataType.PLAYER_ENTITY) { return; }

                RangedWeapon.Bullet bullet = (RangedWeapon.Bullet) data;

                if (other != null && other.getDataType() == DataType.ENEMY_ENTITY) {
                    Entity enemy = (Entity) other.getData();
                    enemy.sendMessage(Component.MESSAGE.GET_DAMAGE, json.toJson(bullet.getDamage()));
                }

                bullet.isActive = false;
        }
    }

    public Object getData() {
        return data;
    }

    public DataType getDataType() {
        return dataType;
    }
}
