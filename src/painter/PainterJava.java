package painter;

import controller.ControllerJavaListener;
import coordinate.Coordinate;
import coordinate.CoordinateGroup;
import painter.painterelement.PainterQueue;
import util.Math3D;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PainterJava implements Painter {
    private static final CoordinateGroup FULL_COORDINATES = new CoordinateGroup(new Coordinate[] {new Coordinate(-.5, -.5), new Coordinate(.5, .5)});

    private final JFrame jframe;
    private final int FRAME_SIZE, IMAGE_SIZE;
    private static int borderSize = 0;
    private BufferedImage canvas;
    private Graphics2D brush;
    private Graphics2D frameBrush;
    private PainterQueue painterQueue;

    public PainterJava(int frameSize, int imageSize, ControllerJavaListener controller) {
        jframe = new JFrame();
        FRAME_SIZE = frameSize;
        IMAGE_SIZE = imageSize;
        canvas = new BufferedImage(IMAGE_SIZE, IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB_PRE);
        brush = (Graphics2D) canvas.getGraphics();
        jframe.setUndecorated(true);
        jframe.getContentPane().setSize(FRAME_SIZE, FRAME_SIZE);
        jframe.pack();
        borderSize = jframe.getHeight();
        jframe.setSize(FRAME_SIZE, FRAME_SIZE + borderSize);
        jframe.setLocationRelativeTo(null);
        jframe.addMouseListener(controller);
        jframe.addKeyListener(controller);
        jframe.addMouseMotionListener(controller);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setIgnoreRepaint(true);
        jframe.setVisible(true);
        frameBrush = (Graphics2D) jframe.getGraphics();
        painterQueue = new PainterQueue();
    }

    private void paint() {
        brush.setColor(Color.BLUE);
        for (int i = 0; i < DEBUG_STRING.length; i++)
            brush.drawString(DEBUG_STRING[i], 25, 25 + 25 * i);
        //         frameBrush.drawImage(canvas, 0, borderSize, null);
        frameBrush.drawImage(canvas, 0, borderSize, FRAME_SIZE, borderSize + FRAME_SIZE, 0, 0, IMAGE_SIZE, IMAGE_SIZE, null);
        drawRectangle(FULL_COORDINATES, 1, Color.WHITE, false, true);
    }

    private void setColor(double light, Color color) {
        light = Math3D.min(1, light);
        brush.setColor(new Color((int) (light * color.getRed()), (int) (light * color.getGreen()), (int) (light * color.getBlue()), color.getAlpha()));
    }

    public void drawLine(CoordinateGroup coordinateGroup, double light, Color color) {
        if (!coordinateGroup.isInView())
            return;

        int[][] xy = coordinateGroup.transformXY(IMAGE_SIZE, IMAGE_SIZE / 2);
        brush.setStroke(new BasicStroke(10));

        setColor(light, color);
        brush.drawLine(xy[0][0], xy[1][0], xy[0][1], xy[1][1]);
    }

    public void drawRectangle(CoordinateGroup coordinateGroup, double light, Color color, boolean frame, boolean fill) {
        if (!coordinateGroup.isInView())
            return;

        int[][] xy = coordinateGroup.transformXY(IMAGE_SIZE, IMAGE_SIZE / 2);
        brush.setStroke(new BasicStroke(1));

        if (frame) {
            brush.setColor(Color.CYAN);
            brush.drawRect(xy[0][0], xy[1][0], xy[0][1] - xy[0][0], xy[1][1] - xy[1][0]);
        }

        if (fill) {
            setColor(light, color);
            brush.fillRect(xy[0][0], xy[1][0], xy[0][1] - xy[0][0], xy[1][1] - xy[1][0]);
        }
    }

    public void drawPolygon(CoordinateGroup coordinateGroup, double light, Color color, boolean frame, boolean fill) {
        if (!coordinateGroup.isInView())
            return;

        int[][] xy = coordinateGroup.transformXY(IMAGE_SIZE, IMAGE_SIZE / 2);
        brush.setStroke(new BasicStroke(1));

        if (frame) {
            brush.setColor(Color.CYAN);
            brush.drawPolygon(xy[0], xy[1], xy[0].length);
        }

        if (fill) {
            setColor(light, color);
            brush.fillPolygon(xy[0], xy[1], xy[0].length);
        }
    }

    public void drawText(Coordinate coordinate, Color color, String text) {
        int xy[] = coordinate.transform(IMAGE_SIZE, 0);

        brush.setColor(color);
        brush.drawString(text, xy[0] + 10, xy[1] + 20);
    }

    public boolean isPainterQueueDone() {
        return !painterQueue.drawReady;
    }

    public void setPainterQueue(PainterQueue painterQueue) {
        this.painterQueue = painterQueue;
    }

    public void run() {
        while (true) {
            if (painterQueue.drawReady) {
                painterQueue.draw(this);
                painterQueue.drawReady = false;
                paint();
            }
            Math3D.sleep(10);
        }
    }
}