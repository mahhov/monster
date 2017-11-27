package house.character;

import controller.Controller;
import house.House;

import java.awt.*;

public class Monster extends Character {
    private static final Color COLOR_TOP = new Color(0, 80, 0), COLOR_SIDE = new Color(0, 120, 0);
    private static final double WALK_SPEED = .15, RUN_SPEED = .45;

    public Monster(double x, double y) {
        super(COLOR_TOP, COLOR_SIDE, x, y);
    }

    public void update(House house, Controller controller) {
    }
}