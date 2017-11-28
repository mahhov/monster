package house;

import controller.Controller;
import house.character.Character;

public interface HouseCharacter extends HouseDrawable {
    void update(House house, Controller controller, Character otherCharacter);
}