package house.character;

import camera.Camera;
import camera.Follow;
import controller.Controller;
import geometry.Coordinate;
import geometry.CubeGeometry;
import house.House;
import house.HouseCharacter;
import painter.painterelement.PainterPolygon;
import painter.painterelement.PainterQueue;

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
        CubeGeometry geometry = new CubeGeometry(x - SIZE * .5, y - SIZE * .5, 0, SIZE, SIZE, SIZE, camera);

        painterQueue.add(new PainterPolygon(geometry.getTop(), 1, colorTop, false, true), PainterQueue.CHARACTER_TOP_LAYER);
        painterQueue.add(new PainterPolygon(geometry.getFront(), 1, colorSide, false, true), PainterQueue.CHARACTER_SIDE_LAYER);
        painterQueue.add(new PainterPolygon(geometry.getRight(), 1, colorSide, false, true), PainterQueue.CHARACTER_SIDE_LAYER);
        painterQueue.add(new PainterPolygon(geometry.getBack(), 1, colorSide, false, true), PainterQueue.CHARACTER_SIDE_LAYER);
        painterQueue.add(new PainterPolygon(geometry.getLeft(), 1, colorSide, false, true), PainterQueue.CHARACTER_SIDE_LAYER);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}