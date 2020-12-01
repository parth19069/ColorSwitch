package menu;

import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import mainPackage.Main;
import obstacles.ConcentricRingObstacle;
import obstacles.Obstacle;
import obstacles.PlusObstacle;
import obstacles.RingObstacle;
import playerinfo.Player;

import java.util.ArrayList;
import java.util.Random;

public class MainMenu extends Application {

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

    @Override
    public void start(Stage primaryStage) throws Exception{
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
        Obstacle obs = new RingObstacle(160, 560, 70, 22, true, new Translate(),0);
        Obstacle obs1 = new RingObstacle(640, 560, 70, 22, false, new Translate(), 0);

        Obstacle obs2 = new ConcentricRingObstacle(400, 560, 100, 22, 2, true, true, new Translate(),0);
        Obstacle obs3 = new RingObstacle(347, 107, 23, 8, true, new Translate(),0);
        Obstacle obs4 = new RingObstacle(475, 107, 23, 8, false, new Translate(),0);
        Obstacle obs5 = new PlusObstacle(120,150,80,17,true,new Translate());
        Obstacle obs6 = new PlusObstacle(680,150,80,17,false,new Translate());
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
//        t.setTextAlignment(TextAlignment.CENTER);
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


        newGameButton = new Button("New Game");
        newGameButton.setFocusTraversable(false);
        resumeButton= new Button("Resume Game");
        resumeButton.setFocusTraversable(false);
        exitButton = new Button("Exit Game");
        exitButton.setFocusTraversable(false);
        newGameButton.setLayoutX(360);
        newGameButton.setLayoutY(545);
        newGameButton.setMaxSize(150,250);
        resumeButton.setLayoutX(107);
        resumeButton.setLayoutY(545);
        resumeButton.setMaxSize(150,250);
        exitButton.setLayoutX(600);
        exitButton.setLayoutY(545);
        exitButton.setMaxSize(150,250);
        newGameButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
//                newGame(primaryStage, false);

                Stage slotsStage = new Stage();
                primaryStage.hide();
                Button saveSlot1 = new Button("Save Slot1");
                Button saveSlot2 = new Button("Save Slot2");
                Button saveSlot3 = new Button("Save Slot3");
                saveSlot1.setLayoutX(350);
                saveSlot1.setLayoutY(100);
                saveSlot2.setLayoutX(350);
                saveSlot2.setLayoutY(150);
                saveSlot3.setLayoutX(350);
                saveSlot3.setLayoutY(200);
                saveSlot1.setMaxSize(300,350);
                saveSlot2.setMaxSize(300,350);
                saveSlot3.setMaxSize(300,350);
                saveSlot1.setFocusTraversable(false);
                saveSlot2.setFocusTraversable(false);
                saveSlot3.setFocusTraversable(false);
                saveSlot1.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        newGame(primaryStage, false, "slot1");
                        slotsStage.hide();
                    }
                });
                saveSlot2.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        newGame(primaryStage, false, "slot2");
                        slotsStage.hide();
                    }
                });
                saveSlot3.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        newGame(primaryStage, false, "slot3");
                        slotsStage.hide();
                    }
                });
                Group slotSaveGroup = new Group();
                slotSaveGroup.getChildren().add(saveSlot1);
                slotSaveGroup.getChildren().add(saveSlot2);
                slotSaveGroup.getChildren().add(saveSlot3);
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
                resumeGame(primaryStage);
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
    public void resumeGame(Stage primaryStage){
        Button exitToMain = new Button("Return to main menu");
        Button loadGame = new Button("Load Game");
        exitToMain.setLayoutX(350);
        exitToMain.setLayoutY(40);
        exitToMain.setMaxSize(300,350);
        exitToMain.setFocusTraversable(false);
        loadGame.setLayoutX(350);
        loadGame.setLayoutY(100);
        loadGame.setFocusTraversable(false);
        loadGame.setMaxSize(300,350);
        exitToMain.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
                    start(primaryStage);
                }
                catch(Exception e){
                }
            }
        });
        loadGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.hide();
                Button slot1 = new Button("Slot1");
                Button slot2 = new Button("Slot2");
                Button slot3 = new Button("Slot3");
                slot1.setLayoutX(350);
                slot1.setLayoutY(100);
                slot2.setLayoutX(350);
                slot2.setLayoutY(150);
                slot3.setLayoutX(350);
                slot3.setLayoutY(200);
                slot1.setMaxSize(300,350);
                slot2.setMaxSize(300,350);
                slot3.setMaxSize(300,350);
                slot1.setFocusTraversable(false);
                slot2.setFocusTraversable(false);
                slot3.setFocusTraversable(false);
                Group loadGameGroup = new Group();
                loadGameGroup.getChildren().add(slot1);
                loadGameGroup.getChildren().add(slot2);
                loadGameGroup.getChildren().add(slot3);
                Stage loadGameStage = new Stage();
                Scene loadGameScene = new Scene(loadGameGroup,800, 800);
                loadGameScene.setFill(Color.rgb(41,41,41));
                loadGameStage.setTitle("Load Game");
                loadGameStage.setScene(loadGameScene);
                loadGameStage.show();
                slot1.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        newGame(primaryStage, true, "slot1");
                        loadGameStage.hide();
                    }
                });
                slot2.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        newGame(primaryStage, true, "slot2");
                        loadGameStage.hide();
                    }
                });
                slot3.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        newGame(primaryStage, true, "slot3");
                        loadGameStage.hide();
                    }
                });
            }
        });

        Group root2 = new Group(exitToMain);
        root2.getChildren().add(loadGame);
        Scene scene2 = new Scene(root2, 800,540);
        scene2.setFill(Color.rgb(41,41,41));
        primaryStage.setScene(scene2);
        primaryStage.show();
    }
    public void newGame(Stage primaryStage, boolean isLoaded, String slot){
        Main mainGame = new Main();
        mainGame.game(primaryStage, isLoaded, slot);
    }
    public void exitGame(){
        System.exit(0);
    }
}