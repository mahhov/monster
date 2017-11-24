package util;

public class Math3D {
    public static double min(double val, double min) {
        if (val > min)
            return min;
        return val;
    }

    public static void sleep(int howLong) {
        try {
            Thread.sleep(howLong);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
