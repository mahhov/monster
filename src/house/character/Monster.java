package house.character;

import camera.Follow;
import house.HouseElement;

public class Monster implements Follow, HouseElement {
    private double x, y;
    private boolean run, light;
    private int stamina;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
