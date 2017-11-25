package util.intersection;

import util.Math3D;

public class IntersectionFinder {
    private Map map;
    private boolean allowSlide, limitDistance;
    private double x, y;
    private double nextx, nexty;
    private int intx, inty;
    private double movex, movey, move, moved;
    private int moveWhich;
    private double[] dir;
    private double shiftedX, shiftedY;
    private int collideNum, isDirZeroNum;
    private boolean[] collide, isDirZero;
    private int xx, yy; // for iterating

    public IntersectionFinder(Map map) {
        this.map = map;
    }

    public Intersection find(double[] orig, double[] dir, double maxMove, boolean allowSlide, double size) {
        reset(orig, dir, maxMove, allowSlide, size);
        if (isDirZeroNum == 2)
            return new Intersection(orig[0], orig[1]);
        while (true) {
            computeNextMove();
            moveBy(move - Math3D.EPSILON);
            if (moved + move > maxMove && limitDistance) {
                moveBy(maxMove - moved);
                return new Intersection(nextx - shiftedX, nexty - shiftedY);
            }
            moveBy(move + Math3D.EPSILON);
            if (!map.isMoveable(intx, inty)) {
                moveBy(move - Math3D.EPSILON);
                if (collideCheck())
                    return new Intersection(x - shiftedX, y - shiftedY);
            }
            nextIter();
        }
    }

    private void reset(double[] orig, double[] dir, double maxMove, boolean allowSlide, double size) {
        moved = 0;
        this.limitDistance = maxMove > 0;
        this.dir = Math3D.setMagnitude(dir[0], dir[1], 1);
        this.allowSlide = allowSlide;
        collideNum = 0;
        isDirZeroNum = 0;
        collide = new boolean[] {false, false};
        isDirZero = new boolean[] {Math3D.isZero(dir[0]), Math3D.isZero(dir[1])};

        double halfSize = size * .5;

        if (isDirZero[0]) {
            dir[0] = 0;
            isDirZeroNum++;
        } else if (dir[0] > 0)
            shiftedX = halfSize;
        else
            shiftedX = -halfSize;


        if (isDirZero[1]) {
            dir[1] = 0;
            isDirZeroNum++;
        } else if (dir[1] > 0)
            shiftedY = halfSize;
        else
            shiftedY = -halfSize;

        nextx = x = orig[0] + shiftedX;
        nexty = y = orig[1] + shiftedY;
        intx = (int) x;
        inty = (int) y;
    }

    private void computeNextMove() {
        if (isDirZero[0])
            movex = Math3D.SQRT3;
        else {
            if (dir[0] > 0)
                movex = (1 + intx - x) / dir[0];
            else {
                movex = (intx - x) / dir[0];
            }
        }

        if (isDirZero[1])
            movey = Math3D.SQRT3;
        else {
            if (dir[1] > 0)
                movey = (1 + inty - y) / dir[1];
            else
                movey = (inty - y) / dir[1];
        }

        if (movex < movey) {
            moveWhich = 0;
            move = movex;
        } else {
            moveWhich = 1;
            move = movey;
        }
    }

    private void moveBy(double move) {
        nextx = x + dir[0] * move;
        nexty = y + dir[1] * move;

        intx = (int) nextx;
        inty = (int) nexty;
        if (nextx < 0)
            intx--;
        if (nexty < 0)
            inty--;
    }

    private boolean collideCheck() {
        if (!allowSlide)
            return true;
        if (!collide[moveWhich]) {
            collideNum++;
            collide[moveWhich] = true;
            if (!isDirZero[moveWhich]) {
                isDirZeroNum++;
                isDirZero[moveWhich] = true;
            }
            dir[moveWhich] = 0;
            return collideNum == 2 || isDirZeroNum == 2;
        }
        return false;
    }

    private void nextIter() {
        moved += move;
        x = nextx;
        y = nexty;
    }
}