package house.character;

import house.House;
import map.pather.Path;
import painter.geometry.Coordinate;
import util.CoordinateD;
import util.Math3D;

import java.awt.*;

public class Monster extends Character {
    private static final Color COLOR_TOP = new Color(160, 0, 0), COLOR_SIDE = new Color(120, 0, 0);
    private static final double WALK_SPEED = .15, RUN_SPEED = .45;

    private static final int SENSE_DISTANCE = 9;
    private boolean sense;
    private double senseX, senseY;
    private Path wanderPath;

    public Monster(Coordinate spawn) {
        super(false, COLOR_TOP, COLOR_SIDE, WALK_SPEED, RUN_SPEED, spawn);
    }

    void applyComputer(House house) {
        setRun(false);

        if (sense)
            wanderPath = house.getPather().pathFind(getX(), getY(), senseX, senseY);
        else if (wanderPath == null || wanderPath.done()) {
            CoordinateD wanderDestination = randomWanderDestination(house);
            wanderPath = house.getPather().pathFind(getX(), getY(), wanderDestination.getX(), wanderDestination.getY());
        }

        if (!wanderPath.done()) {
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

    void setSense(Character source) {
        double distance = Math3D.magnitude(getX() - source.getX(), getY() - source.getY()); // todo : use magnitudeSqr
        if (distance < SENSE_DISTANCE) {
            sense = true;
            senseX = source.getX();
            senseY = source.getY();
        } else
            sense = false;
    }
}