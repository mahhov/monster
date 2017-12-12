package util.map.pather;

import java.util.ArrayList;

class Node { // todo cleanup
    Coordinate coordinate;
    ArrayList<Edge> edges = new ArrayList<>();
    Node cameFrom;
    double g;
    double f;
    boolean open;
    boolean closed;
}