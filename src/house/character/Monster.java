package house.character;

import camera.Camera;
import camera.Follow;
import controller.Controller;
import house.House;
import house.HouseElement;
import painter.painterelement.PainterQueue;

public class Monster implements Follow, HouseElement {
    private double x, y;
    private boolean run, light;
    private int stamina;

    public void update(House hosue, Controller controller) {
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
