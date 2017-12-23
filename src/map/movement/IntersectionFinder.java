package map.movement;

import map.Map;
import util.Math3D;

public class IntersectionFinder {
    // temp vars
    private double x, y;
    private double dx, dy;
    private double halfSize, edgeDx, edgeDy;
    private boolean horizontal;
    private double edgeX, edgeY;
    private double deltaX, deltaY, delta;
    private int nextX, nextY;

    private Map map;

    public IntersectionFinder(Map map) {
        this.map = map;
    }

    public Intersection find(double[] orig, double[] dir, double maxMove, double size) {
        x = orig[0];
        y = orig[1];

        dir = Math3D.setMagnitude(dir[0], dir[1], 1);
        dx = dir[0];
        dy = dir[1];

        halfSize = size / 2;
        edgeDx = dx < 0 ? -halfSize : halfSize;
        edgeDy = dy < 0 ? -halfSize : halfSize;

        while (true) {
            edgeX = x + edgeDx;
            edgeY = y + edgeDy;

            deltaX = getMove(edgeX, dx);
            deltaY = getMove(edgeY, dy);
            horizontal = deltaX < deltaY;
            delta = horizontal ? deltaX : deltaY;

            if (delta > maxMove)
                return new Intersection(x + dx * maxMove, y + dy * maxMove);
            else {
                delta += Math3D.EPSILON;
                nextX = (int) (edgeX + dx * delta);
                nextY = (int) (edgeY + dy * delta);

                if (horizontal && !(map.isInBoundsMoveable(nextX, (int) (y - halfSize))
                        && map.isInBoundsMoveable(nextX, (int) (y + halfSize)))) {
                    if (dy == 0)
                        return new Intersection(x, y);
                    dx = 0;
                    dy = dy < 0 ? -1 : 1;

                } else if (!horizontal && !(map.isInBoundsMoveable((int) (x - halfSize), nextY)
                        && map.isInBoundsMoveable((int) (x + halfSize), nextY))) {
                    if (dx == 0)
                        return new Intersection(x, y);
                    dy = 0;
                    dx = dx < 0 ? -1 : 1;
                
                } else {
                    x += dx * delta;
                    y += dy * delta;
                    maxMove -= delta;
                }
            }
        }
    }

    private double getMove(double pos, double dir) {
        if (dir > 0)
            return ((int) pos + 1 - pos) / dir;
        else if (dir < 0)
            return (pos - (int) pos) / -dir;
        else
            return 2;
    }
}