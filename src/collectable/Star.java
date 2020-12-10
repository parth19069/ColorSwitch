package collectable;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import mainPackage.Main;
import obstacles.Obstacle;
import playerinfo.Player;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class Star extends Collectable{
    private Polygon star;
    private Obstacle obstacle;
    public Star(){
        star = null;
    }
    @Override
    public void setCollectable(int centreX, int centreY, Group root, ArrayList<BooleanBinding> bindings, Player player, Obstacle obstacle){
        double points[] = {5, -10, 20, -60, 35, -10, 0, -40, 40, -40};
        if(star != null) root.getChildren().remove(star);
        star = new Polygon(points);
        star.setScaleX(1.3);
        star.setFill(Color.WHITE);
        star.setLayoutX(centreX - 20);
        star.setLayoutY(centreY + 40);
        this.obstacle = obstacle;
        setRoot(root);
        root.getChildren().add(star);
    }

    public Polygon getStar() {
        return star;
    }

    public void initBindings(ArrayList<BooleanBinding> bindings, Player player, final int size){
        bindings.add(Bindings.createBooleanBinding(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Shape intersect = Shape.intersect(star, player.getIcon());
                return intersect.getBoundsInLocal().getWidth() != -1;
            }
        }, player.getIcon().centerYProperty(), getRoot().layoutYProperty()));
        setBinding(bindings.get(bindings.size() - 1));
        setListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(t1){
//                    System.out.println("IN CHANGER");
                    Main.starSound.play();
                    getRoot().getChildren().remove(star);
                    getBinding().removeListener(getListener());
                    System.out.println("IN STAR");
                    Main.numberOfStars++;
                    Main.obstacleShiftCounter++;
                    obstacle.setStarPresent(false);
                    System.out.println("Number of stars is " + Main.numberOfStars);
                }
            }
        });
        bindings.get(bindings.size() - 1).addListener(getListener());
    }
}
