package room;

import camera.Camera;
import controller.Controller;
import painter.painterelement.PainterQueue;
import util.LList;

public class Room {
    private boolean[][] wall;
    private LList<RoomElement> elements;

    public Room() {
        elements = new LList<>();
    }

    public void addElement(RoomElement element) {
        this.elements.add(element);
    }

    public void update(Controller controller) {
    }

    public void draw(PainterQueue painterQueue, Camera camera) {
    }
}
