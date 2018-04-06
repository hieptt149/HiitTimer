package vn.com.hieptt.myhiittimer.home;

import android.os.AsyncTask;

import vn.com.hieptt.myhiittimer.data.AppDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 1/30/2018.
 */

public class AsnycTaskHomeGetList extends AsyncTask<List<ItemHomeRv>,Void,List<ItemHomeRv>> {
    private HomeScreenActivity activity;
    private AppDatabase appDatabase;

    public AsnycTaskHomeGetList(HomeScreenActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        appDatabase = AppDatabase.getAppDatabase(activity);

    }

    @Override
    protected List<ItemHomeRv> doInBackground(List<ItemHomeRv>[] voids) {
        List<ItemHomeRv> styles = new ArrayList<>();
          styles =      appDatabase.stylesDao().getDemoItem();
//            if (styles.size() > 0) {
//                for (ItemHomeRv demoItem : styles) {
//                    String nameTimer = demoItem.getNameTimer();
//                    long totalTimer = demoItem.getTotalTimer();
//                    ItemHomeRv itemHomeRv = new ItemHomeRv();
//                    itemHomeRv.setNameTimer(nameTimer);
//                    itemHomeRv.setTotalTimer(totalTimer);
////                    list.add(itemHomeRv);
//
//                }

//            }
        return styles;
    }

    @Override
    protected void onPostExecute(List<ItemHomeRv> list) {
        super.onPostExecute(list);
        AdapterRvHome adapterRvHome = new AdapterRvHome(activity,list,activity);
        activity.setAdapterRvHome(adapterRvHome,list);
        activity.showList();

    }
}
