package house.character;

import controller.Controller;
import geometry.Coordinate;
import house.House;

import java.awt.*;

public class Monster extends Character {
    private static final double SMELL_DISTANCE = 3, SOUND_WALK_DISTANCE = 9, SOUND_RUN_DISTANCE = 27;
    private static final Color COLOR_TOP = new Color(160, 0, 0), COLOR_SIDE = new Color(120, 0, 0);
    private static final double WALK_SPEED = .15, RUN_SPEED = .45;

    public Monster(Coordinate spawn) {
        super(COLOR_TOP, COLOR_SIDE, spawn, SMELL_DISTANCE, SOUND_WALK_DISTANCE, SOUND_RUN_DISTANCE);
    }

    public void update(House house, Controller controller, Character otherCharacter) {
        super.update(house, controller, otherCharacter);

    }
}