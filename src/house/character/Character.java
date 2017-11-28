package house.character;

import camera.Camera;
import camera.Follow;
import controller.Controller;
import geometry.Coordinate;
import house.House;
import house.HouseCharacter;
import painter.painterelement.PainterQueue;
import util.DrawUtil;
import util.Math3D;
import util.intersection.Intersection;

import java.awt.*;

public class Character implements Follow, HouseCharacter {
    private static final double SIZE = .5;
    private boolean main;
    private Color colorTop, colorSide;
    private double walkSpeed, runSpeed;

    private double x, y;
    private boolean run;

    private double smellDistance, soundWalkDistance, soundRunDistance;
    Sense sense;

    Character(boolean main, Color colorTop, Color colorSide, double walkSpeed, double runSpeed, Coordinate spawn, double smellDistance, double soundWalkDistance, double soundRunDistance) {
        this.main = main;
        this.colorTop = colorTop;
        this.colorSide = colorSide;
        this.walkSpeed = walkSpeed;
        this.runSpeed = runSpeed;
        x = spawn.getX();
        y = spawn.getY();
        this.smellDistance = smellDistance;
        this.soundWalkDistance = soundWalkDistance;
        this.soundRunDistance = soundRunDistance;
        sense = new Sense();
    }

    public void update(House house, Controller controller, Character otherCharacter) {
        setSense(otherCharacter);
        if (main)
            move(house, applyController(controller));

    }

    private double[] applyController(Controller controller) {
        run = controller.isKeyDown(Controller.KEY_RIGHT_CAROT);

        double[] dir = new double[] {0, 0};
        if (controller.isKeyDown(Controller.KEY_A))
            dir[0] -= 1;
        if (controller.isKeyDown(Controller.KEY_D))
            dir[0] += 1;
        if (controller.isKeyDown(Controller.KEY_W))
            dir[1] -= 1;
        if (controller.isKeyDown(Controller.KEY_S))
            dir[1] += 1;

        return dir;
    }

    private void move(House house, double[] dir) {
        double speed = run ? runSpeed : walkSpeed;
        double[] orig = new double[] {x, y};
        Intersection intersection = house.getIntersectionFinder().find(orig, dir, speed, SIZE);
        x = intersection.getX();
        y = intersection.getY();
    }

    private void setSense(Character source) {
        double distance = Math3D.magnitude(source.getX() - x, source.getY() - y);
        if (source.run && distance < source.soundRunDistance || distance < source.soundWalkDistance)
            sense.setSound(this, source);
        else
            sense.clearSound();
        if (distance < source.smellDistance)
            sense.setSmell(source);
        else
            sense.clearSmell();
    }

    public void draw(PainterQueue painterQueue, Camera camera) {
        DrawUtil.drawCubeFromCenter(painterQueue, camera, x, y, SIZE, colorTop, colorSide, PainterQueue.CHARACTER_TOP_LAYER, PainterQueue.CHARACTER_SIDE_LAYER);

        if (main)
            drawSense(painterQueue, camera);
    }

    private void drawSense(PainterQueue painterQueue, Camera camera) {
        if (sense.sound)
            DrawUtil.drawCubeFromCenter(painterQueue, camera, x + sense.soundDirX, y + sense.soundDirY, .25, Color.LIGHT_GRAY, Color.LIGHT_GRAY, PainterQueue.SENSE_TOP_LAYER, PainterQueue.SENSE_SIDE_LAYER);
        if (sense.smell)
            DrawUtil.drawCubeFromCenter(painterQueue, camera, sense.smellX, sense.smellY, .25, Color.WHITE, Color.WHITE, PainterQueue.SENSE_TOP_LAYER, PainterQueue.SENSE_SIDE_LAYER);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}