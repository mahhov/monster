package map.pather;

import map.Map;

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
        start = nearestMoveable((int) origX, (int) origY);
        if (start == null)
            return Path.EMPTY_PATH;
        end = nearestMoveable((int) destX, (int) destY);
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
        util.Coordinate a = graph.nodes.get(i).coordinate;
        util.Coordinate b = graph.nodes.get(j).coordinate;
        return !intersectionFinder.intersects(a, b);
    }

    private Coordinate nearestMoveable(int x, int y) {
        if (map.isInBoundsMoveable(x, y))
            return new Coordinate(x, y);

        int r = 1;
        while (r < NEAREST_MOVABLE_RANGE) {
            for (int i = -r; i < r; i++) {
                if (map.isInBoundsMoveable(x + i, y - r))
                    return new Coordinate(x + i, y - r);
                if (map.isInBoundsMoveable(x + i, y + r))
                    return new Coordinate(x + i, y + r);
                if (map.isInBoundsMoveable(x - r, y + i))
                    return new Coordinate(x - r, y + i);
                if (map.isInBoundsMoveable(x + r, y + i))
                    return new Coordinate(x + r, y + i);
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