package geometry;

public class Coordinate {
    private double x, y;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public int[] transform(double scale, double shift) {
        return new int[] {(int) (x * scale + shift), (int) (y * scale + shift)};
    }

    public boolean isInView() {
        return x >= -.5 && x <= .5 && y >= -.5 && y <= .5;
    }

    public boolean isInViewX() {
        return x >= -.5 && x <= .5;
    }

    public boolean isInViewY() {
        return y >= -.5 && y <= .5;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
