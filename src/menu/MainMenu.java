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
import javafx.stage.Stage;
import mainPackage.Main;
import obstacles.ConcentricRingObstacle;
import obstacles.Obstacle;
import obstacles.RingObstacle;
import playerinfo.Player;

import java.util.ArrayList;
import java.util.Random;

public class MainMenu extends Application{

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
        Obstacle obs = new RingObstacle(160, 390, 70, 22, true);
        Obstacle obs1 = new RingObstacle(640, 390, 70, 22, false);

        Obstacle obs2 = new ConcentricRingObstacle(400, 390, 100, 22, 2, true, true);
        Obstacle obs3 = new RingObstacle(347, 97, 23, 8, true);
        Obstacle obs4 = new RingObstacle(460, 97, 23, 8, false);

        player = new Player(400, 750, 15, null);
        Circle c1,c2,c3,c4,c5,c6;
        c1 = new Circle(150,190,30,Color.CYAN);
        c2 = new Circle(650,190,30,Color.CYAN);
        c3 = new Circle(130,215,22,Color.rgb(46,126,132));
        c4 = new Circle(670,215,22,Color.rgb(46,126,132));
        c5 = new Circle(110,240,18,Color.rgb(43,62,62));
        c6 = new Circle(690,240,18,Color.rgb(43,62,62));

        TextFlow textFlow = new TextFlow();
        Text t = new Text(200,440,"C   L   R");
        t.setX(258);
        t.setY(125);
        t.setFill(Color.WHITE);
        t.setFont(Font.font("Roboto",80));
        Text t2 = new Text(80,30,"SWITCH");

        t2.setX(247);
        t2.setY(190);
        t2.setFill(Color.WHITE);
        t2.setFont(Font.font("Roboto",80));
        textFlow.getChildren().add(t2);
        newGameButton = new Button("New Game");
        resumeButton= new Button("Resume Game");
        exitButton = new Button("Exit Game");
        newGameButton.setLayoutX(360);
        newGameButton.setLayoutY(375);
        newGameButton.setMaxSize(150,250);
        newGameButton.setFocusTraversable(false);
        resumeButton.setLayoutX(107);
        resumeButton.setLayoutY(375);
        resumeButton.setMaxSize(150,250);
        resumeButton.setFocusTraversable(false);
        exitButton.setLayoutX(600);
        exitButton.setLayoutY(375);
        exitButton.setMaxSize(150,250);
        exitButton.setFocusTraversable(false);
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

        Group root1 = new Group();
        root1.getChildren().add(newGameButton);
        root1.getChildren().add(resumeButton);
        root1.getChildren().add(exitButton);
        root1.getChildren().add(t);
        root1.getChildren().add(t2);
        root1.getChildren().add(c6);
        root1.getChildren().add(c4);
        root1.getChildren().add(c2);
        root1.getChildren().add(c5);
        root1.getChildren().add(c3);
        root1.getChildren().add(c1);
//
        obs.quickSetup(root1, 6000, bindings, player);
        obs1.quickSetup(root1, 6000, bindings, player);
        obs2.quickSetup(root1, 6000, bindings, player);
        obs3.quickSetup(root1, 6000, bindings, player);
        obs4.quickSetup(root1, 6000, bindings, player);
        Scene scene1 = new Scene(root1, 800,540);
        scene1.setFill(Color.rgb(41, 41, 41));
        primaryStage.setScene(scene1);
        primaryStage.show();


    }
    public void resumeGame(Stage pms){
        Button etm = new Button("Return to main menu");
        Button loadGame = new Button("Load Game");
        etm.setLayoutX(350);
        etm.setLayoutY(40);
        etm.setMaxSize(300,350);
        etm.setFocusTraversable(false);
        loadGame.setLayoutX(350);
        loadGame.setLayoutY(100);
        loadGame.setFocusTraversable(false);
        loadGame.setMaxSize(300,350);
        etm.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {

                    start(pms);
                }
                catch(Exception e){

                }
            }
        });
        Group root2 = new Group(etm);
        root2.getChildren().add(loadGame);
        Scene scene2 = new Scene(root2, 800,540);
        scene2.setFill(Color.rgb(41,41,41));
        pms.setScene(scene2);
        pms.show();

    }
    public void newGame(Stage pms){
        Main m = new Main();
        m.game(pms);

    }

    public void exitGame(){
        System.exit(0);
    }
}