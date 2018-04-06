package vn.com.hieptt.myhiittimer.timer;


public class Interval {
    private int intervalId;
    private String intervalTitle;
    private long intervalDuration;
    private int intervalColor;
    private int intervalHaloColor;

    public Interval() {
    }

    public Interval(int intervalId,String intervalTitle, long intervalDuration, int intervalColor, int intervalHaloColor) {
        this.intervalId = intervalId;
        this.intervalTitle = intervalTitle;
        this.intervalDuration = intervalDuration;
        this.intervalColor = intervalColor;
        this.intervalHaloColor = intervalHaloColor;
    }

    public int getIntervalId() {
        return intervalId;
    }

    public void setIntervalId(int intervalId) {
        this.intervalId = intervalId;
    }

    public String getIntervalTitle() {
        return intervalTitle;
    }

    public void setIntervalTitle(String intervalTitle) {
        this.intervalTitle = intervalTitle;
    }

    public long getIntervalDuration() {
        return intervalDuration;
    }

    public void setIntervalDuration(long intervalDuration) {
        this.intervalDuration = intervalDuration;
    }

    public int getIntervalColor() {
        return intervalColor;
    }

    public void setIntervalColor(int intervalColor) {
        this.intervalColor = intervalColor;
    }

    public int getIntervalHaloColor() {
        return intervalHaloColor;
    }

    public void setIntervalHaloColor(int intervalHaloColor) {
        this.intervalHaloColor = intervalHaloColor;
    }
}
