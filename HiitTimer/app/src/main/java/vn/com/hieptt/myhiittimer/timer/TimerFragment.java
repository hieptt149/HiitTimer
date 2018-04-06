package vn.com.hieptt.myhiittimer.timer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.iui.myhiittimer.R;
import vn.com.hieptt.myhiittimer.data.SubStyles;
import vn.com.hieptt.myhiittimer.data.SubTimer;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class TimerFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {

    private TextView tvIntervalTitle, tvTimer, tvIntervalCount, tvElapsedTime, tvRemainingTime;
    private Button btnDone, btnStart, btnReset;
    private ImageView ivPreviousInterval, ivNextInterval, ivLock;
    private ProgressBar pbTimer;
    private CircularSeekBar sbTimer;

    private CountDownTimer countDownTimer, timerAnimation;
    private ArrayList<Interval> lstIntervals, lstTotalIntervals;
    private Handler handler;
    private static boolean isLocked = false, next = false, previous = false, increase = true, rotate = false;

    private enum Status {
        START, PAUSE
    }

    private Status status = Status.PAUSE;
    private static int currInterval;
    private static long timeToGo, intervalDuration, elapsedTime, totalTime, totalInterval, delay;
    private static float haloWidth;
    private Bundle bundle;
    private SubStyles styles;
    private SubTimer timer;
    private Vibrator vibrator;
    private AudioManager audioManager;
    private MediaPlayer mainAlarm, secondaryAlarm;

    public TimerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvIntervalTitle = view.findViewById(R.id.tv_interval_title);
        tvTimer = view.findViewById(R.id.tv_timer);
        tvIntervalCount = view.findViewById(R.id.tv_interval_count);
        tvElapsedTime = view.findViewById(R.id.tv_elapsed_time);
        tvRemainingTime = view.findViewById(R.id.tv_remaining_time);
        btnDone = view.findViewById(R.id.btn_done);
        btnStart = view.findViewById(R.id.btn_start);
        btnReset = view.findViewById(R.id.btn_reset);
        ivPreviousInterval = view.findViewById(R.id.iv_previous_interval);
        ivNextInterval = view.findViewById(R.id.iv_next_interval);
        ivLock = view.findViewById(R.id.iv_lock);
        pbTimer = view.findViewById(R.id.pb_timer);
        sbTimer = view.findViewById(R.id.sb_timer);
        if (savedInstanceState != null) {
            currInterval = savedInstanceState.getInt("currinterval");
            elapsedTime = savedInstanceState.getLong("elapsedtime");
            status = (Status) savedInstanceState.getSerializable("status");
            timer = (SubTimer) savedInstanceState.getSerializable("timer");
            styles = (SubStyles) savedInstanceState.getSerializable("styles");
            intervalDuration = savedInstanceState.getLong("intervalduration");
            haloWidth = savedInstanceState.getFloat("halowidth");
            delay = savedInstanceState.getLong("delay");
            isLocked = savedInstanceState.getBoolean("locked");
            rotate = true;
        } else {
            getDataFromCreateTimer();
        }
        lstIntervals = getListIntervals();
        lstTotalIntervals = getListTotalIntervals(lstIntervals);
        totalTime = getTotalTime(lstTotalIntervals);
        totalInterval = lstTotalIntervals.size() - 1;
        ivPreviousInterval.setOnClickListener(this);
        ivNextInterval.setOnClickListener(this);
        ivLock.setOnClickListener(this);
        btnDone.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        sbTimer.setOnTouchListener(this);
        sbTimer.setOnSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar circularSeekBar, int progress, boolean fromUser) {
                circularSeekBar.setPointerHaloWidth(haloWidth);
                if (increase) {
                    if (haloWidth < 15) {
                        haloWidth += 3;
                    } else if (haloWidth == 15) {
                        increase = false;
                    }
                } else {
                    if (haloWidth > 0) {
                        haloWidth -= 3;
                    } else if (haloWidth == 0) {
                        increase = true;
                    }
                }
            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(CircularSeekBar seekBar) {

            }
        });
        if (savedInstanceState == null) {
            initView();
        } else {
            if (elapsedTime == 0) {
                initView();
            } else {
                vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
                mainAlarm = MediaPlayer.create(getActivity(), R.raw.main_alarm);
                secondaryAlarm = MediaPlayer.create(getActivity(), R.raw.secondary_alarm);
                tvIntervalTitle.setText(lstTotalIntervals.get(currInterval).getIntervalTitle());
                tvIntervalCount.setText((currInterval + 1) + "/" + (totalInterval + 1));
                tvTimer.setText(msTimeFormatter(timeToGo - elapsedTime + 1000));
                sbTimer.setMax((int) lstTotalIntervals.get(currInterval).getIntervalDuration());
                sbTimer.setPointerColor(lstTotalIntervals.get(currInterval).getIntervalColor());
                sbTimer.setPointerHaloColor(lstTotalIntervals.get(currInterval).getIntervalHaloColor());
                sbTimer.setCircleProgressColor(lstTotalIntervals.get(currInterval).getIntervalColor());
                tvElapsedTime.setText(msTimeFormatter(
                        lstTotalIntervals.get(currInterval).getIntervalDuration() - timeToGo + elapsedTime - 1000));
                tvRemainingTime.setText(msTimeFormatter(timeToGo - elapsedTime + 1000));
                pbTimer.setMax((int) lstTotalIntervals.get(currInterval).getIntervalDuration());
                pbTimer.getProgressDrawable().setColorFilter(lstTotalIntervals.get(currInterval).getIntervalColor(),
                        PorterDuff.Mode.SRC_IN);
                tvElapsedTime.setTextColor(lstTotalIntervals.get(currInterval).getIntervalColor());
                if (currInterval == 0) {
                    ivPreviousInterval.setVisibility(View.INVISIBLE);
                } else if (currInterval == totalInterval) {
                    ivNextInterval.setVisibility(View.INVISIBLE);
                }
                if (isLocked) {
                    ivPreviousInterval.setClickable(false);
                    ivNextInterval.setClickable(false);
                    btnDone.setClickable(false);
                    btnStart.setClickable(false);
                    btnReset.setClickable(false);
                    ivLock.setImageLevel(1);
                } else {
                    ivPreviousInterval.setClickable(true);
                    ivNextInterval.setClickable(true);
                    btnDone.setClickable(true);
                    btnStart.setClickable(true);
                    btnReset.setClickable(true);
                    ivLock.setImageLevel(0);
                }
                initTimer();
                initSeekBarProgress();
                if (status == Status.START) {
                    btnStart.setText("Pause");
                    handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            countDownTimer.start();
                        }
                    }, delay);
                    timerAnimation.start();
                } else {
                    btnStart.setText("Start");
                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currinterval", currInterval);
        outState.putLong("elapsedtime", elapsedTime);
        outState.putSerializable("status", status);
        outState.putSerializable("timer", timer);
        outState.putSerializable("styles", styles);
        outState.putLong("intervalduration", intervalDuration);
        outState.putFloat("halowidth", haloWidth);
        outState.putLong("delay", intervalDuration - (getTimeToGo() - elapsedTime));
        outState.putBoolean("locked", isLocked);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_previous_interval:
                if (countDownTimer != null) {
                    previous = true;
                    countDownTimer.cancel();
                    currInterval--;
                    initTimer();
                    countDownTimer.start();
                    status = Status.START;
                    btnStart.setText("Pause");
                } else {
                    Toast.makeText(getActivity(), "You must Start before going to Next/Previous interval", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_next_interval:
                if (countDownTimer != null) {
                    next = true;
                    countDownTimer.cancel();
                    currInterval++;
                    initTimer();
                    countDownTimer.start();
                    status = Status.START;
                    btnStart.setText("Pause");
                } else {
                    Toast.makeText(getActivity(), "You must Start before going to Next/Previous interval", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_done:
                if (status == Status.START) {
                    popupDialog("Are you sure you want to end this timer?",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //Đưa về màn hình chính
                                    getActivity().finish();
                                }
                            });
                } else {
                    //Đưa về màn hình chính
                    getActivity().finish();
                }
                break;
            case R.id.iv_lock:
                if (isLocked) {
                    isLocked = false;
                    ivPreviousInterval.setClickable(true);
                    ivNextInterval.setClickable(true);
                    btnDone.setClickable(true);
                    btnStart.setClickable(true);
                    btnReset.setClickable(true);
                    ivLock.setImageLevel(0);
                } else {
                    isLocked = true;
                    ivPreviousInterval.setClickable(false);
                    ivNextInterval.setClickable(false);
                    btnDone.setClickable(false);
                    btnStart.setClickable(false);
                    btnReset.setClickable(false);
                    ivLock.setImageLevel(1);
                }
                break;
            case R.id.btn_start:
                //Start timer
                if (status == Status.PAUSE) {
                    status = Status.START;
                    btnStart.setText("Pause");
                    //Trường hợp người dùng đã pause trước đó
                    if (countDownTimer != null) {
                        if (rotate) {
                            rotate = false;
                            handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    countDownTimer.start();
                                }
                            }, delay);
                            timerAnimation.start();
                        } else {
                            handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    countDownTimer.resume();
                                }
                            }, delay);
                            timerAnimation.resume();
                        }
                    } else {
                        initTimer();
                        countDownTimer.start();
                    }
                }
                //Pause timer
                else {
                    status = Status.PAUSE;
                    btnStart.setText("Start");
                    countDownTimer.pause();
                    timerAnimation.pause();
                    delay = intervalDuration - (getTimeToGo() - elapsedTime);
                }
                break;
            case R.id.btn_reset:
                popupDialog("Are you sure you want to reset?",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (countDownTimer != null) {
                                    countDownTimer.cancel();
                                    timerAnimation.cancel();
                                    countDownTimer = null;
                                    timerAnimation = null;
                                    status = Status.PAUSE;
                                    btnStart.setText("Start");
                                    initView();
                                }
                            }
                        });
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        if (timerAnimation != null) {
            timerAnimation.cancel();
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
    }

    /**
     * Khởi tạo view và các biến
     */
    private void initView() {
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        mainAlarm = MediaPlayer.create(getActivity(), R.raw.main_alarm);
        secondaryAlarm = MediaPlayer.create(getActivity(), R.raw.secondary_alarm);
        haloWidth = 0;
        currInterval = 0;
        elapsedTime = 0;
        intervalDuration = 0;
        delay = 0;
        status = Status.PAUSE;
        btnStart.setText("Start");
        tvIntervalTitle.setText(timer.getNameTimer());
        tvTimer.setText(msTimeFormatter(elapsedTime));
        tvIntervalCount.setText("0/" + (totalInterval + 1));
        sbTimer.setProgress((int) elapsedTime);
        sbTimer.setPointerColor(CircularSeekBar.DEFAULT_POINTER_COLOR);
        pbTimer.setProgress((int) elapsedTime);
        pbTimer.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorText), PorterDuff.Mode.SRC_IN);
        tvElapsedTime.setText(msTimeFormatter(0));
        tvElapsedTime.setTextColor(getResources().getColor(R.color.colorTextWhite));
        tvRemainingTime.setText(msTimeFormatter(0));
        ivNextInterval.setVisibility(View.VISIBLE);
        ivPreviousInterval.setVisibility(View.VISIBLE);
    }

    /**
     * Khởi tạo CountDownTimer
     */
    private void initTimer() {
        countDownTimer = new CountDownTimer(Long.MAX_VALUE, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (isAdded()) {
                    if (next) {
                        next = false;
                        elapsedTime = timeToGo;
                    }
                    if (previous) {
                        previous = false;
                        elapsedTime = getTimeToGo() - lstTotalIntervals.get(currInterval).getIntervalDuration();
                    }
                    timeToGo = getTimeToGo();
                    tvTimer.setText(msTimeFormatter(timeToGo - elapsedTime));
                    tvElapsedTime.setText(msTimeFormatter(
                            lstTotalIntervals.get(currInterval).getIntervalDuration() - timeToGo + elapsedTime));
                    tvRemainingTime.setText(msTimeFormatter(timeToGo - elapsedTime));
                    if (currInterval == 0) {
                        ivPreviousInterval.setVisibility(View.INVISIBLE);
                    } else if (currInterval == totalInterval) {
                        ivNextInterval.setVisibility(View.INVISIBLE);
                    } else {
                        ivNextInterval.setVisibility(View.VISIBLE);
                        ivPreviousInterval.setVisibility(View.VISIBLE);
                    }
                    if (timeToGo - elapsedTime <= 3000 && timeToGo - elapsedTime > 0) {
                        if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) {
                            secondaryAlarm.start();
                        }
                    }
                    //Nếu đã chạy đến cuối interval
                    if (timeToGo - elapsedTime == 0 && currInterval < totalInterval) {
                        currInterval++;
                        if (audioManager.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
                            vibrator.vibrate(500);
                        } else {
                            mainAlarm.start();
                        }
                    }
                    //Tại đầu mỗi interval
                    if (getTimeToGo() - elapsedTime == lstTotalIntervals.get(currInterval).getIntervalDuration()) {
                        tvIntervalTitle.setText(lstTotalIntervals.get(currInterval).getIntervalTitle());
                        tvIntervalCount.setText((currInterval + 1) + "/" + (totalInterval + 1));
                        sbTimer.setMax((int) lstTotalIntervals.get(currInterval).getIntervalDuration());
                        sbTimer.setPointerColor(lstTotalIntervals.get(currInterval).getIntervalColor());
                        sbTimer.setPointerHaloColor(lstTotalIntervals.get(currInterval).getIntervalHaloColor());
                        sbTimer.setCircleProgressColor(lstTotalIntervals.get(currInterval).getIntervalColor());
                        pbTimer.setMax((int) lstTotalIntervals.get(currInterval).getIntervalDuration());
                        pbTimer.getProgressDrawable().setColorFilter(lstTotalIntervals.get(currInterval).getIntervalColor(),
                                PorterDuff.Mode.SRC_IN);
                        tvElapsedTime.setTextColor(lstTotalIntervals.get(currInterval).getIntervalColor());
                        if (timerAnimation != null) {
                            timerAnimation.cancel();
                            timerAnimation = null;
                        }
                        intervalDuration = lstTotalIntervals.get(currInterval).getIntervalDuration();
                        initSeekBarProgress();
                        timerAnimation.start();
                    }
                    elapsedTime += 1000;
                    //Nếu timer đã chạy hết thì khởi tạo lại các biến
                    if (elapsedTime > totalTime) {
                        mainAlarm.release();
                        secondaryAlarm.release();
                        countDownTimer.cancel();
                        timerAnimation.cancel();
                        status = Status.PAUSE;
                        countDownTimer = null;
                        timerAnimation = null;
                        initView();
                    }
                }
            }

            @Override
            public void onFinish() {

            }
        };
    }

    private void initSeekBarProgress() {
        timerAnimation = new CountDownTimer(Long.MAX_VALUE, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                sbTimer.setProgress((int) intervalDuration);
                pbTimer.setProgress((int) (pbTimer.getMax() - intervalDuration));
                intervalDuration -= 100;
            }

            @Override
            public void onFinish() {

            }
        };
    }

    private void getDataFromCreateTimer() {
        bundle = getArguments();
        if (bundle.containsKey("timer") && bundle.containsKey("styles")) {
            timer = (SubTimer) bundle.getSerializable("timer");
            styles = (SubStyles) bundle.getSerializable("styles");
        }
    }

    /**
     * Hiện cửa sổ popup
     *
     * @param message
     * @param positiveOnClick
     */
    private void popupDialog(String message, DialogInterface.OnClickListener positiveOnClick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(R.drawable.ic_warning)
                .setTitle("Notification")
                .setMessage(message)
                .setPositiveButton("Yes", positiveOnClick)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    /**
     * Format thời gian theo định dạng mm:ss
     *
     * @param milliseconds
     * @return
     */
    private String msTimeFormatter(long milliseconds) {
        String ms = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(milliseconds),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)));
        return ms;
    }

    /**
     * Lấy ra tổng thời gian cần chạy cho đến hết interval hiện tại
     *
     * @return
     */
    public long getTimeToGo() {
        long timeToGo = 0;
        for (int i = 0; i <= currInterval; i++) {
            timeToGo += lstTotalIntervals.get(i).getIntervalDuration();
        }
        return timeToGo;
    }

    private ArrayList<Interval> getListIntervals() {
        ArrayList<Interval> lstIntervals = new ArrayList<>();
        if (styles.getDuartionWampUp() != 0) {
            lstIntervals.add(new Interval(1,
                    styles.getNameWampUp(),
                    styles.getDuartionWampUp() * 1000,
                    getIntervalColor(styles.getDrawColorWampUp()),
                    getIntervalHaloColor(styles.getDrawColorWampUp())));
        }
        lstIntervals.add(new Interval(2,
                styles.getNameHigh(),
                styles.getDuartionHigh() * 1000,
                getIntervalColor(styles.getDrawColorHigh()),
                getIntervalHaloColor(styles.getDrawColorHigh())));
        lstIntervals.add(new Interval(3,
                styles.getNameLow(),
                styles.getDuartionLow() * 1000,
                getIntervalColor(styles.getDrawColorLow()),
                getIntervalHaloColor(styles.getDrawColorLow())));
        if (styles.getDuartionCoolDown() != 0) {
            lstIntervals.add(new Interval(4,
                    styles.getNameCoolDown(),
                    styles.getDuartionCoolDown() * 1000,
                    getIntervalColor(styles.getDrawColorCoolDown()),
                    getIntervalHaloColor(styles.getDrawColorCoolDown())));
        }
        return lstIntervals;
    }

    private int getIntervalColor(int drawable) {
        int color = 0;
        switch (drawable) {
            case R.drawable.draw_color_blue:
                color = Color.argb(255, 61, 66, 255);
                break;
            case R.drawable.draw_color_deep_purple:
                color = Color.argb(255, 142, 66, 132);
                break;
            case R.drawable.draw_color_defaut:
                color = Color.argb(255, 0, 216, 255);
                break;
            case R.drawable.draw_color_green:
                color = Color.argb(255, 74, 242, 23);
                break;
            case R.drawable.draw_color_magenta:
                color = Color.argb(255, 241, 8, 211);
                break;
            case R.drawable.draw_color_orange:
                color = Color.argb(255, 255, 168, 0);
                break;
            case R.drawable.draw_color_purple:
                color = Color.argb(255, 194, 8, 241);
                break;
            case R.drawable.draw_color_red:
                color = Color.argb(255, 230, 14, 14);
                break;
            case R.drawable.draw_color_teal:
                color = Color.argb(255, 23, 172, 242);
                break;
            case R.drawable.draw_color_yellow:
                color = Color.argb(255, 246, 255, 0);
                break;
        }
        return color;
    }

    private int getIntervalHaloColor(int drawable) {
        int haloColor = 0;
        switch (drawable) {
            case R.drawable.draw_color_blue:
                haloColor = Color.argb(100, 61, 66, 255);
                break;
            case R.drawable.draw_color_deep_purple:
                haloColor = Color.argb(100, 142, 66, 132);
                break;
            case R.drawable.draw_color_defaut:
                haloColor = Color.argb(100, 0, 216, 255);
                break;
            case R.drawable.draw_color_green:
                haloColor = Color.argb(100, 74, 242, 23);
                break;
            case R.drawable.draw_color_magenta:
                haloColor = Color.argb(100, 241, 8, 211);
                break;
            case R.drawable.draw_color_orange:
                haloColor = Color.argb(100, 255, 168, 0);
                break;
            case R.drawable.draw_color_purple:
                haloColor = Color.argb(100, 194, 8, 241);
                break;
            case R.drawable.draw_color_red:
                haloColor = Color.argb(100, 230, 14, 14);
                break;
            case R.drawable.draw_color_teal:
                haloColor = Color.argb(100, 23, 172, 242);
                break;
            case R.drawable.draw_color_yellow:
                haloColor = Color.argb(100, 246, 255, 0);
                break;
        }
        return haloColor;
    }

    /**
     * Tạo danh sách tổng các interval cần chạy
     *
     * @param lstIntervals
     * @return
     */
    private ArrayList<Interval> getListTotalIntervals(ArrayList<Interval> lstIntervals) {
        ArrayList<Interval> lstTotalIntervals = new ArrayList<>();
        for (int i = 0; i < timer.getNumberOfSet(); i++) {
            lstTotalIntervals.addAll(lstIntervals);
        }
        return lstTotalIntervals;
    }

    /**
     * Lấy ra tổng thời gian của timer
     *
     * @param lstTotalIntervals
     * @return
     */
    private long getTotalTime(ArrayList<Interval> lstTotalIntervals) {
        long totalTime = 0;
        for (Interval interval : lstTotalIntervals) {
            totalTime += interval.getIntervalDuration();
        }
        return totalTime;
    }
}