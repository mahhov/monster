package map.pather;

import map.Map;
import util.CoordinateD;

import java.util.ArrayList;

public class Pather {
    private static final int NEAREST_MOVABLE_RANGE = 10;
    private Graph graph;
    private Coordinate start, end;
    private Node startNode, endNode;
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
        start = nearestMoveable(origX, origY);
        if (start == null)
            return Path.EMPTY_PATH;
        end = nearestMoveable(destX, destY);
        if (end == null)
            return Path.EMPTY_PATH;
        appendEndpoints();
        prepareGraphForAStar();
        pathNodes = aStar();
        if (pathNodes != null)
            path = new Path(pathNodes);
        else
            path = Path.EMPTY_PATH;
        unappendEndpoints();
        return path;
    }

    private void createGraphNodes() {
        graph = new Graph();
        for (int x = 0; x < map.getWidth(); x++)
            for (int y = 0; y < map.getHeight(); y++) {
                current = new Coordinate(x, y);
                touchCurrent = !map.isMoveable((int) current.getX(), (int) current.getY());
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


                touchUp = !inBounds(up) || !map.isMoveable((int) up.getX(), (int) up.getY());
                touchDown = !inBounds(down) || !map.isMoveable((int) down.getX(), (int) down.getY());
                touchLeft = !inBounds(left) || !map.isMoveable((int) left.getX(), (int) left.getY());
                touchRight = !inBounds(right) || !map.isMoveable((int) right.getX(), (int) right.getY());
                touchUpRight = !inBounds(upRight) || !map.isMoveable((int) upRight.getX(), (int) upRight.getY());
                touchUpLeft = !inBounds(upLeft) || !map.isMoveable((int) upLeft.getX(), (int) upLeft.getY());
                touchDownRight = !inBounds(downRight) || !map.isMoveable((int) downRight.getX(), (int) downRight.getY());
                touchDownLeft = !inBounds(downLeft) || !map.isMoveable((int) downLeft.getX(), (int) downLeft.getY());

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
        CoordinateD a = graph.nodes.get(i).coordinate;
        CoordinateD b = graph.nodes.get(j).coordinate;
        return !intersectionFinder.intersects(a, b);
    }

    private Coordinate nearestMoveable(double x, double y) {
        if (map.isInBoundsMoveable((int) x, (int) y))
            return new Coordinate(x - .5, y - .5);

        int xi = (int) x;
        int yi = (int) y;

        int r = 1;
        while (r < NEAREST_MOVABLE_RANGE) {
            for (int i = -r; i < r; i++) {
                if (map.isInBoundsMoveable(xi + i, yi - r))
                    return new Coordinate(xi + i, yi - r);
                if (map.isInBoundsMoveable(xi + i, yi + r))
                    return new Coordinate(xi + i, yi + r);
                if (map.isInBoundsMoveable(xi - r, yi + i))
                    return new Coordinate(xi - r, yi + i);
                if (map.isInBoundsMoveable(xi + r, yi + i))
                    return new Coordinate(xi + r, yi + i);
            }
            r++;
        }

        return null;
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
        while (graph.hasOpenNode()) {
            Node currentNode = graph.getOpenNodeWithSmallestF();

            if (currentNode == endNode)
                return reconstructPath();

            for (Edge edge : currentNode.edges) {
                Node neighborNode = edge.findNeigbhor(currentNode);

                if (neighborNode.isClosed())
                    continue;

                if (!neighborNode.isOpen())
                    graph.openNode(neighborNode);

                double g = currentNode.getG() + edge.getDistance();
                neighborNode.updateNode(currentNode, g, end);
            }
        }

        return null;
    }

    private void prepareGraphForAStar() {
        graph.reset();
        startNode.updateNode(null, 0, end);
        graph.openNode(startNode);
    }

    private ArrayList<Node> reconstructPath() {
        ArrayList<Node> path = new ArrayList<>();

        Node currentNode = endNode;

        do {
            path.add(currentNode);
            currentNode = currentNode.getCameFrom();
        } while (currentNode != null);

        return path;
    }

    private boolean inBounds(Coordinate coordinate) {
        return coordinate.getX() >= 0 && coordinate.getX() < map.getWidth() && coordinate.getY() >= 0 && coordinate.getY() < map.getHeight();
    }
}