package obstacles;

import collectable.ColorChanger;
import collectable.Star;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import mainPackage.Main;
import menu.Blurrable;
import playerinfo.Data;
import playerinfo.Player;

import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;


public abstract class Obstacle implements Pauseable, Blurrable {
    public static boolean alreadyOver;
    private int centreX, centreY;
    private Timeline timeline;
    private Player player;
    private ColorChanger colorChanger;
    private Star star;
    private Translate initialTranslate;
    private double initialTransformState;
    private ArrayList<String> colorCode;
    private ArrayList<Color> convertColorCode;
    private boolean changerPresent, starPresent;
    private ArrayList<Obstacle> obstacles, obstaclesOrderList;
    private String savePath, saveSlot, saveUser;
    private ArrayList<Pauseable> pauseables;
    private Group root;
    private Stage stage;
    private Timeline rootTimeline;
    private Scene scene;
    public Obstacle(int centreX, int centreY){
        this.centreX = centreX;
        this.centreY = centreY;
        colorChanger = new ColorChanger();
        star = new Star();
        convertColorCode = new ArrayList<Color>();
        convertColorCode.add(Color.CYAN);
        convertColorCode.add(Color.PURPLE);
        convertColorCode.add(Color.YELLOW);
        convertColorCode.add(Color.rgb(250, 22, 151));
    }
    abstract public void initBindings(ArrayList<BooleanBinding> bindings, Player player);
    abstract public void quickSetup(Group root, int duration, ArrayList<BooleanBinding> bindings, Player player, boolean showCollectables, boolean isShifted, boolean showChanger, boolean showStar);
    abstract public void showOnNode(Group root);
    abstract public void setColorChanger();
    abstract public void setYTranslate(double y);
    abstract public double getSpecialValue();
    abstract public void setSpecialValue(double value);
    public void setCollectables(int changerCentreX, int changerCentreY,
                                int starCentreX, int starCentreY,
                                Player player, ArrayList<BooleanBinding> bindings, Group root,
                                boolean showChanger, boolean showStar){
        if(isChangerPresent()) {
            getColorChanger().setCollectable(changerCentreX, changerCentreY, root, bindings, player, this);
            getColorChanger().getChanger().getTransforms().add(getInitialTranslate());
            changerPresent = true;
        }
        if(isStarPresent()) {
            getStar().setCollectable(starCentreX, starCentreY, root, bindings, player, this);
            getStar().getStar().getTransforms().add(getInitialTranslate());
            getStar().initBindings(bindings, player, 0);
            starPresent = true;
        }
    }
    public int getColorCode(Color color){
        int code = -1;
        int idx = 0;
        for(Color c: convertColorCode){
            if(c.equals(color)){
                code = idx;
                break;
            }
            idx++;
        }
        return code;
    }
    public void collision() {
        try {
            if(alreadyOver){
                return;
            }
            alreadyOver = true;
            getStage().hide();
            for(Pauseable pauseable: pauseables){
                pauseable.pause();
            }

            Stage pauseStage = new Stage();
//            enableBlur(true);
            Button saveGame = new Button( "SAVE");
            Button resumeGame = new Button("RESUME USING STARS");
            Button exitGame = new Button("EXIT");
            resumeGame.setLayoutX(245);
            resumeGame.setLayoutY(140);
            resumeGame.setFocusTraversable(false);
            resumeGame.setPrefWidth(320);
            resumeGame.setPrefHeight(80);
            resumeGame.setFont(Font.font("Sans Serif",20));
            resumeGame.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;"+"-fx-border-width: 2;");
            resumeGame.setOnMouseEntered(e -> resumeGame.setStyle("-fx-background-color: #FF4500;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")) ;
            resumeGame.setOnMouseExited(e -> resumeGame.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")); ;

            saveGame.setLayoutX(245);
            saveGame.setLayoutY(250);
            saveGame.setFocusTraversable(false);
            saveGame.setPrefWidth(320);
            saveGame.setPrefHeight(80);
            saveGame.setFont(Font.font("Sans Serif",20));
            saveGame.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;"+"-fx-border-width: 2;");
            saveGame.setOnMouseEntered(e -> saveGame.setStyle("-fx-background-color: #FF4500;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")) ;
            saveGame.setOnMouseExited(e -> saveGame.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")); ;

            exitGame.setLayoutX(245);
            exitGame.setLayoutY(360);
            exitGame.setFocusTraversable(false);
            exitGame.setPrefWidth(320);
            exitGame.setPrefHeight(80);
            exitGame.setFont(Font.font("Sans Serif",20));
            exitGame.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;"+"-fx-border-width: 2;");
            exitGame.setOnMouseEntered(e -> exitGame.setStyle("-fx-background-color: #FF4500;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")) ;
            exitGame.setOnMouseExited(e -> exitGame.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: #FFFFFF;-fx-border-color: #FFFFFF;-fx-border-width: 2;")); ;

            getPlayer().getTimeline().stop();
            getPlayer().getTimeline().getKeyFrames().clear();
            getPlayer().setFall(false);

            Group tempRoot = new Group();
            tempRoot.getChildren().add(exitGame);
            tempRoot.getChildren().add(resumeGame);
            tempRoot.getChildren().add(saveGame);
            Scene s = new Scene(tempRoot, 800,800);
            s.setFill(Color.rgb(57, 54, 54));
//            s.setFill(Color.TRANSPARENT);
            pauseStage.setScene(s);

            pauseStage.show();



            resumeGame.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                    /*
                    Replace this part with alert
                     */
                    if(Main.numberOfStars < 10){
                        System.out.println("Not enough stars");
                        // Uncomment this when alert is made
//                        return;
                    }
                    else{
                        Main.numberOfStars -= 10;
                    }
                    getPlayer().stop();
                    getPlayer().getTimeline().getKeyFrames().clear();
                    pauseables.get(0).stop();
                    rootTimeline.getKeyFrames().clear();
                    getPlayer().getIcon().setCenterY(getPlayer().getIcon().getCenterY() + 300);
                    for(Pauseable pauseable: pauseables){
                        if(pauseable != getPlayer() && pauseable != pauseables.get(0)){
                            pauseable.start();
                        }
                    }
                    getStage().show();
                    pauseStage.hide();
                    alreadyOver = false;
                }
            });

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public void enableBlur(Boolean blurringEffect){
        ColorAdjust adj;
        GaussianBlur blur;
        if(blurringEffect) {
            adj = new ColorAdjust(0, -0.05, -0.2, 0);
            blur = new GaussianBlur(2000);
            adj.setInput(blur);
            getPlayer().getRoot().setEffect(adj);

        }
        else{
            blur = new GaussianBlur(0);
            adj = new ColorAdjust();
            adj.setInput(blur);
            getPlayer().getRoot().setEffect(null);
        }
    }
    public void setPlayer(Player player){
        this.player = player;
    }
    public Player getPlayer(){
        return player;
    }
    public void setTimeline(Timeline timeline){
        this.timeline = timeline;
    }
    public Timeline getTimeline(){
        return timeline;
    }
    public void setColorCode(ArrayList<String> colorCode){
        this.colorCode = colorCode;
    }
    public ArrayList<String> getColorCode(){
        return colorCode;
    }
    public void setCentreX(int centreX){
        this.centreX = centreX;
    }
    public int getCentreX(){
        return centreX;
    }
    public void setCentreY(int centreY){
        this.centreY = centreY;
    }
    public int getCentreY(){
        return centreY;
    }
    public ColorChanger getColorChanger(){
        return colorChanger;
    }
    public void setStar(Star star){
        this.star = star;
    }
    public Star getStar(){
        return star;
    }
    public Translate getInitialTranslate(){
        return initialTranslate;
    }
    public void setInitialTranslate(Translate initialTranslate){
        this.initialTranslate = initialTranslate;
    }
    public double getInitialTransformState() {
        return initialTransformState;
    }

    public void setInitialTransformState(double initialTransformState) {
        this.initialTransformState = initialTransformState;
    }

    public boolean isStarPresent() {
        return starPresent;
    }

    public void setStarPresent(boolean starPresent) {
        this.starPresent = starPresent;
    }

    public boolean isChangerPresent() {
        return changerPresent;
    }

    public void setChangerPresent(boolean changerPresent) {
        this.changerPresent = changerPresent;
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    public void setObstacles(ArrayList<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }

    public ArrayList<Pauseable> getPauseables() {
        return pauseables;
    }

    public void setPauseables(ArrayList<Pauseable> pauseables) {
        this.pauseables = pauseables;
    }
    public Group getRoot(){
        return root;
    }
    public void setRoot(Group root) {
        this.root = root;
    }

    public ArrayList<Obstacle> getObstaclesOrderList() {
        return obstaclesOrderList;
    }

    public void setObstaclesOrderList(ArrayList<Obstacle> obstaclesOrderList) {
        this.obstaclesOrderList = obstaclesOrderList;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getSaveSlot() {
        return saveSlot;
    }

    public void setSaveSlot(String saveSlot) {
        this.saveSlot = saveSlot;
    }

    public String getSaveUser() {
        return saveUser;
    }

    public void setSaveUser(String saveUser) {
        this.saveUser = saveUser;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Timeline getRootTimeline() {
        return rootTimeline;
    }

    public void setRootTimeline(Timeline rootTimeline) {
        this.rootTimeline = rootTimeline;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }


}
