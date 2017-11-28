package house;

import camera.Camera;
import controller.Controller;
import house.character.Character;
import painter.painterelement.PainterQueue;

public interface HouseCharacter {
    void update(House house, Controller controller, Character otherCharacter);

    void draw(PainterQueue painterQueue, Camera camera);
}