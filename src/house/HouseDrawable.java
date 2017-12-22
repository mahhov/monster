package house;

import camera.Camera;
import painter.painterelement.PainterQueue;

public interface HouseDrawable {
    void draw(PainterQueue painterQueue, Camera camera, double light);

    double getX();

    double getY();
}