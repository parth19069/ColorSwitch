package obstacles;

import javafx.animation.Timeline;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.Group;
import playerinfo.Player;

import java.util.ArrayList;

public abstract class Obstacle implements Pauseable {
    private int centreX, centreY;
    private Timeline timeline;
    private Player player;
//    private ArrayList<BooleanBinding> collision;
    private ArrayList<String> colorCode;
    public Obstacle(int centreX, int centreY){
        this.centreX = centreX;
        this.centreY = centreY;
    }
    abstract public void initBindings(ArrayList<BooleanBinding> bindings, Player player);
    abstract public void quickSetup(Group root, int duration);
    abstract public void showOnNode(Group root);
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
}
