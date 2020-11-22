package mainPackage;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import collectable.Star;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import menu.MainMenu;
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

        MainMenu mainmenu = new MainMenu();
        mainmenu.start(primaryStage);

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
        pauseButton.setFocusTraversable(false);
        pauseButton.setMaxSize(100,200);
        pauseButton.setLayoutY(20);
        pauseButton.setLayoutX(700);
        pauseButton.setFocusTraversable(false);


        pauseButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
                    Button etm = new Button("Save and Exit");
                    Button resumeGame = new Button("Resume game");

                    resumeGame.setLayoutX(350);
                    resumeGame.setLayoutY(275);
                    resumeGame.setMaxSize(150,250);
                    resumeGame.setFocusTraversable(false);

                    etm.setLayoutX(350);
                    etm.setLayoutY(375);
                    etm.setMaxSize(150,250);
                    etm.setFocusTraversable(false);

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

                    resumeGame.setOnAction(new EventHandler<ActionEvent>(){
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                game(pms);
                            }
                            catch(Exception e){

                            }
                        }
                    });
                    Group root = new Group();
                    root.getChildren().add(etm);
                    root.getChildren().add(resumeGame);
                    Scene s = new Scene(root, 800,800);
                    s.setFill(Color.rgb(41,41,41));
                    pms.setScene(s);
                    pms.show();
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
        Obstacle obs6 = new PlusObstacle(250, 300, 190, 30, true);
        player = new Player(400, 750, 15, null);

        player.setColor(colorCode.get(rand.nextInt(4)));
        root = new Group();
        sub = new Group();
        root.getChildren().add(player.getIcon());
//        obs.quickSetup(sub, 6000, bindings, player, true);
        obs2.quickSetup(sub, 6000, bindings, player, true);
        obs3.quickSetup(sub, 6000, bindings, player, true);
        obs4.quickSetup(sub, 6000, bindings, player, true);
        obs5.quickSetup(sub, 6000, bindings, player, true);
        obs6.quickSetup(sub, 6000, bindings, player, true);


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
