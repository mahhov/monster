package util.map.pather;

import util.Math3D;

class Edge {
    private Node node1, node2;
    private double distance;

    Edge(Node node1, Node node2) {
        this.node1 = node1;
        this.node2 = node2;
        distance = Math3D.magnitude(node1.coordinate.getX() - node2.coordinate.getX(), node1.coordinate.getY() - node2.coordinate.getY());
    }

    Node findNeigbhor(Node node) {
        return node1 == node ? node2 : node1;
    }

    double getDistance() {
        return distance;
    }
}