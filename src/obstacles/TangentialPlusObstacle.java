package obstacles;

import javafx.beans.binding.BooleanBinding;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.transform.Translate;
import playerinfo.Player;

import java.util.ArrayList;

public class TangentialPlusObstacle extends Obstacle {
    private PlusObstacle plus1, plus2;
    public TangentialPlusObstacle(int centreX, int centreY, int radius1, int radius2, int thickness, boolean clockwise, Translate initialTranslate){
        super(centreX, centreY);
        plus1 = new PlusObstacle(centreX - radius1 - thickness/2, centreY, radius1, thickness, clockwise, initialTranslate);
        plus2 = new PlusObstacle(centreX + radius2 + thickness/2, centreY, radius2, thickness, !clockwise, initialTranslate);
        setInitialTranslate(initialTranslate);
    }
    public void start(){
        plus1.start();
        plus2.start();
    }
    public void stop(){
        plus1.stop();
        plus2.stop();
    }
    public void pause(){
        plus1.pause();
        plus2.pause();
    }
    public void setYTranslate(double y){
        plus1.setYTranslate(y);
        plus2.setYTranslate(y);
    }
    public void showOnNode(Group root){
        setRoot(root);
        plus1.showOnNode(root);
        plus2.showOnNode(root);
    }
    public void setColorChanger(){
        getColorChanger().setColors(plus1.getColors(0), plus1.getColors(1), plus1.getColors(2), plus1.getColors(3));
    }
    @Override
    public double getSpecialValue(){
        return plus1.getSpecialValue();
    }
    @Override
    public void setSpecialValue(double angle){
        plus1.setSpecialValue(angle);
        plus2.setSpecialValue(90-angle);
    }
    public void quickSetup(Group root, int duration, ArrayList<BooleanBinding> bindings, Player player, boolean showCollectables, boolean isShifted, boolean showChanger, boolean showStar){
        if(!isShifted) {
            showOnNode(root);
        }
        plus1.makeRotation(duration);
        plus1.setColors(Color.CYAN, Color.PURPLE, Color.YELLOW, Color.rgb(250, 22, 151));

        plus2.makeRotation(duration);
        plus2.setColors(Color.YELLOW, Color.PURPLE, Color.CYAN, Color.rgb(250, 22, 151));
        plus1.setDuration(duration);
        plus2.setDuration(duration);
        setDuration(duration);
        if(showCollectables) {
            setColorChanger();
            setCollectables(getCentreX(), getCentreY() + Math.max(plus1.getRadius(), plus2.getRadius()) + 200, getCentreX(), getCentreY() + Math.max(plus1.getRadius(), plus2.getRadius()) - 100, player, bindings, root, showChanger, showStar);
        }

        if(!isShifted){
            initBindings(bindings, player);
            start();
        }
    }
    public void initBindings(ArrayList<BooleanBinding> bindings, Player player){
        setPlayer(player);
        plus1.initBindings(bindings, player);
        plus2.initBindings(bindings, player);

        plus1.setStage(getStage());
        plus2.setStage(getStage());

        plus1.setPauseables(getPauseables());
        plus2.setPauseables(getPauseables());

        plus1.setObstacles(getObstacles());
        plus2.setObstacles(getObstacles());

        plus1.setRootTimeline(getRootTimeline());
        plus2.setRootTimeline(getRootTimeline());

        plus1.setSaveUser(getSaveUser());
        plus1.setSavePath(getSavePath());
        plus1.setSaveSlot(getSaveSlot());

        plus2.setSaveUser(getSaveUser());
        plus2.setSavePath(getSavePath());
        plus2.setSaveSlot(getSaveSlot());
    }
}
