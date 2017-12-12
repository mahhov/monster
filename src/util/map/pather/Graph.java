package util.map.pather;

import util.Math3D;

import java.util.ArrayList;

class Graph { // todo cleanup
    ArrayList<Node> nodes; // todo make more efficient than arraylist

    Graph() {
        nodes = new ArrayList<>();
    }

    Node addNode(Coordinate coordinate) {
        Node node = new Node();
        node.coordinate = coordinate;
        nodes.add(node);
        return node;
    }

    void removeNode(Node node) {
        for (Edge edge : node.edges) {
            Node neighbor = edge.node1 == node ? edge.node2 : edge.node1;
            neighbor.edges.remove(edge);
        }
        nodes.remove(node);
    }

    void addEdge(int i, int j) {
        Edge edge = new Edge();
        edge.node1 = nodes.get(i);
        edge.node2 = nodes.get(j);
        edge.distance = Math3D.magnitude(edge.node1.coordinate.getX() - edge.node2.coordinate.getX(), edge.node1.coordinate.getY() - edge.node2.coordinate.getY());
        edge.node1.edges.add(edge);
        edge.node2.edges.add(edge);
    }
}
