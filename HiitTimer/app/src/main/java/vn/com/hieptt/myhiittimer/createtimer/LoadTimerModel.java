package vn.com.hieptt.myhiittimer.createtimer;

import java.io.Serializable;

/**
 * Created by Admin on 1/31/2018.
 */

public class LoadTimerModel implements Serializable {

    public LoadTimerModel() {
    }

    private String edtTimerName, edtNumberOfSet,
            edtWarpUpName, edtWarpUpDuration,
            edtHighName, edtHighDuration,
            edtLowName, edtLowDuration,
            edtCoolDownName, edtCoolDownDuration;
    private int imgWarpUpColor, imgHighColor, imgLowColor, imgCoolDownColor;

    public String getEdtTimerName() {
        return edtTimerName;
    }

    public void setEdtTimerName(String edtTimerName) {
        this.edtTimerName = edtTimerName;
    }

    public String getEdtNumberOfSet() {
        return edtNumberOfSet;
    }

    public void setEdtNumberOfSet(String edtNumberOfSet) {
        this.edtNumberOfSet = edtNumberOfSet;
    }

    public String getEdtWarpUpName() {
        return edtWarpUpName;
    }

    public void setEdtWarpUpName(String edtWarpUpName) {
        this.edtWarpUpName = edtWarpUpName;
    }

    public String getEdtWarpUpDuration() {
        return edtWarpUpDuration;
    }

    public void setEdtWarpUpDuration(String edtWarpUpDuration) {
        this.edtWarpUpDuration = edtWarpUpDuration;
    }

    public String getEdtHighName() {
        return edtHighName;
    }

    public void setEdtHighName(String edtHighName) {
        this.edtHighName = edtHighName;
    }

    public String getEdtHighDuration() {
        return edtHighDuration;
    }

    public void setEdtHighDuration(String edtHighDuration) {
        this.edtHighDuration = edtHighDuration;
    }

    public String getEdtLowName() {
        return edtLowName;
    }

    public void setEdtLowName(String edtLowName) {
        this.edtLowName = edtLowName;
    }

    public String getEdtLowDuration() {
        return edtLowDuration;
    }

    public void setEdtLowDuration(String edtLowDuration) {
        this.edtLowDuration = edtLowDuration;
    }

    public String getEdtCoolDownName() {
        return edtCoolDownName;
    }

    public void setEdtCoolDownName(String edtCoolDownName) {
        this.edtCoolDownName = edtCoolDownName;
    }

    public String getEdtCoolDownDuration() {
        return edtCoolDownDuration;
    }

    public void setEdtCoolDownDuration(String edtCoolDownDuration) {
        this.edtCoolDownDuration = edtCoolDownDuration;
    }

    public int getImgWarpUpColor() {
        return imgWarpUpColor;
    }

    public void setImgWarpUpColor(int imgWarpUpColor) {
        this.imgWarpUpColor = imgWarpUpColor;
    }

    public int getImgHighColor() {
        return imgHighColor;
    }

    public void setImgHighColor(int imgHighColor) {
        this.imgHighColor = imgHighColor;
    }

    public int getImgLowColor() {
        return imgLowColor;
    }

    public void setImgLowColor(int imgLowColor) {
        this.imgLowColor = imgLowColor;
    }

    public int getImgCoolDownColor() {
        return imgCoolDownColor;
    }

    public void setImgCoolDownColor(int imgCoolDownColor) {
        this.imgCoolDownColor = imgCoolDownColor;
    }
}
