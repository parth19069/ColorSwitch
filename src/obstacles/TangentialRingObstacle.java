package obstacles;

import javafx.beans.binding.BooleanBinding;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import playerinfo.Player;

import java.util.ArrayList;

public class TangentialRingObstacle extends Obstacle {
    private RingObstacle ring1, ring2;
    public TangentialRingObstacle(int centreX, int centreY, int radius1, int radius2, int thickness, boolean clockwise){
        super(centreX, centreY);
        ring1 = new RingObstacle(centreX - thickness - radius1, centreY, radius1, thickness, clockwise);
        ring2 = new RingObstacle(centreX + thickness + radius2, centreY, radius2, thickness, !clockwise);
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
    public void showOnNode(Group root){
        ring1.showOnNode(root);
        ring2.showOnNode(root);
    }
    public void quickSetup(Group root){
        showOnNode(root);
        ring1.makeRotation(4000);
        ring1.setColors(Color.CYAN, Color.PURPLE, Color.YELLOW, Color.rgb(250, 22, 151));

        ring2.makeRotation(4000);
        ring2.setColors(Color.CYAN, Color.PURPLE, Color.YELLOW, Color.rgb(250, 22, 151));

        start();
    }
    public void initBindings(ArrayList<BooleanBinding> bindings, Player player){
        ring1.initBindings(bindings, player);
        ring2.initBindings(bindings, player);
    }
}