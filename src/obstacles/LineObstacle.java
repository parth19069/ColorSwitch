package obstacles;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import playerinfo.Data;
import playerinfo.Player;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class LineObstacle extends Obstacle {

    public ArrayList<Polygon> segments;
    private int length, thickness;
    private boolean translationDirection;
    private boolean translationStatus;
    private Color colors[];
    private Translate translate;

    public LineObstacle(int centreX, int centreY, int thickness, boolean toLeft, Translate initialTranslate, int initialTransformState){
        super(centreX, centreY);

        this.thickness = thickness;
        this.translationDirection = toLeft;
        this.translate = new Translate();
        this.colors = new Color[12];
        setColorCode(new ArrayList<String>());
        getColorCode().add("Cyan");
        getColorCode().add("Purple");
        getColorCode().add("Yellow");
        getColorCode().add("Pink");
        getColorCode().add("Cyan");
        getColorCode().add("Purple");
        getColorCode().add("Yellow");
        getColorCode().add("Pink");
        getColorCode().add("Cyan");
        getColorCode().add("Purple");
        getColorCode().add("Yellow");
        getColorCode().add("Pink");
        setTimeline(new Timeline());

        segments = new ArrayList<Polygon>();

        double points1[] = {
                centreX - 800, centreY - thickness/2,
                centreX - (800/4)*3 , centreY - thickness/2,
                centreX - (800/4)*3 ,centreY + thickness/2,
                centreX - 800 , centreY + thickness/2
        };
        double points2[] = {
                centreX - (800/4)*3, centreY - thickness/2,
                centreX - 800/2, centreY - thickness/2 ,
                centreX - 800/2, centreY + thickness/2 ,
                centreX - (800/4)*3, centreY + thickness/2
        };
        double points3[] = {
                centreX - 800/2, centreY - thickness/2,
                centreX - 800/4, centreY - thickness/2,
                centreX - 800/4 ,centreY + thickness/2,
                centreX - 800/2 , centreY + thickness/2
        };
        double points4[] = {
                centreX - 800/4, centreY - thickness/2,
                centreX , centreY - thickness/2,
                centreX  ,centreY + thickness/2,
                centreX - 800/4 , centreY + thickness/2
        };
        double points5[] = {
                centreX, centreY - thickness/2,
                centreX - (800/4)*3+800 , centreY - thickness/2,
                centreX - (800/4)*3+800,centreY + thickness/2,
                centreX, centreY + thickness/2

        };
        double points6[] = {
                centreX - (800/4)*3+800, centreY - thickness/2,
                centreX - 800/2+ 800, centreY - thickness/2 ,
                centreX - 800/2 +800, centreY + thickness/2 ,
                centreX - (800/4)*3 +800, centreY + thickness/2

        };
        double points7[] = {
                centreX - 800/2 +800, centreY - thickness/2,
                centreX - 800/4 +800, centreY - thickness/2,
                centreX - 800/4 +800,centreY + thickness/2,
                centreX - 800/2 +800, centreY + thickness/2
        };
        double points8[] = {
                centreX - 800/4 +800, centreY - thickness/2,
                centreX + 800 , centreY - thickness/2,
                centreX + 800 ,centreY + thickness/2,
                centreX - 800/4 +800 , centreY + thickness/2
        };
        double points9[] = {
                centreX + 800, centreY - thickness/2,
                centreX - (800/4)*3+800 + 800 , centreY - thickness/2,
                centreX - (800/4)*3+800 + 800,centreY + thickness/2,
                centreX + 800, centreY + thickness/2

        };
        double points10[] = {
                centreX - (800/4)*3+800 + 800, centreY - thickness/2,
                centreX - 800/2+ 800 + 800, centreY - thickness/2 ,
                centreX - 800/2 +800 + 800, centreY + thickness/2 ,
                centreX - (800/4)*3 +800 + 800, centreY + thickness/2

        };
        double points11[] = {
                centreX - 800/2 +800 + 800, centreY - thickness/2,
                centreX - 800/4 +800 + 800, centreY - thickness/2,
                centreX - 800/4 +800 + 800,centreY + thickness/2,
                centreX - 800/2 +800 + 800, centreY + thickness/2
        };
        double points12[] = {
                centreX - 800/4 +800 + 800, centreY - thickness/2,
                centreX + 800 + 800, centreY - thickness/2,
                centreX + 800 + 800,centreY + thickness/2,
                centreX - 800/4 +800 + 800 , centreY + thickness/2
        };
        translationStatus = false;

        Polygon segment1 = makeSegment(points1);
        Polygon segment2 = makeSegment(points2);
        Polygon segment3 = makeSegment(points3);
        Polygon segment4 = makeSegment(points4);
        Polygon segment5 = makeSegment(points5);
        Polygon segment6 = makeSegment(points6);
        Polygon segment7 = makeSegment(points7);
        Polygon segment8 = makeSegment(points8);
        Polygon segment9 = makeSegment(points9);
        Polygon segment10 = makeSegment(points10);
        Polygon segment11 = makeSegment(points11);
        Polygon segment12 = makeSegment(points12);


        segments.add(segment1);
        segments.add(segment2);
        segments.add(segment3);
        segments.add(segment4);
        segments.add(segment5);
        segments.add(segment6);
        segments.add(segment7);
        segments.add(segment8);
        segments.add(segment9);
        segments.add(segment10);
        segments.add(segment11);
        segments.add(segment12);

        setInitialTranslate(initialTranslate);
        setInitialTransformState(initialTransformState);
        translate = new Translate();
        translate.setX(initialTransformState);
        for(int i = 0; i < 12; i++){
            segments.get(i).getTransforms().add(translate);
            segments.get(i).getTransforms().add(initialTranslate);
        }
    }
    @Override
    public double getSpecialValue(){
        return translate.getX();
    }
    @Override
    public void setSpecialValue(double x){
        while(x < -800) x += 800;
        setInitialTransformState(x);
        translate.setX(x);
        makeTranslation(6000);
    }
    public void setYTranslate(double y){
        getInitialTranslate().setY(y);
    }
    public void makeTranslation(int durationPerRotation){
        getTimeline().stop();
        getTimeline().getKeyFrames().clear();
        getTimeline().setCycleCount(Animation.INDEFINITE);
        for(int i = 0; i < 12; i++) {
            if (translationDirection) {
                getTimeline().getKeyFrames().addAll(new KeyFrame(Duration.millis(durationPerRotation), new KeyValue(translate.xProperty(), translate.getX() - 800)));
            }
            else {
                getTimeline().getKeyFrames().add(new KeyFrame(Duration.millis(durationPerRotation), new KeyValue(translate.xProperty(), translate.getX() + 800)));
            }
//            getTimeline().setOnFinished(e -> translate.setX(0));
        }
    }

    private Polygon makeSegment(double points[]){
        Polygon segment = new Polygon(points);
        return segment;
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
        segments.get(4).setFill(c1);
        colors[4] = c1;
        segments.get(5).setFill(c2);
        colors[5] = c2;
        segments.get(6).setFill(c3);
        colors[6] = c3;
        segments.get(7).setFill(c4);
        colors[7] = c4;
        segments.get(8).setFill(c1);
        colors[8] = c1;
        segments.get(9).setFill(c2);
        colors[9] = c2;
        segments.get(10).setFill(c3);
        colors[10] = c3;
        segments.get(11).setFill(c4);
        colors[11] = c4;
        setColorChanger();
    }

    @Override
    public void quickSetup(Group root, int duration, ArrayList<BooleanBinding> bindings, Player player, boolean showCollectables, boolean isShifted, boolean showChanger, boolean showStar){
        if(!isShifted) {
            showOnNode(root);
            makeTranslation(duration);
            initBindings(bindings, player);
            setColors(Color.CYAN, Color.PURPLE, Color.YELLOW, Color.rgb(250, 22, 151));
        }
        if(showCollectables) {
            setCollectables(400, getCentreY() + 100, 400, getCentreY() - 100, player, bindings, root, showChanger, showStar);
        }
        if(!isShifted) start();
    }
    public Translate getTranslate(){
        return translate;
    }
    public Polygon getSegment(int i){
        return segments.get(i);
    }
    @Override
    public void initBindings(ArrayList<BooleanBinding> bindings, Player player){
        setPlayer(player);
        bindings.add(Bindings.createBooleanBinding(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Shape intersect;
//                System.out.println(i);
                int i = 0;
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
        }, getTranslate().xProperty()));
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
                                collision();
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
    public void showOnNode(Group group){
        setRoot(group);
        for(int i = 0; i < 12; i++){
            group.getChildren().add(segments.get(i));
        }
    }

    @Override
    public void start(){
        getTimeline().play();
        translationStatus = true;
    }
    @Override
    public void stop(){
        getTimeline().stop();
        translationStatus = false;
    }
    @Override
    public void pause(){
        getTimeline().pause();
        translationStatus = false;
    }

    @Override
    public void setColorChanger(){
        getColorChanger().setColors(colors[0], colors[1], colors[2], colors[3]);
    }


}