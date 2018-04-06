package vn.com.hieptt.myhiittimer.createtimer;

import android.os.AsyncTask;

import vn.com.hieptt.myhiittimer.data.AppDatabase;

/**
 * Created by Admin on 1/31/2018.
 */

public class AsyncTaskDeletel extends AsyncTask<Void, Void, Void> {
    private FragmentCreateNewTimer context;
    private AppDatabase database;
    private long id;

    public AsyncTaskDeletel(FragmentCreateNewTimer fragmentCreateNewTimer) {
        this.context = fragmentCreateNewTimer;
    }

    @Override
    protected Void doInBackground(Void... Voids) {
        database = context.createDataBaseFragmentCreate();
        id = context.getIdLoadTimer();
        if (id >0){
           database.timerDao().deleteTimer(id);
           database.stylesDao().deleteStyles(id);

        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        context.sendBroadCardHomeScreenUpdate();
        context.onBackScreenHome();

    }
}
