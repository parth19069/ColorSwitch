package obstacles;

import javafx.animation.*;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import playerinfo.Player;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class RingObstacle extends Obstacle {
    private ArrayList<Path> segments;
    private Rotate rotate;
    private int centreX, centreY, radius, thickness;
    private Timeline timeline;
    private boolean rotationStatus;
    private boolean rotationDirection;
    private Player player;
    private ArrayList<BooleanBinding> collision;
    private ArrayList<String> colorCode;
    public RingObstacle(int centreX, int centreY, int radius, int thickness, boolean clockwise){
        this.centreX = centreX;
        this.centreY = centreY;
        this.radius = radius;
        this.thickness = thickness;
        colorCode = new ArrayList<String>();
        colorCode.add("Cyan");
        colorCode.add("Purple");
        colorCode.add("Yellow");
        colorCode.add("Pink");
        rotationStatus = false;
        rotationDirection = clockwise;
        segments = new ArrayList<Path>();
        rotate = new Rotate();
        timeline = new Timeline();
        collision = new ArrayList<BooleanBinding>();
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
        rotate.setAngle(45);
        for(int i = 0; i < 4; i++){
            segments.get(i).getTransforms().add(rotate);
        }
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
//        this is a push
    }
    public void makeRotation(int durationPerRotation){
        timeline.setCycleCount(Animation.INDEFINITE);
        if(rotationDirection)timeline.getKeyFrames().add(new KeyFrame(Duration.millis(durationPerRotation), new KeyValue(rotate.angleProperty(), 405)));
        else timeline.getKeyFrames().add(new KeyFrame(Duration.millis(durationPerRotation), new KeyValue(rotate.angleProperty(), -315)));
    }
    public void startRotation(){
        timeline.play();
        rotationStatus = true;
    }
    public void stopRotation(){
        timeline.stop();
        rotationStatus = false;
    }
    public void pauseRotation(){
        timeline.pause();
        rotationStatus = false;
    }
    public void showOnNode(Group group){
        for(int i = 0; i < 4; i++){
            group.getChildren().add(segments.get(i));
        }
    }
    public void setColors(Color c1, Color c2, Color c3, Color c4){
        segments.get(0).setFill(c1);
        segments.get(1).setFill(c2);
        segments.get(2).setFill(c3);
        segments.get(3).setFill(c4);
    }
    // Sets duration to 3 seconds, sets colors to dafault(those found in original game) andstarts timiline
    // Added to children of root
    public void quickSetup(Group root){
        showOnNode(root);
        makeRotation(6000);
        setColors(Color.CYAN, Color.PURPLE, Color.YELLOW, Color.rgb(250, 22, 151));
        startRotation();
    }
    public boolean getRotationStatus(){
        return rotationStatus;
    }

    public Path getSegment(int i){
        return segments.get(i);
    }
    
    public void initBindings(ArrayList<BooleanBinding> bindings, Player player){
        this.player = player;
        //Segment 1
        bindings.add(Bindings.createBooleanBinding(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Shape intersect = Shape.intersect(player.getIcon(), getSegment(0));
                return intersect.getBoundsInLocal().getWidth() != -1;
            }
        }, player.getIcon().centerYProperty(), getRotate().angleProperty()));
        bindings.get(bindings.size() - 1).addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(t1){
                    System.out.println(colorCode.get(0) + " IN");
                    if(player.getColor() != 0){
                        System.out.println("OVER");
                        player.setColor(0);
                    }
                }
                else{
                    System.out.println(colorCode.get(0) + " OUT");
                }
            }
        });

        //Segment 2
        bindings.add(Bindings.createBooleanBinding(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Shape intersect = Shape.intersect(player.getIcon(), getSegment(1));
                return intersect.getBoundsInLocal().getWidth() != -1;
            }
        }, player.getIcon().centerYProperty(), getRotate().angleProperty()));
        bindings.get(bindings.size() - 1).addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(t1){
                    System.out.println(colorCode.get(1) + " IN");
                    if(player.getColor() != 1){
                        System.out.println("OVER");
                        player.setColor(1);
                    }
                }
                else{
                    System.out.println(colorCode.get(1) + " OUT");
                }
            }
        });

        //Segment 3
        bindings.add(Bindings.createBooleanBinding(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Shape intersect = Shape.intersect(player.getIcon(), getSegment(2));
                return intersect.getBoundsInLocal().getWidth() != -1;
            }
        }, player.getIcon().centerYProperty(), getRotate().angleProperty()));
        bindings.get(bindings.size() - 1).addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(t1){
                    System.out.println(colorCode.get(2) + " IN");
                    if(player.getColor() != 2){
                        System.out.println("OVER");
                        player.setColor(2);
                    }
                }
                else{
                    System.out.println(colorCode.get(2) + " OUT");
                }
            }
        });

        //Segment 4
        bindings.add(Bindings.createBooleanBinding(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Shape intersect = Shape.intersect(player.getIcon(), getSegment(3));
                return intersect.getBoundsInLocal().getWidth() != -1;
            }
        }, player.getIcon().centerYProperty(), getRotate().angleProperty()));
        bindings.get(bindings.size() - 1).addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(t1){
                    System.out.println(colorCode.get(3) + " IN");
                    if(player.getColor() != 3){
                        System.out.println("OVER");
                        player.setColor(3);
                    }
                }
                else{
                    System.out.println(colorCode.get(3) + " OUT");
                }
            }
        });
    }

    // Following are temporary functions which will probably be removed
    public Path getSegment1(){
        return segments.get(2);
    }
    public Rotate getRotate(){
        return rotate;
    }

    /*
    Cyan: 1
    Purple: 2
    Yellow: 3
    Pink (rgb): 4
     */
}
