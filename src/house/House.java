package house;

import camera.Camera;
import controller.Controller;
import house.character.Human;
import house.character.Monster;
import painter.painterelement.PainterQueue;
import util.DrawUtil;
import util.LList;
import util.map.Map;
import util.map.intersectionfinder.IntersectionFinder;
import util.map.pather.Pather;

import java.awt.*;

public class House implements Map {
    private boolean[][] walls;
    private LList<HouseElement> elements;
    private IntersectionFinder intersectionFinder;
    private Pather pather;
    private Human human;
    private Monster monster;
    private Exit exit;

    public House(boolean[][] walls) {
        this.walls = walls;
        elements = new LList<>();
        intersectionFinder = new IntersectionFinder(this);
        pather = new Pather(this);
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
                    DrawUtil.drawCubeFromCorner(painterQueue, camera, x, y, 1, Color.LIGHT_GRAY, Color.DARK_GRAY, PainterQueue.WALL_TOP_LAYER, PainterQueue.WALL_SIDE_LAYER);

        for (LList<HouseElement> element : elements)
            element.node.draw(painterQueue, camera);

        human.draw(painterQueue, camera);
        monster.draw(painterQueue, camera);
        exit.draw(painterQueue, camera);
    }
}