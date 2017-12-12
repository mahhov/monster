package painter;

import painter.geometry.Coordinate;
import painter.geometry.CoordinateGroup;
import painter.painterelement.PainterQueue;

import java.awt.*;

public interface Painter extends Runnable {
    String[] DEBUG_STRING = new String[] {""};

    void drawLine(CoordinateGroup coordinateGroup, double light, Color color);

    void drawRectangle(CoordinateGroup coordinateGroup, double light, Color color, boolean frame, boolean fill);

    void drawPolygon(CoordinateGroup coordinateGroup, double light, Color color, boolean frame, boolean fill);

    void drawText(Coordinate coordinate, Color color, String text);

    boolean isPainterQueueDone();

    void setPainterQueue(PainterQueue painterQueue);
}