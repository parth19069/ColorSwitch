package obstacles;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import playerinfo.Player;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class PlusObstacle extends Obstacle{

    public ArrayList<Polygon> segments;
    private Rotate rotate;
    private int radius, thickness;
    private boolean rotationStatus;
    private boolean rotationDirection;
    private Color colors[];

    public PlusObstacle(int centreX, int centreY, int radius, int thickness, boolean clockwise, Translate initialTranslate){
        super(centreX, centreY);
        this.radius = radius;
        this.thickness = thickness;
        this.rotationDirection = clockwise;
        this.rotationStatus = false;
        this.rotate = new Rotate();
        this.colors = new Color[4];
        setColorCode(new ArrayList<String>());
        getColorCode().add("Cyan");
        getColorCode().add("Purple");
        getColorCode().add("Yellow");
        getColorCode().add("Pink");
        setTimeline(new Timeline());

        segments = new ArrayList<Polygon>();

        double points1[] = {centreX, centreY,
                centreX + thickness/2, centreY - thickness/2,
                centreX + thickness/2 + radius, centreY - thickness/2,
                centreX + thickness/2 + radius, centreY + thickness/2,
                centreX + thickness/2, centreY + thickness/2};
        double points2[] = {centreX, centreY,
                centreX + thickness/2, centreY + thickness/2,
                centreX + thickness/2, centreY + thickness/2 + radius,
                centreX - thickness/2, centreY + thickness/2 + radius,
            centreX - thickness/2, centreY + thickness/2};
        double points3[] = {centreX, centreY,
                centreX - thickness/2, centreY - thickness/2,
                centreX - thickness/2 - radius, centreY - thickness/2,
                centreX - thickness/2 - radius, centreY + thickness/2,
                centreX - thickness/2, centreY + thickness/2};
        double points4[] = {centreX, centreY,
                centreX - thickness/2, centreY - thickness/2,
                centreX - thickness/2, centreY - thickness/2 - radius,
                centreX + thickness/2, centreY - thickness/2 - radius,
                centreX + thickness/2, centreY - thickness/2};
        Polygon segment1 = makeSegment(points1);
        Polygon segment2 = makeSegment(points2);
        Polygon segment3 = makeSegment(points3);
        Polygon segment4 = makeSegment(points4);
        segments.add(segment1);
        segments.add(segment2);
        segments.add(segment3);
        segments.add(segment4);
        rotate.setPivotX(centreX);
        rotate.setPivotY(centreY);
        setInitialTranslate(initialTranslate);
        for(int i = 0; i < 4; i++){
            segments.get(i).getTransforms().add(rotate);
            segments.get(i).getTransforms().add(initialTranslate);
        }
    }
    public void makeRotation(int durationPerRotation){
        getTimeline().setCycleCount(Animation.INDEFINITE);
        if(rotationDirection)getTimeline().getKeyFrames().add(new KeyFrame(Duration.millis(durationPerRotation), new KeyValue(rotate.angleProperty(), 360)));
        else getTimeline().getKeyFrames().add(new KeyFrame(Duration.millis(durationPerRotation), new KeyValue(rotate.angleProperty(), -360)));
    }
    public void setColors(Color c1, Color c2, Color c3, Color c4){
        segments.get(0).setFill(c1);
        colors[0] = c1;
        segments.get(1).setFill(c2);
        colors[1] = c2;
        segments.get(2).setFill(c3);
        colors[2] = c3;
        segments.get(3).setFill(c4);
        colors[3] = c4;
        setColorChanger();
    }
    public void setYTranslate(int y){
        getInitialTranslate().setY(y);
        getRotate().setPivotY(getRotate().getPivotY() + y);
    }
    private Polygon makeSegment(double points[]){
        Polygon segment = new Polygon(points);
        return segment;
    }
    public void q(){
        setColors(Color.CYAN, Color.PURPLE, Color.YELLOW, Color.rgb(250, 22, 151));
    }
    public Polygon getSegment(int i){
        return segments.get(i);
    }
    public Rotate getRotate(){
        return this.rotate;
    }


    @Override
    public void showOnNode(Group group){
        for(int i = 0; i < 4; i++){
            group.getChildren().add(segments.get(i));
        }
    }

    @Override
    public void setColorChanger(){
        getColorChanger().setColors(colors[0], colors[1], colors[2], colors[3]);
    }

    @Override
    public void quickSetup(Group root, int duration, ArrayList<BooleanBinding> bindings, Player player, boolean showCollectables){
        showOnNode(root);
        makeRotation(duration);
        initBindings(bindings, player);
        setColors(Color.CYAN, Color.PURPLE, Color.YELLOW, Color.rgb(250, 22, 151));
        if(showCollectables) {
            getColorChanger().setCollectable(400, getCentreY() + radius + 100, root, bindings, player);
            getColorChanger().getChanger().getTransforms().add(getInitialTranslate());

            getStar().setCollectable(400, getCentreY(), root, bindings, player);
            getStar().getStar().getTransforms().add(getInitialTranslate());
            getStar().initBindings(bindings, player, 0);
        }
        start();
    }

    @Override
    public void initBindings(ArrayList<BooleanBinding> bindings, Player player){
        setPlayer(player);
        //Segment 1
        bindings.add(Bindings.createBooleanBinding(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Shape intersect = Shape.intersect(getPlayer().getIcon(), getSegment(0));
                return intersect.getBoundsInLocal().getWidth() != -1;
            }
        }, getPlayer().getIcon().centerYProperty(), getRotate().angleProperty()));
        bindings.get(bindings.size() - 1).addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(t1){
                    System.out.println(getColorCode().get(0) + " IN");
                    if(getPlayer().getColor() != colors[0]){
                        System.out.println("OVER");
                        getPlayer().setColor(colors[0]);
                    }
                }
                else{
                    System.out.println(getColorCode().get(0) + " OUT");
                }
            }
        });

        //Segment 2
        bindings.add(Bindings.createBooleanBinding(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Shape intersect = Shape.intersect(getPlayer().getIcon(), getSegment(1));
                return intersect.getBoundsInLocal().getWidth() != -1;
            }
        }, getPlayer().getIcon().centerYProperty(), getRotate().angleProperty()));
        bindings.get(bindings.size() - 1).addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(t1){
                    System.out.println(getColorCode().get(1) + " IN");
                    if(getPlayer().getColor() != colors[1]){
                        System.out.println("OVER");
                        getPlayer().setColor(colors[1]);
                    }
                }
                else{
                    System.out.println(getColorCode().get(1) + " OUT");
                }
            }
        });

        //Segment 3
        bindings.add(Bindings.createBooleanBinding(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Shape intersect = Shape.intersect(getPlayer().getIcon(), getSegment(2));
                return intersect.getBoundsInLocal().getWidth() != -1;
            }
        }, getPlayer().getIcon().centerYProperty(), getRotate().angleProperty()));
        bindings.get(bindings.size() - 1).addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(t1){
                    System.out.println(getColorCode().get(2) + " IN");
                    if(getPlayer().getColor() != colors[2]){
                        System.out.println("OVER");
                        getPlayer().setColor(colors[2]);
                    }
                }
                else{
                    System.out.println(getColorCode().get(2) + " OUT");
                }
            }
        });

        //Segment 4
        bindings.add(Bindings.createBooleanBinding(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Shape intersect = Shape.intersect(getPlayer().getIcon(), getSegment(3));
                return intersect.getBoundsInLocal().getWidth() != -1;
            }
        }, getPlayer().getIcon().centerYProperty(), getRotate().angleProperty()));
        bindings.get(bindings.size() - 1).addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(t1){
                    System.out.println(getColorCode().get(3) + " IN");
                    if(getPlayer().getColor() != colors[3]){
                        System.out.println("OVER");
                        getPlayer().setColor(colors[3]);
                    }
                }
                else{
                    System.out.println(getColorCode().get(3) + " OUT");
                }
            }
        });
    }

    @Override
    public void start(){
        getTimeline().play();
        rotationStatus = true;
    }
    @Override
    public void stop(){
        getTimeline().stop();
        rotationStatus = false;
    }
    @Override
    public void pause(){
        getTimeline().pause();
        rotationStatus = false;
    }
}
