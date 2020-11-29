package playerinfo;

import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.ArrayList;

public class Data implements Serializable {
    private ArrayList<Integer> indices;
    private ArrayList<Double> initialTransforms, initialTranslates;
    private int subLayoutY, playerX, playerY;
    private int playerColor;
    private boolean isSaved;
    public Data(ArrayList<Double> initialTranslates, ArrayList<Integer> indices, ArrayList<Double> initialTransforms, int subLayoutY, int playerX, int playerY, int playerColor){
        this.indices = indices;
        this.initialTransforms = initialTransforms;
        this.subLayoutY = subLayoutY;
        this.playerX = playerX;
        this.playerY = playerY;
        this.playerColor = playerColor;
        this.initialTranslates = initialTranslates;
        isSaved = true;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }
    public ArrayList<Integer> getIndices(){
        return indices;
    }
    public ArrayList<Double> getInitialTransforms(){
        return initialTransforms;
    }
    public int getSubLayoutY(){
        return subLayoutY;
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public int getPlayerColor() {
        return playerColor;
    }

    public ArrayList<Double> getInitialTranslates() {
        return initialTranslates;
    }
}