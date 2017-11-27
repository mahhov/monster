package house;

import camera.Camera;
import controller.Controller;
import geometry.CubeGeometry;
import painter.painterelement.PainterPolygon;
import painter.painterelement.PainterQueue;
import util.LList;
import util.intersection.IntersectionFinder;
import util.intersection.Map;

import java.awt.*;

public class House implements Map {
    private boolean[][] walls;
    private LList<HouseElement> elements;
    private IntersectionFinder intersectionFinder;

    public House(boolean[][] walls) {
        this.walls = walls;
        elements = new LList<>();
        intersectionFinder = new IntersectionFinder(this);
    }

    public void addElement(HouseElement element) {
        elements = elements.add(element);
    }

    public void update(Controller controller) {
        for (LList<HouseElement> element : elements)
            element.node.update(this, controller);
    }

    public IntersectionFinder getIntersectionFinder() {
        return intersectionFinder;
    }

    public int getWidth() {
        return walls.length;
    }

    public int getHeight() {
        return walls[0].length;
    }

    public boolean isMoveable(int x, int y) {
        return !walls[x][y];
    }

    public void draw(PainterQueue painterQueue, Camera camera) {
        for (int x = 0; x < walls.length; x++)
            for (int y = 0; y < walls[0].length; y++)
                if (walls[x][y]) {
                    CubeGeometry geometry = new CubeGeometry(x, y, 0, 1, 1, 1, camera);

                    painterQueue.add(new PainterPolygon(geometry.getTop(), 1, Color.LIGHT_GRAY, false, true), PainterQueue.WALL_TOP_LAYER);
                    painterQueue.add(new PainterPolygon(geometry.getFront(), 1, Color.DARK_GRAY, false, true), PainterQueue.WALL_SIDE_LAYER);
                    painterQueue.add(new PainterPolygon(geometry.getRight(), 1, Color.DARK_GRAY, false, true), PainterQueue.WALL_SIDE_LAYER);
                    painterQueue.add(new PainterPolygon(geometry.getBack(), 1, Color.DARK_GRAY, false, true), PainterQueue.WALL_SIDE_LAYER);
                    painterQueue.add(new PainterPolygon(geometry.getLeft(), 1, Color.DARK_GRAY, false, true), PainterQueue.WALL_SIDE_LAYER);
                }

        for (LList<HouseElement> element : elements)
            element.node.draw(painterQueue, camera);
    }
}
