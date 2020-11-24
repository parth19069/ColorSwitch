package mainPackage;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;
import javafx.stage.*;
import collectable.Star;
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

public class Main extends Application{
    private ArrayList<BooleanBinding> bindings;
    private Player player;
    private ArrayList<Color> colorCode;
    private Random rand;
    private BooleanBinding scrollBinding;
    private ArrayList<Obstacle> obstacles;
    private int offset;
    private Group root;
    private Group sub;
    BoxBlur blur = new BoxBlur();


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
                    Stage pms2 = new Stage();
                    enableBlur(true);
                    Button etm = new Button("Save and Exit");
                    Button resumeGame = new Button("Resume game");
                    Obstacle obs = new ConcentricRingObstacle(400, 400, 150, 22, 2, true,true ,new Translate());
                    resumeGame.setLayoutX(350);
                    resumeGame.setLayoutY(345);
                    resumeGame.setMaxSize(150,250);
                    resumeGame.setFocusTraversable(false);
                    etm.setLayoutX(350);
                    etm.setLayoutY(415);
                    etm.setMaxSize(150,250);
                    etm.setFocusTraversable(false);

                    etm.setOnAction(new EventHandler<ActionEvent>(){
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                pms2.hide();
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
                                enableBlur(false);
                                pms2.hide();
                                pms.show();
                            }
                            catch(Exception e){

                            }
                        }
                    });
                    Group tempRoot = new Group();
                    obs.quickSetup(tempRoot, 6000,bindings,player, false);
                    tempRoot.getChildren().add(etm);
                    tempRoot.getChildren().add(resumeGame);
                    Scene s = new Scene(tempRoot, 800,800);
                    s.setFill(Color.rgb(41,41,41));
                    s.setFill(Color.TRANSPARENT);
                    pms2.initStyle(StageStyle.TRANSPARENT);
                    pms2.setScene(s);

                    pms2.show();

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
        Obstacle obs = new RingObstacle(400, 300, 190, 30, true, new Translate());
        Obstacle obs2 = new RingObstacle(400, -600, 190, 30, false, new Translate());
        Obstacle obs3 = new RingObstacle(400, -1300, 190, 30, false, new Translate());
        Obstacle obs4 = new ConcentricRingObstacle(400, -2100, 290, 30, 2, true, true, new Translate());
        Obstacle obs5 = new TangentialRingObstacle(400, -2900, 120, 120, 30, true, new Translate());
        Obstacle obs6 = new SquareObstacle(400, 300, 90, 20, true , new Translate());
//        LineObstacle obs7 = new LineObstacle(800, 400, 30,  true);
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
//        obs7.quickSetup(sub,6000,bindings,player,true);

        root.getChildren().add(sub);
        root.getChildren().add(pauseButton);
//        root.setEffect(blur);
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
    public void enableBlur(Boolean blurringEffect){
        ColorAdjust adj;
        GaussianBlur blur;
        if(blurringEffect) {
            adj = new ColorAdjust(0, -0.05, -0.2, 0);
            blur = new GaussianBlur(150);
            adj.setInput(blur);
            root.setEffect(adj);

        }
        else{
            blur = new GaussianBlur(0);
            adj = new ColorAdjust();
            adj.setInput(blur);
            root.setEffect(null);
        }
    }

}
