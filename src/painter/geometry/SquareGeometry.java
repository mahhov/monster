package painter.geometry;

import camera.Camera;

public class SquareGeometry extends Geometry{
    private CoordinateGroup coordinates;

    public SquareGeometry(double left, double top, double bottom, double width, double length, Camera camera) {
        coordinates = getRect(left, top, width, length, camera, bottom);
    } 
    
    public CoordinateGroup getRect() {
        return coordinates;
    }
}