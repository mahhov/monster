package house.character;

import camera.Camera;
import house.House;
import map.pather.Path;
import painter.painterelement.PainterQueue;
import util.CoordinateD;
import util.DrawUtil;
import util.Math3D;

import java.awt.*;

public class Monster extends Character {
    private static final Color COLOR_TOP = new Color(160, 0, 0), COLOR_SIDE = new Color(120, 0, 0);
    private static final double WALK_SPEED = .15, RUN_SPEED = .45;

    private static final int SENSE_SNIFF_COOLDOWN = 100;
    private int sniffCooldown;

    private static final int SENSE_DISTANCE = 9;
    private double destX, destY;
    private Path wanderPath;

    public Monster(util.Coordinate spawn) {
        super(false, COLOR_TOP, COLOR_SIDE, WALK_SPEED, RUN_SPEED, spawn);
    }

    void applyComputer(House house) {
        if (wanderPath != null && !wanderPath.done()) {
            setDirX(wanderPath.getX() - getX());
            setDirY(wanderPath.getY() - getY());
            wanderPath.update(getX(), getY());
        }
    }

    private CoordinateD randomWanderDestination(House house) {
        double wanderX, wanderY;
        do {
            wanderX = Math3D.random(0, house.getWidth() - 1);
            wanderY = Math3D.random(0, house.getHeight() - 1);
        } while (!house.isMoveable((int) wanderX, (int) wanderY));
        return new CoordinateD(wanderX, wanderY);
    }

    void setSense(House house, Character source) {
        boolean lineOfSight = house.lineOfSight(getX(), getY(), source.getX(), source.getY());
        //        System.out.println(lineOfSight + " :: (" + getX() + ", " + getY() + ") -> (" + source.getX() + ", " + source.getY() + ")");
        boolean sniffReady = sniffCooldown == SENSE_SNIFF_COOLDOWN;
        if (!sniffReady)
            sniffCooldown++;

        if (lineOfSight) {
            destX = source.getX();
            destY = source.getY();
        } else if (sniffReady) {
            sniffCooldown = 0;
            double distance = Math3D.magnitude(getX() - source.getX(), getY() - source.getY()); // todo : use magnitudeSqr
            double sniffError = distance / 10;
            double sniffX = Math3D.random(source.getX() - sniffError, source.getX() + sniffError);
            double sniffY = Math3D.random(source.getY() - sniffError, source.getY() + sniffError);
            destX = sniffX;
            destY = sniffY;
        }
        wanderPath = house.getPather().pathFind(getX(), getY(), destX, destY);
    }

    public void draw(PainterQueue painterQueue, Camera camera) {
        super.draw(painterQueue, camera);
        DrawUtil.drawCubeFromCenter(painterQueue, camera, destX, destY, 1, Color.LIGHT_GRAY, Color.LIGHT_GRAY, PainterQueue.CHARACTER_TOP_LAYER, PainterQueue.CHARACTER_SIDE_LAYER);
    }
}