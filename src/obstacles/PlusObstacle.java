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
        getTimeline().stop();
        getTimeline().getKeyFrames().clear();
        getTimeline().setCycleCount(Animation.INDEFINITE);
        if(rotationDirection)getTimeline().getKeyFrames().add(new KeyFrame(Duration.millis(durationPerRotation), new KeyValue(rotate.angleProperty(), rotate.getAngle() + 360)));
        else getTimeline().getKeyFrames().add(new KeyFrame(Duration.millis(durationPerRotation), new KeyValue(rotate.angleProperty(), rotate.getAngle() -360)));
        start();
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
    public double getSpecialValue(){
        return rotate.getAngle();
    }
    @Override
    public void setSpecialValue(double angle){
        setInitialTransformState(angle);
        rotate.setAngle(angle);
        makeRotation(6000);
    }
    public void setYTranslate(double y){
        getInitialTranslate().setY(y);
        getRotate().setPivotY(getCentreY() + getInitialTranslate().getY());
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
        setRoot(group);
        for(int i = 0; i < 4; i++){
            group.getChildren().add(segments.get(i));
        }
    }

    @Override
    public void setColorChanger(){
        getColorChanger().setColors(colors[0], colors[1], colors[2], colors[3]);
    }

    @Override
    public void quickSetup(Group root, int duration, ArrayList<BooleanBinding> bindings, Player player, boolean showCollectables, boolean isShifted, boolean showChanger, boolean showStar){
        if(!isShifted) {
            showOnNode(root);
        }
        makeRotation(duration);
        setColors(Color.CYAN, Color.PURPLE, Color.YELLOW, Color.rgb(250, 22, 151));
        setDuration(duration);
        if(showCollectables) {
            setCollectables(400, getCentreY() + radius + 100, 400, getCentreY(), player, bindings, root, showChanger, showStar);
        }
        if(!isShifted) {
            initBindings(bindings, player);
            start();
        }
    }

    @Override
    public void initBindings(ArrayList<BooleanBinding> bindings, Player player){
        setPlayer(player);
        bindings.add(Bindings.createBooleanBinding(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Shape intersect;
                int i = 0;
//                System.out.println(i);
                for(Polygon segment: segments){
                    intersect = Shape.intersect(getPlayer().getIcon(), segment);
                    if(intersect.getBoundsInLocal().getWidth() != -1){
//                        System.out.println(getColorCode().get(i) + " IN");
                        return true;
                    }
                    i++;
                }
                return false;
            }
        }, getRotate().angleProperty()));
        bindings.get(bindings.size() - 1).addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(t1) {
                    int i = 0;
//                System.out.println(i);
                    Shape intersect;
                    for (Polygon segment : segments) {
                        intersect = Shape.intersect(getPlayer().getIcon(), segment);
                        if (intersect.getBoundsInLocal().getWidth() != -1) {
                            if(!colors[i].equals(getPlayer().getColor())){
                                System.out.println("GAME OVER");
                                double playerCentreY = getPlayer().getIcon().getCenterY();
                                double obstacleCentreY = getCentreY() + getInitialTranslate().getY() + getRoot().getLayoutY();
                                double finalPlayerPos = playerCentreY;
                                if(obstacleCentreY > playerCentreY) finalPlayerPos -= 200;
                                else finalPlayerPos += 200;
                                collision(finalPlayerPos);
                                return;
                            }
                        }
                        i++;
                    }
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
