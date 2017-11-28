package house;

import camera.Camera;
import geometry.CubeGeometry;
import painter.painterelement.PainterPolygon;
import painter.painterelement.PainterQueue;

import java.awt.*;

public interface HouseDrawable {
    void draw(PainterQueue painterQueue, Camera camera);
}