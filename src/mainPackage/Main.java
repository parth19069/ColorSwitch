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

import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.Callable;


public class Main extends Application {
    BooleanBinding pos;
    @Override
    public void start(Stage primaryStage) throws Exception{
        RingObstacle obs = new RingObstacle(400, 300, 150, 30, true);
        RingObstacle obs2 = new RingObstacle(400, 300, 100, 30, false);
        Circle player = new Circle(400, 700, 20);
        player.setStrokeWidth(0);
        player.setFill(Color.YELLOW);

//        player.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        Group root = new Group();
        obs.quickSetup(root);
        obs2.quickSetup(root);
        root.getChildren().add(player);
//        root.getChildren().add(player);
        Scene scene = new Scene(root, 800, 800);
        Interpolator interpolator = new Interpolator() {
            @Override
            protected double curve(double v) {
                return v*(2 - v);
            }
        };
        scene.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                final Timeline timeline = new Timeline();
                timeline.setCycleCount(1);
                System.out.println("Pressed");
                double temp = player.getCenterY();
                timeline.getKeyFrames().addAll(new KeyFrame(Duration.millis(300), new KeyValue(player.centerYProperty(),temp - 120, interpolator)), new KeyFrame(Duration.millis((1100 - player.getCenterY())*2), new KeyValue(player.centerYProperty(), 1000)));
                timeline.play();
            }
        });
        scene.setFill(Color.rgb(57, 54, 54));
        primaryStage.setTitle("Bindings");
        primaryStage.setScene(scene);
        primaryStage.show();
        //Some changes
    }
    public static void main(String[] args) {
        System.out.println(Color.RED.getClass().getName());
        launch(args);
    }
}
