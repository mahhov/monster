package house;

import camera.Camera;
import controller.Controller;
import engine.Engine;
import house.character.Human;
import house.character.Monster;
import house.particle.Particle;
import map.Map;
import map.lighting.Lighter;
import map.movement.IntersectionFinder;
import map.pather.Pather;
import painter.geometry.Coordinate;
import painter.painterelement.PainterQueue;
import painter.painterelement.PainterText;
import util.DrawUtil;
import util.LList;
import util.Math3D;
import util.Matrix;

import java.awt.*;

public class House implements Map {
    private static final double VICTORY_DISTANCE = .5;
    private static final int VICTORY_HUMAN = 1, VICTORY_MONSTER = 2;
    private int victory;

    private static final Color FLOOR_COLOR = new Color(200, 255, 200), SPECIAL_FLOOR_COLOR = new Color(200, 200, 235);
    private static final Color WALL_SIDE_COLOR = Color.LIGHT_GRAY, WALL_TOP_COLOR = Color.GRAY;
    public static final int TILE_FLOOR = 0, TILE_WALL = 1, TILE_SPECIAL_FLOOR = 2;
    private int[][] tiles;

    private LList<HouseDrawable> houseDrawables;
    private IntersectionFinder movementIntersectionFinder;
    private Pather pather;

    private static final int HOUSE_LIGHT_DISTANCE = 4, HUMAN_LIGHT_DISTANCE = 10;
    private Lighter lighter;
    private Matrix light;
    private Matrix staticLight;

    private Human human;
    private Monster monster;
    private Exit exit;
    private LList<Particle> particles;

    public House(int[][] tiles, util.Coordinate[] lights) {
        this.tiles = tiles;
        houseDrawables = new LList<>();
        movementIntersectionFinder = new IntersectionFinder(this);
        pather = new Pather(this);
        lighter = new Lighter(this);
        light = new Matrix(getWidth(), getHeight(), Lighter.MIN_LIGHT);
        staticLight = new Matrix(getWidth(), getHeight(), Lighter.MIN_LIGHT);
        for (util.Coordinate light : lights)
            lighter.calculateLight(light.getX(), light.getY(), staticLight, HOUSE_LIGHT_DISTANCE);
        particles = new LList<>();
    }

    public void setHuman(Human human) {
        this.human = human;
        houseDrawables = houseDrawables.add(human);
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
        houseDrawables = houseDrawables.add(monster);
    }

    public void setExit(Exit exit) {
        this.exit = exit;
        houseDrawables = houseDrawables.add(exit);
    }

    public void addParticle(Particle particle) {
        particles = particles.add(particle);
    }

    public void update(Controller controller) {
        human.update(this, controller, monster);
        monster.update(this, controller, human);

        for (LList<Particle> particle : particles)
            if (particle.node.update())
                particles.remove(particle);

        testWinConditions();

        light.reset();
        lighter.calculateLight(human.getX(), human.getY(), light, HUMAN_LIGHT_DISTANCE);
    }

    private void testWinConditions() {
        if (Math3D.magnitude(human.getX() - exit.getX(), human.getY() - exit.getY()) < VICTORY_DISTANCE)
            victory = VICTORY_HUMAN;
        else if (Math3D.magnitude(human.getX() - monster.getX(), human.getY() - monster.getY()) < VICTORY_DISTANCE)
            victory = VICTORY_MONSTER;
    }

    public IntersectionFinder getMovementIntersectionFinder() {
        return movementIntersectionFinder;
    }

    public boolean lineOfSight(double x1, double y1, double x2, double y2) {
        return !lighter.getIntersectionFinder().intersects(x1, y1, x2, y2);
    }

    public Pather getPather() {
        return pather;
    }

    public int getWidth() {
        return tiles.length;
    }

    public int getHeight() {
        return tiles[0].length;
    }

    public boolean isMoveable(int x, int y) {
        return tiles[x][y] != TILE_WALL;
    }

    public void draw(PainterQueue painterQueue, Camera camera) {
        for (int x = 0; x < tiles.length; x++)
            for (int y = 0; y < tiles[0].length; y++)
                switch (tiles[x][y]) {
                    case TILE_WALL:
                        DrawUtil.drawCubeFromCorner(painterQueue, camera, x, y, 1, getLightValue(x, y), WALL_TOP_COLOR, WALL_SIDE_COLOR, PainterQueue.WALL_TOP_LAYER, PainterQueue.WALL_SIDE_LAYER);
                        break;
                    case TILE_FLOOR:
                        DrawUtil.drawRectFromCorner(painterQueue, camera, x, y, 1, getLightValue(x, y), FLOOR_COLOR, PainterQueue.FLOOR_LAYER);
                        break;
                    case TILE_SPECIAL_FLOOR:
                        DrawUtil.drawRectFromCorner(painterQueue, camera, x, y, 1, getLightValue(x, y), SPECIAL_FLOOR_COLOR, PainterQueue.FLOOR_LAYER);
                        break;

                }

        for (LList<HouseDrawable> houseDrawable : houseDrawables)
            if (isLighted((int) houseDrawable.node.getX(), (int) houseDrawable.node.getY()))
                houseDrawable.node.draw(painterQueue, camera, getLightValue((int) houseDrawable.node.getX(), (int) houseDrawable.node.getY()));

        for (LList<Particle> particle : particles)
            if (isLighted((int) particle.node.getX(), (int) particle.node.getY()))
                particle.node.draw(painterQueue, camera, getLightValue((int) particle.node.getX(), (int) particle.node.getY()));
    }

    private double getLightValue(int x, int y) {
        return Engine.DEBUG_LIGHTING_ON ? 1 : Math3D.max(light.getValue(x, y), staticLight.getValue(x, y));
    }

    private boolean isLighted(int x, int y) {
        return Engine.DEBUG_LIGHTING_ON || light.lighted(x, y) || staticLight.lighted(x, y);
    }

    public boolean done() {
        return victory != 0;
    }

    public void drawVictory(PainterQueue painterQueue) {
        String victoryText = victory == VICTORY_MONSTER ? "MONSTER WINS" : "HUMAN WINS";
        painterQueue.add(new PainterText(new Coordinate(.35, .2), Color.WHITE, victoryText), PainterQueue.OVERLAY_LAYER);
        painterQueue.add(new PainterText(new Coordinate(.35, .25), Color.WHITE, "Press Enter to Restart"), PainterQueue.OVERLAY_LAYER);
    }
}