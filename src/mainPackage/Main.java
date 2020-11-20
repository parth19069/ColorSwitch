package mainPackage;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import collectable.Star;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.beans.binding.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.util.Duration;
import obstacles.*;

import java.security.Key;
import java.sql.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Callable;
import playerinfo.*;




public class Main extends Application {
    private ArrayList<BooleanBinding> bindings;
    private Player player;
    private ArrayList<Color> colorCode;
    private Random rand;
    private BooleanBinding scrollBinding;
    private ArrayList<Obstacle> obstacles;
    private int offset;
    private Group root;
    private Group sub;


    @Override
    public void start(Stage primaryStage) throws Exception{
        /*
        Initialisations of instance variables

         */
        mainMenu mainmenu = new mainMenu();
        mainmenu.start(primaryStage);

        //Creating a Group object


//        bindings = new ArrayList<BooleanBinding>();
//        colorCode = new ArrayList<Color>();
//        rand = new Random();
//        offset = 0;
//        colorCode.add(Color.CYAN);
//        colorCode.add(Color.PURPLE);
//        colorCode.add(Color.YELLOW);
//        colorCode.add(Color.rgb(250, 22, 151));
//
//        /*
//        Obstacle and player declarations
//         */
//        Obstacle obs = new RingObstacle(400, 300, 190, 30, true);
//        Obstacle obs2 = new RingObstacle(400, -600, 190, 30, false);
//        Obstacle obs3 = new RingObstacle(400, -1300, 190, 30, false);
//        Obstacle obs4 = new ConcentricRingObstacle(400, -2100, 290, 30, 2, true, true);
//        Obstacle obs5 = new TangentialRingObstacle(400, -2900, 120, 120, 30, true);
//        player = new Player(400, 750, 15, null);
////        obs.initBindings(bindings, player);
////        obs2.initBindings(bindings, player);
////        obs3.initBindings(bindings, player);
////        obs4.initBindings(bindings, player);
//        player.setColor(colorCode.get(rand.nextInt(4)));
//
//        /*
//        Root and sub-root declarations
//         */
//        root = new Group();
//        sub = new Group();
//        root.getChildren().add(player.getIcon());
//        root.getChildren().add(imageView);
//        root.getChildren().add(button);
//
//
//        obs.quickSetup(sub, 6000, bindings, player);
//        obs2.quickSetup(sub, 6000, bindings, player);
//        obs3.quickSetup(sub, 6000, bindings, player);
//        obs4.quickSetup(sub, 6000, bindings, player);
//        obs5.quickSetup(sub, 6000, bindings, player);
//
//        root.getChildren().add(sub);
//        Scene scene = new Scene(root, 800, 800);
//
//        scene.setFill(Color.rgb(57, 54, 54));
//        primaryStage.setTitle("Bindings");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//
//        /*
//        Event handling
//         */
////        Timeline timeline = new Timeline();
//        Timeline timeline2 = new Timeline();
//        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent keyEvent) {
//                handlePlayerMovement(timeline2);
//            }
//        });
//        System.out.println(bindings);
    }
    void handlePlayerMovement(Timeline timeline){
        double temp = player.getIcon().getCenterY();
        Interpolator interpolator = new Interpolator() {
            @Override
            protected double curve(double v) {
                return v*(2 - v);
            }
        };
        if(temp - 120 >= 300) {
            player.getTimeline().stop();
            player.getTimeline().getKeyFrames().clear();
            player.getTimeline().setCycleCount(1);
            player.getTimeline().getKeyFrames().addAll(new KeyFrame(Duration.millis(300), new KeyValue(player.getIcon().centerYProperty(), temp - 120, interpolator)), new KeyFrame(Duration.millis((1100 - player.getIcon().getCenterY()) * 3), new KeyValue(player.getIcon().centerYProperty(), 1000, Interpolator.LINEAR)));
//                timeline.getKeyFrames().addAll(new KeyFrame(Duration.millis(300), new KeyValue(player.getIcon().centerYProperty(),temp - 20, interpolator)));
            player.getTimeline().play();
        }
        else{
            player.getTimeline().stop();
            player.getTimeline().getKeyFrames().clear();
            player.getTimeline().setCycleCount(1);
            player.getTimeline().getKeyFrames().add(new KeyFrame(Duration.millis((1000 - player.getIcon().getCenterY()) * 3), new KeyValue(player.getIcon().centerYProperty(), 1000, Interpolator.LINEAR)));
            timeline.stop();
            timeline.getKeyFrames().clear();
            timeline.setCycleCount(1);
            timeline.getKeyFrames().addAll(new KeyFrame(Duration.millis(300), new KeyValue(sub.layoutYProperty(), sub.getLayoutY() + 120, interpolator)));
            timeline.play();
            timeline.setOnFinished(e -> player.getTimeline().play());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void game (Stage pms){

        Button pauseButton = new Button("Pause");
        pauseButton.setMaxSize(100,200);
        pauseButton.setLayoutY(20);
        pauseButton.setLayoutX(700);

        pauseButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
                    start(pms);
                }
                catch(Exception e){

                }

            }
        });



        bindings = new ArrayList<BooleanBinding>();
        colorCode = new ArrayList<Color>();
        rand = new Random();
        offset = 0;
        colorCode.add(Color.CYAN);
        colorCode.add(Color.PURPLE);
        colorCode.add(Color.YELLOW);
        colorCode.add(Color.rgb(250, 22, 151));
        Obstacle obs = new RingObstacle(400, 300, 190, 30, true);
        Obstacle obs2 = new RingObstacle(400, -600, 190, 30, false);
        Obstacle obs3 = new RingObstacle(400, -1300, 190, 30, false);
        Obstacle obs4 = new ConcentricRingObstacle(400, -2100, 290, 30, 2, true, true);
        Obstacle obs5 = new TangentialRingObstacle(400, -2900, 120, 120, 30, true);
        player = new Player(400, 750, 15, null);

        player.setColor(colorCode.get(rand.nextInt(4)));
        root = new Group();
        sub = new Group();
        root.getChildren().add(player.getIcon());
        obs.quickSetup(sub, 6000, bindings, player);
        obs2.quickSetup(sub, 6000, bindings, player);
        obs3.quickSetup(sub, 6000, bindings, player);
        obs4.quickSetup(sub, 6000, bindings, player);
        obs5.quickSetup(sub, 6000, bindings, player);

        root.getChildren().add(sub);
        root.getChildren().add(pauseButton);
        Scene scene = new Scene(root, 800, 800);

        scene.setFill(Color.rgb(57, 54, 54));
        pms.setTitle("Bindings");
        pms.setScene(scene);
        pms.show();
        Timeline timeline2 = new Timeline();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                handlePlayerMovement(timeline2);
            }
        });
        System.out.println(bindings);

    }






    }
class mainMenu extends Application{

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
        Obstacle obs = new RingObstacle(160, 390, 70, 22, true);
        Obstacle obs1 = new RingObstacle(640, 390, 70, 22, false);

        Obstacle obs2 = new ConcentricRingObstacle(400, 390, 100, 22, 2, true, true);
        Obstacle obs3 = new RingObstacle(347, 97, 23, 8, true);
        Obstacle obs4 = new RingObstacle(460, 97, 23, 8, false);

        player = new Player(400, 750, 15, null);
//
        // making menu circles

        Circle c1,c2,c3,c4,c5,c6;
        c1 = new Circle(150,190,30,Color.CYAN);
        c2 = new Circle(650,190,30,Color.CYAN);
        c3 = new Circle(130,215,22,Color.rgb(46,126,132));
        c4 = new Circle(670,215,22,Color.rgb(46,126,132));
        c5 = new Circle(110,240,18,Color.rgb(43,62,62));
        c6 = new Circle(690,240,18,Color.rgb(43,62,62));

        TextFlow textFlow = new TextFlow();
        Text t = new Text(200,440,"C   L   R");
//        t.setTextAlignment(TextAlignment.CENTER);
        t.setX(258);
        t.setY(125);
        t.setFill(Color.WHITE);
        t.setFont(Font.font("Roboto",80));
        Text t2 = new Text(80,30,"SWITCH");
//        t.setTextAlignment(TextAlignment.CENTER);
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
        resumeButton.setLayoutX(107);
        resumeButton.setLayoutY(375);
        resumeButton.setMaxSize(150,250);
        exitButton.setLayoutX(600);
        exitButton.setLayoutY(375);
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
//                Button etm = new Button("Return to main menu");
//                etm.setLayoutX(40);
//                etm.setLayoutY(40);
//                etm.setMaxSize(300,350);
//                etm.setOnAction(new EventHandler<ActionEvent>(){
//                    @Override
//                    public void handle(ActionEvent event) {
//                        try {
//                            start(primaryStage);
//                        }
//                        catch(Exception e){
//
//                        }
//
//
//                    }
//                });
//                Group root2 = new Group(etm);
//                Scene scene2 = new Scene(root2, 800,540);
//                scene2.setFill(Color.rgb(41,41,41));
//                primaryStage.setScene(scene2);
//                primaryStage.show();

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
        etm.setLayoutX(40);
        etm.setLayoutY(40);
        etm.setMaxSize(300,350);
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


