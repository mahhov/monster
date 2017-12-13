package util.map.pather;

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
            Node neighbor = edge.findNeigbhor(node);
            neighbor.edges.remove(edge);
        }
        nodes.remove(node);
    }

    void addEdge(int i, int j) {
        Node node1 = nodes.get(i), node2 = nodes.get(j);
        Edge edge = new Edge(node1, node2);
        node1.edges.add(edge);
        node2.edges.add(edge);
    }
}
