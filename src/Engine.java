import camera.Camera;
import controller.Controller;
import controller.ControllerJava;
import controller.ControllerJavaListener;
import painter.Painter;
import painter.PainterJava;
import painter.painterelement.PainterQueue;
import room.Room;
import room.character.Human;
import room.character.Monster;
import util.Math3D;

public class Engine implements Runnable {
    private static final int FRAME = 800, IMAGE = 400;

    private Controller controller;
    private Painter painter;
    private Camera camera;
    private Room room;
    private Human human;
    private Monster monster;

    private boolean pause;

    private Engine() {
        controller = new ControllerJava();
        painter = new PainterJava(FRAME, IMAGE, (ControllerJavaListener) controller);
        camera = new Camera(0, 0, 0);
        createRoom();
    }

    private void createRoom() {
        room = new Room();
        human = new Human();
        monster = new Monster();
        camera.setFollow(human);
        room.addElement(human);
        room.addElement(monster);
    }

    private void begin() {
        new Thread(this).start();
        painter.run();
    }

    public void run() {
        int frame = 0, engineFrame = 0;
        long beginTime = 0, endTime;

        while (true) {
            checkPause();
            while (pause) {
                checkPause();
                Math3D.sleep(30);
            }

            camera.move();
            room.update(controller);

            if (painter.isPainterQueueDone()) {
                PainterQueue painterQueue = new PainterQueue();
                room.draw(painterQueue, camera);
                painterQueue.drawReady = true;
                painter.setPainterQueue(painterQueue);
                frame++;
            }
            engineFrame++;

            Math3D.sleep(10);
            endTime = System.nanoTime() + 1;
            if (endTime - beginTime > 1000000000L) {
                Painter.DEBUG_STRING[0] = "draw fps: " + frame + " ; engine fps: " + engineFrame;
                frame = 0;
                engineFrame = 0;
                beginTime = endTime;
            }
        }
    }

    private void checkPause() {
        if (controller.isKeyPressed(Controller.KEY_P))
            pause = !pause;
    }

    public static void main(String[] args) {
        new Engine().begin();
    }
}
