package house;

import camera.Camera;
import controller.Controller;
import house.character.Human;
import house.character.Monster;
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
    private static final Color FLOOR_COLOR = new Color(200, 255, 200), WALL_SIDE_COLOR = Color.LIGHT_GRAY, WALL_TOP_COLOR = Color.GRAY;
    private static final int VICTORY_HUMAN = 1, VICTORY_MONSTER = 2;
    private static final double VICTORY_DISTANCE = 2;
    private int victory;
    private boolean[][] walls;
    private LList<HouseDrawable> houseDrawables;
    private IntersectionFinder intersectionFinder;
    private Pather pather;
    private Lighter lighter;
    private Matrix light;
    private Matrix staticLight;
    private Human human;
    private Monster monster;
    private Exit exit;

    public House(boolean[][] walls, Coordinate[] lights) {
        this.walls = walls;
        houseDrawables = new LList<>();
        intersectionFinder = new IntersectionFinder(this);
        pather = new Pather(this);
        lighter = new Lighter(this);
        light = new Matrix(getWidth(), getHeight(), Lighter.MIN_LIGHT);
        staticLight = new Matrix(getWidth(), getHeight(), Lighter.MIN_LIGHT);
        for (Coordinate light : lights)
            lighter.calculateLight(light.getX(), light.getY(), staticLight, 4); // todo make light range constant
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

    public void update(Controller controller) {
        human.update(this, controller, monster);
        monster.update(this, controller, human);
        testWinConditions();
        light.reset();
        lighter.calculateLight(human.getX(), human.getY(), light, 10); // todo make light range constant
    }

    private void testWinConditions() {
        if (Math3D.magnitude(human.getX() - exit.getX(), human.getY() - exit.getY()) < VICTORY_DISTANCE)
            victory = VICTORY_HUMAN;
        else if (Math3D.magnitude(human.getX() - monster.getX(), human.getY() - monster.getY()) < VICTORY_DISTANCE)
            victory = VICTORY_MONSTER;
    }

    public IntersectionFinder getIntersectionFinder() {
        return intersectionFinder;
    }

    public Pather getPather() {
        return pather;
    }

    public int getWidth() {
        return walls.length;
    }

    public int getHeight() {
        return walls[0].length;
    }

    public boolean isMoveable(int x, int y) {
        return !walls[x][y];
    }

    public void draw(PainterQueue painterQueue, Camera camera) {
        for (int x = 0; x < walls.length; x++)
            for (int y = 0; y < walls[0].length; y++)
                if (walls[x][y])
                    DrawUtil.drawCubeFromCorner(painterQueue, camera, x, y, 1, getLightValue(x, y), WALL_TOP_COLOR, WALL_SIDE_COLOR, PainterQueue.WALL_TOP_LAYER, PainterQueue.WALL_SIDE_LAYER);
                else
                    DrawUtil.drawRectFromCorner(painterQueue, camera, x, y, 1, getLightValue(x, y), FLOOR_COLOR, PainterQueue.FLOOR_LAYER);

        for (LList<HouseDrawable> houseDrawable : houseDrawables)
            if (isLighted((int) houseDrawable.node.getX(), (int) houseDrawable.node.getY()))
                houseDrawable.node.draw(painterQueue, camera);
    }

    private double getLightValue(int x, int y) {
        return Math3D.max(light.getValue(x, y), staticLight.getValue(x, y));
    }

    private boolean isLighted(int x, int y) {
        return light.lighted(x, y) || staticLight.lighted(x, y);
    }

    public boolean done() {
        return victory != 0;
    }

    public void drawVictory(PainterQueue painterQueue) {
        String victoryText = victory == VICTORY_MONSTER ? "MONSTER WINS" : "HUMAN WINS";
        painterQueue.add(new PainterText(new Coordinate(.3, .3), Color.WHITE, victoryText), PainterQueue.TEXT_LAYER);
    }
}