package painter.geometry;

public class Coordinate { // todo: whenever possible, convert geometry.coordinates to util.coordinates
    private double x, y;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public int[] transform(double scale, double shift) {
        return new int[] {(int) (x * scale + shift), (int) (y * scale + shift)};
    }

    boolean isInView() {
        return x >= -.5 && x <= .5 && y >= -.5 && y <= .5;
    }

    boolean isInViewX() {
        return x >= -.5 && x <= .5;
    }

    boolean isInViewY() {
        return y >= -.5 && y <= .5;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}