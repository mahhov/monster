package house.character;

import painter.geometry.Coordinate;

import java.awt.*;

public class Human extends Character {
    private static final double SMELL_DISTANCE = 3, SOUND_WALK_DISTANCE = 9, SOUND_RUN_DISTANCE = 27;
    private static final Color COLOR_TOP = new Color(0, 120, 0), COLOR_SIDE = new Color(0, 80, 0);
    private static final double WALK_SPEED = .1, RUN_SPEED = .3;
    private boolean light;

    public Human(Coordinate spawn) {
        super(true, COLOR_TOP, COLOR_SIDE, WALK_SPEED, RUN_SPEED, spawn, SMELL_DISTANCE, SOUND_WALK_DISTANCE, SOUND_RUN_DISTANCE);
    }
}