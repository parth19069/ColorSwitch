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
    private int numberOfStars, obstacleShiftCounter;
    private ArrayList<Boolean> changerStatus, starStatus;
    public Data(ArrayList<Double> initialTranslates, ArrayList<Integer> indices,
                ArrayList<Boolean> changerStatus, ArrayList<Boolean> starStatus,
                ArrayList<Double> initialTransforms, int subLayoutY, int playerX,
                int playerY, int playerColor, int numberOfStars, int obstacleShiftCounter){
        this.indices = indices;
        this.initialTransforms = initialTransforms;
        this.changerStatus = changerStatus;
        this.starStatus = starStatus;
        this.subLayoutY = subLayoutY;
        this.playerX = playerX;
        this.playerY = playerY;
        this.playerColor = playerColor;
        this.initialTranslates = initialTranslates;
        this.numberOfStars = numberOfStars;
        this.obstacleShiftCounter = obstacleShiftCounter;
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

    public int getNumberOfStars() {
        return numberOfStars;
    }

    public int getObstacleShiftCounter() {
        return obstacleShiftCounter;
    }

    public ArrayList<Boolean> getChangerStatus() {
        return changerStatus;
    }

    public ArrayList<Boolean> getStarStatus() {
        return starStatus;
    }
}