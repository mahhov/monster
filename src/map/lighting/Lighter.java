package map.lighting;

import map.Map;
import util.Math3D;
import util.Matrix;

public class Lighter {
    public static final double MIN_LIGHT = .1;
    private static final double MAX_LIGHT = 1;

    private IntersectionFinder intersectionFinder;
    private Map map;

    // temp var
    private double srcX, srcY;
    private int lightRange;
    private int x, y, startX, endX, startY, endY;
    private double distance;

    public Lighter(Map map) {
        this.map = map;
        intersectionFinder = new IntersectionFinder(map);
    }

    public void calculateLight(double srcX, double srcY, Matrix light, int lightRange) {
        this.lightRange = lightRange;
        setRange(srcX, srcY);
        srcX -= .5;
        srcY -= .5;

        for (x = startX; x <= endX; x++)
            for (y = startY; y <= endY; y++)
                if (hasView()) {
                    distance = Math3D.magnitude(x - srcX, y - srcY);
                    light.setValue(x, y, boundLight(lightValue()));
                } else
                    light.setValue(x, y, boundLight(0));
    }

    private void setRange(double x, double y) {
        srcX = x;
        srcY = y;
        startX = (int) (x - lightRange);
        endX = startX + lightRange + lightRange + 1;
        startY = (int) (y - lightRange);
        endY = startY + lightRange + lightRange + 1;
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
        return !intersectionFinder.intersects(srcX + .5, srcY + .5, x + .5, y + .5);
    }

    private double lightValue() {
        return 1 - distance / lightRange;
    }

    private double boundLight(double light) {
        return Math3D.minMax(light, MIN_LIGHT, MAX_LIGHT);
    }

    public IntersectionFinder getIntersectionFinder() {
        return intersectionFinder;
    }
}