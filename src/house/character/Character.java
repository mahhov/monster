package house.character;

import camera.Camera;
import camera.Follow;
import geometry.CubeGeometry;
import house.HouseElement;
import painter.painterelement.PainterPolygon;
import painter.painterelement.PainterQueue;

import java.awt.*;

public abstract class Character implements Follow, HouseElement {
    static final double SIZE = .5;
    private Color colorTop, colorSide;
    double x, y;
    boolean run;

    Character(Color colorTop, Color colorSide, double x, double y) {
        this.colorTop = colorTop;
        this.colorSide = colorSide;
        this.x = x;
        this.y = y;
    }

    public void draw(PainterQueue painterQueue, Camera camera) {
        CubeGeometry geometry = new CubeGeometry(x - SIZE * .5, y - SIZE * .5, 0, SIZE, SIZE, SIZE, camera);

        painterQueue.add(new PainterPolygon(geometry.getTop(), 1, colorTop, false, true), PainterQueue.CHARACTER_TOP_LAYER);
        painterQueue.add(new PainterPolygon(geometry.getFront(), 1, colorSide, false, true), PainterQueue.CHARACTER_SIDE_LAYER);
        painterQueue.add(new PainterPolygon(geometry.getRight(), 1, colorSide, false, true), PainterQueue.CHARACTER_SIDE_LAYER);
        painterQueue.add(new PainterPolygon(geometry.getBack(), 1, colorSide, false, true), PainterQueue.CHARACTER_SIDE_LAYER);
        painterQueue.add(new PainterPolygon(geometry.getLeft(), 1, colorSide, false, true), PainterQueue.CHARACTER_SIDE_LAYER);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}