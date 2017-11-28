package camera;

import controller.Controller;

public class Camera {
    private static final double PAN_WEIGHT = .1, Z_SPEED = 1.05;
    private static final double MIN_Z = 10, MAX_Z = 100;

    private double x, y, z, zgoal;
    private Follow follow;

    public Camera(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        setZGoal(z);
    }

    public void move(Controller controller) {
        x += (follow.getX() - x) * PAN_WEIGHT;
        y += (follow.getY() - y) * PAN_WEIGHT;
        z += (zgoal - z) * PAN_WEIGHT;

        if (controller.isKeyDown(Controller.KEY_MINUS))
            setZGoal(zgoal * Z_SPEED);
        if (controller.isKeyDown(Controller.KEY_EQUAL))
            setZGoal(zgoal / Z_SPEED);
    }

    public void setFollow(Follow follow) {
        this.follow = follow;
    }

    public double getViewCenterX() {
        return x;
    }

    public double getViewCenterY() {
        return y;
    }

    public double getBlockSize(double z) {
        return 1 / (this.z - z);
    }

    private void setZGoal(double z) {
        if (z < MIN_Z)
            z = MIN_Z;
        else if (z > MAX_Z)
            z = MAX_Z;
        zgoal = z;
    }
}