package house.character;

import controller.Controller;
import geometry.Coordinate;
import house.House;
import util.intersection.Intersection;

import java.awt.*;

public class Human extends Character {
    private static final double SMELL_DISTANCE = 3, SOUND_WALK_DISTANCE = 9, SOUND_RUN_DISTANCE = 27;
    private static final Color COLOR_TOP = new Color(0, 120, 0), COLOR_SIDE = new Color(0, 80, 0);
    private static final double WALK_SPEED = .1, RUN_SPEED = .3;
    private boolean light;

    public Human(Coordinate spawn) {
        super(COLOR_TOP, COLOR_SIDE, spawn, SMELL_DISTANCE, SOUND_WALK_DISTANCE, SOUND_RUN_DISTANCE);
    }

    public void update(House house, Controller controller, Character otherCharacter) {
        super.update(house, controller, otherCharacter);

        run = controller.isKeyDown(Controller.KEY_RIGHT_CAROT);
        double speed = run ? RUN_SPEED : WALK_SPEED;

        double[] orig = new double[] {x, y};
        double[] dir = new double[] {0, 0};

        if (controller.isKeyDown(Controller.KEY_A))
            dir[0] -= speed;
        if (controller.isKeyDown(Controller.KEY_D))
            dir[0] += speed;
        if (controller.isKeyDown(Controller.KEY_W))
            dir[1] -= speed;
        if (controller.isKeyDown(Controller.KEY_S))
            dir[1] += speed;

        Intersection intersection = house.getIntersectionFinder().find(orig, dir, speed, SIZE);

        x = intersection.getX();
        y = intersection.getY();
    }
}