package util;

public class Math3D {
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

    public static int abs(int value) {
        return value > 0 ? value : -value;
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