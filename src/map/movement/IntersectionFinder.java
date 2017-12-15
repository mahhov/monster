package map.movement;

import map.Map;
import util.Math3D;

public class IntersectionFinder { // todo : clean up + temp vars
    private double x, y;
    private int intLeftX, intRightX;
    private int intTopY, intBottomY;
    private double halfSize;

    private Map map;

    public IntersectionFinder(Map map) {
        this.map = map;
    }

    public Intersection find(double[] orig, double[] dir, double maxMove, double size) {
        x = orig[0];
        y = orig[1];
        dir = Math3D.setMagnitude(dir[0], dir[1], maxMove);
        halfSize = size / 2;

        intTopY = (int) (y - halfSize + Math3D.EPSILON);
        intBottomY = (int) (y + halfSize - Math3D.EPSILON);

        if (dir[0] < 0)
            helperLeft(dir[0]);
        else if (dir[0] > 0)
            helperRight(dir[0]);

        intLeftX = (int) (x - halfSize + Math3D.EPSILON);
        intRightX = (int) (x + halfSize - Math3D.EPSILON);

        if (dir[1] < 0)
            helperTop(dir[1]);
        else if (dir[1] > 0)
            helperBottom(dir[1]);

        return new Intersection(x, y);
    }

    private void helperLeft(double maxDelta) {
        maxDelta = -maxDelta;
        boolean done = false;
        while (!done) {
            double edge = x - halfSize;
            int intEdge = (int) (edge - Math3D.EPSILON);
            if (map.isInBoundsMoveable(intEdge, intTopY) && map.isInBoundsMoveable(intEdge, intBottomY)) {
                double delta = edge - intEdge;
                if (delta > maxDelta) {
                    x -= maxDelta;
                    done = true;
                } else {
                    x -= delta;
                    maxDelta -= delta;
                }
            } else
                done = true;
        }
    }

    private void helperRight(double maxDelta) {
        boolean done = false;
        while (!done) {
            double edge = x + halfSize;
            int intEdge = (int) (edge + Math3D.EPSILON);
            if (map.isInBoundsMoveable(intEdge, intTopY) && map.isInBoundsMoveable(intEdge, intBottomY)) {
                double delta = intEdge - edge + 1;
                if (delta > maxDelta) {
                    x += maxDelta;
                    done = true;
                } else {
                    x += delta;
                    maxDelta -= delta;
                }
            } else
                done = true;
        }
    }

    private void helperTop(double maxDelta) {
        maxDelta = -maxDelta;
        boolean done = false;
        while (!done) {
            double edge = y - halfSize;
            int intEdge = (int) (edge - Math3D.EPSILON);
            if (map.isInBoundsMoveable(intLeftX, intEdge) && map.isInBoundsMoveable(intRightX, intEdge)) {
                double delta = edge - intEdge;
                if (delta > maxDelta) {
                    y -= maxDelta;
                    done = true;
                } else {
                    y -= delta;
                    maxDelta -= delta;
                }
            } else
                done = true;
        }
    }

    private void helperBottom(double maxDelta) {
        boolean done = false;
        while (!done) {
            double edge = y + halfSize;
            int intEdge = (int) (edge + Math3D.EPSILON);
            if (map.isInBoundsMoveable(intLeftX, intEdge) && map.isInBoundsMoveable(intRightX, intEdge)) {
                double delta = intEdge - edge + 1;
                if (delta > maxDelta) {
                    y += maxDelta;
                    done = true;
                } else {
                    y += delta;
                    maxDelta -= delta;
                }
            } else
                done = true;
        }
    }
}