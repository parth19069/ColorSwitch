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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.util.Duration;
import obstacles.RingObstacle;

import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.Callable;


public class Main extends Application {
    BooleanBinding pos;
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Bindings");
        RingObstacle obs = new RingObstacle(400, 300, 100, 30);
        Group root = new Group();
        obs.showOnNode(root);
        obs.makeRotation(3000);
        obs.setColors(Color.CYAN, Color.PURPLE, Color.YELLOW, Color.rgb(250, 22, 151));
        obs.startRotation();
        Scene scene = new Scene(root, 800, 800);
        scene.setFill(Color.rgb(57, 54, 54));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        System.out.println(Color.RED.getClass().getName());
        launch(args);
    }
}
