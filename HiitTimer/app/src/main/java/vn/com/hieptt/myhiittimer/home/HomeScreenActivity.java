package vn.com.hieptt.myhiittimer.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;

import vn.com.hieptt.myhiittimer.ActivitySeconds;
import com.iui.myhiittimer.R;
import vn.com.hieptt.myhiittimer.selectcolor.Keys;

import java.util.ArrayList;
import java.util.List;

public class HomeScreenActivity extends AppCompatActivity implements View.OnClickListener, OnItemHomeListner {

    private Animation slide_open;
    private Animation slide_closed;
    private RelativeLayout layout;
    private Button btnShow;
    private boolean isShow = false;
    private RecyclerView rvHome;
    private int mUpdate = -1;//mUpdate = 1 -> list is update
    private List<ItemHomeRv> homeRvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_layout);
        regiterBroadcard();
        rvHome = findViewById(R.id.home_rv);
        layout = findViewById(R.id.layout_show);
        btnShow = findViewById(R.id.home_btn_menushow);
        homeRvList = new ArrayList<>();
        Button btnLoadTimer = findViewById(R.id.btn_create_load_timer);
        Button btnCreateNewTimer = findViewById(R.id.btn_create_new_timer);
        AsnycTaskHomeGetList asnycTaskHomeGetList = new AsnycTaskHomeGetList(this);
        asnycTaskHomeGetList.execute();
        //Check orientation Screen
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            //code for portrait mode
            slide_closed = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.silde_down);

            slide_open = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.slide_up);
        } else {
            slide_open = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.silde_left);
            slide_closed = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.silde_right);
        }
        //set background button show rv
        if (isShow) {
            btnShow.setBackgroundResource(R.drawable.icon_menu_unclick);
        }
        // set onclick button show rv
        btnLoadTimer.setOnClickListener(this);
        btnShow.setOnClickListener(this);
        btnCreateNewTimer.setOnClickListener(this);
        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean(Keys.KEY_SAVE_HOME)) {
                isShow = false;
                showList();
            } else {
                isShow = true;
                showList();
            }
            Log.d("ABC", isShow + "");
        }

        /*Swipe Gestures top/bottom
        * Swipe Gestures left/right*/
        layout.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeTop() {
                onSwipeOnClick();
                //Toast.makeText(HomeScreenActivity.this, "top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeBottom() {
                onSwipeOnClick();
                //Toast.makeText(HomeScreenActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeLeft() {
                onSwipeOnClick();
                //Toast.makeText(HomeScreenActivity.this, "left", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {
                onSwipeOnClick();
                //Toast.makeText(HomeScreenActivity.this, "right", Toast.LENGTH_SHORT).show();
            }

        });

    }


    // fuction setting layout show list
    public void showList() {
        if (isShow) {
            if (homeRvList.size() > 0) {
                layout.startAnimation(slide_open);
                isShow = false;
            }

            rvHome.setVisibility(View.VISIBLE);
            btnShow.setBackgroundResource(R.drawable.icon_menu_isclick);
        } else {
            if (homeRvList.size() > 0) {
                layout.startAnimation(slide_closed);
                isShow = true;
            }

            rvHome.setVisibility(View.GONE);
            btnShow.setBackgroundResource(R.drawable.icon_menu_unclick);
        }


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_create_load_timer:
                showList();
                break;
            case R.id.home_btn_menushow:
                showList();
                break;
            case R.id.btn_create_new_timer:
                startActivitySeconds(-1);
                break;
        }
    }

    private void startActivitySeconds(long i) {
        Intent intent = new Intent(HomeScreenActivity.this, ActivitySeconds.class);
        intent.putExtra(Keys.KEY_ID_TO_HOME_FROM_SECONDS, i);
        startActivity(intent);
    }

    // item list is clicked
    @Override
    public void OnItemHomeClickLisner(View view, int position) {
        long id = homeRvList.get(position).getIdTimer();
        startActivitySeconds(id);


    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mUpdate = intent.getIntExtra(Keys.KEY_UPDATE, -1);
        }
    };

    public void regiterBroadcard() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Keys.ACTION_UPDATE_LIST_HOME);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    public void unRegisterBroadCard() {
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegisterBroadCard();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mUpdate > -1) {
            AsnycTaskHomeGetList asnycTaskHomeGetList = new AsnycTaskHomeGetList(this);
            asnycTaskHomeGetList.execute();
        }

    }

    public void setAdapterRvHome(AdapterRvHome adapterRvHome, List<ItemHomeRv> list) {
        if (list.size() > 0) {
            homeRvList = list;
            layout.setVisibility(View.VISIBLE);
            rvHome.setLayoutManager(new LinearLayoutManager(this));
            rvHome.setAdapter(adapterRvHome);
        } else {
            homeRvList = new ArrayList<>();
            layout.setVisibility(View.GONE);

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(Keys.KEY_SAVE_HOME, isShow);
    }

    @Override
    public void onBackPressed() {
        if (!isShow) {
            showList();
            isShow = false;
        } else {
            super.onBackPressed();
        }
    }

    private void onSwipeShowLayout() {
        if (homeRvList.size() > 0) {
            layout.setVisibility(View.VISIBLE);
            showList();
        } else {
            layout.setVisibility(View.GONE);
            showList();
        }

    }

    public void onSwipeOnClick() {
        final int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            //code for portrait mode
            onSwipeShowLayout();
        } else {
            onSwipeShowLayout();
        }
    }

}
