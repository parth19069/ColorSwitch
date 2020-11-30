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
    private double initialTransformState;
    private ArrayList<String> colorCode;
    private boolean changerPresent, starPresent;
    public Obstacle(int centreX, int centreY){
        this.centreX = centreX;
        this.centreY = centreY;
        colorChanger = new ColorChanger();
        star = new Star();
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
}
