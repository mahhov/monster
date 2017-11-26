package util.intersection;

import util.Math3D;

public class IntersectionFinder {
    private double x, y;
    private int intLeftX, intRightX;
    private int intTopY, intBottomY;
    private double maxDeltaX, maxDeltaY;
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

        intTopY = (int) (y - halfSize);
        intBottomY = (int) (y + halfSize);

        if (dir[0] < 0)
            helperLeft(dir[0]);
        else if (dir[0] > 0)
            helperRight(dir[0]);

        intLeftX = (int) (x - halfSize);
        intRightX = (int) (x + halfSize);

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
            if (map.isMoveable(intEdge, intTopY) && map.isMoveable(intEdge, intBottomY)) {
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
            if (map.isMoveable(intEdge, intTopY) && map.isMoveable(intEdge, intBottomY)) {
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
            if (map.isMoveable(intLeftX, intEdge) && map.isMoveable(intRightX, intEdge)) {
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
            if (map.isMoveable(intLeftX, intEdge) && map.isMoveable(intRightX, intEdge)) {
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