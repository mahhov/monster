package house;

import camera.Camera;
import controller.Controller;
import house.character.Human;
import house.character.Monster;
import map.Map;
import map.lighting.Lighter;
import map.movement.IntersectionFinder;
import map.pather.Pather;
import painter.painterelement.PainterQueue;
import util.DrawUtil;
import util.LList;
import util.Matrix;

import java.awt.*;

public class House implements Map {
    private static final Color FLOOR_COLOR = new Color(200, 255, 200), WALL_SIDE_COLOR = Color.LIGHT_GRAY, WALL_TOP_COLOR = Color.GRAY;
    private boolean[][] walls;
    private LList<HouseElement> elements;
    private IntersectionFinder intersectionFinder;
    private Pather pather;
    private Lighter lighter;
    private Matrix light;
    private Human human;
    private Monster monster;
    private Exit exit;

    public House(boolean[][] walls) {
        this.walls = walls;
        elements = new LList<>();
        intersectionFinder = new IntersectionFinder(this);
        pather = new Pather(this);
        lighter = new Lighter(this);
    }

    public void addElement(HouseElement element) {
        elements = elements.add(element);
    }

    public void setHuman(Human human) {
        this.human = human;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public void setExit(Exit exit) {
        this.exit = exit;
    }

    public void update(Controller controller) {
        for (LList<HouseElement> element : elements)
            element.node.update(this);
        human.update(this, controller, monster);
        monster.update(this, controller, human);
        light = lighter.calculateLight(human.getX(), human.getY());
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
                    DrawUtil.drawCubeFromCorner(painterQueue, camera, x, y, 1, light.getValue(x, y), WALL_TOP_COLOR, WALL_SIDE_COLOR, PainterQueue.WALL_TOP_LAYER, PainterQueue.WALL_SIDE_LAYER);
                else
                    DrawUtil.drawRectFromCorner(painterQueue, camera, x, y, 1, light.getValue(x, y), FLOOR_COLOR, PainterQueue.FLOOR_LAYER);

        for (LList<HouseElement> element : elements)
            element.node.draw(painterQueue, camera);

        human.draw(painterQueue, camera);
        monster.draw(painterQueue, camera);
        exit.draw(painterQueue, camera);
    }
}