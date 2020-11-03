package mainPackage;

import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
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
import javafx.scene.control.Button;
import javafx.util.Duration;
import obstacles.*;

import java.security.Key;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Callable;
import playerinfo.*;


public class Main extends Application {
    ArrayList<BooleanBinding> bindings;
    Player player;
    ArrayList<String> colorCode;
    Random rand;
    BooleanBinding scrollBinding;
    int offset;
    @Override
    public void start(Stage primaryStage) throws Exception{

        /*
        Initialisations of instance variables
         */
        bindings = new ArrayList<BooleanBinding>();
        colorCode = new ArrayList<String>();
        rand = new Random();
        offset = 0;

        /*
        Obstacle and player declarations
         */
        RingObstacle obs = new RingObstacle(400, 300, 190, 30, true);
        player = new Player(400, 750, 20, 0);
        obs.initBindings(bindings, player);
        player.setColor(rand.nextInt(4));

        /*
        Root and sub-root declarations
         */
        Group root = new Group();
        Group sub = new Group();
        root.getChildren().add(player.getIcon());
        obs.quickSetup(sub);
        root.getChildren().add(sub);
        Scene scene = new Scene(root, 800, 800);

        scene.setFill(Color.rgb(57, 54, 54));
        primaryStage.setTitle("Bindings");
        primaryStage.setScene(scene);
        primaryStage.show();

        /*
        Event handling
         */
        Interpolator interpolator = new Interpolator() {
            @Override
            protected double curve(double v) {
                return v*(2 - v);
            }
        };
        final Timeline timeline = new Timeline();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                timeline.stop();
                timeline.getKeyFrames().clear();
                timeline.setCycleCount(1);
                double temp = player.getIcon().getCenterY();
                timeline.getKeyFrames().addAll(new KeyFrame(Duration.millis(300), new KeyValue(player.getIcon().centerYProperty(),temp - 120, interpolator)), new KeyFrame(Duration.millis((1100 - player.getIcon().getCenterY())*3), new KeyValue(player.getIcon().centerYProperty(), 1000, Interpolator.LINEAR)));
//                timeline.getKeyFrames().addAll(new KeyFrame(Duration.millis(300), new KeyValue(player.getIcon().centerYProperty(),temp - 20, interpolator)));
                timeline.play();
            }
        });
        scrollBinding = Bindings.createBooleanBinding(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return player.getIcon().getCenterY() <= 300 - offset;
            }
        }, player.getIcon().centerYProperty());
        Timeline timeline2 = new Timeline();
//        scrollBinding.addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
//                if(t1){
//                    timeline2.stop();
//                    timeline2.getKeyFrames().clear();
//                    timeline2.setCycleCount(1);
//                    offset += 70;
//                    timeline2.getKeyFrames().add(new KeyFrame(Duration.millis(100), new KeyValue(root.layoutYProperty(), root.getLayoutY() + 70)));
//                    timeline2.play();
//                }
//            }
//        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}