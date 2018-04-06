package vn.com.hieptt.myhiittimer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.iui.myhiittimer.R;

import vn.com.hieptt.myhiittimer.createtimer.AsyncTaskLoadTimer;
import vn.com.hieptt.myhiittimer.createtimer.FragmentCreateNewTimer;
import vn.com.hieptt.myhiittimer.createtimer.LoadTimerModel;
import vn.com.hieptt.myhiittimer.createtimer.OnShowFragment;
import vn.com.hieptt.myhiittimer.data.AppDatabase;
import vn.com.hieptt.myhiittimer.selectcolor.Keys;

public class ActivitySeconds extends AppCompatActivity implements OnShowFragment {
    private FragmentCreateNewTimer fragmentCreateNewTimer;
    private Fragment timerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_seconds);
        fragmentCreateNewTimer = new FragmentCreateNewTimer();
        if (getSupportFragmentManager().getBackStackEntryCount() == 0){
                AsyncTaskLoadTimer asyncTaskLoadTimer = new AsyncTaskLoadTimer(this);
                asyncTaskLoadTimer.execute();


        }

    }

    public void replaceFragment(long idItemLoadTimer,LoadTimerModel loadTimerModel) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putLong(Keys.KEY_ID_TO_ACTIVITY_FROM_CREATE,idItemLoadTimer);
        fragmentCreateNewTimer.setArguments(bundle);
        if (idItemLoadTimer >0){
            bundle.putSerializable(Keys.KEY_BUNDLE_CREATE,loadTimerModel);
        }
        fragmentCreateNewTimer.setArguments(bundle);
        transaction.replace(R.id.contaner_fragment, fragmentCreateNewTimer);
        transaction.commit();

    }
    public long getIntentIdHomeLoadTimer(){
        Intent intent = getIntent();
        long idItemLoadTimer = intent.getLongExtra(Keys.KEY_ID_TO_HOME_FROM_SECONDS,-1);
        return idItemLoadTimer;
    }
    public AppDatabase createAppDataBase (){
        return AppDatabase.getAppDatabase(this);
    }


    @Override
    public void onShowFragment(Fragment fragment, String tag) {
        timerFragment = fragment;
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contaner_fragment,fragment);
//        if (tag.equals("start")){
//        }else {
            transaction.addToBackStack(tag);
//        }
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount()>0){
            getSupportFragmentManager().popBackStackImmediate();
        }else{
            Log.d("SSS", "onBackPressed: ");
            super.onBackPressed();
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "myfragment", fragmentCreateNewTimer);
    }
    @Override
    public void onRestoreInstanceState(Bundle inState){
        fragmentCreateNewTimer = (FragmentCreateNewTimer) getSupportFragmentManager().getFragment(inState,"myfragment");
    }
}

