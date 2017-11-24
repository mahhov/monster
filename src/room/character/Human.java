package room.character;

import camera.Follow;
import room.RoomElement;

public class Human implements Follow, RoomElement {
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
