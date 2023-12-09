package com.mygdx.game.map;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.Arrays;
import java.util.List;

public class GameStage {
    private World world;
    private Array<Room> rooms = new Array<>();
    private Room currentRoom;

    public GameStage(World world) {
        this.world = world;

        Room room = new Room(new Vector2(0, 0));
        room.parseStaticObjects(world);
        rooms.add(room);
        currentRoom = room;
    }

    public Room getCurrentRoom() {
        return currentRoom;
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
            setExitesWithRounded(newRoom);

            rooms.add(newRoom);
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

    private void setExitesWithRounded(Room room) {
        Room room1 = getRoomByPosition(room.getPosition().add(new Vector2(1, 0)));
        if (room1 != null) {
            room.addExit(new RoomExit(RoomExit.Direction.UP_LEFT, room1));
            room1.addExit(new RoomExit(RoomExit.Direction.UP_LEFT.getOpposite(), room));
        }

        Room room2 = getRoomByPosition(room.getPosition().add(new Vector2(0, 1)));
        if (room2 != null) {
            room.addExit(new RoomExit(RoomExit.Direction.UP_RIGHT, room2));
            room2.addExit(new RoomExit(RoomExit.Direction.UP_RIGHT.getOpposite(), room));
        }

        Room room3 = getRoomByPosition(room.getPosition().add(new Vector2(-1, 0)));
        if (room3 != null) {
            room.addExit(new RoomExit(RoomExit.Direction.DOWN_LEFT, room3));
            room3.addExit(new RoomExit(RoomExit.Direction.DOWN_LEFT.getOpposite(), room));
        }

        Room room4 = getRoomByPosition(room.getPosition().add(new Vector2(0, -1)));
        if (room4 != null) {
            room.addExit(new RoomExit(RoomExit.Direction.DOWN_RIGHT, room4));
            room4.addExit(new RoomExit(RoomExit.Direction.DOWN_RIGHT.getOpposite(), room));
        }
    }

    public void changeRoom(RoomExit.Direction direction) {
        currentRoom.destroyStaticObjects(world);
        currentRoom = currentRoom.getExitByDirection(direction).getNextRoom();
        currentRoom.parseStaticObjects(world);
    }
}
