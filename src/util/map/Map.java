package util.map;

public interface Map {
    int getWidth();

    int getHeight();

    boolean isMoveable(int x, int y);

    default boolean isInBounds(int x, int y) {
        return x > 0 && x < getWidth() && y > 0 && y < getHeight();
    }

    default boolean isInBoundsMoveable(int x, int y) {
        return isInBounds(x, y) && isMoveable(x, y);
    }
}