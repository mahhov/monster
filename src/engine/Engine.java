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
    public static final boolean DEBUG_LIGHTING_ON = false;

    private static final long NANOSECONDS_IN__SECOND = 1000000000L;
    private static final int FRAME = 400, IMAGE = 400;

    private Controller controller;
    private Painter painter;
    private Camera camera;
    private House house;

    private boolean pause;

    private Engine() {
        controller = new ControllerJava();
        painter = new PainterJava(FRAME, IMAGE, (ControllerJavaListener) controller);
        camera = new Camera(0, 0, 25);
        createHouse();
    }

    private void createHouse() {
        HouseGenerator houseGenerator = new HouseGenerator();
        houseGenerator.generate();

        Human human = new Human(houseGenerator.getSpawn(HouseGenerator.SPAWN_HUMAN));
        Monster monster = new Monster(houseGenerator.getSpawn(HouseGenerator.SPAWN_MONSTER));
        Exit exit = new Exit(houseGenerator.getSpawn(HouseGenerator.SPAWN_EXIT));

        house = new House(houseGenerator.getTiles(), houseGenerator.getLights());
        house.setHuman(human);
        house.setMonster(monster);
        house.setExit(exit);

        camera.setFollow(human);
    }

    private void begin() {
        new Thread(this).start();
        painter.run();
    }

    public void run() {
        while (true) {
            gameLoop();
            victoryLoop();
        }
    }

    private void gameLoop() {
        int frame = 0, engineFrame = 0;
        long beginTime = 0, endTime;

        while (!house.done()) {
            pauseLoop();
            checkRestart();

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
            if (endTime - beginTime > NANOSECONDS_IN__SECOND) {
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
        while (house.done()) {
            PainterQueue painterQueue = new PainterQueue();
            house.draw(painterQueue, camera);
            house.drawVictory(painterQueue);
            painter.setPainterQueue(painterQueue);
            Math3D.sleep(30);
            checkRestart();
        }
    }

    private void checkPause() {
        if (controller.isKeyPressed(Controller.KEY_P))
            pause = !pause;
    }

    private void checkRestart() {
        if (controller.isKeyPressed(Controller.KEY_ENTER))
            createHouse();
    }

    public static void main(String[] args) {
        new Engine().begin();
    }
}


// todo: better map generation to avoid the "tight and wrinkly" repetitive maps
// todo: pathfinding bug
// todo: bug where spawning in corner makes u unable to move
// todo: bug where only 1 room generated and monster, human, and exit are in same room
// todo: human gets alert when monster has line of sight