package painter.geometry;

import camera.Camera;

public class CubeGeometry extends Geometry {
    private CoordinateGroup coordinatesBottom, coordinatesTop, coordinatesFront, coordinatesRight, coordinatesBack, coordinatesLeft;

    public CubeGeometry(double left, double top, double bottom, double width, double length, double height, Camera camera) {
        coordinatesBottom = getRect(left, top, width, length, camera, bottom);
        coordinatesTop = getRect(left, top, width, length, camera, bottom + height);

        coordinatesFront = new CoordinateGroup(4);
        coordinatesFront.addCoordinate(coordinatesTop.getCoordinate(3));
        coordinatesFront.addCoordinate(coordinatesTop.getCoordinate(2));
        coordinatesFront.addCoordinate(coordinatesBottom.getCoordinate(2));
        coordinatesFront.addCoordinate(coordinatesBottom.getCoordinate(3));

        coordinatesRight = new CoordinateGroup(4);
        coordinatesRight.addCoordinate(coordinatesTop.getCoordinate(2));
        coordinatesRight.addCoordinate(coordinatesTop.getCoordinate(1));
        coordinatesRight.addCoordinate(coordinatesBottom.getCoordinate(1));
        coordinatesRight.addCoordinate(coordinatesBottom.getCoordinate(2));

        coordinatesBack = new CoordinateGroup(4);
        coordinatesBack.addCoordinate(coordinatesTop.getCoordinate(1));
        coordinatesBack.addCoordinate(coordinatesTop.getCoordinate(0));
        coordinatesBack.addCoordinate(coordinatesBottom.getCoordinate(0));
        coordinatesBack.addCoordinate(coordinatesBottom.getCoordinate(1));

        coordinatesLeft = new CoordinateGroup(4);
        coordinatesLeft.addCoordinate(coordinatesTop.getCoordinate(0));
        coordinatesLeft.addCoordinate(coordinatesTop.getCoordinate(3));
        coordinatesLeft.addCoordinate(coordinatesBottom.getCoordinate(3));
        coordinatesLeft.addCoordinate(coordinatesBottom.getCoordinate(0));
    }

    public CoordinateGroup getTop() {
        return coordinatesTop;
    }

    public CoordinateGroup getFront() {
        return coordinatesFront;
    }

    public CoordinateGroup getRight() {
        return coordinatesRight;
    }

    public CoordinateGroup getBack() {
        return coordinatesBack;
    }

    public CoordinateGroup getLeft() {
        return coordinatesLeft;
    }
}