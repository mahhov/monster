package house;

import camera.Camera;
import util.Coordinate;
import painter.painterelement.PainterQueue;
import util.DrawUtil;

import java.awt.*;

public class Exit implements HouseDrawable {
    private static final Color COLOR_TOP = new Color(0, 0, 160), COLOR_SIDE = new Color(0, 0, 120);
    private double x, y;

    public Exit(Coordinate spawn) {
        x = spawn.getX();
        y = spawn.getY();
    }

    public void draw(PainterQueue painterQueue, Camera camera) {
        DrawUtil.drawCubeFromCenter(painterQueue, camera, x, y, 1, COLOR_TOP, COLOR_SIDE, PainterQueue.CHARACTER_TOP_LAYER, PainterQueue.CHARACTER_SIDE_LAYER);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}