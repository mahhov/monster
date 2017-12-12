package painter.geometry;

import java.util.Iterator;

public class CoordinateGroup implements Iterable<Coordinate> {
    private Coordinate[] coordinates;
    private int size;

    CoordinateGroup(int size) {
        coordinates = new Coordinate[size];
    }

    public CoordinateGroup(Coordinate[] coordinates) {
        this.coordinates = coordinates;
        size = coordinates.length;
    }

    public int[][] transformXY(double scale, double shift) {
        int[][] xy = new int[2][size];
        for (int i = 0; i < size; i++) {
            int[] cxy = coordinates[i].transform(scale, shift);
            xy[0][i] = cxy[0];
            xy[1][i] = cxy[1];
        }
        return xy;
    }

    public boolean isInView() {
        for (Coordinate coordinate : coordinates)
            if (coordinate.isInView())
                return true;
        return false;
    }

    public boolean isInViewRect() {
        return (coordinates[0].isInViewX() || coordinates[1].isInViewX()) && (coordinates[0].isInViewY() || coordinates[1].isInViewY());
    }

    public Coordinate[] getCoordinates() {
        return coordinates;
    }

    Coordinate getCoordinate(int i) {
        return coordinates[i];
    }

    void addCoordinate(Coordinate coordinate) {
        coordinates[size++] = coordinate;
    }

    public int getSize() {
        return size;
    }

    public Iterator<Coordinate> iterator() {
        return new CoordinateGroupIterator();
    }

    private class CoordinateGroupIterator implements Iterator<Coordinate> {
        private int current;

        public boolean hasNext() {
            return current < size;
        }

        public Coordinate next() {
            return coordinates[current];
        }
    }
}