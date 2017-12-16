package util;

import camera.Camera;
import painter.geometry.CubeGeometry;
import painter.geometry.SquareGeometry;
import painter.painterelement.PainterPolygon;
import painter.painterelement.PainterQueue;

import java.awt.*;

public class DrawUtil {
    public static void drawRectFromCorner(PainterQueue painterQueue, Camera camera, double left, double top, double size, double light, Color color, int layer) {
        SquareGeometry geometry = new SquareGeometry(left, top, 0, size, size, camera);
        painterQueue.add(new PainterPolygon(geometry.getRect(), light, color, false, true), layer);
    }

    public static void drawCubeFromCorner(PainterQueue painterQueue, Camera camera, double left, double top, double size, double light, Color colorTop, Color colorSide, int topLayer, int sideLayer) {
        CubeGeometry geometry = new CubeGeometry(left, top, 0, size, size, size, camera);
        painterQueue.add(new PainterPolygon(geometry.getTop(), light, colorTop, false, true), topLayer);
        painterQueue.add(new PainterPolygon(geometry.getFront(), light, colorSide, false, true), sideLayer);
        painterQueue.add(new PainterPolygon(geometry.getRight(), light, colorSide, false, true), sideLayer);
        painterQueue.add(new PainterPolygon(geometry.getBack(), light, colorSide, false, true), sideLayer);
        painterQueue.add(new PainterPolygon(geometry.getLeft(), light, colorSide, false, true), sideLayer);
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