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
import obstacles.Obstacle;
import obstacles.Pauseable;
import obstacles.RingObstacle;
import playerinfo.Player;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Callable;

public class ColorChanger extends Collectable implements Pauseable {
    private ArrayList<Color> colors;
    private Circle changer;
    private Random rand;
    private Timeline timeline;
    private int colorPtr;
    private int rvalue, gvalue, bvalue, x, y, z;
    private Obstacle obstacle;
    public ColorChanger(Color ... a){
        colors = new ArrayList<Color>();
        colorPtr = 0;
        rvalue = gvalue = bvalue = 120;
        x = 1; y = 2; z = 3;
        rand = new Random();
        for(int i = 0; i < a.length; i++){
            colors.add(a[i]);
        }
        timeline = new Timeline();
        changer = null;
    }
    public void setColors(Color ... a){
        for(int i = 0; i < a.length; i++){
            colors.add(a[i]);
        }
    }
    public Circle getChanger(){
        return changer;
    }
    @Override
    public void setCollectable(int centreX, int centreY, Group root, ArrayList<BooleanBinding> bindings, Player player, Obstacle obstacle){
        Circle tempCircle = changer;
        if(changer != null) root.getChildren().remove(changer);
        changer = new Circle(centreX, centreY, 25);
        changer.setStrokeWidth(0);
        timeline.stop();
        timeline.getKeyFrames().clear();
        timeline.setCycleCount(Timeline.INDEFINITE);
        this.obstacle = obstacle;
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(5), e -> {
            if(rvalue + x > 255 || rvalue + x < 120) x *= -1;
            if(gvalue + y > 255 || gvalue + y < 120) y *= -1;
            if(bvalue + z > 255 || bvalue + z < 120) z *= -1;
            rvalue += x;
            gvalue += y;
            bvalue += z;
            changer.setFill(Color.rgb(rvalue, gvalue, bvalue));
        }));
        timeline.play();
        root.getChildren().add(changer);
        setRoot(root);
        initBindings(bindings, player, colors.size());
    }
    public void initBindings(ArrayList<BooleanBinding> bindings, Player player, final int size){
        bindings.add(Bindings.createBooleanBinding(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Shape intersect = Shape.intersect(changer, player.getIcon());
                return intersect.getBoundsInLocal().getWidth() != -1;
            }
        }, player.getIcon().centerYProperty(), getRoot().layoutYProperty()));
        setBinding(bindings.get(bindings.size() - 1));
        setListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(t1){
                    boolean debug = getRoot().getChildren().remove(changer);
                    System.out.println("DEBUG: " + debug);
                    getBinding().removeListener(getListener());
                    Color color = colors.get(rand.nextInt(size));
                    timeline.stop();
                    obstacle.setChangerPresent(false);
                    while(color.equals(player.getColor())) color = colors.get(rand.nextInt(size));
                    player.setColor(color);
                }
                else{
                    System.out.println("OUT CHANGER");
                    System.out.println(size);
                }
            }
        });
        bindings.get(bindings.size() - 1).addListener(getListener());
    }
    public void start(){
        timeline.play();
    }
    public void stop(){
        timeline.stop();
    }
    public void pause(){
        timeline.pause();
    }
}
