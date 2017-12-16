package util;

public class Matrix {
    private final double defaultValue;
    private long current;
    private long updated[][];
    private double value[][];

    public Matrix(int width, int height, double defaultValue) {
        this.defaultValue = defaultValue;
        current = 1;
        updated = new long[width][height];
        value = new double[width][height];
    }

    public void reset() {
        current++;
    }

    public void setValue(int x, int y, double value) {
        updated[x][y] = current;
        this.value[x][y] = value;
    }

    public double getValue(int x, int y) {
        return updated[x][y] == current ? value[x][y] : defaultValue;
    }
}
