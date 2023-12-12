package com.mygdx.game.physics.info;

import com.mygdx.game.map.GameStage;
import com.mygdx.game.map.RoomExit;

public class ExitInfo {
    private RoomExit.Direction direction;
    private GameStage gameStage;

    public ExitInfo(RoomExit.Direction direction, GameStage gameStage) {
        this.direction = direction;
        this.gameStage = gameStage;
    }

    public RoomExit.Direction getDirection() {
        return direction;
    }

    public GameStage getGameStage() {
        return gameStage;
    }
}
