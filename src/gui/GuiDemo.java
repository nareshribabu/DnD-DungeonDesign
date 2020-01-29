package gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GuiDemo<toReturn> extends Application {

    private Controller theController;
    private BorderPane root;  //the root element of this GUI
    private Popup descriptionPane;
    private Stage primaryStage;  //The stage that is passed in on initialization
    private Popup mainDesBox;
    private ListView listOfDoors;


    /*a call to start replaces a call to the constructor for a JavaFX GUI*/
    @Override
    public void start(Stage assignedStage) {
        /*Initializing instance variables */
        theController = new Controller(this);
        theController.newLevel.generateDungeon();
        primaryStage = assignedStage;
        root = setUpRoot();
        mainDesBox = createTextArea(350, 800, "Chamber or Passage goes here.");
        Scene scene = new Scene(root, 800, 800);
        primaryStage.setTitle("Dungeon Level");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private BorderPane setUpRoot() {

        BorderPane pane = new BorderPane();
        chambPass(pane);
        listDoor(pane);
        editButtonFn(pane);
        createTitle(pane);

        return pane;
    }

    private void chambPass(BorderPane pane) {
        Node left = setLeftButtonPanel();  //separate method for the left section
        pane.setLeft(left);
    }

    private void listDoor(BorderPane pane) {
        ObservableList<String> doorList = FXCollections.observableArrayList(theController.getDoorList(0));
        listOfDoors = createListView(doorList);
        pane.setRight(listOfDoors);
    }

    private void editButtonFn(BorderPane pane) {
        Node edit = editPopup("Edit");
        pane.setBottom(edit);
        BorderPane.setAlignment(edit, Pos.BOTTOM_LEFT);
    }

    private void createTitle(BorderPane pane) {
        Label title = new Label("\t\t\tPlease note: Each tile represents 5'.\n"
                + "Unusual Shapes are given a default 20' x 20' (4' x 4')image.\n"
                + "Each image of door represents all the doors in the chamber\n");
        pane.setTop(title);
        BorderPane.setAlignment(title, Pos.TOP_CENTER);
    }


    private Node setLeftButtonPanel() {
        VBox temp = new VBox();

        temp.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 2;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: grey;");

        chambButtons(temp);
        passButtons(temp);

        return temp;

    }

    private void passButtons(VBox temp) {
        for (int j = 0; j < theController.returnPassageSize() - 1; j++) {
            Button button;
            String name = "Passage " + (j + 1);
            button = makeButtonPassage(name, (j));
            temp.getChildren().add(button);

        }
    }

    private void chambButtons(VBox temp) {
        for (int i = 0; i < theController.returnChambSize() - 1; i++) {
            Button button;
            String name = "Chamber " + (i + 1);
            button = makeButton(name, (i));
            temp.getChildren().add(button);
        }
    }

    private ChoiceBox editPopup(String nameOfButton) {

        ChoiceBox<String> edit = new ChoiceBox<>();
        edit.getItems().add("Edit choose below");
        edit.getItems().add("Edit Monster");
        edit.getItems().add("Edit Treasure");

        edit.setValue("Edit choose below");

        return edit;
    }


    private ListView createListView(ObservableList<String> spaces) {
        ListView temp = new ListView<String>(spaces);
        temp.setPrefWidth(150);
        temp.setPrefHeight(100);
        return temp;
    }

    private void updateChamberPicture(int chambNum) {
        int length, width, monTrNum = 0;

        length = theController.roomLength(chambNum);
        width = theController.roomWidth(chambNum);

        if (length == 0) {
            length = 20;
        }
        if (width == 0) {
            width = 20;
        }

        length = length / 5;
        width = width / 5;

        monTrNum = theController.containMonsterTreasure(chambNum);
        String str = theController.stringDesOfChambContents(chambNum);
        root.setCenter(new ChamberView(length, width, monTrNum, str));

    }

    private void updatePassagePicture(int passNum) {
        int width;
        String passDes = theController.getPassageDescription(passNum);

        width = findAllWords(passDes, "passage");

        int contentsPic = theController.containPass(passNum);
        root.setCenter(new ChamberView(2, width, contentsPic, null)); //change last parameter after for objects in passage
    }

    private int findAllWords(String in, String findWord) {

        int i = 0;
        Pattern p = Pattern.compile(findWord);
        Matcher m = p.matcher(in);
        while (m.find()) {
            i++;
        }

        return i;
    }


    private void updateListViewChamb (int num) {
        int i;
        int flag = 0;
        listOfDoors.getSelectionModel().clearSelection();
        listOfDoors.getItems().clear();

        ArrayList<String> doors = theController.getDoorList(num);
        doorsForChamb(doors, num);

    }

    private void doorsForChamb(ArrayList<String> doors, int num) {
        int i;
        for (i = 0; i < doors.size(); i++) {
            listOfDoors.getItems().addAll(doors.get(i));
            listOfDoors.setOnMouseClicked((MouseEvent event)->{
                descriptionForChamb(num, doors);
            });
        }
    }

    private void descriptionForChamb(int num, ArrayList<String> doors){

        String str = (String) listOfDoors.getSelectionModel().getSelectedItem();
        String[] newStr = str.split(" ");
        int doorNum = Integer.parseInt(newStr[1]);

        if (doorNum == doors.size()) { //last door
            changeDescriptionText("Chamber doors are based on numExits; therefore they will be the default door:\n"
                    + theController.getDoorDescription(doorNum, num)
                    +"\n\nThis door belongs to Chamber " + (num + 1)
                    + "\n\nThis door connects Chamber " + (num + 1) + " to Passage " + (num + 1));
        }
        else {
            changeDescriptionText("Chamber doors are based on numExits; therefore they will be the default door:\n"
                    + theController.getDoorDescription(doorNum, num)
                    +"\n\nThis door belongs to Chamber " + (num + 1));
        }

    }

    private void updateListViewPassage (int num) { //passage number
        int i;
        int flag = 0;
        listOfDoors.getSelectionModel().clearSelection();
        listOfDoors.getItems().clear();

        ArrayList<String> doors = theController.getPassageDoorList(num);
        doorsForPass(doors, num);

    }

    private void doorsForPass(ArrayList<String> doors, int num) {
        int i;
        for (i = 0; i < doors.size(); i++) {
            listOfDoors.getItems().addAll(doors.get(i));
            listOfDoors.setOnMouseClicked((MouseEvent event)->{
                descriptionForPassage(doors, num);
            });
        }
    }

    private void descriptionForPassage(ArrayList<String> doors, int num) {
        String str = (String) listOfDoors.getSelectionModel().getSelectedItem(); //get the door number from here and chamber number from num parameter and use getDoorDescription
        String[] newStr = str.split(" ");
        int doorNum = Integer.parseInt(newStr[1]);
        if (doorNum == doors.size()) { //last door
            changeDescriptionText(theController.getDoorDescription(doorNum, num)
                    + "\n\nThis doors belongs to Passage " + (num + 1)
                    + "\n\nThis door connects Passage " + (num + 1) + " to Chamber " + (num + 1));
        }
        else {
            changeDescriptionText(theController.getDoorDescription(doorNum, num) + "\n\nThis door belongs to Passage " + (num + 1));
        }
    }

    /**Generates a new button for chamber
     */
    private Button makeButton(String nameOfButton, int num) {

        Button button = createButton(nameOfButton, "-fx-background-color: #FFFFFF; ");
        button.setOnAction((ActionEvent event) -> {
            mainDesBox.show(primaryStage);
            changeDescriptionText(theController.getNewDescription(num));
            updateListViewChamb(num);
            updateChamberPicture(num);
        });

        return button;
    }

    /**Generates a new button for passages
     */
    private Button makeButtonPassage(String nameOfButton, int num) {

        Button button = createButton(nameOfButton, "-fx-background-color: #FFFFFF; ");
        button.setOnAction((ActionEvent event) -> {
            mainDesBox.show(primaryStage);
            changeDescriptionText(theController.getPassageDescription(num));
            updateListViewPassage(num);
            updatePassagePicture(num);
        });

        return button;
    }

    /* an example of a popup area that can be set to nearly any
    type of node
     */
    private Popup createPopUp(int x, int y, String text) {
        Popup popup = new Popup();
        popup.setX(x);
        popup.setY(y);
        TextArea textA = new TextArea(text);
        popup.getContent().addAll(textA);
        textA.setStyle(" -fx-background-color: white;");
        textA.setMinWidth(80);
        textA.setMinHeight(50);
        return popup;
    }

    private Popup createTextArea (int x, int y, String textDes) {
        Popup textPopup = new Popup();
        textPopup.setX(x);
        textPopup.setY(y);
        TextArea textA = new TextArea(textDes);
        textPopup.getContent().addAll(textA);
        textA.setStyle(" -fx-background-color: white;");
        textA.setMinWidth(500);
        textA.setMinHeight(100);
        return textPopup;
    }

    /*generic button creation method ensure that all buttons will have a
    similar style and means that the style only need to be in one place
     */
    private Button createButton(String text, String format) {
        Button btn = new Button();
        btn.setText(text);
        btn.setStyle("");
        return btn;
    }

    private void changeDescriptionText(String text) {
        ObservableList<Node> list = mainDesBox.getContent();
        for (Node t : list) {
            if (t instanceof TextArea) {
                TextArea temp = (TextArea) t;
                temp.setText(text);
            }

        }

    }


    public static void main(String[] args) {

        launch(args);
    }

}

/**Resources**/
/*https://stackoverflow.com/questions/22566503/count-the-number-of-occurrences-of-a-word-in-a-string*/
