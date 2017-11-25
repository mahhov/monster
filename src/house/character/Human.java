package house.character;

import camera.Camera;
import camera.Follow;
import controller.Controller;
import coordinate.CoordinateGroup;
import house.House;
import house.HouseElement;
import painter.painterelement.PainterQueue;
import painter.painterelement.PainterRectangle;
import util.intersection.Intersection;

import java.awt.*;

public class Human implements Follow, HouseElement {
    private static final Color COLOR = new Color(0, 80, 0);
    private static final double WALK_SPEED = .1, RUN_SPEED = .3;
    private static final double SIZE = .5;
    private double x, y;
    private boolean run, light;
    private int stamina;

    public Human(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void update(House house, Controller controller) {
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

        Intersection intersection = house.getIntersectionFinder().find(orig, dir, speed, true, SIZE);

        x = intersection.getX();
        y = intersection.getY();
    }

    public void draw(PainterQueue painterQueue, Camera camera) {
        CoordinateGroup coordinates = House.getRect(x - SIZE * .5, y - SIZE * .5, SIZE, SIZE, camera);
        painterQueue.add(new PainterRectangle(coordinates, 1, COLOR, true, true));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
