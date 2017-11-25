package house;

import camera.Camera;
import controller.Controller;
import painter.painterelement.PainterQueue;

public interface HouseElement {
    void update(Controller controller);

    void draw(PainterQueue painterQueue, Camera camera);
}
