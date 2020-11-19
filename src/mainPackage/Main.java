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
        Obstacle obs = new RingObstacle(160, 230, 60, 10, true);
        Obstacle obs1 = new RingObstacle(640, 230, 60, 10, false);
        Obstacle obs3 = new RingObstacle(345, 42, 25, 6, true);
        Obstacle obs4 = new RingObstacle(460, 42, 25, 6, false);
        Obstacle obs2 = new ConcentricRingObstacle(400, 410, 100, 10, 2, true, true);




        player = new Player(400, 750, 15, null);
//        obs.initBindings(bindings, player);
//        obs2.initBindings(bindings, player);
//        obs3.initBindings(bindings, player);
//        obs4.initBindings(bindings, player);




        Image image = new Image(new FileInputStream("/Users/sjohari/Desktop/gamemainmenu.jpg"));
        ImageView imageView = new ImageView(image);
        imageView.setX(0);
        imageView.setY(0);

        //setting the fit height and width of the image view
        imageView.setFitHeight(800);
        imageView.setFitWidth(800);

        //Setting the preserve ratio of the image view

        imageView.setPreserveRatio(true);
        Label tlabel = new Label();
        tlabel.setText("hello wassup");
        tlabel.setFont(Font.font("Tahoma"));
        tlabel.setTextFill(Color.WHITE);
        TextFlow textFlow = new TextFlow();
        Text t = new Text(200,440,"C   L   R");
//        t.setTextAlignment(TextAlignment.CENTER);
        t.setX(260);
        t.setY(70);
        t.setFill(Color.WHITE);
        t.setFont(Font.font("Tahoma",80));
        Text t2 = new Text(80,30,"SWITCH");
//        t.setTextAlignment(TextAlignment.CENTER);
        t2.setX(255);
        t2.setY(160);
        t2.setFill(Color.WHITE);
        t2.setFont(Font.font("Tahoma",80));
        textFlow.getChildren().add(t2);

        Button newGameButton,resumeButton,exitButton;
        newGameButton = new Button("New Game");
        resumeButton= new Button("Resume Game");
        exitButton = new Button("Exit Game");
        newGameButton.setLayoutX(360);
        newGameButton.setLayoutY(360);
        newGameButton.setMaxSize(100,250);
        resumeButton.setLayoutX(350);
        resumeButton.setLayoutY(400);
        resumeButton.setMaxSize(150,250);
        exitButton.setLayoutX(360);
        exitButton.setLayoutY(440);
        exitButton.setMaxSize(100,250);
        newGameButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                game(primaryStage);

            }
        });
        resumeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Button etm = new Button("Return to main menu");
                etm.setLayoutX(40);
                etm.setLayoutY(40);
                etm.setMaxSize(300,350);
                etm.setOnAction(new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            start(primaryStage);
                        }
                        catch(Exception e){

                        }


                    }
                });
                Group root2 = new Group(etm);
                Scene scene2 = new Scene(root2, 500,500);
                primaryStage.setScene(scene2);
                primaryStage.show();




            }
        }) ;

        exitButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                     System.exit(0);
            }
        });

        Group root1 = new Group();
//        root1.getChildren().add(imageView);
        root1.getChildren().add(newGameButton);
        root1.getChildren().add(resumeButton);
        root1.getChildren().add(exitButton);
        root1.getChildren().add(t);
        root1.getChildren().add(t2);

        obs.quickSetup(root1, 6000, bindings, player);
        obs1.quickSetup(root1, 6000, bindings, player);
        obs2.quickSetup(root1, 6000, bindings, player);
        obs3.quickSetup(root1, 6000, bindings, player);
        obs4.quickSetup(root1, 6000, bindings, player);
        Scene scene1 = new Scene(root1, 800,540);
        scene1.setFill(Color.rgb(41, 41, 41));



        primaryStage.setScene(scene1);
        primaryStage.show();



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
        pauseButton.setLayoutX(650);

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
//        obs.initBindings(bindings, player);
//        obs2.initBindings(bindings, player);
//        obs3.initBindings(bindings, player);
//        obs4.initBindings(bindings, player);
        player.setColor(colorCode.get(rand.nextInt(4)));
        root = new Group();
        sub = new Group();
        root.getChildren().add(player.getIcon());
//                root.getChildren().add(imageView);
//                root.getChildren().add(button);
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


