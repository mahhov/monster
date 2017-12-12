package util;

import camera.Camera;
import painter.geometry.CubeGeometry;
import painter.painterelement.PainterPolygon;
import painter.painterelement.PainterQueue;

import java.awt.*;

public class DrawUtil {
    public static void drawCubeFromCorner(PainterQueue painterQueue, Camera camera, double left, double top, double size, Color colorTop, Color colorSide, int topLayer, int sideLayer) {
        CubeGeometry geometry = new CubeGeometry(left, top, 0, size, size, size, camera);

        painterQueue.add(new PainterPolygon(geometry.getTop(), 1, colorTop, false, true), topLayer);
        painterQueue.add(new PainterPolygon(geometry.getFront(), 1, colorSide, false, true), sideLayer);
        painterQueue.add(new PainterPolygon(geometry.getRight(), 1, colorSide, false, true), sideLayer);
        painterQueue.add(new PainterPolygon(geometry.getBack(), 1, colorSide, false, true), sideLayer);
        painterQueue.add(new PainterPolygon(geometry.getLeft(), 1, colorSide, false, true), sideLayer);
    }

    public static void drawCubeFromCenter(PainterQueue painterQueue, Camera camera, double x, double y, double size, Color colorTop, Color colorSide, int topLayer, int sideLayer) {
        CubeGeometry geometry = new CubeGeometry(x - size * .5, y - size * .5, 0, size, size, size, camera);

        painterQueue.add(new PainterPolygon(geometry.getTop(), 1, colorTop, false, true), topLayer);
        painterQueue.add(new PainterPolygon(geometry.getFront(), 1, colorSide, false, true), sideLayer);
        painterQueue.add(new PainterPolygon(geometry.getRight(), 1, colorSide, false, true), sideLayer);
        painterQueue.add(new PainterPolygon(geometry.getBack(), 1, colorSide, false, true), sideLayer);
        painterQueue.add(new PainterPolygon(geometry.getLeft(), 1, colorSide, false, true), sideLayer);
    }
}