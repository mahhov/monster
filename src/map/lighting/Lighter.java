package map.lighting;

import map.Map;
import util.Math3D;
import util.Matrix;

public class Lighter {
    private static final int MAX_LIGHT_DISTANCE = 10;
    private static final double MIN_LIGHT = .1, MAX_LIGHT = 1;

    private IntersectionFinder intersectionFinder;
    private Map map;
    private Matrix light;

    // temp var
    private int srcX, srcY;
    private int x, y, startX, endX, startY, endY;
    private double distance;

    public Lighter(Map map) {
        this.map = map;
        intersectionFinder = new IntersectionFinder(map);
        light = new Matrix(map.getWidth(), map.getHeight(), MIN_LIGHT);
    }

    public Matrix calculateLight(double srcX, double srcY) {
        setRange((int) srcX, (int) srcY);
        srcX -= .5;
        srcY -= .5;
        light.reset();

        for (x = startX; x <= endX; x++)
            for (y = startY; y <= endY; y++)
                if (hasView()) {
                    distance = Math3D.magnitude(x - srcX, y - srcY);
                    light.setValue(x, y, boundLight(lightValue()));
                } else
                    light.setValue(x, y, boundLight(0));

        return light;
    }

    private void setRange(int x, int y) {
        srcX = x;
        srcY = y;
        startX = x - MAX_LIGHT_DISTANCE;
        endX = x + MAX_LIGHT_DISTANCE;
        startY = y - MAX_LIGHT_DISTANCE;
        endY = y + MAX_LIGHT_DISTANCE;
        if (startX < 0)
            startX = 0;
        if (endX >= map.getWidth())
            endX = map.getWidth() - 1;
        if (startY < 0)
            startY = 0;
        if (endY >= map.getHeight())
            endY = map.getHeight() - 1;
    }

    private boolean hasView() {
        return !intersectionFinder.intersects(srcX, srcY, x, y);
    }

    private double lightValue() {
        return 1 - distance / MAX_LIGHT_DISTANCE;
    }

    private double boundLight(double light) {
        return Math3D.minMax(light, MIN_LIGHT, MAX_LIGHT);
    }
}