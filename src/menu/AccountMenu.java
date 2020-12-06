package menu;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import obstacles.Pauseable;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;

public class AccountMenu extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{

        Image img = new Image(new FileInputStream("images/username.png"),52,52,true,true);
        ImageView view = new ImageView(img);
        view.preserveRatioProperty();
        view.setLayoutX(275);
        view.setLayoutY(330);
        Image img2 = new Image(new FileInputStream("images/Password.png"),52,52,true,true);
        ImageView view2 = new ImageView(img2);
        view2.preserveRatioProperty();
        view2.setLayoutX(275);
        view2.setLayoutY(410);


        Text mainText = new Text("Sign In / Up");
        mainText.setFont(Font.font("Sans Serif",35));
        mainText.setX(300);
        mainText.setY(125);
        mainText.setFill(Color.WHITE);

        TextField tf = new TextField();
        tf.setFont(Font.font("Sans Serif",13));
        tf.setLayoutX(370);
        tf.setLayoutY(342);
        tf.setPrefWidth(200);
        tf.setPrefHeight(30);
        tf.setFocusTraversable(false);
        tf.setPromptText("Username");



        PasswordField pf = new PasswordField();
        pf.setFont(Font.font("Sans Serif",13));
        pf.setLayoutX(370);
        pf.setLayoutY(422);
        pf.setPrefWidth(200);
        pf.setPrefHeight(30);
        pf.setFocusTraversable(false);
        pf.setPromptText("Password");
        Button goButton = new Button("GO !");
        goButton.setLayoutX(300);
        goButton.setLayoutY(530);
        goButton.setFocusTraversable(false);
        goButton.setFont(Font.font("Sans Serif",20));
        goButton.setPrefWidth(190);
        goButton.setPrefHeight(50);
        goButton.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;"+"-fx-border-width: 2;");
        goButton.setOnMouseEntered(e -> goButton.setStyle("-fx-background-color: green;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")) ;
        goButton.setOnMouseExited(e -> goButton.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")); ;

        ToggleGroup tg = new ToggleGroup();
        ToggleButton rb1 = new ToggleButton("Sign In");
        ToggleButton rb2 = new ToggleButton("Sign Up");
        rb1.setToggleGroup(tg);
        rb2.setToggleGroup(tg);
        rb1.setLayoutX(210);
        rb1.setLayoutY(230);
        rb2.setLayoutX(420);
        rb2.setLayoutY(230);
        rb1.setFocusTraversable(false);
        rb2.setFocusTraversable(false);
        rb1.setFont(Font.font("Sans Serif",15));
        rb2.setFont(Font.font("Sans Serif",15));
        rb1.setPrefWidth(170);
        rb1.setPrefHeight(40);
        rb2.setPrefWidth(170);
        rb2.setPrefHeight(40);
        rb1.setStyle("-fx-background-color: #FF4500;" + "-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;");
        rb1.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {

                if(rb1.isSelected()){
                    rb1.setStyle("-fx-background-color: #FF4500;" + "-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;");

                    rb2.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;");

                }
                else{
                    rb1.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;");
                }

            }
        });
        rb2.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;");
        rb2.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if(rb2.isSelected()) {
                    rb2.setStyle("-fx-background-color: #FF4500;" + "-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;");;
                    rb1.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;");;
                }
                else{
                    rb2.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;");
                    ;
                }
            }
        });
        goButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                primaryStage.hide();
                try {
                    MainMenu mainMenu = new MainMenu();
                    mainMenu.startMainMenu(new Stage());
                }
                catch (Exception e){

                }

            }
        });



        Group accountGroup = new Group();
        accountGroup.getChildren().add(rb1);
        accountGroup.getChildren().add(rb2);
        accountGroup.getChildren().add(tf);
        accountGroup.getChildren().add(pf);
        accountGroup.getChildren().add(goButton);
        accountGroup.getChildren().add(mainText);
        accountGroup.getChildren().add(view);
        accountGroup.getChildren().add(view2);
        Scene s = new Scene(accountGroup,800,800);
        s.setFill(Color.rgb(41,41,41));
        primaryStage.setTitle("Account Menu");
        primaryStage.setScene(s);
        primaryStage.show();






    }

}
