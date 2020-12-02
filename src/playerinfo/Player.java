package playerinfo;

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import obstacles.Pauseable;

import java.util.ArrayList;

public class Player implements Pauseable {
    private Circle icon;
    private ArrayList<Color> colorCode;
    private Color color;
    private Timeline timeline;
    private Group root;
    private boolean fall;
    public Player(int centreX, int centreY, int radius, Color color){
        icon = new Circle(centreX, centreY, radius);
        icon.setStrokeWidth(0);
        colorCode = new ArrayList<Color>();
        timeline = new Timeline();
        colorCode.add(Color.CYAN);
        colorCode.add(Color.PURPLE);
        colorCode.add(Color.YELLOW);
        colorCode.add(Color.rgb(250, 22, 151));
        fall = true;
        this.color = color;
    }
    public void setColor(Color color){
        icon.setFill(color);
        this.color = color;
    }
    public Circle getIcon(){
        return icon;
    }
    public Color getColor(){
        return color;
    }
    public Timeline getTimeline(){
        return timeline;
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

    public Group getRoot() {
        return root;
    }

    public void setRoot(Group root) {
        this.root = root;
    }

    public boolean isFall() {
        return fall;
    }

    public void setFall(boolean fall) {
        this.fall = fall;
    }
}
