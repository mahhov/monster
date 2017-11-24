package camera;

public class Camera {
    private static final double PAN_WEIGHT = .1;

    private double x, y, z;
    private Follow follow;

    public Camera(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void move() {
        x += (follow.getX() - x) * PAN_WEIGHT;
        y += (follow.getY() - y) * PAN_WEIGHT;
    }

    public void setFollow(Follow follow) {
        this.follow = follow;
    }
}
