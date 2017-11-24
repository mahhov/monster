package controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class ControllerJava extends Controller implements ControllerJavaListener {
    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        setKeyState(e.getKeyCode(), PRESSED);
    }

    public void keyReleased(KeyEvent e) {
        setKeyState(e.getKeyCode(), RELEASED);
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        setMouseState(PRESSED);
    }

    public void mouseReleased(MouseEvent e) {
        setMouseState(RELEASED);
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
        setMousePos(e.getX(), e.getY());
    }
}	
