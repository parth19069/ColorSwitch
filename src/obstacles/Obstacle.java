package obstacles;

import collectable.ColorChanger;
import collectable.Star;
import javafx.animation.Timeline;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.Group;
import javafx.scene.transform.Translate;
import playerinfo.Player;

import java.util.ArrayList;

public abstract class Obstacle implements Pauseable {
    private int centreX, centreY;
    private Timeline timeline;
    private Player player;
    private ColorChanger colorChanger;
    private Star star;
    private Translate initialTranslate;
    private int lowerBound, upperBound;
//    private ArrayList<BooleanBinding> collision;
    private ArrayList<String> colorCode;
    public Obstacle(int centreX, int centreY){
        this.centreX = centreX;
        this.centreY = centreY;
        colorChanger = new ColorChanger();
        star = new Star();
    }
    abstract public void initBindings(ArrayList<BooleanBinding> bindings, Player player);
    abstract public void quickSetup(Group root, int duration, ArrayList<BooleanBinding> bindings, Player player, boolean showCollectables, boolean isShifted);
    abstract public void showOnNode(Group root);
    abstract public void setColorChanger();
    abstract public void setYTranslate(int y);
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
    public void setLowerBound(int lowerBound){
        this.lowerBound = lowerBound;
    }
    public int getLowerBound(){
        return lowerBound;
    }
    public void setUpperBound(int upperBound){
        this.upperBound = upperBound;
    }
    public int getUpperBound(){
        return upperBound;
    }
}
