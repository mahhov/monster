package painter.geometry;

import camera.Camera;

class Geometry {
    static CoordinateGroup getRect(double left, double top, double width, double height, Camera camera, double z) {
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