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
//            getColorChanger().setCollectable(400, getCentreY() + 100, root, bindings, player);
//            getColorChanger().getChanger().getTransforms().add(getInitialTranslate());
//
//            getStar().setCollectable(400, getCentreY() - 100, root, bindings, player);
//            getStar().getStar().getTransforms().add(getInitialTranslate());
//            getStar().initBindings(bindings, player, 0);

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
//        //Segment 1
//        bindings.add(Bindings.createBooleanBinding(new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                Shape intersect = Shape.intersect(getPlayer().getIcon(), getSegment(0));
//                return intersect.getBoundsInLocal().getWidth() != -1;
//            }
//        }, getPlayer().getIcon().centerYProperty(), getTranslate().xProperty()));
//        bindings.get(bindings.size() - 1).addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
//                if(t1){
//                    System.out.println(getColorCode().get(0) + " IN");
//                    if(getPlayer().getColor() != colors[0]){
//                        System.out.println("OVER");
//                        getPlayer().setColor(colors[0]);
//                    }
//                }
//                else{
//                    System.out.println(getColorCode().get(0) + " OUT");
//                }
//            }
//        });
//
//        //Segment 2
//        bindings.add(Bindings.createBooleanBinding(new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                Shape intersect = Shape.intersect(getPlayer().getIcon(), getSegment(1));
//                return intersect.getBoundsInLocal().getWidth() != -1;
//            }
//        }, getPlayer().getIcon().centerYProperty(), getTranslate().xProperty()));
//        bindings.get(bindings.size() - 1).addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
//                if(t1){
//                    System.out.println(getColorCode().get(1) + " IN");
//                    if(getPlayer().getColor() != colors[1]){
//                        System.out.println("OVER");
//                        getPlayer().setColor(colors[1]);
//                    }
//                }
//                else{
//                    System.out.println(getColorCode().get(1) + " OUT");
//                }
//            }
//        });
//
//        //Segment 3
//        bindings.add(Bindings.createBooleanBinding(new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                Shape intersect = Shape.intersect(getPlayer().getIcon(), getSegment(2));
//                return intersect.getBoundsInLocal().getWidth() != -1;
//            }
//        }, getPlayer().getIcon().centerYProperty(), getTranslate().xProperty()));
//        bindings.get(bindings.size() - 1).addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
//                if(t1){
//                    System.out.println(getColorCode().get(2) + " IN");
//                    if(getPlayer().getColor() != colors[2]){
//                        System.out.println("OVER");
//                        getPlayer().setColor(colors[2]);
//                    }
//                }
//                else{
//                    System.out.println(getColorCode().get(2) + " OUT");
//                }
//            }
//        });
//
//        //Segment 4
//        bindings.add(Bindings.createBooleanBinding(new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                Shape intersect = Shape.intersect(getPlayer().getIcon(), getSegment(3));
//                return intersect.getBoundsInLocal().getWidth() != -1;
//            }
//        }, getPlayer().getIcon().centerYProperty(), getTranslate().xProperty()));
//        bindings.get(bindings.size() - 1).addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
//                if(t1){
//                    System.out.println(getColorCode().get(3) + " IN");
//                    if(getPlayer().getColor() != colors[3]){
//                        System.out.println("OVER");
//                        getPlayer().setColor(colors[3]);
//                    }
//                }
//                else{
//                    System.out.println(getColorCode().get(3) + " OUT");
//                }
//            }
//        });
//
//        //Segment 5
//        bindings.add(Bindings.createBooleanBinding(new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                Shape intersect = Shape.intersect(getPlayer().getIcon(), getSegment(4));
//                return intersect.getBoundsInLocal().getWidth() != -1;
//            }
//        }, getPlayer().getIcon().centerYProperty(), getTranslate().xProperty()));
//        bindings.get(bindings.size() - 1).addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
//                if(t1){
//                    System.out.println(getColorCode().get(4) + " IN");
//                    if(getPlayer().getColor() != colors[4]){
//                        System.out.println("OVER");
//                        getPlayer().setColor(colors[4]);
//                    }
//                }
//                else{
//                    System.out.println(getColorCode().get(4) + " OUT");
//                }
//            }
//        });
//
//        //Segment 6
//        bindings.add(Bindings.createBooleanBinding(new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                Shape intersect = Shape.intersect(getPlayer().getIcon(), getSegment(5));
//                return intersect.getBoundsInLocal().getWidth() != -1;
//            }
//        }, getPlayer().getIcon().centerYProperty(), getTranslate().xProperty()));
//        bindings.get(bindings.size() - 1).addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
//                if(t1){
//                    System.out.println(getColorCode().get(5) + " IN");
//                    if(getPlayer().getColor() != colors[5]){
//                        System.out.println("OVER");
//                        getPlayer().setColor(colors[5]);
//                    }
//                }
//                else{
//                    System.out.println(getColorCode().get(5) + " OUT");
//                }
//            }
//        });
//
//        //Segment 7
//        bindings.add(Bindings.createBooleanBinding(new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                Shape intersect = Shape.intersect(getPlayer().getIcon(), getSegment(6));
//                return intersect.getBoundsInLocal().getWidth() != -1;
//            }
//        }, getPlayer().getIcon().centerYProperty(), getTranslate().xProperty()));
//        bindings.get(bindings.size() - 1).addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
//                if(t1){
//                    System.out.println(getColorCode().get(6) + " IN");
//                    if(getPlayer().getColor() != colors[6]){
//                        System.out.println("OVER");
//                        getPlayer().setColor(colors[6]);
//                    }
//                }
//                else{
//                    System.out.println(getColorCode().get(6) + " OUT");
//                }
//            }
//        });
//
//        //Segment 8
//        bindings.add(Bindings.createBooleanBinding(new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                Shape intersect = Shape.intersect(getPlayer().getIcon(), getSegment(7));
//                return intersect.getBoundsInLocal().getWidth() != -1;
//            }
//        }, getPlayer().getIcon().centerYProperty(), getTranslate().xProperty()));
//        bindings.get(bindings.size() - 1).addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
//                if(t1){
//                    System.out.println(getColorCode().get(7) + " IN");
//                    if(getPlayer().getColor() != colors[7]){
//                        System.out.println("OVER");
//                        getPlayer().setColor(colors[7]);
//                    }
//                }
//                else{
//                    System.out.println(getColorCode().get(7) + " OUT");
//                }
//            }
//        });
//
//        //Segment 9
//        bindings.add(Bindings.createBooleanBinding(new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                Shape intersect = Shape.intersect(getPlayer().getIcon(), getSegment(8));
//                return intersect.getBoundsInLocal().getWidth() != -1;
//            }
//        }, getPlayer().getIcon().centerYProperty(), getTranslate().xProperty()));
//        bindings.get(bindings.size() - 1).addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
//                if(t1){
//                    System.out.println(getColorCode().get(8) + " IN");
//                    if(getPlayer().getColor() != colors[8]){
//                        System.out.println("OVER");
//                        getPlayer().setColor(colors[8]);
//                    }
//                }
//                else{
//                    System.out.println(getColorCode().get(8) + " OUT");
//                }
//            }
//        });
//
//        //Segment 10
//        bindings.add(Bindings.createBooleanBinding(new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                Shape intersect = Shape.intersect(getPlayer().getIcon(), getSegment(9));
//                return intersect.getBoundsInLocal().getWidth() != -1;
//            }
//        }, getPlayer().getIcon().centerYProperty(), getTranslate().xProperty()));
//        bindings.get(bindings.size() - 1).addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
//                if(t1){
//                    System.out.println(getColorCode().get(9) + " IN");
//                    if(getPlayer().getColor() != colors[9]){
//                        System.out.println("OVER");
//                        getPlayer().setColor(colors[9]);
//                    }
//                }
//                else{
//                    System.out.println(getColorCode().get(9) + " OUT");
//                }
//            }
//        });
//
//        //Segment 11
//        bindings.add(Bindings.createBooleanBinding(new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                Shape intersect = Shape.intersect(getPlayer().getIcon(), getSegment(10));
//                return intersect.getBoundsInLocal().getWidth() != -1;
//            }
//        }, getPlayer().getIcon().centerYProperty(), getTranslate().xProperty()));
//        bindings.get(bindings.size() - 1).addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
//                if(t1){
//                    System.out.println(getColorCode().get(10) + " IN");
//                    if(getPlayer().getColor() != colors[10]){
//                        System.out.println("OVER");
//                        getPlayer().setColor(colors[10]);
//                    }
//                }
//                else{
//                    System.out.println(getColorCode().get(10) + " OUT");
//                }
//            }
//        });
//
//        //Segment 12
//        bindings.add(Bindings.createBooleanBinding(new Callable<Boolean>() {
//            @Override
//            public Boolean call() throws Exception {
//                Shape intersect = Shape.intersect(getPlayer().getIcon(), getSegment(11));
//                return intersect.getBoundsInLocal().getWidth() != -1;
//            }
//        }, getPlayer().getIcon().centerYProperty(), getTranslate().xProperty()));
//        bindings.get(bindings.size() - 1).addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
//                if(t1){
//                    System.out.println(getColorCode().get(11) + " IN");
//                    if(getPlayer().getColor() != colors[11]){
//                        System.out.println("OVER");
//                        getPlayer().setColor(colors[11]);
//                    }
//                }
//                else{
//                    System.out.println(getColorCode().get(11) + " OUT");
//                }
//            }
//        });

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