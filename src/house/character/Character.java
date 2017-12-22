package house.character;

import camera.Camera;
import camera.Follow;
import controller.Controller;
import house.House;
import house.HouseCharacter;
import map.movement.Intersection;
import painter.painterelement.PainterQueue;
import util.DrawUtil;

import java.awt.*;

public class Character implements Follow, HouseCharacter {
    private static final double SIZE = .5;
    private boolean main;
    private Color colorTop, colorSide;
    private double walkSpeed, runSpeed;

    private double x, y;
    private double dirX, dirY;
    private boolean run;

    Character(boolean main, Color colorTop, Color colorSide, double walkSpeed, double runSpeed, util.Coordinate spawn) {
        this.main = main;
        this.colorTop = colorTop;
        this.colorSide = colorSide;
        this.walkSpeed = walkSpeed;
        this.runSpeed = runSpeed;
        x = spawn.getX();
        y = spawn.getY();
    }

    public void update(House house, Controller controller, Character otherCharacter) {
        setSense(house, otherCharacter);
        if (main)
            applyController(controller);
        else
            applyComputer(house);
        move(house);
    }

    private void applyController(Controller controller) {
        setRun(controller.isKeyDown(Controller.PERIOD));

        if (controller.isKeyDown(Controller.KEY_A))
            setDirX(-1);
        else if (controller.isKeyDown(Controller.KEY_D))
            setDirX(1);
        else
            setDirX(0);
        if (controller.isKeyDown(Controller.KEY_W))
            setDirY(-1);
        else if (controller.isKeyDown(Controller.KEY_S))
            setDirY(1);
        else
            setDirY(0);
    }

    void applyComputer(House house) {
    }

    private void move(House house) {
        double speed = run ? runSpeed : walkSpeed;
        double[] orig = new double[] {x, y};
        Intersection intersection = house.getMovementIntersectionFinder().find(orig, new double[] {dirX, dirY}, speed, SIZE);
        x = intersection.getX();
        y = intersection.getY();
    }

    void setSense(House house, Character source) {
    }

    public void draw(PainterQueue painterQueue, Camera camera, double light) {
        DrawUtil.drawCubeFromCenter(painterQueue, camera, x, y, SIZE, light, colorTop, colorSide, PainterQueue.CHARACTER_TOP_LAYER, PainterQueue.CHARACTER_SIDE_LAYER);

        if (main)
            drawSense(painterQueue, camera);
    }

    void drawSense(PainterQueue painterQueue, Camera camera) {
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    void setDirX(double dirX) {
        this.dirX = dirX;
    }

    void setDirY(double dirY) {
        this.dirY = dirY;
    }

    void setRun(boolean run) {
        this.run = run;
    }
}