package vn.com.hieptt.myhiittimer.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;
import android.arch.persistence.room.Update;

import vn.com.hieptt.myhiittimer.home.ItemHomeRv;

import java.util.List;

/**
 * Created by Admin on 1/22/2018.
 */
@Dao
public interface StylesDao {
    @Query("SELECT * FROM Styles")
    List<Styles> getAll();

    @Insert
    void insertAll(Styles... styles);
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT Styles.Duration, Styles.Timer_ID , Styles.Type_ID , Styles.Color , UserTimer.Timer_Name AS nameTimer , SUM (Duration) AS totalTimer ,UserTimer.Timer_ID AS idTimer" +
            " FROM Styles , UserTimer WHERE UserTimer.Timer_ID=Styles.Timer_ID GROUP BY Styles.Timer_ID")
    List<ItemHomeRv> getDemoItem();

    @Query("SELECT * FROM Styles WHERE Timer_ID IN (:timerID)")
    List<Styles> loadAllByIds(long timerID);

    @Query("DELETE FROM Styles WHERE Timer_ID = (:timerID);")
    void deleteStyles(long timerID);
    @Update
    void updateStyles(Styles styles);
}
