package house.generator;

import painter.geometry.Coordinate;
import util.Math3D;

public class HouseGenerator {
    private static final int WIDTH = 128, HEIGHT = 128;
    private static final int MIN_ROOM_SIZE = 5, MAX_ROOM_SIZE = 12;
    private static final int NUM_ROOMS = 1000;
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

    private void fillRoomWalls() {
        for (int i = 0; i < roomCount; i++)
            if (rooms[i].isConnected())
                rooms[i].empty(walls);
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
                room1.setConnected();
                room2.setConnected();

                int startX = room1.getX();
                int startY = room1.getY();
                int endX = room2.getX();
                int endY = room2.getY();
                int deltaX = endX > startX ? 1 : -1;
                int deltaY = endY > startY ? 1 : -1;
                endX += deltaX;
                endY += deltaY;

                if (Math3D.randBoolean(.5)) {
                    for (int x = startX; x != endX; x += deltaX)
                        walls[x][startY] = false;
                    endX -= deltaX;
                    for (int y = startY; y != endY; y += deltaY)
                        walls[endX][y] = false;
                } else {
                    for (int y = startY; y != endY; y += deltaY)
                        walls[startX][y] = false;
                    endY -= deltaY;
                    for (int x = startX; x != endX; x += deltaX)
                        walls[x][endY] = false;
                }
            }
        }
    }

    private void findSpawns() {
        spawns = new Coordinate[3];
        int j = -1;
        for (int i = 0; i < spawns.length; i++) {
            do
                j++;
            while (!rooms[j].isConnected());
            spawns[i] = new Coordinate(rooms[j].getX(), rooms[j].getY());
        }
    }

    private void generateLights() {
        lights = new Coordinate[NUM_LIGHTS];
        int j = -1;
        for (int i = 0; i < lights.length; i++) {
            do {
                j++;
                if (j == roomCount)
                    j = 0;
            }
            while (!rooms[j].isConnected());
            lights[i] = new Coordinate(rooms[j].getX(), rooms[j].getY());
        }
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