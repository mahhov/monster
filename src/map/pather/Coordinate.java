package map.pather;

class Coordinate extends map.Coordinate {
    Coordinate(int x, int y) {
        super(x, y);
    }

    Coordinate modify(int dx, int dy) {
        return new Coordinate(getX() + dx, getY() + dy);
    }
}