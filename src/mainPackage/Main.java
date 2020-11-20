package mainPackage;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.animation.*;
import javafx.application.Application;

import javafx.beans.binding.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.util.Duration;
import menu.MainMenu;
import obstacles.*;

import java.util.ArrayList;
import java.util.Random;

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
        MainMenu mainmenu = new MainMenu();
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


