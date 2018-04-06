package vn.com.hieptt.myhiittimer.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Admin on 1/19/2018.
 */
@Entity (tableName = "UserTimer")
public class Timer {
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = "Timer_ID")
    public long TimerID;
    @ColumnInfo (name = "Timer_Name")
    public String TimerName;
    @ColumnInfo (name = "Number_OfSet")
    public int NumberOfSet;

    public Timer(long timerID, String timerName, int numberOfSet) {
        TimerID = timerID;
        TimerName = timerName;
        NumberOfSet = numberOfSet;
    }

    public void setTimerID(long timerID) {
        TimerID = timerID;
    }

    public Timer() {
    }

    public long getTimerID() {
        return TimerID;
    }

    public String getTimerName() {
        return TimerName;
    }

    public void setTimerName(String timerName) {
        TimerName = timerName;
    }

    public int getNumberOfSet() {
        return NumberOfSet;
    }

    public void setNumberOfSet(int numberOfSet) {
        NumberOfSet = numberOfSet;
    }
}
