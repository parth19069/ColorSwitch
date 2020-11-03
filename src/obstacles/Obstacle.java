package obstacles;

import javafx.beans.binding.BooleanBinding;
import playerinfo.Player;

import java.util.ArrayList;

abstract class Obstacle {
    abstract public void initBindings(ArrayList<BooleanBinding> bindings, Player player);
}
