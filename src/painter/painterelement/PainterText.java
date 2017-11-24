package painter.painterelement;

import coordinate.Coordinate;
import painter.Painter;

import java.awt.*;

public class PainterText extends PainterElement {
    private Coordinate coordinate;
    private Color color;
    private String text;

    public PainterText(Coordinate coordinate, Color color, String text) {
        this.coordinate = coordinate;
        this.color = color;
        this.text = text;
    }

    void draw(Painter painter) {
        painter.drawText(coordinate, color, text);
    }
}
