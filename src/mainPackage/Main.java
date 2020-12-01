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

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.Callable;
import playerinfo.*;


public class Main extends Application implements Pauseable{
    public static int numberOfStars, obstacleShiftCounter, obstacleShiftCounterValue;
    private String savePath, saveSlot, finalPath;
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
    private double obsYTranslate;
    private BooleanBinding starsBinding;
    private Translate translateSub;
    private Scene scene;
    private ArrayList<Integer> saveObstacles;
    private ArrayList<Double> saveInitialTransform, saveInitialTranslates;
    private ArrayList<Boolean> saveChangerStatus, saveStarStatus;
    private ArrayList<Obstacle> obstaclesOrderList;
    private Data loadData;
    private boolean isLoaded;
    public int getColorCode(Color color){
        int code = -1;
        int idx = 0;
        for(Color c: colorCode){
            if(c.equals(color)){
                code = idx;
                break;
            }
            idx++;
        }
        return code;
    }
    public Color getColor(int code){
        return colorCode.get(code);
    }
    public void game (Stage gameStage, boolean loaded, String slot){
        this.isLoaded = loaded;
        savePath = "/home/parth20/Desktop/";
        saveSlot = slot;
        finalPath = savePath + slot;
        Button pauseButton = new Button("Pause");
        pauseButton.setFocusTraversable(false);
        pauseButton.setMaxSize(100,200);
        pauseButton.setLayoutY(20);
        pauseButton.setLayoutX(700);
        pauseButton.setFocusTraversable(false);
        obstaclesOrderList = new ArrayList<Obstacle>();
        obstacles = new ArrayList<Obstacle>();
//        saveObstacles = new ArrayList<Integer>();
//        obstacles = new ArrayList<Obstacle>();
//        saveInitialTransform = new ArrayList<Double>();
//        saveInitialTranslates = new ArrayList<Double>();
//        obstaclesOrderList = new ArrayList<Obstacle>();
//        saveChangerStatus = new ArrayList<Boolean>();
//        saveStarStatus = new ArrayList<Boolean>();
        createObstaclesOrderList();


        pauseButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
                    Stage pauseStage = new Stage();
                    enableBlur(true);
                    Button saveGame = new Button("Save");
                    Button resumeGame = new Button("Resume game");
                    Button exitGame = new Button("Exit");
                    resumeGame.setLayoutX(350);
                    resumeGame.setLayoutY(345);
                    resumeGame.setMaxSize(150,250);
                    resumeGame.setFocusTraversable(false);

                    saveGame.setLayoutX(350);
                    saveGame.setLayoutY(415);
                    saveGame.setMaxSize(150,250);
                    saveGame.setFocusTraversable(false);

                    exitGame.setLayoutX(350);
                    exitGame.setLayoutY(485);
                    exitGame.setMaxSize(150,250);
                    exitGame.setFocusTraversable(false);

                    for(Pauseable pauseable: pauseables){
                        pauseable.pause();
                    }

                    saveGame.setOnAction(new EventHandler<ActionEvent>(){
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                saveObstacles = new ArrayList<Integer>();
                                saveInitialTransform = new ArrayList<Double>();
                                saveInitialTranslates = new ArrayList<Double>();
                                saveChangerStatus = new ArrayList<Boolean>();
                                saveStarStatus = new ArrayList<Boolean>();
                                for(Obstacle obs: obstacles){
                                    saveObstacles.add(obstaclesOrderList.indexOf(obs));
                                    saveInitialTransform.add(obs.getSpecialValue());
                                    saveInitialTranslates.add(obs.getInitialTranslate().getY());
                                    saveChangerStatus.add(obs.isChangerPresent());
                                    saveStarStatus.add(obs.isStarPresent());
                                }
                                System.out.println(saveObstacles);
                                System.out.println(saveInitialTransform);
                                System.out.println(saveInitialTranslates);
                                Data saveData = new Data(saveInitialTranslates, saveObstacles, saveChangerStatus, saveStarStatus, saveInitialTransform, (int)sub.getLayoutY(), (int)player.getIcon().getCenterX(), (int)player.getIcon().getCenterY(), getColorCode(player.getColor()), numberOfStars, obstacleShiftCounter);
                                try{
                                    FileOutputStream outputStream = new FileOutputStream(finalPath);
                                    ObjectOutputStream out = new ObjectOutputStream(outputStream);
                                    out.writeObject(saveData);
                                    out.close();
                                    outputStream.close();
                                    System.out.println("Data serialized");
                                }
                                catch (Exception e) {
//                                    throw e;
                                }
                            }
                            catch(Exception e){

                            }
                        }
                    });
                    exitGame.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            try {
                                pauseStage.hide();
                                start(gameStage);
                            }
                            catch (Exception e){

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
                    tempRoot.getChildren().add(exitGame);
                    tempRoot.getChildren().add(resumeGame);
                    tempRoot.getChildren().add(saveGame);
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
        loadData = new Data(new ArrayList<Double>(), new ArrayList<Integer>(), new ArrayList<Boolean>(), new ArrayList<Boolean>(), new ArrayList<Double>(), 0, 400, 750, 0, 0, 0);
        if(isLoaded) {
            try {
                FileInputStream inputStream = new FileInputStream(finalPath);
                ObjectInputStream in = new ObjectInputStream(inputStream);
                System.out.println("working");
                loadData = (Data) in.readObject();
                in.close();
                inputStream.close();
            } catch (IOException i) {
                System.out.println("IO");
                i.printStackTrace();
            } catch (ClassNotFoundException c) {
                System.out.println("ClassNotFound");
                c.printStackTrace();
            }
        }
        if(loadData.getInitialTranslates().size() != 0)obsYTranslate = loadData.getInitialTranslates().get(0);
        numberOfStars = loadData.getNumberOfStars();
        obstacleShiftCounter = loadData.getObstacleShiftCounter();
        System.out.println("After loading: " + obsYTranslate);
        rand = new Random();
        translateSub = new Translate();
        offset = 0;
        obstacleShiftCounterValue = 4;
        colorCode.add(Color.CYAN);
        colorCode.add(Color.PURPLE);
        colorCode.add(Color.YELLOW);
        colorCode.add(Color.rgb(250, 22, 151));
        player = new Player(loadData.getPlayerX(), loadData.getPlayerY(), 15, null);
//        int si = pauseables.size();
//        for(int i = 0; i < si; i++){
//            Obstacle ob = (Obstacle)pauseables.get(i);
//            pauseables.add(ob.getColorChanger());
//        }
//        pauseables.add(player);
//        pauseables.add(this);

        player.setColor(getColor(loadData.getPlayerColor()));
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
        System.out.println(root + " " + sub);

        scene = new Scene(root, 800, 1000);

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
                    System.out.println("obsYtrans = " + obsYTranslate);
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
//        player = new Player(400, 900, 15, null);
//        player.setColor(Color.CYAN);
        if(isLoaded && loadData.isSaved()){
            ArrayList<Integer> indices = loadData.getIndices();
            ArrayList<Double> transformState = loadData.getInitialTransforms();
            ArrayList<Double> translateY = loadData.getInitialTranslates();
            ArrayList<Boolean> changerStatus = loadData.getChangerStatus();
            ArrayList<Boolean> starStatus = loadData.getStarStatus();
            System.out.println(indices);
            System.out.println(transformState);
            System.out.println(translateY);
            sub.setLayoutY(loadData.getSubLayoutY());
            for(int i = 0; i < indices.size(); i++){
                obstacles.add(obstaclesOrderList.get(indices.get(i)));
                obstacles.get(obstacles.size() - 1).setSpecialValue(transformState.get(i));
                obstacles.get(obstacles.size() - 1).setChangerPresent(changerStatus.get(i));
                obstacles.get(obstacles.size() - 1).setStarPresent(starStatus.get(i));
//                obstacles.get(obstacles.size() - 1).setYTranslate(translateY.get(i));
            }
        }


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
        pauseables.add(this);
    }
    public void quickSetupAllObstacles(Group sub){
        /*
        Only sets up obstacles that are added in addObstacles function
         */
        for(Obstacle obs: obstacles){
            if(isLoaded)obs.quickSetup(sub, 8000, bindings, player, true, false, obs.isChangerPresent(), obs.isStarPresent());
            else {
                obs.setChangerPresent(true);
                obs.setStarPresent(true);
                obs.quickSetup(sub, 8000, bindings, player, true, false, true, true);
            }
            obs.setYTranslate(obsYTranslate);
            obsYTranslate -= 800;
        }
    }
    public void shiftObstacles(Group sub){
        ArrayList<Obstacle> shiftedObstacles = new ArrayList<Obstacle>();
        for(int i = 0; i < obstacleShiftCounterValue/2; i++){
            shiftedObstacles.add(obstacles.get(i));
        }
        for(Obstacle obs: shiftedObstacles){
            obstacles.remove(obs);
        }
        Collections.shuffle(shiftedObstacles);
        for(Obstacle obs: shiftedObstacles){
            obstacles.add(obs);
            obs.setYTranslate(obsYTranslate);
            obs.setChangerPresent(true);
            obs.setStarPresent(true);
            obs.quickSetup(sub, 6000, bindings, player, true, true, true, true);
            obsYTranslate -= 800;
        }
        System.out.println(obsYTranslate);
    }
    public void createObstaclesOrderList(){
        obstaclesOrderList.add(new RingObstacle(400, 300, 10, 30, true, new Translate(),0));
        obstaclesOrderList.add(new RingObstacle(400, 300, 20, 30, true, new Translate(),0));
        obstaclesOrderList.add(new RingObstacle(400, 300, 30, 30, true, new Translate(),0));
        obstaclesOrderList.add(new RingObstacle(400, 300, 40, 30, true, new Translate(),0));
        obstaclesOrderList.add(new RingObstacle(400, 300, 50, 30, true, new Translate(),0));
        obstaclesOrderList.add(new RingObstacle(400, 300, 60, 30, true, new Translate(),0));
        obstaclesOrderList.add(new RingObstacle(400, 300, 70, 30, true, new Translate(),0));
        obstaclesOrderList.add(new RingObstacle(400, 300, 80, 30, true, new Translate(),0));
        obstaclesOrderList.add(new RingObstacle(400, 300, 90, 30, true, new Translate(),0));
        obstaclesOrderList.add(new RingObstacle(400, 300, 100, 30, true, new Translate(),0));
//        obstaclesOrderList.add(new LineObstacle(800, 300, 30,  true, new Translate(), 0));
//        obstaclesOrderList.add(new TangentialRingObstacle(400, 300, 120, 120, 30, true, new Translate(), 45));
//        obstaclesOrderList.add(new ConcentricRingObstacle(400, 300, 190, 30, 2, true, true, new Translate(), 45));
//        obstaclesOrderList.add(new ConcentricRingObstacle(400, 300, 190, 30, 3, false, true, new Translate(), 45));
//        obstaclesOrderList.add(new PlusObstacle(200, 300, 190, 30, false, new Translate()));
//        obstaclesOrderList.add(new TangentialRingObstacle(400, 300, 120, 120, 30, true, new Translate(), 45));
//        obstaclesOrderList.add(new SquareObstacle(400, 300, 90, 20, true , new Translate()));
//        obstaclesOrderList.add(new RingObstacle(400, 300, 190, 30, true, new Translate(), 0));
//        obstaclesOrderList.add(new SquareObstacle(400, 300, 90, 20, true , new Translate()));
        if(!isLoaded) {
            for (Obstacle obs : obstaclesOrderList) {
                obstacles.add(obs);
            }
        }
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
