package controller;

import java.awt.event.KeyEvent;

public class Controller {
    public static final int UP = 0, DOWN = 1, PRESSED = 2, RELEASED = 3;
    private static final int NUM_KEYS;
    public static final int
            KEY_W, KEY_A, KEY_S, KEY_D,
            PERIOD,
            KEY_P, KEY_ENTER,
            KEY_MINUS, KEY_EQUAL;

    static {
        int i = 0;
        KEY_W = i++;
        KEY_A = i++;
        KEY_S = i++;
        KEY_D = i++;
        PERIOD = i++;
        KEY_P = i++;
        KEY_ENTER = i++;
        KEY_MINUS = i++;
        KEY_EQUAL = i++;
        NUM_KEYS = i;
    }

    private Key[] keys;
    private Mouse mouse;

    Controller() {
        keys = new Key[NUM_KEYS];
        keys[KEY_W] = new Key(KeyEvent.VK_W);
        keys[KEY_A] = new Key(KeyEvent.VK_A);
        keys[KEY_S] = new Key(KeyEvent.VK_S);
        keys[KEY_D] = new Key(KeyEvent.VK_D);
        keys[PERIOD] = new Key(KeyEvent.VK_PERIOD);
        keys[KEY_P] = new Key(KeyEvent.VK_P);
        keys[KEY_ENTER] = new Key(KeyEvent.VK_ENTER);
        keys[KEY_MINUS] = new Key(KeyEvent.VK_MINUS);
        keys[KEY_EQUAL] = new Key(KeyEvent.VK_EQUALS);
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