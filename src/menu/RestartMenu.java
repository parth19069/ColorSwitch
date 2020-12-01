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

public class RestartMenu extends Application {

    private Group restartMenuGroup;
    private Button restartWithStars, exit;


    @Override
    public void start(Stage restartMenuStage) throws Exception {
        restartWithStars = new Button("Restart With Stars");
        restartWithStars.setLayoutX(360);
        restartWithStars.setLayoutY(245);
        restartWithStars.setMaxSize(150,250);
        restartWithStars.setFocusTraversable(false);
        exit = new Button("Exit");
        exit.setLayoutX(360);
        exit.setLayoutY(345);
        exit.setMaxSize(150,250);
        exit.setFocusTraversable(false);
        restartWithStars.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });

        restartMenuGroup = new Group();
        restartMenuGroup.getChildren().add(restartWithStars);
        restartMenuGroup.getChildren().add(exit);
        Scene restartMenuScene = new Scene(restartMenuGroup,800,800);
        restartMenuScene.setFill(Color.rgb(41,41,41));
        restartMenuStage.setTitle("Restart Menu");
        restartMenuStage.setScene(restartMenuScene);
        restartMenuStage.show();

    }
}
