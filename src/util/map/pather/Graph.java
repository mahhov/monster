package util.map.pather;

import java.util.ArrayList;

class Graph { // todo cleanup
    ArrayList<Node> nodes;
    private ArrayList<Node> openNodes;

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

    void reset() {
        openNodes = new ArrayList<>();
        for (Node node : nodes)
            node.reset();
    }

    boolean hasOpenNode() {
        return !openNodes.isEmpty();
    }

    void openNode(Node node) {
        openNodes.add(node);
        node.open();
    }

    void closeNode(Node node) {
        openNodes.remove(node);
        node.close();
    }

    Node getNodeWithSmallestF() {
        Node current = null;
        for (Node node : openNodes)
            if (current == null || node.getF() < current.getF())
                current = node;
        return current;
    }
}