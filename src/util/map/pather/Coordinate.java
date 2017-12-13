package util.map.pather;

class Coordinate {
    private int x, y;

    Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    Coordinate modify(int dx, int dy) {
        return new Coordinate(x + dx, y + dy);
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }
}