import camera.Camera;
import controller.Controller;
import controller.ControllerJava;
import controller.ControllerJavaListener;
import house.House;
import house.character.Human;
import house.character.Monster;
import house.generator.HouseGenerator;
import painter.Painter;
import painter.PainterJava;
import painter.painterelement.PainterQueue;
import util.Math3D;

public class Engine implements Runnable {
    private static final int FRAME = 800, IMAGE = 400;

    private Controller controller;
    private Painter painter;
    private Camera camera;
    private House house;
    private Human human;
    private Monster monster;

    private boolean pause;

    private Engine() {
        controller = new ControllerJava();
        painter = new PainterJava(FRAME, IMAGE, (ControllerJavaListener) controller);
        camera = new Camera(0, 0, 10);
        createRoom();
    }

    private void createRoom() {
        HouseGenerator houseGenerator = new HouseGenerator();
        houseGenerator.generate();

        house = new House(houseGenerator.getWalls());
        human = new Human(houseGenerator.getHumanSpawnX(), houseGenerator.getHumanSpawnY());
        monster = new Monster();
        camera.setFollow(human);
        house.addElement(human);
        house.addElement(monster);
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
            house.update(controller);

            if (painter.isPainterQueueDone()) {
                PainterQueue painterQueue = new PainterQueue();
                house.draw(painterQueue, camera);
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
