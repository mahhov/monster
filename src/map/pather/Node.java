package map.pather;

import util.CoordinateD;
import util.Math3D;

import java.util.ArrayList;

class Node implements Comparable<Node> {
    CoordinateD coordinate;
    ArrayList<Edge> edges = new ArrayList<>(); // todo make private
    private Node cameFrom;
    private double g;
    private double h;
    private double f;
    private boolean open;
    private boolean closed;

    void reset() {
        cameFrom = null;
        g = Double.MAX_VALUE;
        h = Double.MAX_VALUE;
        f = Double.MAX_VALUE;
        open = false;
        closed = false;
    }

    void updateNode(Node cameFrom, double g, CoordinateD endCoordinate) {
        if (g > this.g)
            return;
        this.cameFrom = cameFrom;
        this.g = g;
        if (h == Double.MAX_VALUE)
            h = Math3D.magnitude(coordinate.getX() - endCoordinate.getX(), coordinate.getY() - endCoordinate.getY());
        f = g + h;
    }

    Node getCameFrom() {
        return cameFrom;
    }

    double getG() {
        return g;
    }

    boolean isOpen() {
        return open;
    }

    void open() {
        open = true;
    }

    boolean isClosed() {
        return closed;
    }

    void close() {
        closed = true;
    }

    public int compareTo(Node o) {
        return f == o.f ? 0 : (f > o.f ? 1 : -1);
    }
}