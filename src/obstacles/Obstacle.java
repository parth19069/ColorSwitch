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
import javafx.scene.text.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
import mainPackage.Main;
import menu.Blurrable;
import menu.CrashMenu;
import menu.MainMenu;
import playerinfo.Player;

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
    private Text gameOverText;
    private int duration;

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
    @Override
    public String toString(){
        return "Obstacle:" + this.getClass().getName() + " " + getSaveUser();
    }
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
    public void collision(double playerCentreY) {
        CrashMenu crashMenu = new CrashMenu(getStage(), pauseables, getPlayer(), rootTimeline, getSaveSlot(), getSaveUser());
        crashMenu.displayMenu(playerCentreY);
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


    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
