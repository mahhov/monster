package house.character;

class Sense {
    boolean smell, sound;
    double soundDirX, soundDirY;
    double smellX, smellY;

    void setSound(Character observer, Character source) {
        sound = true;
        soundDirX = source.getX() - observer.getX();
        soundDirY = source.getY() - observer.getY();
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