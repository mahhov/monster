package house.character;

import util.Math3D;

class Sense {
    boolean smell, sound;
    double soundDirX, soundDirY;
    double smellX, smellY;

    void setSound(Character observer, Character source) {
        sound = true;

        soundDirX = source.getX() - observer.getX();
        soundDirY = source.getY() - observer.getY();
        double absX = Math3D.abs(soundDirX);
        double absY = Math3D.abs(soundDirY);
        
        if (absX > absY) {
            soundDirY /= absX;
            soundDirX = Math3D.sign(soundDirX);
        } else {
            soundDirX /= absY;
            soundDirY = Math3D.sign(soundDirY);
        }
    }

    void clearSound() {
        sound = false;
    }

    void setSmell(Character source) {
        smell = true;
        smellX = source.getX();
        smellY = source.getY();
    }

    void clearSmell() {
        smell = false;
    }
}