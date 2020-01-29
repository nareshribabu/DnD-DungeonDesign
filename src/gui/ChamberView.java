package gui;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import mainpkg.Chamber;


public class ChamberView extends GridPane {
    private String floor;
    private String treasure;
    private  String monster;
    private String door;
    private String stairs;
    private String trap;
    private int length;
    private int width;
    private Popup picturePopUp;
    private Controller theController;


    public ChamberView(int len, int wid, int num, String str) {
        floor = "/floor2.png"; //put back /res
        treasure = "/treasure2.png";
        monster = "/monster.png";
        door = "/door.png";
        stairs = "/stairs.png";
        trap = "/trap.png";

        length = len;
        width = wid; //user these values to decide the size of the view and how many tiles
        picturePopUp = createPictureArea(500, 350, "Description");
        theController = new Controller(this);
        theController.newLevel.generateDungeon();

        grid(length, width, num, str);

    }



    private Popup createPictureArea (int x, int y, String textDes) {
        Popup textPopup = new Popup();
        textPopup.setX(x);
        textPopup.setY(y);
        TextArea textA = new TextArea(textDes);
        textPopup.getContent().addAll(textA);
        textA.setStyle(" -fx-background-color: white;");
        textA.setMinWidth(400);
        textA.setMinHeight(100);
        return textPopup;
    }

    public void grid(int len, int wid, int num, String str) {
        int counter = 0;
        int total;
        int i;
        int j;

        width = wid;
        length = len;

        total = width * length;

        Node[] tiles = makeTiles(total, num, str);
        for (i = 0; i < width; i++) {
            for (j = 0; j < length; j++) {
                add(tiles[counter], i, j);
                counter++;
            }
        }

    }


    private Node[] makeTiles(int total, int num, String str) {  //should have a parameter and a loop

        Chamber chamb = new Chamber();
        Node[] toReturn = new Node[total];
        int last = total - 1;

        for(int i = 0; i < total; i++) {
            toReturn[i] = floorFactory(floor);
        }

        if (num == 1) {
            toReturn[1] = monsterFactory(monster);
        } else if (num == 2)  {
            toReturn[1] = treasureFactory(treasure);
        } else if (num == 3) {
            toReturn[1] = treasureFactory(treasure);
            toReturn[2] = monsterFactory(monster);
        } else if (num == 4) {
            toReturn[1] = trapFactory(trap);
        } else if (num == 5) {
            toReturn[1] = stairsFactory(stairs);
        }


        toReturn[last] = doorFactory(door);
        toReturn[last].setOnMouseClicked((MouseEvent event)->{
            Stage newStage = new Stage();
            newStage.setTitle("Doors.");
            StackPane layout = new StackPane();
            Button newB = new Button("Click me to view Doors");
            layout.getChildren().add(newB);
            Scene scene = new Scene(layout, 300, 100);
            newStage.setScene(scene);
            newStage.show();

            newB.setOnAction((ActionEvent event2) -> {
                picturePopUp.show(newStage);
                changeDescriptionPopUp(theController.doorsDescription(0));
            });
        });


        toReturn[1].setOnMouseClicked((MouseEvent event)-> {
            Stage newStage1 = new Stage();
            newStage1.setTitle("Chamber Contents");
            StackPane layout = new StackPane();
            Button newB = new Button("Click me to view Contents");
            layout.getChildren().add(newB);
            Scene scene = new Scene(layout, 300, 100);
            newStage1.setScene(scene);
            newStage1.show();

            newB.setOnAction((ActionEvent event2) -> {
                picturePopUp.show(newStage1);
                changeDescriptionPopUp(str);
            });
        });

        toReturn[2].setOnMouseClicked((MouseEvent event)-> {
            Stage newStage1 = new Stage();
            newStage1.setTitle("Chamber Contents.");
            StackPane layout = new StackPane();
            Button newB = new Button("Click me to view contents");
            layout.getChildren().add(newB);
            Scene scene = new Scene(layout, 300, 100);
            newStage1.setScene(scene);
            newStage1.show();

            newB.setOnAction((ActionEvent event2) -> {
                picturePopUp.show(newStage1);
                changeDescriptionPopUp(str);
            });
        });

        return toReturn;
    }



    private void changeDescriptionPopUp(String text) {
        ObservableList<Node> list = picturePopUp.getContent();
        for (Node t : list) {
            if (t instanceof TextArea) {
                TextArea temp = (TextArea) t;
                temp.setText(text);
            }

        }

    }

    public Node floorFactory(String img) {
        Image floor = new Image(getClass().getResourceAsStream(img));
        Label toReturn = new Label();
        ImageView imageView = new ImageView(floor);
        imageView.setFitWidth(60);
        imageView.setFitHeight(55);
        toReturn.setGraphic(imageView);
        return toReturn;
    }

    public Node treasureFactory(String img) {
        Image treasure = new Image(getClass().getResourceAsStream(img));
        Label toReturn = new Label();
        ImageView imageView = new ImageView(treasure);
        imageView.setFitWidth(60);
        imageView.setFitHeight(55);
        toReturn.setGraphic(imageView);
        return toReturn;

    }

    public Node monsterFactory(String img) {
        Image monster = new Image(getClass().getResourceAsStream(img));
        Label toReturn = new Label();
        ImageView imageView = new ImageView(monster);
        imageView.setFitWidth(60); //60
        imageView.setFitHeight(55); //55
        toReturn.setGraphic(imageView);
        return toReturn;

    }

    public Node doorFactory(String img) {
        Image door = new Image(getClass().getResourceAsStream(img));
        Label toReturn = new Label();
        ImageView imageView = new ImageView(door);
        imageView.setFitWidth(60);
        imageView.setFitHeight(55);
        toReturn.setGraphic(imageView);
        return toReturn;

    }

    public Node stairsFactory(String img) {
        Image stairs = new Image(getClass().getResourceAsStream(img));
        Label toReturn = new Label();
        ImageView imageView = new ImageView(stairs);
        imageView.setFitWidth(60);
        imageView.setFitHeight(55);
        toReturn.setGraphic(imageView);
        return toReturn;

    }

    public Node trapFactory(String img) {
        Image trap = new Image(getClass().getResourceAsStream(img));
        Label toReturn = new Label();
        ImageView imageView = new ImageView(trap);
        imageView.setFitWidth(60);
        imageView.setFitHeight(55);
        toReturn.setGraphic(imageView);
        return toReturn;

    }

}

