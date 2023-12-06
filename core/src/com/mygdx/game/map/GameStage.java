package com.mygdx.game.map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Arrays;
import java.util.List;

public class GameStage {
    private Array<Room> rooms;

    public GameStage() {

    }

    private void createRooms(int roomsNumber) {
        rooms.add(new Room(Vector2.Zero));

        for (int i = 1; i < roomsNumber; i++) {
            Room randomRoom = rooms.get((int) (Math.random() * rooms.size));
            while (randomRoom.getExites().size == 4) {
                randomRoom = rooms.get((int) (Math.random() * rooms.size));
            } //выбор рандомной комнаты с хотя бы одним свободным выходом

            Vector2 deltaPosition = RoomExit.toVector2(randomRoom.getRandomEmptyDirection());
            Vector2 newPosition = randomRoom.getPosition().add(deltaPosition);
            Room newRoom = new Room(newPosition); //новая комната из предыдущей


        }
    }

    private Room getRoomByPosition(Vector2 position) {
        for (Room room : rooms) {
            if (room.getPosition().equals(position)) {
                return room;
            }
        }
        return null;
    }

    private Array<Room> getRoundedRooms(Room room) {
        Array<Room> roundedRooms = new Array<>();

        Room room1 = getRoomByPosition(room.getPosition().add(new Vector2(1, 0)));
        if (room1 != null) {
            roundedRooms.add(room1);
        }

        Room room2 = getRoomByPosition(room.getPosition().add(new Vector2(0, 1)));
        if (room2 != null) {
            roundedRooms.add(room2);
        }

        Room room3 = getRoomByPosition(room.getPosition().add(new Vector2(-1, 0)));
        if (room3 != null) {
            roundedRooms.add(room3);
        }

        Room room4 = getRoomByPosition(room.getPosition().add(new Vector2(0, -1)));
        if (room4 != null) {
            roundedRooms.add(room4);
        }

        return roundedRooms;
    }
}
