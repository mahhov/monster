package house.particle;

import camera.Camera;
import house.HouseDrawable;
import painter.painterelement.PainterQueue;
import util.DrawUtil;

import java.awt.*;

public class Particle implements HouseDrawable {
    private double x, y;
    private double size;
    private Color color;

    Particle(double x, double y, double size, Color color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
    }

    public boolean update() {
        return false;
    }

    public void draw(PainterQueue painterQueue, Camera camera, double light) {
        DrawUtil.drawRectFromCenter(painterQueue, camera, x, y, size, light, color, PainterQueue.PARTICLE_LAYER);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}