package vn.com.hieptt.myhiittimer.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Admin on 1/19/2018.
 */
@Dao
public interface TimerDao {
    @Query("SELECT * FROM UserTimer")
    List<Timer> getAll();

    @Query("SELECT * FROM UserTimer WHERE Timer_ID IN (:timerID)")
    Timer loadAllByIds(long timerID);

    @Query("SELECT * FROM UserTimer WHERE Timer_Name LIKE :timerName")
    Timer findByName(String timerName);

    @Query("SELECT * FROM UserTimer WHERE Number_OfSet IN (:numberOfSet)")
    Timer findByNumber(int numberOfSet);

    @Query("DELETE FROM UserTimer WHERE Timer_ID = (:timerID);")
    void deleteTimer(long timerID);

    @Insert
    void insertAll(Timer... timers);
    @Update
    void updateTimer(Timer timer);

    @Insert
    long insertObj(Timer timers);

    @Delete
    void delete(Timer timer);

}
