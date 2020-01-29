package mainpkg;
import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.ArrayList;

import dnd.models.Treasure;
import dnd.die.D20;
import dnd.models.ChamberShape;
import dnd.models.ChamberContents;
import dnd.models.Monster;


/**
*Creates a level for the dungeon master.
*/
public class DungeonLevel {

    /**array list of chambers.*/
    private  ArrayList<Chamber> LevelChamberList = new ArrayList<Chamber>();
    /**array list of doors.*/
    private  ArrayList<Door> LevelDoorsList = new ArrayList<Door>();
    /**array list of passages.*/
    private ArrayList<Passage> LevelPassageList = new ArrayList<Passage>();
    /**chamber counter.*/
    int chamberCounter = 0;
    /**passage counter.*/
    int passageCounter = 0;
    /**door counter.*/
    int doorCounter = 0;

    /**space counter.*/
    int space = 0; //(1) chamber  (2)passage (3)deadend


    public String generateDungeon() {


        //DungeonLevel level = new DungeonLevel();
        int flag = 0;

        StringBuilder fullDes = new StringBuilder();


        String outputForChamber = new String();
        int j = 0;

        String[] splitWords1 = new String[2];
        String[] splitWords2 = new String[2];

        /*First Door*/
        Door startDoor = new Door();

        startDoor.rollForDoorStatus();
        startDoor.setArchway(true);

//        System.out.println("\n******************************************************************");
//        System.out.println("The level first starts with an archway (door) leading to a passage");
//        System.out.println("******************************************************************\n");

        this.LevelDoorsList.add(startDoor);
        this.doorCounter++;

       fullDes.append("\n_____________Door " + this.doorCounter + "_____________\n");
       fullDes.append(this.LevelDoorsList.get(0).getDescription() + "\n");

        Passage passage = new Passage();

        this.LevelPassageList.add(passage);
        this.passageCounter++;

        this.goStraight(passage);
        this.doorToChamber(passage);

        /*Connection between door and passage*/

        this.LevelDoorsList.get(0).setSpaces(passage, null);

        String[] splitWords = this.LevelDoorsList.get(0).getSpaces().get(0).getClass().getName().split("\\.");

        fullDes.append("\n________________________________");
        fullDes.append("Door " + (this.doorCounter - 1) + " connects to " + splitWords[1] + " 1.");
        fullDes.append("________________________________\n");

        fullDes.append("\n____________Passage " + this.passageCounter + "____________");
        fullDes.append(passage.getDescription() + "\n");



    while (this.chamberCounter < 6) {

            /*Chamber generation*/

            Chamber chamber = new Chamber();

            chamber = this.LevelChamberList.get(this.chamberCounter - 1);

            /*Door generation and connection*/
            this.LevelDoorsList.get(this.doorCounter - 1).setSpaces(this.LevelChamberList.get(0), passage); //changed this


            splitWords1 = this.LevelDoorsList.get(this.doorCounter - 1).getSpaces().get(0).getClass().getName().split("\\.");
            splitWords2 = this.LevelDoorsList.get(this.doorCounter - 1).getSpaces().get(1).getClass().getName().split("\\.");


            fullDes.append("\n________________________________");
            fullDes.append("Door " + this.doorCounter + " connects " + splitWords1[1] + " " + this.passageCounter + " and " + splitWords2[1] + " " + this.chamberCounter);
            fullDes.append("________________________________");

            fullDes.append("\n_____________Door " + this.doorCounter + "_____________");
            fullDes.append(this.LevelDoorsList.get(this.doorCounter - 1).getDescription() + "\n");

            fullDes.append("\n____________Chamber " + this.chamberCounter + "____________\n");
            fullDes.append(chamber.getDescription());


            this.space = 1;


         if (this.space == 1) {

             passage = new Passage();

             this.LevelPassageList.add(passage);
             this.passageCounter++;

             this.space = 0;

             while (this.space != 1) {

                 this.createPassage(passage);

             }
            if (this.passageCounter < 6) {
                fullDes.append("\n____________Passage " + this.passageCounter + "____________");
                fullDes.append(passage.getDescription() + "\n");
            }

        }

    }

    String strDes = fullDes.toString();


    return strDes;
}

    /**Return ArrayList of chambers.
     * @return ArrayList of chamber
     */
    public ArrayList<Chamber> returnChamberList() {
        return this.LevelChamberList;
    }

    /**Return size of the chamber list.
     * @return int size.
     */
    public int returnChamberListSize() {
        return this.LevelChamberList.size();
    }

    /**Return ArrayList of Passages.
     * @return ArrayList of Passages
     */
    public ArrayList<Passage> returnPassageList() {
        return this.LevelPassageList;
    }

    /**Return size of the passage list.
     * @return int size.
     */
    public int returnPassageListSize() {
        return this.LevelPassageList.size();
    }

    /**Return ArrayList of Doors.
     * @return ArrayList of Doors
     */
    public ArrayList<Door> returnDoorList() {
        return this.LevelDoorsList;
    }

    /**Return size of the passage list.
     * @return int size.
     */
    public int returnDoorListSize() {
        return this.LevelDoorsList.size();
    }


    /**Creates a passage by rolling a random dice.
     * @param p of type Passage is a passage
     */
    public void createPassage(Passage p) {

        D20 rollForPassage = new D20();
        int rollNum = -1;

        rollNum = rollForPassage.roll();

        if (rollNum < 3) {
            /*Passage Straight*/
            this.goStraight(p);
            space = 2;

        } else if (rollNum < 6) {
            /*Door to chamber*/

           this.doorToChamber(p);
           space = 1;
        } else if (rollNum < 8) {
            /*Archway right*/
            this.archRight(p);
            space = 2;

        } else if (rollNum < 10) {
            /*Archway left*/
            this.archLeft(p);
            space = 2;

        } else if (rollNum < 12) {
            /*Passage turns left*/

            this.turnLeft(p);
            space = 2;

        } else if (rollNum  < 14) {
            /*Passage turns right*/

            this.turnRight(p);
            space = 2;
        } else if (rollNum < 17) {
            /*passage ends in archway to chamber*/
            this.endsInArchway(p);
            space = 1;
        } else if (rollNum < 18) {
            /*stairs*/

            this.passageStairs(p);
            space = 2;
        } else if (rollNum < 20) {

            /*Dead End*/
            this.deadEnd(p);
            space = 3;


        } else {
            /*Monster*/
           this.wanderingMonster(p);
           space = 2;

        }


    }


    /**Creates a passage.
     * @param p of type Passage is a passage.
     */
    public void goStraight(Passage p) {

        String s;

        s = "passage goes straight for 10 ft";

        PassageSection straight = new PassageSection(s);

        p.addPassageSection(straight);

    }

    /**Creates a passage.
     * @param p of type Passage is a passage.
     */
    public void doorToChamber(Passage p) {

        Door door = new Door();
        Chamber theShape;

        int index;

        index = LevelDoorsList.size() - 1;

        if (index > 0) {

            door = LevelDoorsList.get(index);

        }

        String s = "passage ends in Door to a Chamber";

        //create new passage section
        PassageSection passageToChamber = new PassageSection(s);

        //door.rollForDoorStatus();

        p.addPassageSection(passageToChamber);

        //create a new chamber
        theShape = new Chamber();

        LevelDoorsList.add(door);
        door.rollForDoorStatus();
        p.setDoor(door);
        door.setSpaces(p, theShape);
        doorCounter++;

        LevelChamberList.add(theShape);
        chamberCounter++;

    }

    /**Creates a passage.
     * @param p of type Passage is a passage.
     */
    public void archRight(Passage p) {

        String s;
        Door door = new Door();

        s = "archway (door) to the right (main passage continues straight for 10 ft)";

        PassageSection rArch = new PassageSection(s);

        door.rollForDoorStatus();
        door.setArchway(true);
        p.setDoor(door);

        p.addPassageSection(rArch);
    }

    /**Creates a passage.
     * @param p of type Passage is a passage.
     */
    public void archLeft(Passage p) {

       String s;

       s = "archway (door) to the left (main passage continues straight for 10 ft)";

        Door door = new Door();

        PassageSection lArch = new PassageSection(s);

        door.rollForDoorStatus();
        door.setArchway(true);
        p.setDoor(door); //add door to the arrayList

        p.addPassageSection(lArch);


    }

    /**Creates a passage.
     * @param p of type Passage is a passage.
     */
    public void turnLeft(Passage p) {

        String s;
        s = "passage turns to left and continues for 10 ft";

        PassageSection left = new PassageSection(s);

        p.addPassageSection(left);

    }

    /**Creates a passage.
     * @param p of type Passage is a passage.
     */
    public void turnRight(Passage p) {

        String s;
        s = "passage turns to right and continues for 10 ft";

        PassageSection right = new PassageSection(s);

        p.addPassageSection(right);

    }

    /**Creates a passage.
     * @param p of type Passage is a passage.
     */
    public void endsInArchway(Passage p) {

        String s;
        s = "passage ends in archway (door) to chamber";


        Door door = new Door();

        PassageSection endArch = new PassageSection(s);

        door.rollForDoorStatus();
        door.setArchway(true);
        p.setDoor(door);

        p.addPassageSection(endArch);

        Chamber newChamber = new Chamber();

       newChamber.setDoor(door);

       door.setSpaces(p, newChamber);

        LevelDoorsList.add(door);
        doorCounter++;

        LevelChamberList.add(newChamber);
        chamberCounter++;

    }

    /**Creates a passage.
     * @param p of type Passage is a passage.
     */
    public void passageStairs(Passage p) {

        String s;
        s = "Stairs, (passage continues straight for 10 ft)";

        PassageSection passStairs = new PassageSection(s);

        p.addPassageSection(passStairs);


    }

    /**Creates a passage.
     * @param p of type Passage is a passage.
     */
    public void deadEnd(Passage p) {

        String s;
        //s = "Dead End";
        s = "passage goes straight for 10 ft";


    }

    /**Creates a passage.
     * @param p of type Passage is a passage.
     */
    public void wanderingMonster(Passage p) {

        String s;
        s = "Wandering Monster (passage continues straight for 10 ft)";

        PassageSection passMonster = new PassageSection(s);

        p.addPassageSection(passMonster);

    }



}
