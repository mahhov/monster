package house;

import camera.Camera;
import controller.Controller;
import coordinate.Coordinate;
import coordinate.CoordinateGroup;
import house.generator.HouseGenerator;
import painter.painterelement.PainterQueue;
import painter.painterelement.PainterRectangle;
import util.LList;

import java.awt.*;

public class House {
    private boolean[][] walls;
    private LList<HouseElement> elements;

    public House() {
        walls = new HouseGenerator().generate();
        elements = new LList<>();
    }

    public void addElement(HouseElement element) {
        elements = elements.add(element);
    }

    public void update(Controller controller) {
        for (LList<HouseElement> element : elements)
            element.node.update(controller);
    }

    public void draw(PainterQueue painterQueue, Camera camera) {
        for (int x = 0; x < walls.length; x++)
            for (int y = 0; y < walls[0].length; y++)
                if (walls[x][y]) {
                    CoordinateGroup coordinates = getRect(x, y, 1, 1, camera);
                    painterQueue.add(new PainterRectangle(coordinates, 1, Color.DARK_GRAY, false, true));
                }

        for (LList<HouseElement> element : elements)
            element.node.draw(painterQueue, camera);
    }

    public static CoordinateGroup getRect(double left, double top, double width, double height, Camera camera) {
        CoordinateGroup coordinateGroup = new CoordinateGroup(2);
        double blockSize = camera.getBlockSize();
        left = (left - camera.getViewCenterX()) * blockSize;
        top = (top - camera.getViewCenterY()) * blockSize;
        coordinateGroup.addCoordinate(new Coordinate(left, top));
        coordinateGroup.addCoordinate(new Coordinate(left + width * blockSize, top + height * blockSize));
        return coordinateGroup;
    }
}
