package map.lighting;

import map.Map;
import util.Math3D;

public class IntersectionFinder {
    private Map map;

    // temp vars
    private double dirX, dirY;
    private double checkDx, checkDy;
    private double totalMoved, totalToMove;
    private double moveX, moveY, move;

    IntersectionFinder(Map map) {
        this.map = map;
    }

    public boolean intersects(double srcX, double srcY, double destX, double destY) {
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
            moveX = getMove(srcX, dirX);
            moveY = getMove(srcY, dirY);

            move = (moveX < moveY ? moveX : moveY) + Math3D.EPSILON;
            totalMoved += move;

            if (totalMoved > totalToMove)
                return false;

            srcX += dirX * move;
            srcY += dirY * move;

            if (!map.isInBoundsMoveable((int) srcX, (int) srcY))
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