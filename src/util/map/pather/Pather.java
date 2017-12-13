package util.map.pather;

import util.Math3D;
import util.map.Map;

import java.util.ArrayList;

public class Pather {
    private Graph graph;
    private Coordinate start, end;
    private Node startNode, endNode;
    private int openCount;
    private IntersectionFinder intersectionFinder;
    private Map map;

    // temp vars
    private Coordinate current, upRight, upLeft, downRight, downLeft, up, down, left, right;
    private boolean touchCurrent, touchUp, touchDown, touchLeft, touchRight, touchUpRight, touchUpLeft, touchDownRight, touchDownLeft;
    private boolean specialUpRight, specialUpLeft, specialDownRight, specialDownLeft;
    private ArrayList<Node> pathNodes;
    private Path path;

    public Pather(Map map) {
        this.map = map;
        intersectionFinder = new IntersectionFinder(map);
        createGraphNodes();
        createGraphEdges();
    }

    public Path pathFind(double origX, double origY, double destX, double destY) {
        start = new Coordinate((int) origX, (int) origY);
        end = new Coordinate((int) destX, (int) destY);
        appendEndpoints();
        prepareGraphForAStar();
        pathNodes = aStar();
        if (pathNodes != null)
            path = new Path(pathNodes);
        else {
            System.out.println("NO PATH POSSIBLE");
            path = Path.EMPTY_PATH;
        }
        unappendEndpoints();
        return path;
    }

    private void createGraphNodes() {
        graph = new Graph();
        for (int x = 0; x < map.getWidth(); x++)
            for (int y = 0; y < map.getHeight(); y++) {
                current = new Coordinate(x, y);
                touchCurrent = !map.isMoveable(current.getX(), current.getY());
                if (touchCurrent)
                    continue;

                upRight = current.modify(1, -1);
                upLeft = current.modify(-1, -1);
                downRight = current.modify(1, 1);
                downLeft = current.modify(-1, 1);
                up = current.modify(0, -1);
                down = current.modify(0, 1);
                left = current.modify(-1, 0);
                right = current.modify(1, 0);


                touchUp = !inBounds(up) || !map.isMoveable(up.getX(), up.getY());
                touchDown = !inBounds(down) || !map.isMoveable(down.getX(), down.getY());
                touchLeft = !inBounds(left) || !map.isMoveable(left.getX(), left.getY());
                touchRight = !inBounds(right) || !map.isMoveable(right.getX(), right.getY());
                touchUpRight = !inBounds(upRight) || !map.isMoveable(upRight.getX(), upRight.getY());
                touchUpLeft = !inBounds(upLeft) || !map.isMoveable(upLeft.getX(), upLeft.getY());
                touchDownRight = !inBounds(downRight) || !map.isMoveable(downRight.getX(), downRight.getY());
                touchDownLeft = !inBounds(downLeft) || !map.isMoveable(downLeft.getX(), downLeft.getY());

                specialUpRight = (touchUpRight && !touchUp && !touchRight);
                specialUpLeft = (touchUpLeft && !touchUp && !touchLeft);
                specialDownRight = (touchDownRight && !touchDown && !touchRight);
                specialDownLeft = (touchDownLeft && !touchDown && !touchLeft);

                if (specialUpRight || specialUpLeft || specialDownRight || specialDownLeft)
                    graph.addNode(current);
            }
    }

    private void createGraphEdges() {
        for (int i = 1; i < graph.nodes.size(); i++)
            createGraphEdgesForNode(i);
    }

    private void createGraphEdgesForNode(int i) {
        for (int j = 0; j < i; j++)
            if (connectedGraphNodes(j, i))
                graph.addEdge(j, i);
    }

    private boolean connectedGraphNodes(int i, int j) {
        Coordinate a = graph.nodes.get(i).coordinate;
        Coordinate b = graph.nodes.get(j).coordinate;
        return !intersectionFinder.intersects(a, b);
    }

    private void appendEndpoints() {
        startNode = graph.addNode(start);
        endNode = graph.addNode(end);
        createGraphEdgesForNode(graph.nodes.size() - 2);
        createGraphEdgesForNode(graph.nodes.size() - 1);
    }

    private void unappendEndpoints() {
        graph.removeNode(startNode);
        graph.removeNode(endNode);
    }

    private ArrayList<Node> aStar() {
        while (openCount > 0) {
            Node currentNode = getNodeWithSmallestF();

            if (currentNode == endNode)
                return reconstructPath();

            openCount--;
            currentNode.open = false;
            currentNode.closed = true;

            for (Edge edge : currentNode.edges) {
                Node neighborNode = edge.node1 == currentNode ? edge.node2 : edge.node1;

                if (neighborNode.closed)
                    continue;

                if (!neighborNode.open) {
                    openCount++;
                    neighborNode.open = true;
                }

                double g = currentNode.g + edge.distance;
                if (g >= neighborNode.g)
                    continue;

                neighborNode.cameFrom = currentNode;
                neighborNode.g = g;
                neighborNode.f = g + getH(neighborNode.coordinate);
            }
        }

        return null;
    }

    private void prepareGraphForAStar() {
        for (Node node : graph.nodes) {
            node.cameFrom = null;
            node.g = Double.MAX_VALUE;
            node.f = Double.MAX_VALUE;
            node.open = false;
            node.closed = false;
        }

        startNode.g = 0;
        startNode.f = getH(startNode.coordinate);
        startNode.open = true;
        openCount = 1;
    }

    private Node getNodeWithSmallestF() { // todo: make more efficient
        Node current = null;
        for (Node node : graph.nodes)
            if (node.open && (current == null || node.f < current.f))
                current = node;
        return current;
    }

    private double getH(Coordinate coordinate) {
        return Math3D.magnitude(coordinate.getX() - end.getX(), coordinate.getY() - end.getY());
    }

    private ArrayList<Node> reconstructPath() {
        ArrayList<Node> path = new ArrayList<>();

        Node currentNode = endNode;

        do {
            path.add(currentNode);
            currentNode = currentNode.cameFrom;
        } while (currentNode != null);

        return path;
    }

    private boolean inBounds(Coordinate coordinate) {
        return coordinate.getX() >= 0 && coordinate.getX() < map.getWidth() && coordinate.getY() >= 0 && coordinate.getY() < map.getHeight();
    }
}