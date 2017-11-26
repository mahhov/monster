package camera;

public class Camera {
    private static final double PAN_WEIGHT = .1;

    public double x, y, z, zinv;
    private Follow follow;

    public Camera(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        zinv = 1 / z;
    }

    public void move() {
        x += (follow.getX() - x) * PAN_WEIGHT;
        y += (follow.getY() - y) * PAN_WEIGHT;
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

    public double getViewSize() {
        return z;
    }

    public double getBlockSize() {
        return zinv;
    }

    public double getBlockSize(double z) {
        return z == 0 ? zinv : 1 / (this.z - z);
    }
}
