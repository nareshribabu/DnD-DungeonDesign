package mainpkg;

import dnd.models.Monster;
import java.util.ArrayList;


/*
A passage begins at a door and ends at a door.  It may have many other doors along
the way

You will need to keep track of which door is the "beginning" of the passage
so that you know how to
*/

/**
*Creates a Passage.
*/
public class Passage extends Space {

    /**arrayList for all the passage sections.*/
    private ArrayList<PassageSection> thePassage;

    /**arrayList for all the doors.*/
    private ArrayList<Door> doors;

/**no args constructor for passage.*/
public Passage() {

    doors = new ArrayList<Door>();

    thePassage = new ArrayList<PassageSection>();

}

/**gets an array list of doors.
* @return returns an array list of type door
*/
public ArrayList<Door> getArrayOfDoors() {

    return doors;
}

/**gets a door in section i. If there is no door it returns null.
* @param i this is the section of the passage
* @return returns a variable of type door
*/
public Door getDoor(int i) {

    Door door;

    if ((thePassage.size() - 1) > i) {
        return null;
    }

    door = thePassage.get(i).getDoor();

    return door;
}

/**adds monster to section i of the passage.
* @param theMonster takes in a variable of type monster
* @param i the section of the passage
*/
public void addMonster(Monster theMonster, int i) {

    if ((thePassage.size() - 1) > i) {
        return;
    }

    thePassage.get(i).setMonster(theMonster);

}

/**gets a monster from a section i of the passage.
* If there is no monster then return null.
* @param i this is the section of the passage
* @return returns a variable of type monster
*/
public Monster getMonster(int i) {


    Monster monster = new Monster();

    if ((thePassage.size() - 1) > i) {
        return null;
    }

    monster = thePassage.get(i).getMonster();

    return monster;
}


/**Adds passages to the array list of passages.
* @param toAdd this is the section of the passage to add
*/
public void addPassageSection(PassageSection toAdd) {

        thePassage.add(toAdd);
}

/**sets a door connection to the current Passage Section.
* @param newDoor this is the door to connect
*/
@Override
public void setDoor(Door newDoor) {

    if (thePassage.size() > 0) {
        thePassage.get(thePassage.size() - 1).setDoor(newDoor);
        //newDoor.rollForDoorStatus();
        doors.add(newDoor);
    } else if (thePassage.size() <= 0) {
        PassageSection ps = new PassageSection();
        thePassage.add(ps);
        //newDoor.rollForDoorStatus();
        ps.setDoor(newDoor);

    }
}

/**gets a description of the passage.
*/
@Override
public String getDescription() {

    int i;
    int j;
    String strDescription;
    StringBuilder description = new StringBuilder();

    for (i = 0; i < thePassage.size(); i++) {
        j = i + 1;
        description.append("\nPassage section ").append(j).append(": ");
        description.append(thePassage.get(i).getDescription());
    }

    strDescription = description.toString();

    return strDescription;
}


}
