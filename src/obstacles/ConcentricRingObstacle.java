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
    public ConcentricRingObstacle(int centreX, int centreY, int radius, int thickness, int numberOfRings, boolean outerClockwise, boolean alternateRotation, Translate initialTranslate){
        super(centreX, centreY);
        this.numberOfRings = numberOfRings;
        rings = new ArrayList<RingObstacle>();
        int offset = 0;
        for(int i = 0; i < numberOfRings; i++){
            if(alternateRotation) rings.add(new RingObstacle(centreX, centreY, radius - offset, thickness, i % 2 == 0 ? outerClockwise : !outerClockwise, initialTranslate));
            else rings.add(new RingObstacle(centreX, centreY, radius - offset, thickness, outerClockwise, initialTranslate));
            offset += thickness + 5;
        }
        setInitialTranslate(initialTranslate);
    }
    public void initBindings(ArrayList<BooleanBinding> bindings, Player player){
        for(int i = 0; i < numberOfRings; i++){
            rings.get(i).initBindings(bindings, player);
        }
    }
    public void showOnNode(Group root){
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
    public void setYTranslate(int y){
        for(int i = 0; i < numberOfRings; i++){
            rings.get(i).setYTranslate(y);
        }
    }
    public void setColorChanger(){
        getColorChanger().setColors(rings.get(0).getColors(0), rings.get(0).getColors(2));
    }
    @Override
    public void quickSetup(Group root, int duration, ArrayList<BooleanBinding> bindings, Player player, boolean showCollectables, boolean isShifted){
        if(!isShifted) {
            showOnNode(root);
            for (int i = 0; i < numberOfRings; i++) {
                rings.get(i).makeRotation(duration);
                rings.get(i).setColors(Color.CYAN, Color.PURPLE, Color.YELLOW, Color.rgb(250, 22, 151));
            }
        }
        if(showCollectables) {
            setColorChanger();
            getColorChanger().setCollectable(getCentreX(), getCentreY() + rings.get(0).getRadius() + 100, root, bindings, player);
            getColorChanger().getChanger().getTransforms().add(getInitialTranslate());

            getStar().setCollectable(getCentreX(), getCentreY(), root, bindings, player);
            getStar().initBindings(bindings, player, 0);
            getStar().getStar().getTransforms().add(getInitialTranslate());
        }
        if(!isShifted) {
            initBindings(bindings, player);
            start();
        }
    }
}
