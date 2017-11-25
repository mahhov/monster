package house.generator;

import util.Math3D;

class Room {
    private int left, right, top, bottom, centerX, centerY, width, height;
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