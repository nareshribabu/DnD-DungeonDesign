package mainpkg;
import dnd.models.Exit;
import dnd.models.Trap;
import dnd.die.D20;
import dnd.die.D4;
import dnd.die.D6;
import dnd.die.D10;


import java.util.ArrayList;

/**
*Creates a Door.
*/

public class Door {

        /**set open.*/
        private boolean open;
        /**set trap.*/
        private boolean trap;
        /**set archway.*/
        private boolean archway;
        /**set locked.*/
        private boolean locked;
        /**set occupied for new algorithm.*/
        private boolean occupied;
        /**trap number.*/
        private int trapNum;

        /**array list of type space.*/
        private ArrayList<Space> space;

        /**exit object.*/
        private Exit exit;

    /**No args door constructor: sets the instance variables to default.*/
    public Door() {
        space = new ArrayList<Space>();
        exit = new Exit();

        open = false;
        trap = false;
        archway = false;
        locked = false;
        occupied = false;
        trapNum = -1;
    }

    /**Door constructor, sets up the door based on the Exit from the tables.
    * @param theExit of type Exit is the exit object passed through
    */
    public Door(Exit theExit) {

        space = new ArrayList<Space>();

        exit = new Exit();
        exit = theExit;

        open = false;
        trap = false;
        archway = false;
        locked = false;
        occupied = false;
        trapNum = -1;

    }

    /**Set occupied: says the door is already connected to a chamber.
    * @param flag of type boolean sets true or false
    */
    public void setOccupied(boolean flag) {

        if (flag) {
            occupied = true;
        } else {
            occupied = false;
        }
    }


    /**Set locked: locks the door.
    * @param flag of type boolean sets true or false
    */
    public void setLocked(boolean flag) {

        if (flag) {
            locked = true;
        } else {
            locked = false;
        }
    }

    /**Set Trapped: traps the door.
    * @param flag of type boolean sets true or false
    * @param roll allows either random roll or specific value to be put in
    */
    public void setTrapped(boolean flag, int... roll) {

        D20 random = new D20();

        if (flag) {
            trap = true;
            archway = false;

             if (roll.length <= 0) { //ask if length of roll starts at 0 or 1?
                  trapNum = random.roll();
              } else {
                  trapNum = roll[0];
              }

          } else {
              trap = false;
          }

    }

     /**Set open: opens the door.
     * @param flag of type boolean sets true or false
     */
    public void setOpen(boolean flag) {

        if (flag) {
            open = true;
            locked = false;
        } else {
            if (this.isArchway()) {
                open = true;
            } else {
                open = false;
            }
        }
    }

    /**Set arch: makes the door an archway.
    * An archway is always open, not locked and not trapped.
    * @param flag of type boolean sets true or false
    */
    public void setArchway(boolean flag) {
        if (flag) {
            archway = true;
            open = true;
            locked = false;
            trap = false;
        } else {
            archway = false;
        }
    }

    /**is occupied: makes the door occupied.
    * @return type boolean returns true or false
    */
    public boolean isOccupied() {

        if (occupied) {
            return true;
        }

        return false;
    }

    /**is trapped: makes the door trapped.
    *@return type boolean returns true or false
    */
    public boolean isTrapped() {

        if (trap) {
            return true;
        }

        return false;
    }

    /**is open: checks to see if door is open.
    * @return type boolean returns true or false
    */
    public boolean isOpen() {

        if (open) {
            return true;
        }

        return false;

    }

    /**is archway: check to see if door is an archway.
    * @return type boolean returns true or false
    */
    public boolean isArchway() {

        if (archway) {
            return true;
        }

        return false;

    }

    /**is locked: checks to see if door is locked.
    * @return type boolean returns true or false
    */
    public boolean isLocked() {

        if (locked) {
            return true;
        }

        return false;

    }

    /**is get trap Description: gets Trap description based on trap number.
    * @return type string returns string
    */
    public String getTrapDescription() {

        String trapDescription;
        Trap trapObj = new Trap();
        trapObj.chooseTrap(trapNum);
        trapDescription = trapObj.getDescription();

        return trapDescription;
    }

    /**array list of type space: returns the two spaces that are connected by the door.
    *@return type array list of type space returns array list
    */
    public ArrayList<Space> getSpaces() {

        return this.space;
    }

    /**sets spaces in front and behind of door.
    * @param spaceOne gives space one
    * @param spaceTwo gives space two
    */
    public void setSpaces(Space spaceOne, Space spaceTwo) {

        this.space.add(spaceOne);
        this.space.add(spaceTwo);

    }

    /**is get Description: gets description about the door.
    * @return type string returns string
    */
    public String getDescription() {

        StringBuilder description = new StringBuilder();

        String archDes = descriptionArchway();
        description.append(archDes);

        String openDes = descriptionOpen();
        description.append(openDes);

        String lockedDes = descriptionLocked();
        description.append(lockedDes);

        String trappedDes = descriptionTrapped();
        description.append(trappedDes);

        String strDescription = description.toString();

        return strDescription;
    }

    /** gets Description: gets description about the archway.
    *@return type string returns string
    */
    private String descriptionArchway() {
        String description = null;

        if (isArchway()) {
            description = "\nThe Door is an Archway.";
        } else {
            description = "The Door is not an Archway";
        }

        return description;
    }

    /** gets Description: gets description stating if door is open or not.
    * @return type string returns string
    */
    private String descriptionOpen() {
        String description;

        if (isOpen()) {
            description = "\nThe Door is open.";
        } else {
            description = "\nThe Door is closed.";
        }

        return description;
    }

    /** gets Description: gets description stating if door is locked or not.
    * @return type string returns string
    */
    private String descriptionLocked() {
        String description;
        if (isLocked()) {
            description = "\nThe Door is locked.";
        } else {
            description = "\nThe Door is unlocked.";
        }

        return description;
    }

    /** gets Description: gets description stating if door is trapped or not.
    * @return type string returns string
    */
    private String descriptionTrapped() {
        StringBuilder description = new StringBuilder();
        String finalDescription;

        if (isTrapped()) {
            description.append("\nThe Door is trapped.\nTrap: ");
            description.append(getTrapDescription());
        } else {
            description.append("\nThe Door is not trapped.");
        }

        finalDescription = description.toString();

        return finalDescription;
    }


    /**roll for what the door will look like.*/
    public void rollForDoorStatus() {

        rollTrapped();
        rollLocked();
        rollOpen();
        rollArchway();

    }

    /**roll for if door is trapped or not.*/
    private void rollTrapped() {

        D20 roll20 = new D20();

        if ((roll20.roll() < 2)) {
            setTrapped(true);
         } else {
            setTrapped(false);
        }

    }

    /**roll for if door is locked or not.*/
    private void rollLocked() {

        D6 roll6 = new D6();

       if (roll6.roll() < 2) {
           setLocked(true);
       } else {
           setLocked(false);
       }

    }

    /**roll for if door is open or not.*/
    private void rollOpen() {

        D4 roll4 = new D4();

        if ((roll4.roll() <= 2)) {
            setOpen(true);
        } else {
            setOpen(false);
        }

    }

    /**roll for if door is an archway or not.*/
    private void rollArchway() {

        D10 roll10 = new D10();

        if ((roll10.roll() < 5)) {
            setArchway(true);
        } else {
            setArchway(false);
        }
    }

}
