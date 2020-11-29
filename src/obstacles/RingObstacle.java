package obstacles;

/*
    Cyan: 1
    Purple: 2
    Yellow: 3
    Pink (rgb): 4
*/

import javafx.animation.*;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import playerinfo.Player;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class RingObstacle extends Obstacle {
    private ArrayList<Path> segments;
    private Rotate rotate;
    private int radius, thickness;
    private boolean rotationStatus;
    private boolean rotationDirection;
    private Color colors[];

    public RingObstacle(int centreX, int centreY, int radius, int thickness, boolean clockwise, Translate initialTranslate, int initialTransformState){
        super(centreX, centreY);
        this.radius = radius;
        this.thickness = thickness;

        setColorCode(new ArrayList<String>());
        getColorCode().add("Cyan");
        getColorCode().add("Purple");
        getColorCode().add("Yellow");
        getColorCode().add("Pink");
        rotationStatus = false;
        rotationDirection = clockwise;
        segments = new ArrayList<Path>();
        rotate = new Rotate();
        colors = new Color[4];
        setTimeline(new Timeline());
        Path segment1, segment2, segment3, segment4;
        segment1 = makeSegment(centreX, centreY - radius, centreX - radius, centreY, centreX, centreY - radius - thickness, centreY - radius, centreX - radius - thickness, radius, radius + thickness, false, true);
        segment2 = makeSegment(centreX, centreY + radius, centreX - radius, centreY, centreX, centreY + radius + thickness, centreY + radius, centreX - radius - thickness, radius, radius + thickness, true, false);
        segment3 = makeSegment(centreX, centreY + radius, centreX + radius, centreY, centreX, centreY + radius + thickness, centreY + radius, centreX + radius + thickness, radius, radius + thickness, false, true);
        segment4 = makeSegment(centreX, centreY - radius, centreX + radius, centreY, centreX, centreY - radius - thickness, centreY - radius, centreX + radius + thickness, radius, radius + thickness, true, false);
        segments.add(segment1);
        segments.add(segment2);
        segments.add(segment3);
        segments.add(segment4);
        rotate.setPivotX(centreX);
        rotate.setPivotY(centreY);
        setInitialTranslate(initialTranslate);
        if(clockwise)setInitialTransformState(initialTransformState);
        else setInitialTransformState(initialTransformState);
        rotate.setAngle(getInitialTransformState());
        for(int i = 0; i < 4; i++){
            segments.get(i).getTransforms().add(rotate);
            segments.get(i).getTransforms().add(initialTranslate);
        }
    }
    @Override
    public double getSpecialValue(){
        System.out.println(rotate.getAngle());
        if(rotationDirection)return rotate.getAngle();
        return rotate.getAngle();
    }
    @Override
    public void setSpecialValue(double angle){
        if(rotationDirection)setInitialTransformState(angle);
        else setInitialTransformState(angle);
        rotate.setAngle(getInitialTransformState());
        makeRotation(6000);
    }
    private Path makeSegment(int startx, int starty, int innerx, int innery, int outerx, int outery, int vLine, int hLine, int innerRadius, int outerRadius, boolean innerFlag, boolean outerFlag){
        Path segment = new Path();
        segment.setStrokeWidth(0);
        MoveTo begin = new MoveTo(startx, starty);
        ArcTo inner = new ArcTo(), outer = new ArcTo();
        VLineTo vert = new VLineTo(vLine);
        HLineTo horz = new HLineTo(hLine);
        inner.setX(innerx);
        inner.setY(innery);
        inner.setRadiusX(innerRadius);
        inner.setRadiusY(innerRadius);
        outer.setRadiusX(outerRadius);
        outer.setRadiusY(outerRadius);
        outer.setX(outerx);
        outer.setY(outery);
        outer.setSweepFlag(outerFlag);
        inner.setSweepFlag(innerFlag);
        segment.getElements().addAll(begin, inner, horz, outer, vert);
        return segment;
    }
    public void makeRotation(int durationPerRotation){
        getTimeline().stop();
        getTimeline().getKeyFrames().clear();
        getTimeline().setCycleCount(Animation.INDEFINITE);
        if(rotationDirection)getTimeline().getKeyFrames().add(new KeyFrame(Duration.millis(durationPerRotation), new KeyValue(rotate.angleProperty(), rotate.getAngle() + 360)));
        else getTimeline().getKeyFrames().add(new KeyFrame(Duration.millis(durationPerRotation), new KeyValue(rotate.angleProperty(), rotate.getAngle() - 360)));
    }
    public boolean getRotationDirection(){
        return rotationDirection;
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
    @Override
    public void showOnNode(Group group){
        for(int i = 0; i < 4; i++){
            group.getChildren().add(segments.get(i));
        }
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
    @Override
    public void setColorChanger(){
        getColorChanger().setColors(colors[0], colors[1], colors[2], colors[3]);
    }
    public Color getColors(int i){
        return colors[i];
    }
    public void setYTranslate(double y){
        getInitialTranslate().setY(y);
        getRotate().setPivotY(getCentreY() + getInitialTranslate().getY());
    }
    // Sets duration to 3 seconds, sets colors to dafault(those found in original game) andstarts timiline
    // Added to children of root
    @Override
    public void quickSetup(Group root, int duration, ArrayList<BooleanBinding> bindings, Player player, boolean showCollectables, boolean isShifted){
        if(!isShifted) {
            showOnNode(root);
            makeRotation(duration);
            initBindings(bindings, player);
            setColors(Color.CYAN, Color.PURPLE, Color.YELLOW, Color.rgb(250, 22, 151));
        }
        if(showCollectables) {
            getColorChanger().setCollectable(getCentreX(), getCentreY() + radius + 100, root, bindings, player);
            getColorChanger().getChanger().getTransforms().add(getInitialTranslate());
            getStar().setCollectable(getCentreX(), getCentreY(), root, bindings, player);
            getStar().getStar().getTransforms().add(getInitialTranslate());
            getStar().initBindings(bindings, player, 0);
        }
        if(!isShifted)start();
    }
    public boolean getRotationStatus(){
        return rotationStatus;
    }

    public Path getSegment(int i){
        return segments.get(i);
    }
    public Rotate getRotate(){
        return rotate;
    }
    public int getRadius(){
        return radius;
    }
    @Override
    public void initBindings(ArrayList<BooleanBinding> bindings, Player player){
        setPlayer(player);
//        System.out.println((player == null));
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
}

