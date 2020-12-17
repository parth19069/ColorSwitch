package menu;

import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import mainPackage.Main;
import obstacles.Obstacle;
import obstacles.Pauseable;
import playerinfo.Player;

import java.util.ArrayList;

public class CrashMenu {
    private Stage stage;
    ArrayList<Pauseable> pauseables;
    private Text gameOverText;
    private Player player;
    private Timeline rootTimeline;
    private String saveSlot, saveUser;

    public CrashMenu(Stage stage, ArrayList<Pauseable> pauseables, Player player, Timeline rootTimeline, String saveSlot, String saveUser){
        this.stage = stage;
        this.pauseables = pauseables;
        this.player = player;
        this.rootTimeline = rootTimeline;
        this.saveSlot = saveSlot;
        this.saveUser = saveUser;
    }
    public void displayMenu(double playerCentreY){
        try {
            if(Obstacle.alreadyOver){
                return;
            }
            Main.deadSound.play();
            Obstacle.alreadyOver = true;
            stage.hide();
            for(Pauseable pauseable: pauseables){
                pauseable.pause();
            }

            Stage pauseStage = new Stage();
//            enableBlur(true);

            gameOverText = new Text("GAME OVER!");
            gameOverText.setFont(Font.font("Sans Serif",45));
            gameOverText.setX(270);
            gameOverText.setY(125);
            gameOverText.setFill(Color.WHITE);
            Button restartGame = new Button( "RESTART");
            Button resumeGame = new Button("RESUME USING 10 STARS");
            Button exitGame = new Button("EXIT");
            resumeGame.setLayoutX(245);
            resumeGame.setLayoutY(200);
            resumeGame.setFocusTraversable(false);
            resumeGame.setPrefWidth(320);
            resumeGame.setPrefHeight(80);
            resumeGame.setFont(Font.font("Sans Serif",20));
            resumeGame.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;"+"-fx-border-width: 2;");
            resumeGame.setOnMouseEntered(e -> resumeGame.setStyle("-fx-background-color: #FF4500;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")) ;
            resumeGame.setOnMouseExited(e -> resumeGame.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")); ;
            buttonTransition(resumeGame,true);
            restartGame.setLayoutX(245);
            restartGame.setLayoutY(310);
            restartGame.setFocusTraversable(false);
            restartGame.setPrefWidth(320);
            restartGame.setPrefHeight(80);
            restartGame.setFont(Font.font("Sans Serif",20));
            restartGame.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;"+"-fx-border-width: 2;");
            restartGame.setOnMouseEntered(e -> restartGame.setStyle("-fx-background-color: #FF4500;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")) ;
            restartGame.setOnMouseExited(e -> restartGame.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")); ;
            buttonTransition(restartGame,false);
            exitGame.setLayoutX(245);
            exitGame.setLayoutY(420);
            exitGame.setFocusTraversable(false);
            exitGame.setPrefWidth(320);
            exitGame.setPrefHeight(80);
            exitGame.setFont(Font.font("Sans Serif",20));
            exitGame.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;"+"-fx-border-width: 2;");
            exitGame.setOnMouseEntered(e -> exitGame.setStyle("-fx-background-color: #FF4500;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")) ;
            exitGame.setOnMouseExited(e -> exitGame.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")); ;
            buttonTransition(exitGame,true);
            player.getTimeline().stop();
            player.getTimeline().getKeyFrames().clear();
            player.setFall(false);

            Group tempRoot = new Group();
            tempRoot.getChildren().add(exitGame);
            tempRoot.getChildren().add(resumeGame);
            tempRoot.getChildren().add(restartGame);
            tempRoot.getChildren().add(gameOverText);
            Scene s = new Scene(tempRoot, 800,800);
            s.setFill(Color.rgb(57, 54, 54));
//            s.setFill(Color.TRANSPARENT);
            pauseStage.setScene(s);
            pauseStage.show();

            resumeGame.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    if(Main.numberOfStars < 10){
                        Alert a = new Alert(Alert.AlertType.INFORMATION,"Not enough stars");
                        a.show();
                        return;
                    }
                    else{
                        Main.numberOfStars -= 10;
                    }
                    player.stop();
                    player.getTimeline().getKeyFrames().clear();
                    pauseables.get(0).stop();
                    rootTimeline.getKeyFrames().clear();
                    player.getIcon().setCenterY(playerCentreY);
                    for(Pauseable pauseable: pauseables){
                        if(pauseable != player && pauseable != pauseables.get(0)){
                            pauseable.start();
                        }
                    }
                    stage.show();
                    pauseStage.hide();
                    Obstacle.alreadyOver = false;
                }
            });
            restartGame.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    // Add to total stars collected here
                    pauseStage.hide();
                    Main main = new Main();
                    Obstacle.alreadyOver = false;
                    player.getIcon().setCenterY(-100000);
                    try{
                        main.game(new Stage(), false, saveSlot, saveUser);
                    } catch (Exception e){
                        System.out.println("Restart error");
//                        e.printStackTrace();
                    }
                }
            });
            exitGame.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    // Add to total stars collected here
                    pauseStage.hide();
                    MainMenu mainMenu = new MainMenu();
                    Obstacle.alreadyOver = false;
                    player.getIcon().setCenterY(-100000);
                    System.out.println("----" + saveUser + "----");
                    try{
                        mainMenu.startMainMenu(new Stage(), saveUser);
                    } catch (Exception e){
                        System.out.println("Exit game error");
//                        e.printStackTrace();
                    }
                }
            });

        }
        catch(Exception e){
            System.out.println("Unexpected error");
//            e.printStackTrace();
        }
    }
    public void buttonTransition(Button b, Boolean leftToRight){
        TranslateTransition t = new TranslateTransition();
        t.setDuration(Duration.millis(400));
        t.setNode(b);
        if(leftToRight) {
            t.setFromX(-801);
            t.setFromY(0);
            t.setToX(0);
            t.setToY(0);
            t.play();
        }
        else{
            t.setFromX(801);
            t.setFromY(0);
            t.setToX(0);
            t.setToY(0);
            t.play();
        }

    }
}
