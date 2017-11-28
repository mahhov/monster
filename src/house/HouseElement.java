package house;

import camera.Camera;
import painter.painterelement.PainterQueue;

public interface HouseElement {
    void update(House house);

    void draw(PainterQueue painterQueue, Camera camera);
}
