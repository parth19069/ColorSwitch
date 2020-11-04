package obstacles;

import javafx.beans.binding.BooleanBinding;
import javafx.scene.Group;
import playerinfo.Player;

import java.util.ArrayList;

public abstract class Obstacle {
    private int centreX, centreY;
    public Obstacle(int centreX, int centreY){
        this.centreX = centreX;
        this.centreY = centreY;
    }
    abstract public void initBindings(ArrayList<BooleanBinding> bindings, Player player);
    abstract public void quickSetup(Group root);
}
