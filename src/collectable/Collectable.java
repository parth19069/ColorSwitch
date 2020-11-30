package collectable;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.scene.Group;
import obstacles.Obstacle;
import playerinfo.Player;

import java.util.ArrayList;

abstract public class Collectable {
    private Group root;
    private BooleanBinding binding;
    private ChangeListener<Boolean> listener;

    abstract public void setCollectable(int centreX, int centreY, Group root, ArrayList<BooleanBinding> bindings, Player player, Obstacle obstacle);
    abstract public void initBindings(ArrayList<BooleanBinding> bindings, Player player, final int size);

    public void setRoot(Group root){
        this.root = root;
    }
    public Group getRoot(){
        return root;
    }
    public void setBinding(BooleanBinding binding){
        this.binding = binding;
    }
    public BooleanBinding getBinding(){
        return binding;
    }

    public ChangeListener<Boolean> getListener() {
        return listener;
    }

    public void setListener(ChangeListener<Boolean> listener) {
        this.listener = listener;
    }
}
