package vn.com.hieptt.myhiittimer.createtimer;

import android.os.AsyncTask;

import vn.com.hieptt.myhiittimer.ActivitySeconds;
import vn.com.hieptt.myhiittimer.data.AppDatabase;
import vn.com.hieptt.myhiittimer.data.Styles;
import vn.com.hieptt.myhiittimer.data.Timer;

import java.util.List;

/**
 * Created by Admin on 1/31/2018.
 */

public class AsyncTaskLoadTimer extends AsyncTask<Void, Void, LoadTimerModel> {
    private ActivitySeconds activitySeconds;
    private AppDatabase database;
    private long id;

    public AsyncTaskLoadTimer(ActivitySeconds activitySeconds) {
        this.activitySeconds = activitySeconds;
    }

    @Override
    protected LoadTimerModel doInBackground(Void... Voids) {
        database = activitySeconds.createAppDataBase();
        id = activitySeconds.getIntentIdHomeLoadTimer();
        if (id >0){
            LoadTimerModel loadTimerModel = new LoadTimerModel();
            Timer timer = database.timerDao().loadAllByIds(id);
            List<Styles> styles = database.stylesDao().loadAllByIds(id);
            loadTimerModel.setEdtTimerName(timer.getTimerName());
            loadTimerModel.setEdtNumberOfSet(timer.getNumberOfSet()+"");

            loadTimerModel.setEdtWarpUpName(styles.get(0).getTitleType());
            loadTimerModel.setEdtWarpUpDuration(styles.get(0).getDuration()+"");
            loadTimerModel.setImgWarpUpColor(styles.get(0).getColorType());

            loadTimerModel.setEdtHighName(styles.get(1).getTitleType());
            loadTimerModel.setEdtHighDuration(styles.get(1).getDuration()+"");
            loadTimerModel.setImgHighColor(styles.get(1).getColorType());

            loadTimerModel.setEdtLowName(styles.get(2).getTitleType()+"");
            loadTimerModel.setEdtLowDuration(styles.get(2).getDuration()+"");
            loadTimerModel.setImgLowColor(styles.get(2).getColorType());

            loadTimerModel.setEdtCoolDownName(styles.get(3).getTitleType());
            loadTimerModel.setEdtCoolDownDuration(styles.get(3).getDuration()+"");
            loadTimerModel.setImgCoolDownColor(styles.get(3).getColorType());
            return loadTimerModel;
        }else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(LoadTimerModel loadTimerModel) {
        super.onPostExecute(loadTimerModel);
        activitySeconds.replaceFragment(id,loadTimerModel);

    }
}
