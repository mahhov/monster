package house.character;

import camera.Camera;
import camera.Follow;
import controller.Controller;
import coordinate.CoordinateGroup;
import house.House;
import house.HouseElement;
import painter.painterelement.PainterQueue;
import painter.painterelement.PainterRectangle;

import java.awt.*;

public class Human implements Follow, HouseElement {
    private static final Color COLOR = new Color(0, 80, 0);
    private static final double WALK_SPEED = .1, RUN_SPEED = .3;
    private double x, y;
    private boolean run, light;
    private int stamina;

    public void update(Controller controller) {
        run = controller.isKeyDown(Controller.KEY_RIGHT_CAROT);
        double speed = run ? RUN_SPEED : WALK_SPEED;

        if (controller.isKeyDown(Controller.KEY_A))
            x -= speed;
        if (controller.isKeyDown(Controller.KEY_D))
            x += speed;
        if (controller.isKeyDown(Controller.KEY_W))
            y -= speed;
        if (controller.isKeyDown(Controller.KEY_S))
            y += speed;
    }

    public void draw(PainterQueue painterQueue, Camera camera) {
        CoordinateGroup coordinates = House.getRect(x - .25, y - .25, .5, .5, camera);
        painterQueue.add(new PainterRectangle(coordinates, 1, COLOR, true, true));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
