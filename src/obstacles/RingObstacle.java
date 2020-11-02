package obstacles;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.ArrayList;

public class RingObstacle extends Obstacle {
    private ArrayList<Path> segments;
    private Rotate rotate;
    private int centreX, centreY, radius, thickness;
    private Timeline timeline;
    private boolean rotationStatus;
    private boolean rotationDirection;
    public RingObstacle(int centreX, int centreY, int radius, int thickness, boolean clockwise){
        this.centreX = centreX;
        this.centreY = centreY;
        this.radius = radius;
        this.thickness = thickness;
        rotationStatus = false;
        rotationDirection = clockwise;
        segments = new ArrayList<Path>();
        rotate = new Rotate();
        timeline = new Timeline();
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
        makeRotation(3000);
        setColors(Color.CYAN, Color.PURPLE, Color.YELLOW, Color.rgb(250, 22, 151));
        startRotation();
    }
    public boolean getRotationStatus(){
        return rotationStatus;
    }
}
