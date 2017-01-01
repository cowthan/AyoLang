package org.ayo.lang;

/**
 * 三种状态的boolean
 */

public class TripleState {

    private int state = 0;

    public void toPositive(){
        state = 0;
    }

    public void toNutral(){
        state = 1;
    }

    public void toNegative(){
        state = 2;
    }

    public void toNextState(){
        state++;
        if(state == 3) state = 0;
    }

    public boolean isPositive(){
        return state == 0;
    }
    public boolean isNutral(){
        return state == 1;
    }
    public boolean isNegative(){
        return state == 2;
    }


}
