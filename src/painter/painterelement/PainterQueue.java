package painter.painterelement;

import painter.Painter;
import util.LList;

public class PainterQueue extends PainterElement {
    private static final int NUM_LAYERS = 4;
    public static final int WALL_SIDE_LAYER = 0, WALL_TOP_LAYER = 1, CHARACTER_SIDE_LAYER = 2, CHARACTER_TOP_LAYER = 3;

    private Layer[] layers;
    public boolean drawReady;

    public PainterQueue() {
        layers = new Layer[NUM_LAYERS];
        for (int i = 0; i < NUM_LAYERS; i++)
            layers[i] = new Layer();
    }

    public void add(PainterElement e) {
        layers[0].add(e);
    }

    public void add(PainterElement e, int layer) {
        layers[layer].add(e);
    }

    public void draw(Painter painter) {
        for (Layer layer : layers)
            layer.draw(painter);
    }

    private class Layer {
        private LList<PainterElement> elements;
        private LList<PainterElement> tailElement;

        public Layer() {
            tailElement = elements = new LList<>();
        }

        public void add(PainterElement e) {
            elements = elements.add(e);
        }

        public void draw(Painter painter) {
            for (LList<PainterElement> e : tailElement.reverseIterator())
                e.node.draw(painter);
        }
    }
}