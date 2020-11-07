package collectable;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import obstacles.RingObstacle;
import playerinfo.Player;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Callable;

public class ColorChanger {
    private ArrayList<Color> colors;
    private Circle changer;
    private Random rand;
    private Group root;
    private BooleanBinding binding;
    private ChangeListener<Boolean> listener;
    private Timeline timeline;
    private int colorPtr;
    public ColorChanger(Color ... a){
        colors = new ArrayList<Color>();
        colorPtr = 0;
        rand = new Random();
        for(int i = 0; i < a.length; i++){
            colors.add(a[i]);
        }
        timeline = new Timeline();
    }
    public void setColors(Color ... a){
        for(int i = 0; i < a.length; i++){
            colors.add(a[i]);
        }
    }
    public void setChanger(int centreX, int centreY, Group root, ArrayList<BooleanBinding> bindings, Player player){
        changer = new Circle(centreX, centreY, 30);
        changer.setStrokeWidth(0);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100), e -> {changer.setFill(colors.get(colorPtr)); colorPtr += 1; colorPtr %= colors.size();}));
        timeline.play();
        root.getChildren().add(changer);
        this.root = root;
        initBindings(bindings, player, colors.size());
    }
    public void initBindings(ArrayList<BooleanBinding> bindings, Player player, final int size){
        System.out.println("here");
        bindings.add(Bindings.createBooleanBinding(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Shape intersect = Shape.intersect(changer, player.getIcon());
                return intersect.getBoundsInLocal().getWidth() != -1;
            }
        }, player.getIcon().centerYProperty()));
        binding = bindings.get(bindings.size() - 1);
        listener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(t1){
//                    System.out.println("IN CHANGER");
                    root.getChildren().remove(changer);
                    binding.removeListener(listener);
                    Color color = colors.get(rand.nextInt(size));
                    timeline.stop();
                    while(color == player.getColor()) color = colors.get(rand.nextInt(size));
                    player.setColor(color);
                }
                else{
                    System.out.println("OUT CHANGER");
                    System.out.println(size);
                }
            }
        };
        bindings.get(bindings.size() - 1).addListener(listener);
    }
}
