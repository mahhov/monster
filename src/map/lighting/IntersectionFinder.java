package map.lighting;

import map.Map;
import util.Math3D;

class IntersectionFinder {
    private Map map;

    // temp vars
    private double x, y;
    private double dirX, dirY;
    private double checkDx, checkDy;
    private double totalMoved, totalToMove;
    private double moveX, moveY, move;

    IntersectionFinder(Map map) {
        this.map = map;
    }

    boolean intersects(int srcX, int srcY, int destX, int destY) {
        x = srcX + .5;
        y = srcY + .5;

        dirX = destX - srcX;
        dirY = destY - srcY;

        checkDx = dirX < 0 ? .1 : -.1;
        checkDy = dirY < 0 ? .1 : -.1;

        dirX += 5 * checkDx;
        dirY += 5 * checkDy;

        totalMoved = 0;
        totalToMove = Math3D.magnitude(dirX, dirY);

        dirX /= totalToMove;
        dirY /= totalToMove;

        while (true) {
            moveX = getMove(x, dirX);
            moveY = getMove(y, dirY);

            move = (moveX < moveY ? moveX : moveY) + Math3D.EPSILON;
            totalMoved += move;

            if (totalMoved > totalToMove)
                return false;

            x += dirX * move;
            y += dirY * move;

            if (!map.isInBoundsMoveable((int) x, (int) y))
                return true;
        }
    }

    ;

    private double getMove(double pos, double dir) {
        if (dir > 0)
            return ((int) pos + 1 - pos) / dir;
        else if (dir < 0)
            return (pos - (int) pos) / -dir;
        else
            return 2;
    }
}