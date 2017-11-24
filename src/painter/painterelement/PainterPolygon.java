package painter.painterelement;

import coordinate.CoordinateGroup;
import painter.Painter;

import java.awt.*;

public class PainterPolygon extends PainterElement {
    private CoordinateGroup coordinateGroup;
    private double light;
    private Color color;
    private boolean frame, fill;

    public PainterPolygon(CoordinateGroup coordinateGroup, double light, Color color, boolean frame, boolean fill) {
        this.coordinateGroup = coordinateGroup;
        this.light = light;
        this.color = color;
        this.frame = frame;
        this.fill = fill;
    }

    void draw(Painter painter) {
        painter.drawPolygon(coordinateGroup, light, color, frame, fill);
    }
}