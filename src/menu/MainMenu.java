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
        Obstacle obs = new RingObstacle(160, 510, 70, 22, true, new Translate());
        Obstacle obs1 = new RingObstacle(640, 510, 70, 22, false, new Translate());

        Obstacle obs2 = new ConcentricRingObstacle(400, 510, 100, 22, 2, true, true, new Translate());
        Obstacle obs3 = new RingObstacle(347, 107, 23, 8, true, new Translate());
        Obstacle obs4 = new RingObstacle(460, 107, 23, 8, false, new Translate());
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
        newGameButton.setLayoutY(495);
        newGameButton.setMaxSize(150,250);
        resumeButton.setLayoutX(107);
        resumeButton.setLayoutY(495);
        resumeButton.setMaxSize(150,250);
        exitButton.setLayoutX(600);
        exitButton.setLayoutY(495);
        exitButton.setMaxSize(150,250);
        newGameButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                newGame(primaryStage);
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
        obs.quickSetup(menuRoot, 6000, bindings, player, false);
        obs1.quickSetup(menuRoot, 6000, bindings, player, false);
        obs2.quickSetup(menuRoot, 6000, bindings, player, false);
        obs3.quickSetup(menuRoot, 6000, bindings, player, false);
        obs4.quickSetup(menuRoot, 6000, bindings, player, false);
        obs5.quickSetup(menuRoot,6000,bindings,player,false);
        obs6.quickSetup(menuRoot,6000,bindings,player,false);

        Scene menuScene = new Scene(menuRoot, 800,800);
        menuScene.setFill(Color.rgb(41, 41, 41));
        primaryStage.setScene(menuScene);
        primaryStage.show();

    }
    public void resumeGame(Stage pms){
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
                    start(pms);
                }
                catch(Exception e){
                }
            }
        });
        Group root2 = new Group(exitToMain);
        root2.getChildren().add(loadGame);
        Scene scene2 = new Scene(root2, 800,540);
        scene2.setFill(Color.rgb(41,41,41));
        pms.setScene(scene2);
        pms.show();
    }
    public void newGame(Stage primaryStage){
        Main m = new Main();
        m.game(primaryStage);
    }
    public void exitGame(){
        System.exit(0);
    }
}

