package house.generator;

import util.Math3D;

import java.util.Iterator;

class Room {
    private int left, right, top, bottom, centerX, centerY, width, height;
    private Room[] neighbors;
    private int numNeighbors;
    private boolean connected;

    Room(int left, int top, int width, int height) {
        this.left = left;
        this.top = top;
        right = left + width;
        bottom = top + height;
        centerX = left + width / 2;
        centerY = top + height / 2;
        this.width = width;
        this.height = height;
        neighbors = new Room[HouseGenerator.NUM_ROOMS];
    }

    boolean intersects(Room room) {
        return left <= room.right && right >= room.left && top <= room.bottom && bottom >= room.top;
    }

    int distance(Room room) {
        int distX = Math3D.abs(room.centerX - centerX);
        int distY = Math3D.abs(room.centerY - centerY);
        distX -= width + room.width;
        distY -= height + room.height;
        distX = Math3D.max(distX, 0);
        distY = Math3D.max(distY, 0);
        return distX + distY;
    }

    void empty(boolean[][] walls) {
        for (int x = left + 1; x < right; x++)
            for (int y = top + 1; y < bottom; y++)
                walls[x][y] = false;

        // connections
        for (Room neighbor : getNeighbors()) {
            int startX = getX();
            int startY = getY();
            int endX = neighbor.getX();
            int endY = neighbor.getY();
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

    void addNeighbor(Room neighbor) {
        neighbors[numNeighbors++] = neighbor;
    }

    Iterable<Room> getNeighbors() {
        return () -> new Iterator<Room>() {
            private int i = 0;

            public boolean hasNext() {
                return i < numNeighbors;
            }

            public Room next() {
                return neighbors[i++];
            }
        };
    }

    int getX() {
        return Math3D.random(left + 1, right - 1);
    }

    int getY() {
        return Math3D.random(top + 1, bottom - 1);
    }

    boolean isConnected() {
        return connected;
    }

    void setConnected() {
        connected = true;
    }
}