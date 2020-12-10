package obstacles;

import javafx.beans.binding.BooleanBinding;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.transform.Translate;
import playerinfo.Player;

import java.util.ArrayList;

public class TangentialRingObstacle extends Obstacle {
    private RingObstacle ring1, ring2;
    public TangentialRingObstacle(int centreX, int centreY, int radius1, int radius2, int thickness, boolean clockwise, Translate initialTranslate, int initialTransformState){
        super(centreX, centreY);
        ring1 = new RingObstacle(centreX - thickness - radius1, centreY, radius1, thickness, clockwise, initialTranslate, initialTransformState);
        ring2 = new RingObstacle(centreX + thickness + radius2, centreY, radius2, thickness, !clockwise, initialTranslate, initialTransformState);
        setInitialTranslate(initialTranslate);
    }
    public void start(){
        ring1.start();
        ring2.start();
    }
    public void stop(){
        ring1.stop();
        ring2.stop();
    }
    public void pause(){
        ring1.pause();
        ring2.pause();
    }
    public void setYTranslate(double y){
        ring1.setYTranslate(y);
        ring2.setYTranslate(y);
    }
    public void showOnNode(Group root){
        setRoot(root);
        ring1.showOnNode(root);
        ring2.showOnNode(root);
    }
    public void setColorChanger(){
        getColorChanger().setColors(ring1.getColors(0), ring1.getColors(1), ring1.getColors(2), ring1.getColors(3));
    }
    @Override
    public double getSpecialValue(){
        return ring1.getSpecialValue();
    }
    @Override
    public void setSpecialValue(double angle){
        ring1.setSpecialValue(angle);
        ring2.setSpecialValue(90-angle);
    }
    public void quickSetup(Group root, int duration, ArrayList<BooleanBinding> bindings, Player player, boolean showCollectables, boolean isShifted, boolean showChanger, boolean showStar){
        if(!isShifted) {
            showOnNode(root);
            ring1.makeRotation(duration);
            ring1.setColors(Color.CYAN, Color.PURPLE, Color.YELLOW, Color.rgb(250, 22, 151));

            ring2.makeRotation(duration);
            ring2.setColors(Color.CYAN, Color.rgb(250, 22, 151), Color.YELLOW, Color.PURPLE);
        }
        if(showCollectables) {
            setColorChanger();
            setCollectables(getCentreX(), getCentreY() + Math.max(ring1.getRadius(), ring2.getRadius()) + 250, getCentreX(), getCentreY() + Math.max(ring1.getRadius(), ring2.getRadius()) + 100, player, bindings, root, showChanger, showStar);
        }

        if(!isShifted){
            initBindings(bindings, player);
            start();
        }
    }
    public void initBindings(ArrayList<BooleanBinding> bindings, Player player){
        ring1.initBindings(bindings, player);
        ring2.initBindings(bindings, player);

        ring1.setStage(getStage());
        ring2.setStage(getStage());

        ring1.setPauseables(getPauseables());
        ring2.setPauseables(getPauseables());

        ring1.setObstacles(getObstacles());
        ring2.setObstacles(getObstacles());

        ring1.setRootTimeline(getRootTimeline());
        ring2.setRootTimeline(getRootTimeline());

        ring1.setSaveUser(getSaveUser());
        ring1.setSavePath(getSavePath());
        ring1.setSaveSlot(getSaveSlot());

        ring2.setSaveUser(getSaveUser());
        ring2.setSavePath(getSavePath());
        ring2.setSaveSlot(getSaveSlot());
    }
}
