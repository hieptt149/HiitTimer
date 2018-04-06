package vn.com.hieptt.myhiittimer.home;

import vn.com.hieptt.myhiittimer.data.Styles;

/**
 * Created by Admin on 1/25/2018.
 */

public class ItemHomeRv extends Styles {
    private long totalTimer;
    private String nameTimer;
    private int idTimer;

    public void setIdTimer(int idTimer) {
        this.idTimer = idTimer;
    }

    public int getIdTimer() {
        return idTimer;
    }

    public String getNameTimer() {
        return nameTimer;
    }

    public void setNameTimer(String nameTimer) {
        this.nameTimer = nameTimer;
    }

    public ItemHomeRv() {
    }

    public ItemHomeRv(long totalTimer, String nameTimer) {
        this.totalTimer = totalTimer;
        this.nameTimer = nameTimer;
    }

    public long getTotalTimer() {
        return totalTimer;
    }

    public void setTotalTimer(long totalTimer) {
        this.totalTimer = totalTimer;
    }
}
