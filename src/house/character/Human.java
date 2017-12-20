package house.character;

import camera.Camera;
import house.House;
import painter.geometry.Coordinate;
import painter.geometry.CoordinateGroup;
import painter.painterelement.PainterQueue;
import painter.painterelement.PainterRectangle;
import util.DrawUtil;
import util.Math3D;

import java.awt.*;

public class Human extends Character {
    private static final Color COLOR_TOP = new Color(0, 120, 0), COLOR_SIDE = new Color(0, 80, 0), COLOR_SENSE = new Color(180, 0, 0);
    private static final double WALK_SPEED = .1, RUN_SPEED = .3;

    private static final int SENSE_DISTANCE = 24;
    private double senseAlert;
    private double senseDirX, senseDirY;

    public Human(util.Coordinate spawn) {
        super(true, COLOR_TOP, COLOR_SIDE, WALK_SPEED, RUN_SPEED, spawn);
    }

    void setSense(House house, Character source) {
        senseDirX = source.getX() - getX();
        senseDirY = source.getY() - getY();
        double distance = Math3D.magnitude(senseDirX, senseDirY);
        senseDirX /= distance;
        senseDirY /= distance;
        senseAlert = Math3D.minMax(1 - distance / SENSE_DISTANCE, 0, 1);
    }

    void drawSense(PainterQueue painterQueue, Camera camera) {
        if (senseAlert == 0)
            return;

        double width = .4 * senseAlert;
        CoordinateGroup coord = new CoordinateGroup(new Coordinate[] {
                new Coordinate(-width, .40),
                new Coordinate(width, .42)
        });
        painterQueue.add(new PainterRectangle(coord, 1, COLOR_SENSE, false, true), PainterQueue.OVERLAY_LAYER);

        DrawUtil.drawRectFromCorner(painterQueue, camera, getX() + senseDirX, getY() + senseDirY, .25, 1, COLOR_SENSE, PainterQueue.OVERLAY_LAYER);
    }
}