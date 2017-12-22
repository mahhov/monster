package house.particle;

import java.awt.*;

public class TimedParticle extends Particle {
    private int time;

    public TimedParticle(double x, double y, double size, Color color, int time) {
        super(x, y, size, color);
        this.time = time;
    }

    public boolean update() {
        return time-- == 0;
    }
}