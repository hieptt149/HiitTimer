package vn.com.hieptt.myhiittimer.data;

import java.io.Serializable;

/**
 * Created by Admin on 1/29/2018.
 */

public class SubTimer implements Serializable {
    private String nameTimer;
    private int numberOfSet;

    public SubTimer() {
    }

    public String getNameTimer() {
        return nameTimer;
    }

    public void setNameTimer(String nameTimer) {
        this.nameTimer = nameTimer;
    }

    public int getNumberOfSet() {
        return numberOfSet;
    }

    public void setNumberOfSet(int numberOfSet) {
        this.numberOfSet = numberOfSet;
    }

    public SubTimer(String nameTimer, int numberOfSet) {

        this.nameTimer = nameTimer;
        this.numberOfSet = numberOfSet;
    }
}
