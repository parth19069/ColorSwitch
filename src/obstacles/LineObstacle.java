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

public class LineObstacle extends Obstacle {

    public ArrayList<Polygon> segments;
    private int length, thickness;
    private boolean translationDirection;
    private boolean translationStatus;
    private Color colors[];
    private Translate translate;

    public LineObstacle(int centreX, int centreY, int thickness, boolean toLeft){
        super(centreX, centreY);

        this.thickness = thickness;
        this.translationDirection = toLeft;
        this.translate = new Translate();
        this.colors = new Color[8];
        setColorCode(new ArrayList<String>());
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
        translationStatus = false;

        Polygon segment1 = makeSegment(points1);
        Polygon segment2 = makeSegment(points2);
        Polygon segment3 = makeSegment(points3);
        Polygon segment4 = makeSegment(points4);
        Polygon segment5 = makeSegment(points5);
        Polygon segment6 = makeSegment(points6);
        Polygon segment7 = makeSegment(points7);
        Polygon segment8 = makeSegment(points8);


        segments.add(segment1);
        segments.add(segment2);
        segments.add(segment3);
        segments.add(segment4);
        segments.add(segment5);
        segments.add(segment6);
        segments.add(segment7);
        segments.add(segment8);

        for(int i = 0; i < 8; i++){
            segments.get(i).getTransforms().add(translate);
        }
    }

    public void makeTranslation(int durationPerRotation){
        getTimeline().setCycleCount(Animation.INDEFINITE);
        if(translationDirection)getTimeline().getKeyFrames().add(new KeyFrame(Duration.millis(durationPerRotation), new KeyValue(translate.xProperty(), -800)));
        else getTimeline().getKeyFrames().add(new KeyFrame(Duration.millis(durationPerRotation), new KeyValue(translate.xProperty(), 800)));
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
        setColorChanger();
    }

    @Override
    public void quickSetup(Group root, int duration, ArrayList<BooleanBinding> bindings, Player player, boolean showCollectables){
        showOnNode(root);
        makeTranslation(duration);
        initBindings(bindings, player);
        setColors(Color.CYAN, Color.PURPLE, Color.YELLOW, Color.rgb(250, 22, 151));
        if(showCollectables) {
            getColorChanger().setCollectable(400, getCentreY() + 100, root, bindings, player);
            getStar().setCollectable(400, getCentreY() - 100, root, bindings, player);
            getStar().initBindings(bindings, player, 0);
        }
        start();
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
        //Segment 1
        bindings.add(Bindings.createBooleanBinding(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Shape intersect = Shape.intersect(getPlayer().getIcon(), getSegment(0));
                return intersect.getBoundsInLocal().getWidth() != -1;
            }
        }, getPlayer().getIcon().centerYProperty(), getTranslate().xProperty()));
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
        }, getPlayer().getIcon().centerYProperty(), getTranslate().xProperty()));
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
        }, getPlayer().getIcon().centerYProperty(), getTranslate().xProperty()));
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
        }, getPlayer().getIcon().centerYProperty(), getTranslate().xProperty()));
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

        //Segment 5
        bindings.add(Bindings.createBooleanBinding(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Shape intersect = Shape.intersect(getPlayer().getIcon(), getSegment(4));
                return intersect.getBoundsInLocal().getWidth() != -1;
            }
        }, getPlayer().getIcon().centerYProperty(), getTranslate().xProperty()));
        bindings.get(bindings.size() - 1).addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(t1){
                    System.out.println(getColorCode().get(4) + " IN");
                    if(getPlayer().getColor() != colors[4]){
                        System.out.println("OVER");
                        getPlayer().setColor(colors[4]);
                    }
                }
                else{
                    System.out.println(getColorCode().get(4) + " OUT");
                }
            }
        });

        //Segment 6
        bindings.add(Bindings.createBooleanBinding(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Shape intersect = Shape.intersect(getPlayer().getIcon(), getSegment(5));
                return intersect.getBoundsInLocal().getWidth() != -1;
            }
        }, getPlayer().getIcon().centerYProperty(), getTranslate().xProperty()));
        bindings.get(bindings.size() - 1).addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(t1){
                    System.out.println(getColorCode().get(5) + " IN");
                    if(getPlayer().getColor() != colors[5]){
                        System.out.println("OVER");
                        getPlayer().setColor(colors[5]);
                    }
                }
                else{
                    System.out.println(getColorCode().get(5) + " OUT");
                }
            }
        });

        //Segment 7
        bindings.add(Bindings.createBooleanBinding(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Shape intersect = Shape.intersect(getPlayer().getIcon(), getSegment(6));
                return intersect.getBoundsInLocal().getWidth() != -1;
            }
        }, getPlayer().getIcon().centerYProperty(), getTranslate().xProperty()));
        bindings.get(bindings.size() - 1).addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(t1){
                    System.out.println(getColorCode().get(6) + " IN");
                    if(getPlayer().getColor() != colors[6]){
                        System.out.println("OVER");
                        getPlayer().setColor(colors[6]);
                    }
                }
                else{
                    System.out.println(getColorCode().get(6) + " OUT");
                }
            }
        });

        //Segment 8
        bindings.add(Bindings.createBooleanBinding(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Shape intersect = Shape.intersect(getPlayer().getIcon(), getSegment(7));
                return intersect.getBoundsInLocal().getWidth() != -1;
            }
        }, getPlayer().getIcon().centerYProperty(), getTranslate().xProperty()));
        bindings.get(bindings.size() - 1).addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(t1){
                    System.out.println(getColorCode().get(7) + " IN");
                    if(getPlayer().getColor() != colors[7]){
                        System.out.println("OVER");
                        getPlayer().setColor(colors[7]);
                    }
                }
                else{
                    System.out.println(getColorCode().get(7) + " OUT");
                }
            }
        });
    }

    @Override
    public void showOnNode(Group group){
        for(int i = 0; i < 8; i++){
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