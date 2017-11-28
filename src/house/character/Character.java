package house.character;

import camera.Camera;
import camera.Follow;
import controller.Controller;
import geometry.Coordinate;
import house.House;
import house.HouseCharacter;
import painter.painterelement.PainterQueue;
import util.DrawUtil;

import java.awt.*;

public class Character implements Follow, HouseCharacter {
    static final double SIZE = .5;
    private Color colorTop, colorSide;
    double x, y;
    boolean run;
    private double smellDistance, soundWalkDistance, soundRunDistance;
    Sense sense;

    Character(Color colorTop, Color colorSide, Coordinate spawn, double smellDistance, double soundWalkDistance, double soundRunDistance) {
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

    public void setSense(Character source) {
        double distance = 0;
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
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}