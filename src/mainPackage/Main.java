package mainPackage;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
import menu.AccountMenu;
import menu.Blurrable;
import menu.CrashMenu;
import menu.MainMenu;
import obstacles.*;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.Callable;
import playerinfo.*;


public class Main extends Application implements Pauseable, Blurrable {
    public static int numberOfStars, obstacleShiftCounter, obstacleShiftCounterValue;
    private String savePath, saveSlot, username, finalPath;
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
    private Timeline timeline;
    private double obsYTranslate;
    private BooleanBinding starsBinding;
    private Translate translateSub;
    private Scene scene;
    private ArrayList<Integer> saveObstacles, saveDuration;
    private ArrayList<Double> saveInitialTransform, saveInitialTranslates;
    private ArrayList<Boolean> saveChangerStatus, saveStarStatus;
    private ArrayList<Obstacle> obstaclesOrderList;
    private Data loadData;
    private boolean isLoaded;
    private Text starText;
    private Stage stage;
    private boolean changedLoaded;
    public static Media musicSound;
    public static AudioClip starSound, changerSound, jumpSound;
    public static MediaPlayer musicPlayer;
    private Text pauseText;
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
    public void game (Stage gameStage, boolean loaded, String slot, String username) throws Exception{

        timeline = new Timeline();
        stage = gameStage;
        this.username = username;
        this.isLoaded = loaded;
        savePath = "ColorSwitchData/";
        saveSlot = slot;
        finalPath = savePath + username + "/" + slot;
        changedLoaded = false;

        Image img = new Image(new FileInputStream("images/pause.png"),73,73,true,true);
        ImageView view = new ImageView(img);
        view.preserveRatioProperty();
        Image img2 = new Image(new FileInputStream("images/saveButton.png"),73,73,true,true);
        ImageView view2 = new ImageView(img2);
        view2.preserveRatioProperty();
        Button pauseButton = new Button();
        pauseButton.setGraphic(view);
        pauseButton.setShape(new Circle(15));
        pauseButton.setContentDisplay(ContentDisplay.CENTER);
        pauseButton.setMaxSize(15,15);
        pauseButton.setLayoutY(12);
        pauseButton.setLayoutX(710);
        pauseButton.setFocusTraversable(false);
        Button saveButton = new Button();
        saveButton.setGraphic(view2);
        saveButton.setShape(new Circle(15));
        saveButton.setContentDisplay(ContentDisplay.CENTER);
        saveButton.setMaxSize(15,15);
        saveButton.setLayoutY(92);
        saveButton.setLayoutX(710);
        saveButton.setFocusTraversable(false);
        ScaleTransition st = new ScaleTransition(Duration.millis(200),pauseButton);
        st.setToX(0.9);
        st.setToY(0.9);
        ScaleTransition st1 = new ScaleTransition(Duration.millis(200),pauseButton);
        st1.setToX(1);
        st1.setToY(1);
        pauseButton.setStyle(
                "-fx-background-radius: 500em; " +
                        "-fx-min-width: 65px; " +
                        "-fx-min-height: 65px; " +
                        "-fx-max-width: 65px; " +
                        "-fx-max-height: 65px;"+
                        "-fx-background-color: -fx-shadow-highlight-color;"
        );
        pauseButton.setOnMouseEntered(e -> pauseButton.setStyle("-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;"+
                "-fx-background-radius: 500em; " +
                "-fx-min-width: 65px; " +
                "-fx-min-height: 65px; " +
                "-fx-max-width: 65px; " +
                "-fx-max-height: 65px;"));
        pauseButton.setOnMouseEntered(e -> st.play());

        pauseButton.setOnMouseExited(e -> pauseButton.setStyle("-fx-background-color: -fx-shadow-highlight-color;"+
                "-fx-background-radius: 500em; " +
                "-fx-min-width: 65px; " +
                "-fx-min-height: 65px; " +
                "-fx-max-width: 65px; " +
                "-fx-max-height: 65px;"));
        pauseButton.setOnMouseExited(e -> st1.play());

        ScaleTransition st2 = new ScaleTransition(Duration.millis(200),saveButton);
        st2.setToX(0.9);
        st2.setToY(0.9);
        ScaleTransition st3 = new ScaleTransition(Duration.millis(200),saveButton);
        st3.setToX(1);
        st3.setToY(1);
        saveButton.setStyle(
                "-fx-background-radius: 500em; " +
                        "-fx-min-width: 65px; " +
                        "-fx-min-height: 65px; " +
                        "-fx-max-width: 65px; " +
                        "-fx-max-height: 65px;"+
                        "-fx-background-color: -fx-shadow-highlight-color;"
        );
        saveButton.setOnMouseEntered(e -> saveButton.setStyle("-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;"+
                "-fx-background-radius: 500em; " +
                "-fx-min-width: 65px; " +
                "-fx-min-height: 65px; " +
                "-fx-max-width: 65px; " +
                "-fx-max-height: 65px;"));
        saveButton.setOnMouseEntered(e -> st2.play());

        saveButton.setOnMouseExited(e -> saveButton.setStyle("-fx-background-color: -fx-shadow-highlight-color;"+
                "-fx-background-radius: 500em; " +
                "-fx-min-width: 65px; " +
                "-fx-min-height: 65px; " +
                "-fx-max-width: 65px; " +
                "-fx-max-height: 65px;"));
        saveButton.setOnMouseExited(e -> st3.play());



        obstaclesOrderList = new ArrayList<Obstacle>();
        obstacles = new ArrayList<Obstacle>();
        pauseables = new ArrayList<Pauseable>();
        createObstaclesOrderList();


        pauseButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try {
                    pauseButton.setVisible(false);
                    Stage pauseStage = new Stage();
                    enableBlur(true);
                    pauseText = new Text("PAUSE");
                    pauseText.setFont(Font.font("Sans Serif",45));
                    pauseText.setX(328);
                    pauseText.setY(125);
                    pauseText.setFill(Color.WHITE);
                    Button saveGame = new Button("SAVE");
                    Button resumeGame = new Button("RESUME GAME");
                    Button exitGame = new Button("Exit");
                    resumeGame.setLayoutX(300);
                    resumeGame.setLayoutY(325);
                    resumeGame.setPrefWidth(200);
                    resumeGame.setPrefHeight(60);
                    resumeGame.setFocusTraversable(false);
                    resumeGame.setFont(Font.font("Sans Serif",20));
                    resumeGame.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;"+"-fx-border-width: 2;");
                    resumeGame.setOnMouseEntered(e -> resumeGame.setStyle("-fx-background-color: green;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")) ;
                    resumeGame.setOnMouseExited(e -> resumeGame.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")); ;
                    buttonTransition(resumeGame,true);
                    saveGame.setLayoutX(300);
                    saveGame.setLayoutY(415);
                    saveGame.setPrefWidth(200);
                    saveGame.setPrefHeight(60);
                    saveGame.setMaxSize(250,350);
                    saveGame.setFocusTraversable(false);
                    saveGame.setFont(Font.font("Sans Serif",20));
                    saveGame.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;"+"-fx-border-width: 2;");
                    saveGame.setOnMouseEntered(e -> saveGame.setStyle("-fx-background-color: #FF4500;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")) ;
                    saveGame.setOnMouseExited(e -> saveGame.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")); ;
                    buttonTransition(saveGame,false);
                    exitGame.setLayoutX(300);
                    exitGame.setLayoutY(505);
                    exitGame.setPrefWidth(200);
                    exitGame.setPrefHeight(60);
                    exitGame.setMaxSize(250,350);
                    exitGame.setFocusTraversable(false);
                    exitGame.setFont(Font.font("Sans Serif",20));
                    exitGame.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;"+"-fx-border-width: 2;");
                    exitGame.setOnMouseEntered(e -> exitGame.setStyle("-fx-background-color: #FF0000 ;"+"-fx-text-fill: #ffffff;-fx-border-color: #FFFFFF;-fx-border-width: 2;")) ;
                    exitGame.setOnMouseExited(e -> exitGame.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")); ;
                    buttonTransition(exitGame,true);
                    for(Pauseable pauseable: pauseables){
                        pauseable.pause();
                    }

                    saveGame.setOnAction(new EventHandler<ActionEvent>(){
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                save(finalPath);
                            }
                            catch(Exception e){
                                System.out.println("Could not save");
                            }
                        }
                    });
                    exitGame.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            try {
                                pauseStage.hide();
                                gameStage.hide();
                                startMainMenu(gameStage);
                            }
                            catch (Exception e){
                                System.out.println("Could not start game/ hide pauseStage");
                            }
                        }
                    });

                    resumeGame.setOnAction(new EventHandler<ActionEvent>(){
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                pauseButton.setVisible(true);
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
                    tempRoot.getChildren().add(pauseText);
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
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try{
                    System.out.println("here");
                    save(finalPath);
                }
                catch (Exception e){
                    System.out.println("Could not save");
                }
            }
        });

        bindings = new ArrayList<BooleanBinding>();
        colorCode = new ArrayList<Color>();
        loadData = new Data(new ArrayList<Double>(), new ArrayList<Integer>(), new ArrayList<Boolean>(), new ArrayList<Boolean>(), new ArrayList<Double>(), 0, 400, 750, 0, 0, 0, new ArrayList<Integer>());
        if(isLoaded) {
            try {
                System.out.println(finalPath);
                FileInputStream inputStream = new FileInputStream(finalPath);
                ObjectInputStream in = new ObjectInputStream(inputStream);
                System.out.println("working");
                loadData = (Data) in.readObject();
                in.close();
                inputStream.close();
            } catch (IOException i) {
                System.out.println("IO");
                i.printStackTrace();
                isLoaded = false;
                changedLoaded = true;
            } catch (ClassNotFoundException c) {
                System.out.println("ClassNotFound");
                c.printStackTrace();
                changedLoaded = true;
            }
        }
        if(loadData.getInitialTranslates().size() != 0)obsYTranslate = loadData.getInitialTranslates().get(0);
        numberOfStars = loadData.getNumberOfStars();
        obstacleShiftCounter = loadData.getObstacleShiftCounter();
        starText = new Text(20, 60, Integer.toString(numberOfStars));
        starText.setFont(Font.font("Roboto", 60));
        starText.setFill(Color.WHITE);
        Timeline updateStarCountOnScreen = new Timeline(new KeyFrame(Duration.millis(10), (ActionEvent e) -> {
            starText.setText(Integer.toString(numberOfStars));
        }));
        updateStarCountOnScreen.setCycleCount(Timeline.INDEFINITE);
        updateStarCountOnScreen.play();

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
        root.getChildren().add(saveButton);
        root.getChildren().add(starText);

        player.setRoot(root);

        scene = new Scene(root, 800, 800);

        scene.setFill(Color.rgb(57, 54, 54));

        gameStage.setTitle("Bindings");
        gameStage.setScene(scene);
        gameStage.show();
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
        Timeline crashTimeline = new Timeline();
        crashTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(100), ActionEvent -> {
            if(player.getIcon().getCenterY() > 800){
                obstacles.get(0).collision(790);
            }
        }));
        crashTimeline.setCycleCount(Timeline.INDEFINITE);
        crashTimeline.play();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            String musicPath = "audio/music/StayInsideMe.wav";
            String starSoundPath = "audio/sound/star.wav";
            String changerSoundPath = "audio/sound/colorswitch.wav";
            String jumpSoundPath = "audio/sound/jump.wav";
            musicSound = new Media(new File(musicPath).toURI().toString());
            starSound = new AudioClip(new File(starSoundPath).toURI().toString());
            changerSound = new AudioClip(new File(changerSoundPath).toURI().toString());
            jumpSound = new AudioClip(new File(jumpSoundPath).toURI().toString());

            musicPlayer = new MediaPlayer(musicSound);
//            jumpPlayer = new MediaPlayer(jumpSound);
            musicPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    musicPlayer.seek(Duration.ZERO);
                }
            });
            musicPlayer.play();
        } catch (Exception e){
            System.out.println("Media error");
        }
        AccountMenu accountMenu = new AccountMenu();
        accountMenu.start(primaryStage);
    }

    void handlePlayerMovement(){
//        jumpPlayer.seek(Duration.ZERO);
//        jumpPlayer.play();
        jumpSound.play();
        double temp = player.getIcon().getCenterY();
        Interpolator interpolator = new Interpolator() {
            @Override
            protected double curve(double v) {
                return v*(2 - v);
            }
        };
        scene.setFill(Color.rgb(57, 54, 54));
        if(temp - 120 >= 300) {
            player.setFall(true);
            player.getTimeline().stop();
            player.getTimeline().getKeyFrames().clear();
            player.getTimeline().setCycleCount(1);
            player.getTimeline().getKeyFrames().addAll(new KeyFrame(Duration.millis(300), new KeyValue(player.getIcon().centerYProperty(), temp - 120, interpolator)));
            player.getTimeline().play();
            player.getTimeline().setOnFinished(actionEvent -> {
                player.getTimeline().stop();
                player.getTimeline().getKeyFrames().clear();
                if(player.isFall()) {
                    player.getTimeline().setCycleCount(Timeline.INDEFINITE);
                    player.setSpeed(0);
                    player.getTimeline().getKeyFrames().add(new KeyFrame(Duration.millis(5), ActionEvent -> {
                        player.fall();
                    }));
                    player.getTimeline().play();
                }
            });
        }
        else{
            player.setFall(true);
            player.getTimeline().stop();
            player.getTimeline().getKeyFrames().clear();
            player.getTimeline().setCycleCount(Timeline.INDEFINITE);
            player.setSpeed(0);
            player.getTimeline().getKeyFrames().add(new KeyFrame(Duration.millis(5), ActionEvent -> {
                player.fall();
            }));
            timeline.stop();
            timeline.getKeyFrames().clear();
            timeline.setCycleCount(1);
            temp = sub.getLayoutY();
            timeline.getKeyFrames().addAll(new KeyFrame(Duration.millis(300), new KeyValue(sub.layoutYProperty(), temp + 120, interpolator)));
            timeline.play();
            if(player.isFall()) {
                timeline.setOnFinished(e -> player.getTimeline().play());
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    @Override
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
        if(changedLoaded){
            for(Obstacle obs: obstaclesOrderList){
                obstacles.add(obs);
            }
            return;
        }
        if(isLoaded && loadData.isSaved()){
            ArrayList<Integer> indices = loadData.getIndices();
            ArrayList<Double> transformState = loadData.getInitialTransforms();
            ArrayList<Double> translateY = loadData.getInitialTranslates();
            ArrayList<Boolean> changerStatus = loadData.getChangerStatus();
            ArrayList<Boolean> starStatus = loadData.getStarStatus();
            ArrayList<Integer> duration = loadData.getDuration();
            System.out.println(indices);
            System.out.println(transformState);
            System.out.println(translateY);
            sub.setLayoutY(loadData.getSubLayoutY());
            for(int i = 0; i < indices.size(); i++){
                obstacles.add(obstaclesOrderList.get(indices.get(i)));
                obstacles.get(obstacles.size() - 1).setSpecialValue(transformState.get(i));
                obstacles.get(obstacles.size() - 1).setChangerPresent(changerStatus.get(i));
                obstacles.get(obstacles.size() - 1).setStarPresent(starStatus.get(i));
                obstacles.get(obstacles.size() - 1).setDuration(duration.get(i));
            }
        }


    }
    public void addPauseables(){
        /*
        This function adds all obstacles, their
        color changers and player to pauseables
        arraylist.
         */
        pauseables.add(this);
        for(Obstacle obs: obstacles){
            pauseables.add(obs);
            pauseables.add(obs.getColorChanger());
        }
        pauseables.add(player);
    }
    public void quickSetupAllObstacles(Group sub){
        /*
        Only sets up obstacles that are added in addObstacles function
         */
        for(Obstacle obs: obstacles){
            if(isLoaded){
                obs.quickSetup(sub, obs.getDuration(), bindings, player, true, false, obs.isChangerPresent(), obs.isStarPresent());
            }
            else {
                obs.setChangerPresent(true);
                obs.setStarPresent(true);
                obs.quickSetup(sub, 5000, bindings, player, true, false, true, true);
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
            obs.quickSetup(sub, Math.max(obs.getDuration() - 1000, 1000), bindings, player, true, true, true, true);
            obsYTranslate -= 800;
        }
        for(Obstacle obs: obstaclesOrderList){
            System.out.println(obs);
        }
    }
    public void createObstaclesOrderList(){
        obstaclesOrderList.add(new RingObstacle(400, 300, 190, 30, true, new Translate(), 0));
        obstaclesOrderList.add(new PlusObstacle(250, 300, 190, 30, false, new Translate()));
        obstaclesOrderList.add(new LineObstacle(800, 200, 30,  true, new Translate(), 0));
        obstaclesOrderList.add(new SquareObstacle(400, 300, 120, 30, true , new Translate()));
        obstaclesOrderList.add(new ConcentricRingObstacle(400, 300, 190, 30, 3, false, true, new Translate(), 45));
        obstaclesOrderList.add(new TangentialRingObstacle(400, 300, 150, 150, 25, true, new Translate(), 45));
        obstaclesOrderList.add(new ConcentricRingObstacle(400, 300, 190, 30, 2, true, true, new Translate(), 45));
        obstaclesOrderList.add(new TangentialRingObstacle(400, 300, 150, 130, 25, true, new Translate(), 45));
        obstaclesOrderList.add(new SquareObstacle(400, 300, 120, 30, true , new Translate()));
        obstaclesOrderList.add(new ConcentricRingObstacle(400, 300, 240, 30, 4, false, true, new Translate(), 45));


//        obstaclesOrderList.add(new TangentialRingObstacle(400, 300, 120, 120, 30, true, new Translate(), 45));
//        obstaclesOrderList.add(new RingObstacle(400, 300, 190, 30, true, new Translate(), 0));
//        obstaclesOrderList.add(new PlusObstacle(250, 300, 190, 30, false, new Translate()));
//        obstaclesOrderList.add(new LineObstacle(800, 200, 30,  true, new Translate(), 0));
//        obstaclesOrderList.add(new SquareObstacle(400, 300, 120, 20, true , new Translate()));
//        obstaclesOrderList.add(new ConcentricRingObstacle(400, 300, 190, 30, 3, false, true, new Translate(), 45));
//        obstaclesOrderList.add(new SquareObstacle(400, 300, 120, 30, true , new Translate()));
//        obstaclesOrderList.add(new ConcentricRingObstacle(400, 300, 190, 30, 2, true, true, new Translate(), 45));
//        obstaclesOrderList.add(new TangentialRingObstacle(400, 300, 120, 120, 30, true, new Translate(), 45));
        for(Obstacle obs: obstaclesOrderList){
            obs.setObstacles(obstacles);
            obs.setPauseables(pauseables);
            obs.setObstaclesOrderList(obstaclesOrderList);
            obs.setStage(stage);
            obs.setRootTimeline(timeline);
            obs.setSaveUser(username);
            obs.setSaveSlot(saveSlot);
            obs.setSavePath(savePath);
        }
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

    public void save(String finalPath){
        saveObstacles = new ArrayList<Integer>();
        saveInitialTransform = new ArrayList<Double>();
        saveInitialTranslates = new ArrayList<Double>();
        saveChangerStatus = new ArrayList<Boolean>();
        saveStarStatus = new ArrayList<Boolean>();
        saveDuration = new ArrayList<Integer>();
        for(Obstacle obs: obstacles){
            saveObstacles.add(obstaclesOrderList.indexOf(obs));
            saveInitialTransform.add(obs.getSpecialValue());
            saveInitialTranslates.add(obs.getInitialTranslate().getY());
            saveChangerStatus.add(obs.isChangerPresent());
            saveStarStatus.add(obs.isStarPresent());
            saveDuration.add(obs.getDuration());
        }
        System.out.println(saveObstacles);
        System.out.println(saveInitialTransform);
        System.out.println(saveInitialTranslates);
        Data saveData = new Data(saveInitialTranslates, saveObstacles, saveChangerStatus, saveStarStatus, saveInitialTransform, (int)sub.getLayoutY(), (int)player.getIcon().getCenterX(), (int)player.getIcon().getCenterY(), getColorCode(player.getColor()), numberOfStars, obstacleShiftCounter, saveDuration);
        try{
            FileOutputStream outputStream = new FileOutputStream(finalPath);
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            out.writeObject(saveData);
            out.close();
            outputStream.close();
            System.out.println(finalPath);
            System.out.println("Data serialized");
        }
        catch (Exception e) {
            e.printStackTrace();
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
    public void startMainMenu(Stage stage){
        try {
            MainMenu mainMenu = new MainMenu();
            mainMenu.startMainMenu(stage, username);
        }
        catch (Exception e){

        }
    }
}