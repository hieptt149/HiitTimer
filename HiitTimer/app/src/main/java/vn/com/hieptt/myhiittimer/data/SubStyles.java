package vn.com.hieptt.myhiittimer.data;

import java.io.Serializable;

/**
 * Created by Admin on 1/29/2018.
 */

public class SubStyles implements Serializable{
    private String nameWampUp,nameHigh,nameLow,nameCoolDown;
    private long duartionWampUp;
    private long duartionHigh;
    private long duartionLow;
    private long duartionCoolDown;
    private int drawColorWampUp,drawColorHigh,drawColorLow,drawColorCoolDown;
    public SubStyles() {
    }

    public int getDrawColorWampUp() {
        return drawColorWampUp;
    }

    public void setDrawColorWampUp(int drawColorWampUp) {
        this.drawColorWampUp = drawColorWampUp;
    }

    public int getDrawColorHigh() {
        return drawColorHigh;
    }

    public void setDrawColorHigh(int drawColorHigh) {
        this.drawColorHigh = drawColorHigh;
    }

    public int getDrawColorLow() {
        return drawColorLow;
    }

    public void setDrawColorLow(int drawColorLow) {
        this.drawColorLow = drawColorLow;
    }

    public int getDrawColorCoolDown() {
        return drawColorCoolDown;
    }

    public void setDrawColorCoolDown(int drawColorCoolDown) {
        this.drawColorCoolDown = drawColorCoolDown;
    }

    public String getNameWampUp() {
        return nameWampUp;
    }

    public void setNameWampUp(String nameWampUp) {
        this.nameWampUp = nameWampUp;
    }

    public String getNameHigh() {
        return nameHigh;
    }

    public void setNameHigh(String nameHigh) {
        this.nameHigh = nameHigh;
    }

    public String getNameLow() {
        return nameLow;
    }

    public void setNameLow(String nameLow) {
        this.nameLow = nameLow;
    }

    public String getNameCoolDown() {
        return nameCoolDown;
    }

    public void setNameCoolDown(String nameCoolDown) {
        this.nameCoolDown = nameCoolDown;
    }

    public long getDuartionWampUp() {
        return duartionWampUp;
    }

    public void setDuartionWampUp(long duartionWampUp) {
        this.duartionWampUp = duartionWampUp;
    }

    public long getDuartionHigh() {
        return duartionHigh;
    }

    public void setDuartionHigh(long duartionHigh) {
        this.duartionHigh = duartionHigh;
    }

    public long getDuartionLow() {
        return duartionLow;
    }

    public void setDuartionLow(long duartionLow) {
        this.duartionLow = duartionLow;
    }

    public long getDuartionCoolDown() {
        return duartionCoolDown;
    }

    public void setDuartionCoolDown(long duartionCoolDown) {
        this.duartionCoolDown = duartionCoolDown;
    }
}
