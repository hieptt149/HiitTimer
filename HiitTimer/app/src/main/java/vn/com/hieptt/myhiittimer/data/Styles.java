package vn.com.hieptt.myhiittimer.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Admin on 1/22/2018.
 */
@Entity (tableName = "Styles")
public class Styles {
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo (name = "Type_ID")
    public int idType;
    @ForeignKey(entity = Timer.class,parentColumns = "Timer_ID",childColumns = "Timer_ID")
    @ColumnInfo (name = "Timer_ID")
    public long timerID;
    @ColumnInfo (name = "Type")
    public String type;
    @ColumnInfo (name = "Title")
    public String titleType;
    @ColumnInfo (name = "Duration")
    public long duration;
    @ColumnInfo (name = "Color")
    public int colorType;

    public Styles() {
    }

    public Styles(long timerID, String type, String titleType, long duration, int colorType) {
        this.timerID = timerID;
        this.type = type;
        this.titleType = titleType;
        this.duration = duration;
        this.colorType = colorType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public int getIdType() {
        return idType;
    }

    public long getTimerID() {
        return timerID;
    }

    public void setTimerID(long timerID) {
        this.timerID = timerID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitleType() {
        return titleType;
    }

    public void setTitleType(String titleType) {
        this.titleType = titleType;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getColorType() {
        return colorType;
    }

    public void setColorType(int colorType) {
        this.colorType = colorType;
    }
}
