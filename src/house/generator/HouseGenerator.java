package house.generator;

import util.Coordinate;
import util.Math3D;
import util.Queue;

public class HouseGenerator {
    private static final int WIDTH = 128, HEIGHT = 128;
    private static final int MIN_ROOM_SIZE = 5, MAX_ROOM_SIZE = 12;
    static final int NUM_ROOMS = 1000;
    private static final int NUM_CONNECTIONS = 1000, MAX_CONNECTION_LENGTH = 10;
    private static final int NUM_LIGHTS = 0;
    private Room[] rooms;
    private int roomCount;
    private boolean[][] walls;
    private Coordinate[] spawns;
    private Coordinate[] lights;

    public void generate() {
        initWalls();
        placeRooms();
        connectRooms();
        pruneUnconnectedRooms();
        fillRoomWalls();
        findSpawns();
        generateLights();
    }

    private void initWalls() {
        walls = new boolean[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                walls[x][y] = true;
    }

    private void placeRooms() {
        rooms = new Room[NUM_ROOMS];

        for (int i = 0; i < NUM_ROOMS; i++) {
            int width = Math3D.random(MIN_ROOM_SIZE, MAX_ROOM_SIZE);
            int height = Math3D.random(MIN_ROOM_SIZE, MAX_ROOM_SIZE);
            int left = Math3D.random(0, WIDTH - width);
            int top = Math3D.random(0, HEIGHT - height);
            Room room = new Room(left, top, width, height);

            boolean intersects = false;
            for (int j = 0; j < roomCount; j++)
                if (room.intersects(rooms[j])) {
                    intersects = true;
                    break;
                }
            if (!intersects)
                rooms[roomCount++] = room;
        }
    }

    private void connectRooms() {
        for (int i = 0; i < NUM_CONNECTIONS; i++) {
            int roomNum1 = Math3D.random(0, roomCount - 1);
            int roomNum2 = Math3D.random(0, roomCount - 2);
            if (roomNum1 == roomNum2)
                roomNum2++;

            Room room1 = rooms[roomNum1];
            Room room2 = rooms[roomNum2];

            if (room1.distance(room2) < MAX_CONNECTION_LENGTH) {
                room1.addNeighbor(room2);
                room2.addNeighbor(room1);
            }
        }
    }

    private void pruneUnconnectedRooms() {
        Queue<Room> openRooms = new Queue<>(NUM_ROOMS);
        openRooms.insert(rooms[0]);
        rooms[0].setConnected();
        while (!openRooms.isEmpty())
            for (Room neighbor : openRooms.remove().getNeighbors())
                if (!neighbor.isConnected()) {
                    neighbor.setConnected();
                    openRooms.insert(neighbor);
                }
    }

    private void fillRoomWalls() {
        for (int i = 0; i < roomCount; i++)
            if (rooms[i].isConnected())
                rooms[i].empty(walls);
    }

    private void findSpawns() {
        spawns = findCoordinates(3);
    }

    private void generateLights() {
        lights = findCoordinates(NUM_LIGHTS);
    }

    private Coordinate[] findCoordinates(int num) {
        Coordinate[] coordinates = new Coordinate[num];
        int j = -1;
        for (int i = 0; i < num; i++) {
            do
                if (++j == roomCount)
                    j = 0;
            while (!rooms[j].isConnected());
            coordinates[i] = new Coordinate(rooms[j].getX(), rooms[j].getY());
        }
        return coordinates;
    }

    public boolean[][] getWalls() {
        return walls;
    }

    public Coordinate getSpawn(int i) {
        return spawns[i];
    }

    public Coordinate[] getLights() {
        return lights;
    }
}