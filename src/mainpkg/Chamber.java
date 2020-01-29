/**
*mainpkg generates a Chamber, Door, Passage, PassageSection.
*@author Nareshri
*/
package mainpkg;

import dnd.models.ChamberContents;
import dnd.models.ChamberShape;
import dnd.models.Monster;
import dnd.models.Treasure;
import dnd.models.Stairs;
import dnd.models.Trap;
import java.util.ArrayList;

import dnd.exceptions.UnusualShapeException;

import dnd.die.D20;
import dnd.die.Percentile;

/**
*Creates a Chamber.
*/
public class Chamber extends Space {

    /**Contents.*/
    private ChamberContents myContents;
    /**Shape.*/
    private ChamberShape mySize;

    /**Monster.*/
    private Monster myMonster;
    /**Treasure.*/
    private Treasure myTreasure;
    /**ArrayList treasure.*/
    private  ArrayList<Treasure> treasure;
    /**ArrayList Monster.*/
    private  ArrayList<Monster> monster;

    /**Door.*/
    private ArrayList<Door> door;


    /**Shape.*/
    private int shape;
    /**Area.*/
    private int area;
    /**Length.*/
    private int length;
    /**Width.*/
    private int width;
    /**Number of Exits.*/
    private int numExits;

    /**Description Monster.*/
    private String monsterStr;
    /**Description contents.*/
    private String contentsDes;
    /**exit door.*/
    private Door exitDoor;
    /**entranceDoor.*/
    private Door entranceDoor;
    /**flag.*/
    private int flag1 = 0;

/**
*Chamber no args constructor.
* Sets instance variables to defaults.
*/
public Chamber() {

    initializeInstanceVariables();
    int roll;

    /*Shape and contents*/
    roll = dice20();
    mySize = ChamberShape.selectChamberShape(roll);

    roll = dice20();
    myContents.chooseContents(roll);

    contentsDes = myContents.getDescription();

    contentsMonster();

    contentsTreasure();

    initializeDimentions();

    numExits = mySize.getNumExits();

    createDoorArray();

}

/**
*If the contents description contains a monster.
*create a new monster and add that to the array list.
*/
private void contentsMonster() {

    int rollHun;
    if (contentsDes.contains("monster")) {
        Monster theMonster = new Monster();

        rollHun = dice100();

        theMonster.setType(rollHun);
        addMonster(theMonster);
    }

}

/**
*If the contents description contains a treasure.
*create a new treasure and add that to the array list.
*/
private void contentsTreasure() {

    int rollHun;

    if (contentsDes.contains("treasure")) {
        Treasure theTreasure = new Treasure();

        rollHun = dice100();
        theTreasure.chooseTreasure(rollHun);

        addTreasure(theTreasure);
    }

}

/**
*Initializes the dimentions for chamber.
*Area, Length, Width
*/
private void initializeDimentions() {

    try {
        length = mySize.getLength();
        width = mySize.getWidth();
        area = mySize.getArea();
        flag1 = 1;
    } catch (UnusualShapeException e) {
        area = mySize.getArea();
        flag1 = 0;
    }

}

/**
*Initializes the instance variables with new objects.
*/
private void initializeInstanceVariables() {

    myContents = new ChamberContents();
    myMonster = new Monster();
    myTreasure = new Treasure();
    treasure = new ArrayList<Treasure>();
    monster = new ArrayList<Monster>();
    door = new ArrayList<Door>();
    exitDoor = new Door();
    entranceDoor = new Door();

}

/**
*Chamber constructor.
*@param theShape gets chamber shape
*@param theContents gets chamber contents
*/

public Chamber(ChamberShape theShape, ChamberContents theContents) {

    int roll = -1;

    initializeInstanceVariables();

    roll = dice20();
    mySize = ChamberShape.selectChamberShape(roll);
    mySize = theShape;

    myContents = theContents;

    contentsDes = myContents.getDescription();

    contentsMonster();

    contentsTreasure();

    initializeDimentions();

    numExits = mySize.getNumExits();

    createDoorArray();

}

/**
*Set shape for chamber.
*@param theShape gets the shape
*/

public void setShape(ChamberShape theShape) {

    int roll = dice20();
    mySize = ChamberShape.selectChamberShape(roll);
    mySize = theShape;

    initializeDimentions();

    numExits = mySize.getNumExits();

    createDoorArray();

}

/**
*Sets up an array list of doors based on the number of exits.
*/
private void createDoorArray() {

    int i;

    door.clear();
    for (i = 0; i < numExits; i++) {
        Door d = new Door();
        door.add(d);
    }
}

/**
*Sends back an array list of doors for the chamber.
*@return arraylist of doors
*/

public ArrayList<Door> getDoors() {

    return door;

}

/**
*Add monster to the array list.
*@param theMonster gets monster information
*/

public void addMonster(Monster theMonster) {

    this.monster.add(theMonster);

    monsterStr = theMonster.getDescription();
}

/**
*Returns array list of monsters.
*@return array list of monster
*/
public ArrayList<Monster> getMonsters() {

    return monster;
}

/**
*Adds treasure to the arraylist.
*@param theTreasure gets the treasure info
*/

public void addTreasure(Treasure theTreasure) {

    this.treasure.add(theTreasure);
}

/**
*Returns array list of treasure.
*@return arrayList of treasure
*/
public ArrayList<Treasure> getTreasureList() {

    return treasure;
}


/**
*Gets chamber description.
*/
@Override
public String getDescription() {

    StringBuilder description = new StringBuilder();

    description.append("\nChamber Shape: ").append(mySize.getShape());

    description.append(dimentionDes());

    description.append("\n\nThis chamber has ").append(getDoors().size()).append(" door(s).");

    description.append(setDescriptionChambContents());

    String strDescription = description.toString();

    return strDescription;
}

/**
*Sets all the descriptions for chamber contents.
*@return description String contains any information pertaining to chamber contents.
*/
public String setDescriptionChambContents() {

    StringBuilder description = new StringBuilder();

    contentsDes = myContents.getDescription();
    description.append("\n\nChamber Contains: ").append(contentsDes);

    description.append(monsterDes());

    description.append(treasureDes());

    description.append(stairsDes());

    description.append(trapDes());

    String strDescription = description.toString();

    return strDescription;
}

/**
*Gives back a string containing information about the dimentions of the chamber.
*Length, Width, Area.
*@return description String containing details about dimentions.
*/
private String dimentionDes() {
    String description;

    if (flag1 == 1) {
        description = "\nLength: " + length + "\nWidth: " + width + "\nArea: " + area;
    } else {
        description = "\nArea: " + area;
    }

    return description;
}

/**
*Gives back a string containing information about monster if contents say "monster".
*@return description String containing details about monster
*/
private String monsterDes() {

    String description;

    if (contentsDes.contains("monster")) {
        description = "\nMonster: " + monster.get(0).getDescription();
    } else {
        description = "";
    }

    return description;

}

/**
*Gives back a string containing information about treasure if contents say "treasure".
*@return description String containing details about treasure
*/
private String treasureDes() {

    String description;
    int roll;

    if (contentsDes.contains("treasure")) {
        roll = dice20();
        treasure.get(0).setContainer(roll);
        description = "\nTreasure: " + treasure.get(0).getWholeDescription();
    } else {
        description = "";
    }

    return description;
}

/**
*Gives back a string containing information about the stairs if contents say "stairs".
* @return description String contains details about stairs.
*/
private String stairsDes() {

    String description;
    int roll;

    if (contentsDes.contains("stairs")) {
        Stairs stairs = new Stairs();
        roll = dice20();
        stairs.setType(roll);
        description = "\nStairs: " + stairs.getDescription();
    } else {
        description = "";
    }

    return description;
}

/**
*Gives back a string containing information about the trap if contents say "trap".
*@return description String contains details about trap
*/
private String trapDes() {

    String description;
    int roll;

    if (contentsDes.contains("trap")) {
        Trap trap = new Trap();
        roll = dice20();
        trap.chooseTrap(roll);
        description = "\nTrap: " + trap.getDescription();
    } else {
        description = "";
    }

    return description;

}

/**
*Sets door.
*@param newDoor takes in a new door
*/
@Override
public void setDoor(Door newDoor) {

    door.add(newDoor);

}

/**
*gives a die roll between 0 to 20.
*@return rollNum returns a number between 1 to 20
*/
private int dice20() {

    D20 roll = new D20();
    int rollNum = -1;

    rollNum = roll.roll();

    return rollNum;
}

/**
*gives a die roll between 0 to 100.
*@return rollNum returns a number between 1 to 100
*/
private int dice100() {

    Percentile roll = new Percentile();
    int rollNum = -1;

    rollNum = roll.roll();

    return rollNum;
}

/**
*Gets the number of exits in the chamber.
*@return numExits Int returns the number of exits.
*/
public int getNumExitsChamber() {

    if (numExits > 4) {
        numExits = 1;
    }

    return numExits;
}

/**Gets the length of the chamber if any; else returns a default number
 * @return length Int
 */
public int getLengthChamb() {
    return length;

}

/**Gets the width of the chamber if any; else returns a default number
 * @return length Int
 */
public int getWidthChamb() {
    return width;

}

/**Gets a random monster description
 * @return string of type String
 */
//public String monster() {
//    Monster mon = new Monster();
//    String str = mon.getDescription();
//
//    return str;
//}


}
