package engine;

import camera.Camera;
import controller.Controller;
import controller.ControllerJava;
import controller.ControllerJavaListener;
import house.Exit;
import house.House;
import house.character.Human;
import house.character.Monster;
import house.generator.HouseGenerator;
import painter.Painter;
import painter.PainterJava;
import painter.painterelement.PainterQueue;
import util.Math3D;

public class Engine implements Runnable {
    private static final int FRAME = 400, IMAGE = 400;

    private Controller controller;
    private Painter painter;
    private Camera camera;
    private House house;

    private boolean pause;

    private Engine() {
        controller = new ControllerJava();
        painter = new PainterJava(FRAME, IMAGE, (ControllerJavaListener) controller);
        camera = new Camera(0, 0, 20);
        createHouse();
    }

    private void createHouse() {
        HouseGenerator houseGenerator = new HouseGenerator();
        houseGenerator.generate();

        Human human = new Human(houseGenerator.getSpawn(0));
        Monster monster = new Monster(houseGenerator.getSpawn(1));

        house = new House(houseGenerator.getWalls(), houseGenerator.getLights());
        house.setHuman(human);
        house.setMonster(monster);
        house.setExit(new Exit(houseGenerator.getSpawn(2)));

        camera.setFollow(human);
    }

    private void begin() {
        new Thread(this).start();
        painter.run();
    }

    public void run() {
        gameLoop();
        victoryLoop();
    }

    private void gameLoop() {
        int frame = 0, engineFrame = 0;
        long beginTime = 0, endTime;

        while (!house.done()) {
            pauseLoop();

            camera.move(controller);
            house.update(controller);

            if (painter.isPainterQueueDone()) {
                PainterQueue painterQueue = new PainterQueue();
                house.draw(painterQueue, camera);
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

    private void pauseLoop() {
        checkPause();
        while (pause) {
            checkPause();
            Math3D.sleep(30);
        }
    }

    private void victoryLoop() {
        while (true) {
            PainterQueue painterQueue = new PainterQueue();
            house.draw(painterQueue, camera);
            house.drawVictory(painterQueue);
            painter.setPainterQueue(painterQueue);
            Math3D.sleep(30);
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

// todo redo senses