package house.character;

import camera.Camera;
import camera.Follow;
import controller.Controller;
import house.House;
import house.HouseCharacter;
import painter.geometry.Coordinate;
import painter.painterelement.PainterQueue;
import util.DrawUtil;
import util.Math3D;
import util.map.intersectionfinder.Intersection;

import java.awt.*;

public class Character implements Follow, HouseCharacter {
    private static final double SIZE = .5;
    private boolean main;
    private Color colorTop, colorSide;
    private double walkSpeed, runSpeed;

    private double x, y;
    private double dirX, dirY;
    private boolean run;

    private double smellDistance, soundWalkDistanceSqr, soundRunDistanceSqr;
    Sense sense; // todo can we make this private

    Character(boolean main, Color colorTop, Color colorSide, double walkSpeed, double runSpeed, Coordinate spawn, double smellDistance, double soundWalkDistance, double soundRunDistance) {
        this.main = main;
        this.colorTop = colorTop;
        this.colorSide = colorSide;
        this.walkSpeed = walkSpeed;
        this.runSpeed = runSpeed;
        x = spawn.getX();
        y = spawn.getY();
        this.smellDistance = smellDistance;
        soundWalkDistanceSqr = soundWalkDistance * soundWalkDistance;
        soundRunDistanceSqr = soundRunDistance * soundRunDistance;
        sense = new Sense();
    }

    public void update(House house, Controller controller, Character otherCharacter) {
        setSense(otherCharacter);
        if (main)
            applyController(controller);
        else
            applyComputer(house, otherCharacter);
        move(house);
    }

    private void applyController(Controller controller) {
        setRun(controller.isKeyDown(Controller.KEY_RIGHT_CAROT));

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

    void applyComputer(House house, Character otherCharacter) {
    }

    private void move(House house) {
        double speed = run ? runSpeed : walkSpeed;
        double[] orig = new double[] {x, y};
        Intersection intersection = house.getIntersectionFinder().find(orig, new double[] {dirX, dirY}, speed, SIZE);
        x = intersection.getX();
        y = intersection.getY();
    }

    private void setSense(Character source) {
        double distance = getDistance(source.getX(), source.getY());
        if (source.run && distance < source.soundRunDistanceSqr || distance < source.soundWalkDistanceSqr)
            sense.setSound(this, source);
        else
            sense.clearSound();
        if (distance < source.smellDistance)
            sense.setSmell(source);
        else
            sense.clearSmell();
    }

    private double getDistance(double x, double y) {
        return Math3D.magnitudeSqr(x - this.x, y - this.y);
    }

    public void draw(PainterQueue painterQueue, Camera camera) {
        DrawUtil.drawCubeFromCenter(painterQueue, camera, x, y, SIZE, colorTop, colorSide, PainterQueue.CHARACTER_TOP_LAYER, PainterQueue.CHARACTER_SIDE_LAYER);

        if (main)
            drawSense(painterQueue, camera);
    }

    private void drawSense(PainterQueue painterQueue, Camera camera) {
        if (sense.sound)
            DrawUtil.drawCubeFromCenter(painterQueue, camera, x + sense.soundDirX, y + sense.soundDirY, .25, Color.RED, Color.RED, PainterQueue.SENSE_TOP_LAYER, PainterQueue.SENSE_SIDE_LAYER);
        if (sense.smell)
            DrawUtil.drawCubeFromCenter(painterQueue, camera, sense.smellX, sense.smellY, .25, Color.WHITE, Color.WHITE, PainterQueue.SENSE_TOP_LAYER, PainterQueue.SENSE_SIDE_LAYER);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setDirX(double dirX) {
        this.dirX = dirX;
    }

    public void setDirY(double dirY) {
        this.dirY = dirY;
    }

    public void setRun(boolean run) {
        this.run = run;
    }
}