package map.pather;

import util.CoordinateD;
import util.Math3D;

import java.util.ArrayList;

public class Path {
    static final Path EMPTY_PATH = new Path();
    private CoordinateD[] points;
    private int current;

    private Path() {
        points = new Coordinate[0];
    }

    Path(ArrayList<Node> nodes) {
        points = new Coordinate[nodes.size() - 1];
        for (int i = 0; i < points.length; i++)
            points[i] = nodes.get(points.length - i - 1).coordinate;
    }

    public void update(double x, double y) {
        if (Math3D.magnitudeSqr(x - getX(), y - getY()) < .1)
            current++;
    }

    public boolean done() {
        return current >= points.length;
    }

    public double getX() {
        return points[current].getX() + .5;
    }

    public double getY() {
        return points[current].getY() + .5;
    }
}