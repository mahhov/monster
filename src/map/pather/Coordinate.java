package map.pather;

class Coordinate extends util.CoordinateD {
    Coordinate(double x, double y) {
        super(x, y);
    }

    Coordinate modify(double dx, double dy) {
        return new Coordinate(getX() + dx, getY() + dy);
    }
}