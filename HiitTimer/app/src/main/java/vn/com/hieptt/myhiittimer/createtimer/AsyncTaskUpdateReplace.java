package vn.com.hieptt.myhiittimer.createtimer;

import android.os.AsyncTask;

import vn.com.hieptt.myhiittimer.data.AppDatabase;
import vn.com.hieptt.myhiittimer.data.Styles;
import vn.com.hieptt.myhiittimer.data.Timer;

import java.util.List;

/**
 * Created by Admin on 1/31/2018.
 */

public class AsyncTaskUpdateReplace extends AsyncTask<List<Styles>, Void, Void> {
    private FragmentCreateNewTimer context;
    private AppDatabase database;
    private long id;

    public AsyncTaskUpdateReplace(FragmentCreateNewTimer fragmentCreateNewTimer) {
        this.context = fragmentCreateNewTimer;
    }


    @Override
    protected Void doInBackground(List<Styles>... lists) {
        database = context.createDataBaseFragmentCreate();
        id = context.getIdLoadTimer();
        if (id > 0) {
            List<Styles> styles = database.stylesDao().loadAllByIds(id);
            Timer timer = context.saveReplaceTimer();
            timer.setTimerID(id);
            database.timerDao().updateTimer(timer);
            Styles styles1 = lists[0].get(0);
            styles1.setTimerID(id);
            styles1.setIdType(styles.get(0).getIdType());
            Styles styles2 = lists[0].get(1);
            styles2.setTimerID(id);
            styles2.setIdType(styles.get(1).getIdType());
            Styles styles3 = lists[0].get(2);
            styles3.setTimerID(id);
            styles3.setIdType(styles.get(2).getIdType());
            Styles styles4 = lists[0].get(3);
            styles4.setTimerID(id);
            styles4.setIdType(styles.get(3).getIdType());

            database.stylesDao().updateStyles(styles1);
            database.stylesDao().updateStyles(styles2);
            database.stylesDao().updateStyles(styles3);
            database.stylesDao().updateStyles(styles4);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void loadTimerModel) {
        super.onPostExecute(loadTimerModel);
        context.sendBroadCardHomeScreenUpdate();


    }
}
