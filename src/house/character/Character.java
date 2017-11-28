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

import java.awt.*;

public class Character implements Follow, HouseCharacter {
    static final double SIZE = .5;
    private boolean main;
    private Color colorTop, colorSide;
    double x, y;
    boolean run;
    private double smellDistance, soundWalkDistance, soundRunDistance;
    Sense sense;

    Character(boolean main, Color colorTop, Color colorSide, Coordinate spawn, double smellDistance, double soundWalkDistance, double soundRunDistance) {
        this.main = main;
        this.colorTop = colorTop;
        this.colorSide = colorSide;
        x = spawn.getX();
        y = spawn.getY();
        this.smellDistance = smellDistance;
        this.soundWalkDistance = soundWalkDistance;
        this.soundRunDistance = soundRunDistance;
        sense = new Sense();
    }

    public void update(House house, Controller controller, Character otherCharacter) {
        setSense(otherCharacter);
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