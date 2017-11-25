package house.character;

import camera.Camera;
import camera.Follow;
import controller.Controller;
import house.HouseElement;
import painter.painterelement.PainterQueue;

public class Human implements Follow, HouseElement {
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
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
