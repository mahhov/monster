package house.character;

import house.House;
import map.pather.Path;
import util.Math3D;

import java.awt.*;

public class Monster extends Character {
    private static final Color COLOR_TOP = new Color(160, 0, 0), COLOR_SIDE = new Color(120, 0, 0);
    private static final double WALK_SPEED = .15, RUN_SPEED = .45;

    private static final int SENSE_SNIFF_COOLDOWN = 250;
    private int sniffCooldown;
    private Path wanderPath;

    public Monster(util.Coordinate spawn) {
        super(false, COLOR_TOP, COLOR_SIDE, WALK_SPEED, RUN_SPEED, spawn);
    }

    void applyComputer(House house) {
        if (wanderPath != null && !wanderPath.done()) {
            setDirX(wanderPath.getX() - getX());
            setDirY(wanderPath.getY() - getY());
            wanderPath.update(getX(), getY());
        } else {
            setDirX(0);
            setDirY(0);
        }
    }

    void setSense(House house, Character source) {
        boolean lineOfSight = house.lineOfSight(getX(), getY(), source.getX(), source.getY());
        boolean sniffReady = sniffCooldown == SENSE_SNIFF_COOLDOWN;
        if (!sniffReady)
            sniffCooldown++;

        if (lineOfSight)
            wanderPath = house.getPather().pathFind(getX(), getY(), source.getX(), source.getY());
        else if (sniffReady) {
            sniffCooldown = 0;
            double distance = Math3D.magnitude(getX() - source.getX(), getY() - source.getY()); // todo : use magnitudeSqr
            double sniffError = distance / 10;
            double sniffX = Math3D.random(source.getX() - sniffError, source.getX() + sniffError);
            double sniffY = Math3D.random(source.getY() - sniffError, source.getY() + sniffError);
            wanderPath = house.getPather().pathFind(getX(), getY(), sniffX, sniffY);
        }
    }
}