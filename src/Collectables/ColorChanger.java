package Collectables;

import java.util.ArrayList;

public class ColorChanger {
    private ArrayList<Integer> colors;
    public ColorChanger(int ... a){
        for(int i = 0; i < a.length; i++){
            colors.add(a[i]);
        }
    }
}
