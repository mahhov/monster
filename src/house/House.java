package house;

import camera.Camera;
import controller.Controller;
import coordinate.Coordinate;
import coordinate.CoordinateGroup;
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
                    CoordinateGroup coordinatesBottom = getRect(x, y, 1, 1, camera, 0);

                    CoordinateGroup coordinatesTop = House.getRect(x, y, 1, 1, camera, 1);

                    CoordinateGroup coordinatesFront = new CoordinateGroup(4);
                    coordinatesFront.addCoordinate(coordinatesTop.getCoordinate(3));
                    coordinatesFront.addCoordinate(coordinatesTop.getCoordinate(2));
                    coordinatesFront.addCoordinate(coordinatesBottom.getCoordinate(2));
                    coordinatesFront.addCoordinate(coordinatesBottom.getCoordinate(3));

                    CoordinateGroup coordinatesRight = new CoordinateGroup(4);
                    coordinatesRight.addCoordinate(coordinatesTop.getCoordinate(2));
                    coordinatesRight.addCoordinate(coordinatesTop.getCoordinate(1));
                    coordinatesRight.addCoordinate(coordinatesBottom.getCoordinate(1));
                    coordinatesRight.addCoordinate(coordinatesBottom.getCoordinate(2));

                    CoordinateGroup coordinatesBack = new CoordinateGroup(4);
                    coordinatesBack.addCoordinate(coordinatesTop.getCoordinate(1));
                    coordinatesBack.addCoordinate(coordinatesTop.getCoordinate(0));
                    coordinatesBack.addCoordinate(coordinatesBottom.getCoordinate(0));
                    coordinatesBack.addCoordinate(coordinatesBottom.getCoordinate(1));

                    CoordinateGroup coordinatesLeft = new CoordinateGroup(4);
                    coordinatesLeft.addCoordinate(coordinatesTop.getCoordinate(0));
                    coordinatesLeft.addCoordinate(coordinatesTop.getCoordinate(3));
                    coordinatesLeft.addCoordinate(coordinatesBottom.getCoordinate(3));
                    coordinatesLeft.addCoordinate(coordinatesBottom.getCoordinate(0));

                    painterQueue.add(new PainterPolygon(coordinatesTop, 1, Color.LIGHT_GRAY, false, true), PainterQueue.WALL_TOP_LAYER);
                    painterQueue.add(new PainterPolygon(coordinatesFront, 1, Color.DARK_GRAY, false, true), PainterQueue.WALL_SIDE_LAYER);
                    painterQueue.add(new PainterPolygon(coordinatesRight, 1, Color.DARK_GRAY, false, true), PainterQueue.WALL_SIDE_LAYER);
                    painterQueue.add(new PainterPolygon(coordinatesBack, 1, Color.DARK_GRAY, false, true), PainterQueue.WALL_SIDE_LAYER);
                    painterQueue.add(new PainterPolygon(coordinatesLeft, 1, Color.DARK_GRAY, false, true), PainterQueue.WALL_SIDE_LAYER);
                }

        for (LList<HouseElement> element : elements)
            element.node.draw(painterQueue, camera);
    }

    public static CoordinateGroup getRect(double left, double top, double width, double height, Camera camera, double z) {
        CoordinateGroup coordinateGroup = new CoordinateGroup(4);
        double blockSize = camera.getBlockSize(z);
        left = (left - camera.getViewCenterX()) * blockSize;
        top = (top - camera.getViewCenterY()) * blockSize;
        coordinateGroup.addCoordinate(new Coordinate(left, top));
        coordinateGroup.addCoordinate(new Coordinate(left + width * blockSize, top));
        coordinateGroup.addCoordinate(new Coordinate(left + width * blockSize, top + height * blockSize));
        coordinateGroup.addCoordinate(new Coordinate(left, top + height * blockSize));
        return coordinateGroup;
    }
}
