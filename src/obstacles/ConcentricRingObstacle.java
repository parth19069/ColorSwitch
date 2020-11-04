package obstacles;

import javafx.beans.binding.BooleanBinding;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import playerinfo.Player;

import java.util.ArrayList;

public class ConcentricRingObstacle extends Obstacle {
    private ArrayList<RingObstacle> rings;
    private int numberOfRings;
    public ConcentricRingObstacle(int centreX, int centreY, int radius, int thickness, int numberOfRings, boolean outerClockwise, boolean alternateRotation){
        super(centreX, centreY);
        this.numberOfRings = numberOfRings;
        rings = new ArrayList<RingObstacle>();
        int offset = 0;
        for(int i = 0; i < numberOfRings; i++){
            if(alternateRotation) rings.add(new RingObstacle(centreX, centreY, radius - offset, thickness, i % 2 == 0 ? outerClockwise : !outerClockwise));
            else rings.add(new RingObstacle(centreX, centreY, radius - offset, thickness, outerClockwise));
            offset += thickness + 5;
        }
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
    public void quickSetup(Group root, int duration){
        showOnNode(root);
        for(int i = 0; i < numberOfRings; i++){
            rings.get(i).makeRotation(duration);
            rings.get(i).setColors(Color.CYAN, Color.PURPLE, Color.YELLOW, Color.rgb(250, 22, 151));
        }
        start();
    }
}
