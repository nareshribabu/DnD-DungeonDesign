package gui;

import java.util.ArrayList;

import mainpkg.Chamber;
import mainpkg.Door;
import mainpkg.DungeonLevel;

public class Controller {
    private GuiDemo myGui;
    private ChamberView gui;
    public DungeonLevel newLevel = new DungeonLevel();
    private int size1;


    public Controller(GuiDemo theGui){
        myGui = theGui;
    }

    public Controller(ChamberView theGui) {
        gui = theGui;
    }

    public String getNameList(int num){

        String nameList = newLevel.returnChamberList().get(num).getDescription();

        return nameList;
    }

    private String getPassageList(int num){
        String namePassageList = newLevel.returnPassageList().get(num).getDescription();

        return namePassageList;
    }

    public String getNewDescription(int num){
        //return "this would normally be a Chamber description pulled from the model of the Dungeon level.";
        return getNameList(num);
    }

    public String getPassageDescription(int num){
        //return "this would normally be a Passage description pulled from the model of the Dungeon level.";
        return getPassageList(num);
    }

    public String getDoorDescription(int num, int chambNum) {
        num = num - 1;
        String doorDes = newLevel.returnChamberList().get(chambNum).getDoors().get(num).getDescription();

        return doorDes;
    }

    public int returnChambSize() {
        return newLevel.returnChamberListSize();
    }

    public int returnPassageSize() {
        return newLevel.returnPassageListSize();
    }

    public ArrayList<Door> returnDoorInChamber(int num) {
        return newLevel.returnChamberList().get(num).getDoors();
    }


    public ArrayList<String> getDoorList(int chambNum){
        ArrayList<String> DoorList = new ArrayList<>();
        int i;
        ArrayList<Door> door = newLevel.returnChamberList().get(chambNum).getDoors();

        for (i = 0; i < door.size(); i++){
            DoorList.add("Door " + (i + 1));
        }

        return DoorList;
    }

    public ArrayList<String> getPassageDoorList(int passageNum) {
        ArrayList<String> PassageList = new ArrayList<>();
        int i;
        int doorSize = 0;

        ArrayList<Door> door = newLevel.returnPassageList().get(passageNum).getArrayOfDoors();
        doorSize = door.size();
        doorSize = noDoors(doorSize, passageNum);

        for (i = 0; i < doorSize; i++) {
            PassageList.add("Door " + (i + 1));
        }

        return PassageList;
    }

    private int noDoors (int doorSize, int passageNum) {
        if (doorSize == 0) {
            doorSize = 1;
            Door newdoor = new Door();
            newdoor.rollForDoorStatus();
            newLevel.returnPassageList().get(passageNum).setDoor(newdoor);
        }

        return doorSize;

    }

    public int roomLength(int num) {
        int length;

        length = newLevel.returnChamberList().get(num).getLengthChamb();

        return length;
    }

    public int roomWidth(int num) {
        int width;

        width = newLevel.returnChamberList().get(num).getLengthChamb();

        return width;
    }

    public int containMonsterTreasure(int chambNum) {
        int returnNum = 0;
        String chambDes;

        chambDes = getNewDescription(chambNum);

        returnNum = contentsNum(chambDes);

        return returnNum;
    }

    public String stringDesOfChambContents(int chambNum) {

        String str;
        str = newLevel.returnChamberList().get(chambNum).setDescriptionChambContents();

        return str;

    }

    private int contentsNum(String chambDes) {
        int returnNum = 0;

        if ((chambDes.contains("monster")) && (chambDes.contains("treasure"))) {
            returnNum = 3;
        } else if (chambDes.contains("treasure"))  {
            returnNum = 2;
        } else if (chambDes.contains("monster")){
            returnNum = 1;
        } else if (chambDes.contains("trap")) {
            returnNum = 4;
        } else if (chambDes.contains("stairs")) {
            returnNum = 5;
        }

        return returnNum;

    }

    public int containPass(int passNum) {
        int returnNum = 0;
        String passDes;

        passDes = getPassageDescription(passNum);

        returnNum = passageContentsNum(passDes);

        return returnNum;
    }

    private int passageContentsNum(String passDes) {
        int returnNum = 0;

         if (passDes.contains("Monster")) {
             returnNum = 1;
         }
        if (passDes.contains("Stairs")) {
            returnNum = 5;
        }

        return returnNum;

    }

    public String doorsDescription(int chambNum) {
        int i;
        StringBuilder returnString = new StringBuilder();

        returnString.append("Doors Description\n");
        returnString.append(newLevel.returnChamberList().get(chambNum).getDoors().get(0).getDescription());

        String str = returnString.toString();

        return str;
    }

//    public String monster() {
//        Chamber chamber = new Chamber();
//        return chamber.Monster();
//
//    }


}
