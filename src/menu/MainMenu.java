package menu;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
import mainPackage.Main;
import obstacles.ConcentricRingObstacle;
import obstacles.Obstacle;
import obstacles.PlusObstacle;
import obstacles.RingObstacle;
import playerinfo.Player;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainMenu {

    private ArrayList<BooleanBinding> bindings;
    private Player player;
    private ArrayList<Color> colorCode;
    private Random rand;
    private BooleanBinding scrollBinding;
    private ArrayList<Obstacle> obstacles;
    private int offset;
    private Group root;
    private Group sub;

    private Button newGameButton,resumeButton,exitButton;

    public void resumeGame(Stage primaryStage, String username){
        Text resumeGameText;
        resumeGameText = new Text("RESUME GAME");
        resumeGameText.setFont(Font.font("Sans Serif",45));
        resumeGameText.setX(240);
        resumeGameText.setY(125);
        resumeGameText.setFill(Color.WHITE);
        Button exitToMain = new Button("RETURN TO MAIN MAIN");
        Button loadGame = new Button("LOAD GAME");
        loadGame.setLayoutX(245);
        loadGame.setLayoutY(230);
        loadGame.setPrefWidth(320);
        loadGame.setPrefHeight(80);
        loadGame.setFocusTraversable(false);
        loadGame.setFont(Font.font("Sans Serif",20));
        loadGame.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;"+"-fx-border-width: 2;");
        loadGame.setOnMouseEntered(e -> loadGame.setStyle("-fx-background-color: #FF4500;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")) ;
        loadGame.setOnMouseExited(e -> loadGame.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")); ;
        exitToMain.setLayoutX(245);
        exitToMain.setLayoutY(360);
        exitToMain.setPrefWidth(320);
        exitToMain.setPrefHeight(80);
        exitToMain.setFocusTraversable(false);
        exitToMain.setFont(Font.font("Sans Serif",20));
        exitToMain.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;"+"-fx-border-width: 2;");
        exitToMain.setOnMouseEntered(e -> exitToMain.setStyle("-fx-background-color: #FF0000;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")) ;
        exitToMain.setOnMouseExited(e -> exitToMain.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")); ;
        buttonTransition(loadGame,true);
        buttonTransition(exitToMain,false);

        exitToMain.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
                    startMainMenu(primaryStage, username);
                }
                catch(Exception e){
                    System.out.println("Unexpected error");
                }
            }
        });
        loadGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.hide();
                Button slot1 = new Button("SLOT 1");
                Button slot2 = new Button("SLOT 2");
                Button slot3 = new Button("SLOT 3");
                Text loadGameText = new Text("CHOOSE GAME TO LOAD");
                loadGameText.setFont(Font.font("Sans Serif",45));
                loadGameText.setX(130);
                loadGameText.setY(125);
                loadGameText.setFill(Color.WHITE);
                slot1.setLayoutX(225);
                slot1.setLayoutY(200);
                slot1.setPrefWidth(350);
                slot1.setPrefHeight(80);
                slot1.setFocusTraversable(false);
                slot1.setFont(Font.font("Sans Serif",20));
                slot1.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;"+"-fx-border-width: 2;");
                slot1.setOnMouseEntered(e -> slot1.setStyle("-fx-background-color: #FF4500;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")) ;
                slot1.setOnMouseExited(e -> slot1.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")); ;
                buttonTransition(slot1,true);
                slot2.setLayoutX(225);
                slot2.setLayoutY(300);
                slot2.setPrefWidth(350);
                slot2.setPrefHeight(80);
                slot2.setFocusTraversable(false);
                slot2.setFont(Font.font("Sans Serif",20));
                slot2.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;"+"-fx-border-width: 2;");
                slot2.setOnMouseEntered(e -> slot2.setStyle("-fx-background-color: #FF4500;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")) ;
                slot2.setOnMouseExited(e -> slot2.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")); ;
                buttonTransition(slot2,false);
                slot3.setLayoutX(225);
                slot3.setLayoutY(400);
                slot3.setPrefWidth(350);
                slot3.setPrefHeight(80);
                slot3.setFocusTraversable(false);
                slot3.setFont(Font.font("Sans Serif",20));
                slot3.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;"+"-fx-border-width: 2;");
                slot3.setOnMouseEntered(e -> slot3.setStyle("-fx-background-color: #FF4500;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")) ;
                slot3.setOnMouseExited(e -> slot3.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")); ;
                buttonTransition(slot3,true);
                Group loadGameGroup = new Group();
                loadGameGroup.getChildren().add(slot1);
                loadGameGroup.getChildren().add(slot2);
                loadGameGroup.getChildren().add(slot3);
                loadGameGroup.getChildren().add(loadGameText);
                Stage loadGameStage = new Stage();
                Scene loadGameScene = new Scene(loadGameGroup,800, 800);
                loadGameScene.setFill(Color.rgb(41,41,41));
                loadGameStage.setTitle("Load Game");
                loadGameStage.setScene(loadGameScene);
                loadGameStage.show();
                slot1.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        newGame(primaryStage, true, "slot1.ser", username);
                        loadGameStage.hide();
                    }
                });
                slot2.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        newGame(primaryStage, true, "slot2.ser", username);
                        loadGameStage.hide();
                    }
                });
                slot3.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        newGame(primaryStage, true, "slot3.ser", username);
                        loadGameStage.hide();
                    }
                });
            }
        });

        Group root2 = new Group(exitToMain);
        root2.getChildren().add(loadGame);
        root2.getChildren().add(resumeGameText);
        Scene scene2 = new Scene(root2, 800,800);
        scene2.setFill(Color.rgb(41,41,41));
        primaryStage.setScene(scene2);

        primaryStage.show();


    }
    public void newGame(Stage primaryStage, boolean isLoaded, String slot, String username){
        try {
            Main mainGame = new Main();
            mainGame.game(primaryStage, isLoaded, slot, username);
        }
        catch(Exception e){
            System.out.println("Unexpected error");
        }
    }
    public void exitGame(){
        System.exit(0);
    }

    public void startMainMenu(Stage stage, String username) throws Exception{
        Stage primaryStage = new Stage();
        bindings = new ArrayList<BooleanBinding>();
        colorCode = new ArrayList<Color>();
        rand = new Random();
        offset = 0;
        colorCode.add(Color.CYAN);
        colorCode.add(Color.PURPLE);
        colorCode.add(Color.YELLOW);
        colorCode.add(Color.rgb(250, 22, 151));
        /*
        Obstacle and player declarations
         */
        Obstacle obs = new RingObstacle(160, 560, 75, 22, true, new Translate(),0);
        Obstacle obs1 = new RingObstacle(640, 560, 75, 22, false, new Translate(), 0);

        Obstacle obs2 = new ConcentricRingObstacle(400, 560, 102, 22, 2, true, true, new Translate(),0);
        Obstacle obs3 = new RingObstacle(347, 107, 23, 8, true, new Translate(),0);
        Obstacle obs4 = new RingObstacle(475, 107, 23, 8, false, new Translate(),0);
        Obstacle obs5 = new PlusObstacle(120,150,80,17,true,new Translate(), 0);
        Obstacle obs6 = new PlusObstacle(680,150,80,17,false,new Translate(), 0);
        player = new Player(400, 750, 15, null);
        Circle c1,c2,c3,c4,c5,c6;
        c1 = new Circle(150,300,30,Color.CYAN);
        c2 = new Circle(650,300,30,Color.CYAN);
        c3 = new Circle(130,325,22,Color.rgb(46,126,132));
        c4 = new Circle(670,325,22,Color.rgb(46,126,132));
        c5 = new Circle(110,350,18,Color.rgb(43,62,62));
        c6 = new Circle(690,350,18,Color.rgb(43,62,62));

        TextFlow textFlow = new TextFlow();
        Text t = new Text(200,440,"C   L   R");
        t.setX(258);
        t.setY(135);
        t.setFill(Color.WHITE);
        t.setFont(Font.font("Roboto",80));
        Text t2 = new Text(80,30,"SWITCH");

        t2.setX(247);
        t2.setY(200);
        t2.setFill(Color.WHITE);
        t2.setFont(Font.font("Roboto",80));
        textFlow.getChildren().add(t2);

        /* Main menu images */

        Image img = new Image(new FileInputStream("images/Play-Button-715x715.png"),132,132,true,true);
        ImageView view = new ImageView(img);
        view.preserveRatioProperty();
        Image img2 = new Image(new FileInputStream("images/exitButton.png"),132,132,true,true);
        ImageView view2 = new ImageView(img2);
        view2.preserveRatioProperty();
        Image img3 = new Image(new FileInputStream("images/resumeButton.png"),132,132,true,true);
        ImageView view3 = new ImageView(img3);
        view2.preserveRatioProperty();

        /*
        New game button Details
        */

        newGameButton = new Button();
        newGameButton.setGraphic(view);
        newGameButton.setShape(new Circle(15));
        newGameButton.setContentDisplay(ContentDisplay.CENTER);
        newGameButton.setMaxSize(15,15);
        newGameButton.setLayoutX(335);
        newGameButton.setLayoutY(495);
        newGameButton.setFocusTraversable(false);
        ScaleTransition st = new ScaleTransition(Duration.millis(150),newGameButton);
        st.setToX(0.9);
        st.setToY(0.9);
        ScaleTransition st1 = new ScaleTransition(Duration.millis(150),newGameButton);
        st1.setToX(1);
        st1.setToY(1);
        newGameButton.setStyle(
                "-fx-background-radius: 500em; " +
                        "-fx-min-width: 130px; " +
                        "-fx-min-height: 130px; " +
                        "-fx-max-width: 130px; " +
                        "-fx-max-height: 130px;"+
                        "-fx-background-color: -fx-shadow-highlight-color;"
        );
        newGameButton.setOnMouseEntered(e -> newGameButton.setStyle("-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;"+
                "-fx-background-radius: 500em; " +
                "-fx-min-width: 130px; " +
                "-fx-min-height: 130px; " +
                "-fx-max-width: 130px; " +
                "-fx-max-height: 130px;"));
        newGameButton.setOnMouseEntered(e -> st.play()) ;

        newGameButton.setOnMouseExited(e -> newGameButton.setStyle("-fx-background-color: -fx-shadow-highlight-color;"+
                "-fx-background-radius: 500em; " +
                "-fx-min-width: 130px; " +
                "-fx-min-height: 130px; " +
                "-fx-max-width: 130px; " +
                "-fx-max-height: 130px;"));
        newGameButton.setOnMouseExited(e -> st1.play()) ;

        /*
          Resume button Details
        */

        resumeButton= new Button();
        resumeButton.setFocusTraversable(false);
        resumeButton.setGraphic(view3);
        resumeButton.setShape(new Circle(15));
        resumeButton.setContentDisplay(ContentDisplay.CENTER);
        resumeButton.setLayoutX(95);
        resumeButton.setLayoutY(495);
        ScaleTransition st2 = new ScaleTransition(Duration.millis(150),resumeButton);
        st2.setToX(0.9);
        st2.setToY(0.9);
        ScaleTransition st3 = new ScaleTransition(Duration.millis(150),resumeButton);
        st3.setToX(1);
        st3.setToY(1);
        resumeButton.setStyle(
                "-fx-background-radius: 500em; " +
                        "-fx-min-width: 130px; " +
                        "-fx-min-height: 130px; " +
                        "-fx-max-width: 130px; " +
                        "-fx-max-height: 130px;"+
                        "-fx-background-color: -fx-shadow-highlight-color;"
        );
        resumeButton.setOnMouseEntered(e -> resumeButton.setStyle("-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;"+
                "-fx-background-radius: 500em; " +
                "-fx-min-width: 130px; " +
                "-fx-min-height: 130px; " +
                "-fx-max-width: 130px; " +
                "-fx-max-height: 130px;"));
        resumeButton.setOnMouseEntered(e -> st2.play()); ;


        resumeButton.setOnMouseExited(e -> resumeButton.setStyle("-fx-background-color: -fx-shadow-highlight-color;"+
                "-fx-background-radius: 500em; " +
                "-fx-min-width: 130px; " +
                "-fx-min-height: 130px; " +
                "-fx-max-width: 130px; " +
                "-fx-max-height: 130px;"));
        resumeButton.setOnMouseExited(e -> st3.play()); ;

        /*
            Exit button Details
        */

        exitButton = new Button();
        exitButton.setGraphic(view2);
        exitButton.setShape(new Circle(15));
        exitButton.setContentDisplay(ContentDisplay.CENTER);
        exitButton.setMaxSize(15,15);
        exitButton.setFocusTraversable(false);
        exitButton.setLayoutX(575);
        exitButton.setLayoutY(495);
        ScaleTransition st4 = new ScaleTransition(Duration.millis(150),exitButton);
        st4.setToX(0.9);
        st4.setToY(0.9);
        ScaleTransition st5 = new ScaleTransition(Duration.millis(150),exitButton);
        st5.setToX(1);
        st5.setToY(1);
        exitButton.setStyle(
                "-fx-background-radius: 500em; " +
                        "-fx-min-width: 130px; " +
                        "-fx-min-height: 130px; " +
                        "-fx-max-width: 130px; " +
                        "-fx-max-height: 130px;"+
                        "-fx-background-color: -fx-shadow-highlight-color;"

        );
        exitButton.setOnMouseEntered(e -> exitButton.setStyle("-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;"+
                "-fx-background-radius: 500em; " +
                "-fx-min-width: 130px; " +
                "-fx-min-height: 130px; " +
                "-fx-max-width: 130px; " +
                "-fx-max-height: 130px;")) ;
        exitButton.setOnMouseEntered(e -> st4.play()) ;

        exitButton.setOnMouseExited(e -> exitButton.setStyle("-fx-background-color: -fx-shadow-highlight-color;"+
                "-fx-background-radius: 500em; " +
                "-fx-min-width: 130px; " +
                "-fx-min-height: 130px; " +
                "-fx-max-width: 130px; " +
                "-fx-max-height: 130px;"));
        exitButton.setOnMouseExited(e -> st5.play()); ;
        newGameButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Stage slotsStage = new Stage();
                primaryStage.hide();
                Button saveSlot1 = new Button("SAVE SLOT 1");
                Button saveSlot2 = new Button("SAVE SLOT 2");
                Button saveSlot3 = new Button("SAVE SLOT 3");
                Text chooseSlotText;
                chooseSlotText = new Text("CHOOSE SLOT TO SAVE");
                chooseSlotText.setFont(Font.font("Sans Serif",45));
                chooseSlotText.setX(140);
                chooseSlotText.setY(125);
                chooseSlotText.setFill(Color.WHITE);
                saveSlot1.setLayoutX(230);
                saveSlot1.setLayoutY(190);
                saveSlot1.setPrefWidth(350);
                saveSlot1.setPrefHeight(80);
                saveSlot2.setLayoutX(230);
                saveSlot2.setLayoutY(300);
                saveSlot2.setPrefWidth(350);
                saveSlot2.setPrefHeight(80);
                saveSlot3.setLayoutX(230);
                saveSlot3.setLayoutY(410);
                saveSlot3.setPrefWidth(350);
                saveSlot3.setPrefHeight(80);
                saveSlot1.setFocusTraversable(false);
                saveSlot2.setFocusTraversable(false);
                saveSlot3.setFocusTraversable(false);
                saveSlot1.setFont(Font.font("Sans Serif",20));
                saveSlot1.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;"+"-fx-border-width: 2;");
                saveSlot1.setOnMouseEntered(e -> saveSlot1.setStyle("-fx-background-color: #FF4500;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")) ;
                saveSlot1.setOnMouseExited(e -> saveSlot1.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")); ;
                saveSlot2.setFont(Font.font("Sans Serif",20));
                saveSlot2.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;"+"-fx-border-width: 2;");
                saveSlot2.setOnMouseEntered(e -> saveSlot2.setStyle("-fx-background-color: #FF4500;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")) ;
                saveSlot2.setOnMouseExited(e -> saveSlot2.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")); ;
                saveSlot3.setFont(Font.font("Sans Serif",20));
                saveSlot3.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;"+"-fx-border-width: 2;");
                saveSlot3.setOnMouseEntered(e -> saveSlot3.setStyle("-fx-background-color: #FF4500;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")) ;
                saveSlot3.setOnMouseExited(e -> saveSlot3.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")); ;
                buttonTransition(saveSlot1,true);
                buttonTransition(saveSlot2,false);
                buttonTransition(saveSlot3,true);

                saveSlot1.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        newGame(primaryStage, false, "slot1.ser", username);
                        slotsStage.hide();
                    }
                });
                saveSlot2.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        newGame(primaryStage, false, "slot2.ser", username);
                        slotsStage.hide();
                    }
                });
                saveSlot3.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        newGame(primaryStage, false, "slot3.ser", username);
                        slotsStage.hide();
                    }
                });
                Group slotSaveGroup = new Group();
                slotSaveGroup.getChildren().add(saveSlot1);
                slotSaveGroup.getChildren().add(saveSlot2);
                slotSaveGroup.getChildren().add(saveSlot3);
                slotSaveGroup.getChildren().add(chooseSlotText);
                Scene slotSaveScene = new Scene(slotSaveGroup, 800,800);
                slotSaveScene.setFill(Color.rgb(41,41,41));
                slotsStage.setTitle("Slot Stage");
                slotsStage.setScene(slotSaveScene);
                slotsStage.show();
            }
        });
        resumeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                resumeGame(primaryStage, username);
            }
        }) ;

        exitButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                exitGame();
            }
        });

        Group menuRoot = new Group();
        menuRoot.getChildren().add(newGameButton);
        menuRoot.getChildren().add(resumeButton);
        menuRoot.getChildren().add(exitButton);
        menuRoot.getChildren().add(t);
        menuRoot.getChildren().add(t2);
        menuRoot.getChildren().add(c6);
        menuRoot.getChildren().add(c4);
        menuRoot.getChildren().add(c2);
        menuRoot.getChildren().add(c5);
        menuRoot.getChildren().add(c3);
        menuRoot.getChildren().add(c1);
        obs.quickSetup(menuRoot, 6000, bindings, player, false, false, false, false);
        obs1.quickSetup(menuRoot, 6000, bindings, player, false, false, false, false);
        obs2.quickSetup(menuRoot, 6000, bindings, player, false, false, false, false);
        obs3.quickSetup(menuRoot, 6000, bindings, player, false, false, false, false);
        obs4.quickSetup(menuRoot, 6000, bindings, player, false, false, false, false);
        obs5.quickSetup(menuRoot,6000,bindings,player,false, false, false, false);
        obs6.quickSetup(menuRoot,6000,bindings,player,false, false, false, false);

        Scene menuScene = new Scene(menuRoot, 800,800);
        menuScene.setFill(Color.rgb(41, 41, 41));
        primaryStage.setScene(menuScene);
        primaryStage.show();


    }

    public void buttonTransition(Button b, Boolean leftToRight){
        TranslateTransition t = new TranslateTransition();
        t.setDuration(Duration.millis(400));
        t.setNode(b);
        if(leftToRight) {
            t.setFromX(-801);
            t.setFromY(0);
            t.setToX(0);
            t.setToY(0);
            t.play();
        }
        else{
            t.setFromX(801);
            t.setFromY(0);
            t.setToX(0);
            t.setToY(0);
            t.play();
        }

    }

}