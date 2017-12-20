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


// todo: movement when sliding on walls shouldn't slow down
// todo: monster slower or more warning so you're not dead before you can respond
// todo: better map generation to avoid the "tight and wrinkly" repetitive maps
// todo: some kind of clues as to where the exit is or some way of figuring it out without relying on pure random luck and walking "my experience was running around aimlessly"
// todo: pathfinding bug

// todo : senses
// -> monster goes to general area where human is
// -> monster sniffs human location based on cooldown, and accuracy based on distance
// -> monster goes to last sniff location unless line of sight of human or human light
// human light toggle
// monster leave footprint trail
// human alert when monster sniffed human
// running makes detectible from farther
// monster only slightly faster
