package controller;

public class Controller {
    public static final int UP = 0, DOWN = 1, PRESSED = 2, RELEASED = 3;
    public static final int KEY_W = 0, KEY_A = 1, KEY_S = 2, KEY_D = 3, KEY_LEFT_CAROT = 4, KEY_RIGHT_CAROT = 5, KEY_P = 6;

    private Key[] keys;
    private Mouse mouse;

    Controller() {
        keys = new Key[7];
        keys[KEY_W] = new Key(87);
        keys[KEY_A] = new Key(65);
        keys[KEY_S] = new Key(83);
        keys[KEY_D] = new Key(68);
        keys[KEY_LEFT_CAROT] = new Key(44);
        keys[KEY_RIGHT_CAROT] = new Key(46);
        keys[KEY_P] = new Key(80);

        mouse = new Mouse();
    }

    void setKeyState(int keyCode, int state) {
        Key key = getKey(keyCode);
        if (key != null && (state != PRESSED || key.state != DOWN))
            key.state = state;
    }

    void setMouseState(int state) {
        mouse.state = state;
    }

    void setMousePos(int x, int y) {
        mouse.x = x;
        mouse.y = y;
    }

    public boolean isKeyPressed(int keyIndex) {
        Key key = keys[keyIndex];

        if (key.state == PRESSED) {
            key.state = DOWN;
            return true;
        }
        return false;
    }

    public boolean isKeyDown(int keyIndex) {
        Key key = keys[keyIndex];

        if (key.state == PRESSED) {
            key.state = DOWN;
            return true;
        }
        return key.state == DOWN;
    }

    private Key getKey(int code) {
        for (Key key : keys)
            if (key.code == code)
                return key;
        return null;
    }

    private static class Key {
        private int code, state;

        private Key(int code) {
            this.code = code;
        }
    }

    private static class Mouse {
        private int x, y;
        private int state;
    }
}
