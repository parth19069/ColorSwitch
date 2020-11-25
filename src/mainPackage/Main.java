package mainPackage;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.animation.*;
import javafx.application.Application;
import javafx.beans.binding.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Translate;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import menu.MainMenu;
import obstacles.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.Callable;

import playerinfo.*;

public class Main extends Application implements Pauseable{
    public static int numberOfStars, obstacleShiftCounter, obstacleShiftCounterValue;
    private ArrayList<BooleanBinding> bindings;
    private Player player;
    private ArrayList<Color> colorCode;
    private Random rand;
    private BooleanBinding scrollBinding;
    private ArrayList<Obstacle> obstacles;
    private int offset;
    private Group root;
    private Group sub;
    private ArrayList<Pauseable> pauseables;
//    private BoxBlur blur;
    private Timeline timeline;
    private int obsYTranslate;
    private BooleanBinding starsBinding;
    private Translate translateSub;
    private Scene scene;

    public void game (Stage gameStage){
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
                    Stage pauseStage = new Stage();
                    enableBlur(true);
                    Button exitToMain = new Button("Save and Exit");
                    Button resumeGame = new Button("Resume game");
                    resumeGame.setLayoutX(350);
                    resumeGame.setLayoutY(345);
                    resumeGame.setMaxSize(150,250);
                    resumeGame.setFocusTraversable(false);
                    exitToMain.setLayoutX(350);
                    exitToMain.setLayoutY(415);
                    exitToMain.setMaxSize(150,250);
                    exitToMain.setFocusTraversable(false);
                    for(Pauseable pauseable: pauseables){
                        pauseable.pause();
                    }

                    exitToMain.setOnAction(new EventHandler<ActionEvent>(){
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                pauseStage.hide();
                                start(gameStage);
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
                                for(Pauseable pauseable: pauseables){
                                    pauseable.start();
                                }
                                pauseStage.hide();
                                gameStage.show();
                            }
                            catch(Exception e){

                            }
                        }
                    });
                    Group tempRoot = new Group();
                    tempRoot.getChildren().add(exitToMain);
                    tempRoot.getChildren().add(resumeGame);
                    Scene s = new Scene(tempRoot, 800,800);
                    s.setFill(Color.rgb(41,41,41));
                    s.setFill(Color.TRANSPARENT);
                    pauseStage.initStyle(StageStyle.TRANSPARENT);
                    pauseStage.setScene(s);

                    pauseStage.show();

                }
                catch(Exception e){

                }

            }
        });
        bindings = new ArrayList<BooleanBinding>();
        pauseables = new ArrayList<Pauseable>();
        colorCode = new ArrayList<Color>();
        obstacles = new ArrayList<Obstacle>();
        rand = new Random();
        translateSub = new Translate();
        offset = 0;
        obsYTranslate = 0;
        numberOfStars = 0;
        obstacleShiftCounterValue = 4;
        obstacleShiftCounter = 0;
        colorCode.add(Color.CYAN);
        colorCode.add(Color.PURPLE);
        colorCode.add(Color.YELLOW);
        colorCode.add(Color.rgb(250, 22, 151));
        player = new Player(400, 750, 15, null);
        int si = pauseables.size();
        for(int i = 0; i < si; i++){
            Obstacle ob = (Obstacle)pauseables.get(i);
            pauseables.add(ob.getColorChanger());
        }
        pauseables.add(player);
        pauseables.add(this);

        player.setColor(colorCode.get(rand.nextInt(4)));
        root = new Group();
        sub = new Group();
        sub.getTransforms().add(translateSub);

        /*
        Add all obstacles, set them up
        and finally create pauseables list
        Note that obstacles arraylist will be modified
        but that doesn't affect working of pauseables
         */
        addObstacles();
        quickSetupAllObstacles(sub);
        addPauseables();

        root.getChildren().add(player.getIcon());
        root.getChildren().add(sub);
        root.getChildren().add(pauseButton);

        scene = new Scene(root, 800, 800);

        scene.setFill(Color.rgb(57, 54, 54));

        gameStage.setTitle("Bindings");
        gameStage.setScene(scene);
        gameStage.show();
        timeline = new Timeline();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                handlePlayerMovement();
            }
        });
        starsBinding = Bindings.createBooleanBinding(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return obstacleShiftCounter == obstacleShiftCounterValue;
            }
        }, player.getIcon().centerYProperty(), sub.layoutYProperty());
        starsBinding.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(t1){
                    for(Obstacle obs: obstacles){
                        System.out.println(obs.getInitialTranslate().getY());
                    }
                    System.out.println("shift = " + obstacleShiftCounter);
                    shiftObstacles(sub);
                    obstacleShiftCounter = obstacleShiftCounterValue/2;
                }
            }
        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        MainMenu mainmenu = new MainMenu();
        mainmenu.start(primaryStage);

    }

    void handlePlayerMovement(){
        double temp = player.getIcon().getCenterY();
        Interpolator interpolator = new Interpolator() {
            @Override
            protected double curve(double v) {
                return v*(2 - v);
            }
        };
        scene.setFill(Color.rgb(57, 54, 54));
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
            temp = sub.getLayoutY();
            timeline.getKeyFrames().addAll(new KeyFrame(Duration.millis(300), new KeyValue(sub.layoutYProperty(), temp + 120, interpolator)));
            timeline.play();
            timeline.setOnFinished(e -> player.getTimeline().play());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    public void enableBlur(Boolean blurringEffect){
        ColorAdjust adj;
        GaussianBlur blur;
        if(blurringEffect) {
            adj = new ColorAdjust(0, -0.05, -0.2, 0);
            blur = new GaussianBlur(20);
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
    public void addObstacles(){
        /*
        Add any new obstacle here. Everything else is taken care of.
         */
        obstacles.add(new PlusObstacle(200, 300, 190, 30, false, new Translate()));
        obstacles.add(new LineObstacle(800, 300, 30,  true, new Translate()));
        obstacles.add(new RingObstacle(400, 300, 190, 30, true, new Translate()));
        obstacles.add(new ConcentricRingObstacle(400, 300, 290, 30, 2, true, true, new Translate()));
        obstacles.add(new TangentialRingObstacle(400, 300, 120, 120, 30, true, new Translate()));
        obstacles.add(new SquareObstacle(400, 300, 90, 20, true , new Translate()));
//        obstacles.add(new RingObstacle(400, 300, 190, 30, true, new Translate()));
//        obstacles.add(new ConcentricRingObstacle(400, 300, 290, 30, 2, true, true, new Translate()));
//        obstacles.add(new TangentialRingObstacle(400, 300, 120, 120, 30, true, new Translate()));
//        obstacles.add(new SquareObstacle(400, 300, 90, 20, true , new Translate()));
    }
    public void addPauseables(){
        /*
        This function adds all obstacles, their
        color changers and player to pauseables
        arraylist.
         */
        for(Obstacle obs: obstacles){
            pauseables.add(obs);
            pauseables.add(obs.getColorChanger());
        }
        pauseables.add(player);
    }
    public void quickSetupAllObstacles(Group root){
        /*
        Only sets up obstacles that are added in addObstacles function
         */
        for(Obstacle obs: obstacles){
            obs.quickSetup(root, 6000, bindings, player, true, false);
            obs.setYTranslate(obsYTranslate);
            obsYTranslate -= 800;
        }
    }
    public void shiftObstacles(Group root){
        ArrayList<Obstacle> shiftedObstacles = new ArrayList<Obstacle>();
        for(int i = 0; i < obstacleShiftCounterValue/2; i++){
            shiftedObstacles.add(obstacles.get(i));
        }
        for(Obstacle obs: shiftedObstacles){
            obstacles.remove(obs);
        }
        for(Obstacle obs: shiftedObstacles){
            obs.setYTranslate(obsYTranslate);
        }
        Collections.shuffle(shiftedObstacles);
        for(Obstacle obs: shiftedObstacles){
            obstacles.add(obs);
            obs.setYTranslate(obsYTranslate);
            obs.quickSetup(root, 6000, bindings, player, true, true);
            obsYTranslate -= 800;
        }
        System.out.println(obsYTranslate);
    }
    public void start(){
        timeline.play();
    }
    public void pause(){
        timeline.pause();
    }
    public void stop(){
        timeline.stop();
    }
}
