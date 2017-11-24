package painter.painterelement;

import painter.Painter;
import util.LList;

public class PainterQueue extends PainterElement {
    private LList<PainterElement> elements;
    private LList<PainterElement> tailElement;
    public boolean drawReady;

    public PainterQueue() {
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