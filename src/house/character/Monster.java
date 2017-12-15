package house.character;

import house.House;
import painter.geometry.Coordinate;
import util.Math3D;
import map.pather.Path;

import java.awt.*;

public class Monster extends Character {
    private static final double SMELL_DISTANCE = 3, SOUND_WALK_DISTANCE = 9, SOUND_RUN_DISTANCE = 27;
    private static final Color COLOR_TOP = new Color(160, 0, 0), COLOR_SIDE = new Color(120, 0, 0);
    private static final double WALK_SPEED = .15, RUN_SPEED = .45;

    private Path wanderPath;

    public Monster(Coordinate spawn) {
        super(false, COLOR_TOP, COLOR_SIDE, WALK_SPEED, RUN_SPEED, spawn, SMELL_DISTANCE, SOUND_WALK_DISTANCE, SOUND_RUN_DISTANCE);
    }

    void applyComputer(House house, Character target) {
        if (sense.sound) {
            // setRun(true);
            setDirX(sense.soundDirX);
            setDirY(sense.soundDirY);
            wanderPath = null;
            return;
        }

        if (sense.smell) {
            // setRun(true);
            setDirX(sense.smellX - getX());
            setDirY(sense.smellY - getY());
            wanderPath = null;
            return;
        }

        // todo: sense.sight

        wander(house, target);
    }

    private void wander(House house, Character target) {
        setRun(false);

        if (wanderPath == null || wanderPath.done())
            resetWander(house, target);
        else {
            setDirX(wanderPath.getX() - getX());
            setDirY(wanderPath.getY() - getY());
            wanderPath.update(getX(), getY());
        }
    }

    private void resetWander(House house, Character target) {
        double wanderX, wanderY;
        do {
            wanderX = Math3D.random(0, house.getWidth() - 1);
            wanderY = Math3D.random(0, house.getHeight() - 1);
        } while (!house.isMoveable((int) wanderX, (int) wanderY));
        wanderPath = house.getPather().pathFind(getX(), getY(), wanderX, wanderY);
    }
}