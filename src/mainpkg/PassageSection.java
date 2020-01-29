package mainpkg;

import dnd.models.Monster;
import dnd.die.Percentile;

/**Creates a passage section.*/
public class PassageSection {


    /**monster.*/
    private Monster myMonster;
    /**door.*/
    private Door myDoor;

    /**passage section string.*/
    private String passageSecStr;


/**no args constructor.
*sets up the 10 foot section with default settings.
*/
public PassageSection() {

    passageSecStr = "passage goes straight for 10 ft";

}

/**constructor.
* Initializes instance variables.
* Sets up a specific passage based on the values sent in from table 1.
* @param description string
*/
public PassageSection(String description) {

    int roll;

    if (description.contains("Monster")) {
       myMonster = new Monster();
       roll = dice100();
       myMonster.setType(roll);
    }

    passageSecStr = description;


}

/**gets a door.
 * @return returns a door
 */
public Door getDoor() {

    return myDoor;

}

/**gets a monster.
 * @return returns a monster
 */
public Monster getMonster() {

    return myMonster;

}

/**get Description.
 * @return returns a string
 */
public String getDescription() {

    String strDescription;
    StringBuilder description = new StringBuilder();

    description.append(passageSecStr);

    if (this.myMonster != null) {
        description.append("\n\t\t   Monster: ").append(this.getMonster().getDescription());
    }

    strDescription = description.toString();

    return strDescription;
}

/**sets a door to this passage section.
 * @param door of type door new door
 */
public void setDoor(Door door) {

    this.myDoor = door;

}

/**set a monster to this passage section.
 * @param monster of type monster new monster
 */
public void setMonster(Monster monster) {

    this.myMonster = monster;

}

/**
*gives a die roll between 0 to 100.
* @return rollNum returns a number between 1 to 100
*/
private int dice100() {

    Percentile roll = new Percentile();
    int rollNum = -1;

    rollNum = roll.roll();

    return rollNum;
}

}

