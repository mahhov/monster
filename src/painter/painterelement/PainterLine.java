package painter.painterelement;

import geometry.CoordinateGroup;
import painter.Painter;

import java.awt.*;

public class PainterLine extends PainterElement {
    private CoordinateGroup coordinateGroup;
    private double light;
    private Color color;

    public PainterLine(CoordinateGroup coordinateGroup, double light, Color color) {
        this.coordinateGroup = coordinateGroup;
        this.light = light;
        this.color = color;
    }

    void draw(Painter painter) {
        painter.drawLine(coordinateGroup, light, color);
    }
}