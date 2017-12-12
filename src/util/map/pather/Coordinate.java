package util.map.pather;

class Coordinate { // todo cleanup
    private int x, y;

    Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    Coordinate modify(int dx, int dy) {
        return new Coordinate(x + dx, y + dy);
    }

    boolean equals(int x, int y) {
        return x == this.x && y == this.y;
    }

    boolean equals(Coordinate compare) {
        return x == compare.x && y == compare.y;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }
}