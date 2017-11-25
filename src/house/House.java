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
        this.elements.add(element);
    }

    public void update(Controller controller) {
    }

    public void draw(PainterQueue painterQueue, Camera camera) {
        for (int x = 0; x < walls.length; x++)
            for (int y = 0; y < walls[0].length; y++)
                if (walls[x][y])
                    painterQueue.add(getRect(x, y));
    }

    private PainterRectangle getRect(int x, int y) {
        CoordinateGroup coordinateGroup = new CoordinateGroup(2);
        double width = 1. / walls.length, height = 1. / walls[0].length;
        double left = x * width - .5, top = y * height - .5;
        coordinateGroup.addCoordinate(new Coordinate(left, top));
        coordinateGroup.addCoordinate(new Coordinate(left + width, top + height));
        return new PainterRectangle(coordinateGroup, 1, Color.DARK_GRAY, false, true);
    }
}
