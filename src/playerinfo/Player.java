package playerinfo;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Player {
    private Circle icon;
    private ArrayList<Color> colorCode;
    private int color;
    public Player(int centreX, int centreY, int radius, int color){
        icon = new Circle(centreX, centreY, radius);
        icon.setStrokeWidth(0);
        colorCode = new ArrayList<Color>();
        colorCode.add(Color.CYAN);
        colorCode.add(Color.PURPLE);
        colorCode.add(Color.YELLOW);
        colorCode.add(Color.rgb(250, 22, 151));
        this.color = color;
    }
    public void setColor(int color){
        icon.setFill(colorCode.get(color));
        this.color = color;
    }
    public void setColor(Color color){
        icon.setFill(color);
    }
    public Circle getIcon(){
        return icon;
    }
    public int getColor(){
        return color;
    }
}
