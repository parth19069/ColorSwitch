package obstacles;

import javafx.beans.binding.BooleanBinding;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.transform.Translate;
import playerinfo.Player;

import java.util.ArrayList;

public class ConcentricRingObstacle extends Obstacle {
    private ArrayList<RingObstacle> rings;
    private int numberOfRings;
    public ConcentricRingObstacle(int centreX, int centreY, int radius, int thickness, int numberOfRings, boolean outerClockwise, boolean alternateRotation, Translate initialTranslate, int initialTransformState){
        super(centreX, centreY);
        this.numberOfRings = numberOfRings;
        rings = new ArrayList<RingObstacle>();
        int offset = 0;
        for(int i = 0; i < numberOfRings; i++){
            if(alternateRotation) rings.add(new RingObstacle(centreX, centreY, radius - offset, thickness, i % 2 == 0 ? outerClockwise : !outerClockwise, initialTranslate, initialTransformState));
            else rings.add(new RingObstacle(centreX, centreY, radius - offset, thickness, outerClockwise, initialTranslate, initialTransformState));
            offset += thickness + 5;
        }
        setInitialTransformState(initialTransformState);
        setInitialTranslate(initialTranslate);
    }
    @Override
    public double getSpecialValue(){
        return rings.get(0).getSpecialValue();
    }
    @Override
    public void setSpecialValue(double angle){
        for(int i = 0; i < numberOfRings; i++){
            if(rings.get(i).getRotationDirection())rings.get(i).setSpecialValue(angle);
            else rings.get(i).setSpecialValue(90 - angle);
        }
    }
    public void initBindings(ArrayList<BooleanBinding> bindings, Player player){
        for(int i = 0; i < numberOfRings; i++){
            rings.get(i).initBindings(bindings, player);
            rings.get(i).setStage(getStage());
            rings.get(i).setPauseables(getPauseables());
            rings.get(i).setObstacles(getObstacles());
            rings.get(i).setRootTimeline(getRootTimeline());

            rings.get(i).setSaveUser(getSaveUser());
            rings.get(i).setSavePath(getSavePath());
            rings.get(i).setSaveSlot(getSaveSlot());
        }
    }
    public void showOnNode(Group root){
        setRoot(root);
        for(int i = 0; i < numberOfRings; i++){
            rings.get(i).showOnNode(root);
        }
    }
    public void start(){
        for(int i = 0; i < numberOfRings; i++){
            rings.get(i).start();
        }
    }
    public void stop(){
        for(int i = 0; i < numberOfRings; i++){
            rings.get(i).stop();
        }
    }
    public void pause(){
        for(int i = 0; i < numberOfRings; i++){
            rings.get(i).pause();
        }
    }
    public void setYTranslate(double y){
        for(int i = 0; i < numberOfRings; i++){
            rings.get(i).setYTranslate(y);
        }
    }
    public void setColorChanger(){
        getColorChanger().setColors(rings.get(0).getColors(0), rings.get(0).getColors(2));
    }
    @Override
    public void quickSetup(Group root, int duration, ArrayList<BooleanBinding> bindings, Player player, boolean showCollectables, boolean isShifted, boolean showChanger, boolean showStar){
        if(!isShifted) {
            showOnNode(root);
        }
        for (int i = 0; i < numberOfRings; i++) {
            rings.get(i).makeRotation(duration);
            rings.get(i).setColors(Color.CYAN, Color.PURPLE, Color.YELLOW, Color.rgb(250, 22, 151));
            rings.get(i).setDuration(duration);
        }
        setDuration(duration);
        if(showCollectables) {
            setColorChanger();
            setCollectables(getCentreX(), getCentreY() + rings.get(0).getRadius() + 100, getCentreX(), getCentreY(), player, bindings, root, showChanger, showStar);
        }
        if(!isShifted) {
            initBindings(bindings, player);
            start();
        }
    }
}
