package util;

public class Math3D {
    public static final double EPSILON = 0.00001;
    public static final double SQRT3 = Math.sqrt(3);

    public static double min(double val, double min) {
        if (val > min)
            return min;
        return val;
    }

    public static int max(int val, int max) {
        if (val < max)
            return max;
        return val;
    }

    public static double max(double val, double max) {
        if (val < max)
            return max;
        return val;
    }

    public static int abs(int value) {
        return value > 0 ? value : -value;
    }

    public static double abs(double value) {
        return value > 0 ? value : -value;
    }

    public static double sign(double value) {
        return value > 0 ? 1 : -1;
    }

    public static boolean isZero(double value) {
        return value < Math3D.EPSILON && value > -Math3D.EPSILON;
    }

    public static double magnitude(double x, double y) {
        return java.lang.Math.sqrt(x * x + y * y);
    }

    public static double magnitudeSquared(double x, double y) {
        return x * x + y * y;
    }

    public static double[] setMagnitude(double x, double y, double mag) {
        double m = magnitude(x, y) / mag;
        if (m == 0)
            return new double[] {x, y};
        return new double[] {x / m, y / m};
    }

    public static boolean randBoolean(double trueWeight) {
        return Math.random() < trueWeight;
    }

    public static int random(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    public static void sleep(int howLong) {
        try {
            Thread.sleep(howLong);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}