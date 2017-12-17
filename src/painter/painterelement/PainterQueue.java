package painter.painterelement;

import painter.Painter;
import util.LList;

public class PainterQueue extends PainterElement {
    private static final int NUM_LAYERS;
    public static final int
            FLOOR_LAYER, WALL_SIDE_LAYER, WALL_TOP_LAYER,
            CHARACTER_SIDE_LAYER, CHARACTER_TOP_LAYER,
            SENSE_TOP_LAYER, SENSE_SIDE_LAYER,
            TEXT_LAYER;

    static {
        int i = 0;
        FLOOR_LAYER = i++;
        WALL_SIDE_LAYER = i++;
        WALL_TOP_LAYER = i++;
        CHARACTER_SIDE_LAYER = i++;
        CHARACTER_TOP_LAYER = i++;
        SENSE_TOP_LAYER = i++;
        SENSE_SIDE_LAYER = i++;
        TEXT_LAYER = i++;
        NUM_LAYERS = i;
    }

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
        } // todo : can we make this private?

        public void add(PainterElement e) {
            elements = elements.add(e);
        } // todo : can we make this private?

        public void draw(Painter painter) {
            for (LList<PainterElement> e : tailElement.reverseIterator())
                e.node.draw(painter);
        }
    }
}