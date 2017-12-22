package house.character;

import controller.Controller;
import house.House;
import house.particle.TimedParticle;
import map.pather.Path;
import util.Math3D;

import java.awt.*;

public class Monster extends Character {
    private static final Color COLOR_TOP = new Color(160, 0, 0), COLOR_SIDE = new Color(120, 0, 0);

    private static final Color COLOR_TRAIL = Color.BLACK;
    private static final double TRAIL_SIZE = .4, TRAIL_DESNITY = .05;
    private static final int TRAIL_DURATION = 800;

    private static final double WALK_SPEED = .12, RUN_SPEED = .45;

    private static final double MIN_SNIFF_ERROR = 5, MAX_100_SNIFF_ERROR = 25;
    private static final int SENSE_SNIFF_COOLDOWN = 250;
    private int sniffCooldown;
    private Path wanderPath;

    public Monster(util.Coordinate spawn) {
        super(COLOR_TOP, COLOR_SIDE, WALK_SPEED, RUN_SPEED, spawn);
    }

    public void update(House house, Controller controller, Character otherCharacter) {
        if (Math3D.randBoolean(TRAIL_DESNITY))
            house.addParticle(new TimedParticle(getX(), getY(), TRAIL_SIZE, COLOR_TRAIL, TRAIL_DURATION));
        applyComputer(house);
        super.update(house, controller, otherCharacter);
    }

    private void applyComputer(House house) {
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
            double distanceSqr = Math3D.magnitudeSqr(getX() - source.getX(), getY() - source.getY());
            double sniffError = distanceSqr / 10 + 5;
            double sniffX = Math3D.random(source.getX() - sniffError, source.getX() + sniffError);
            double sniffY = Math3D.random(source.getY() - sniffError, source.getY() + sniffError);
            wanderPath = house.getPather().pathFind(getX(), getY(), sniffX, sniffY);
        }
    }
}