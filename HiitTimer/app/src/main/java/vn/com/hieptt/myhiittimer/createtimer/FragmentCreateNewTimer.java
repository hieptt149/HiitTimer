package vn.com.hieptt.myhiittimer.createtimer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iui.myhiittimer.R;
import vn.com.hieptt.myhiittimer.data.AppDatabase;
import vn.com.hieptt.myhiittimer.data.Styles;
import vn.com.hieptt.myhiittimer.data.SubStyles;
import vn.com.hieptt.myhiittimer.data.SubTimer;
import vn.com.hieptt.myhiittimer.data.Timer;
import vn.com.hieptt.myhiittimer.selectcolor.FragmentSelectColor;
import vn.com.hieptt.myhiittimer.selectcolor.Keys;
import vn.com.hieptt.myhiittimer.timer.TimerFragment;

import java.util.ArrayList;
import java.util.List;

import br.com.sapereaude.maskedEditText.MaskedEditText;

public class FragmentCreateNewTimer extends Fragment implements View.OnClickListener {
    private OnShowFragment onShowFragment;
    private TextView btnCancel;
    private ImageView btnStart;
    private static int mColorWampUp = R.drawable.draw_color_yellow,
            mColorHigh = R.drawable.draw_color_orange,
            mColorLow = R.drawable.draw_color_purple,
            mColorCoolDown = R.drawable.draw_color_defaut;
    private int coutColor = -1;
    private View viewAble;
    private EditText edtTimerName, edtNumberOfSet,
            edtWarpUpName, edtWarpUpDuration,
            edtHighName, edtHighDuration,
            edtLowName, edtLowDuration,
            edtCoolDownName, edtCoolDownDuration;
    private ConstraintLayout layoutTimerName, layoutNumberOfSet,
            layoutWarpUpName, layoutWarpUpDuration,
            layoutHighName, layoutHighDuration,
            layoutLowName, layoutLowDuration,
            layoutCoolDownName, layoutCoolDownDuration;
    private ImageView imgWarpUpColor, imgHighColor, imgLowColor, imgCoolDownColor;
    private Button btnSave, btnDelete, btnSaveReplace;
    private long idTimerObjSave;
    private long durationWarpUp, durationHigh, durationLow, durationCoolDown;
    private static long id;
    private static boolean isNew;
    private boolean isLoad = false;
    private boolean isDurationType = true;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onShowFragment = (OnShowFragment) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isNew = true;
        regiterBroadcard();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_new_timer, container, false);
        //button
        btnCancel = view.findViewById(R.id.toolbar_cancel_txt);
        btnStart = view.findViewById(R.id.toolbar_start_img);
        btnSave = view.findViewById(R.id.create_btn_save);
        btnDelete = view.findViewById(R.id.create_btn_delete);
        btnSaveReplace = view.findViewById(R.id.create_btn_savereplace);
        //edt
        edtTimerName = view.findViewById(R.id.create_name_timer_edt);
        edtNumberOfSet = view.findViewById(R.id.create_num_of_set_edt);
        edtWarpUpName = view.findViewById(R.id.create_warmup_name_edt);
        edtWarpUpDuration = (MaskedEditText) view.findViewById(R.id.create_warmup_duration_edt);
        edtHighName = view.findViewById(R.id.create_high_name_edt);
        edtHighDuration = (MaskedEditText) view.findViewById(R.id.create_high_duration_edt);
        edtLowName = view.findViewById(R.id.create_low_name_edt);
        edtLowDuration = (MaskedEditText) view.findViewById(R.id.create_low_duration_edt);
        edtCoolDownName = view.findViewById(R.id.create_cooldown_name_edt);
        edtCoolDownDuration = (MaskedEditText) view.findViewById(R.id.create_cooldown_duration_edt);

        // layout
        layoutTimerName = view.findViewById(R.id.create_layout_name_timer_edt);
        layoutNumberOfSet = view.findViewById(R.id.create_layout_num_of_set_edt);
        layoutWarpUpName = view.findViewById(R.id.create_layout_warmup_name_edt);
        layoutWarpUpDuration = view.findViewById(R.id.create_layout_warmup_duration_edt);
        layoutHighName = view.findViewById(R.id.create_layout_high_name_edt);
        layoutHighDuration = view.findViewById(R.id.create_layout_high_duration_edt);
        layoutLowName = view.findViewById(R.id.create_layout_low_name_edt);
        layoutLowDuration = view.findViewById(R.id.create_layout_low_duration_edt);
        layoutCoolDownName = view.findViewById(R.id.create_layout_cooldown_name_edt);
        layoutCoolDownDuration = view.findViewById(R.id.create_layout_cooldown_duration_edt);
        //image
        imgWarpUpColor = view.findViewById(R.id.create_warmup_color_img);
        imgHighColor = view.findViewById(R.id.create_high_color_img);
        imgLowColor = view.findViewById(R.id.create_low_color_img);
        imgCoolDownColor = view.findViewById(R.id.create_cooldown_color_img);
        if (isNew) {
            Bundle bundle = getArguments();
            id = bundle.getLong(Keys.KEY_ID_TO_ACTIVITY_FROM_CREATE, -1);
            if (id > 0) {
                isLoad = true;
                LoadTimerModel loadTimer1 = (LoadTimerModel) bundle.getSerializable(Keys.KEY_BUNDLE_CREATE);
                setDataEdt(loadTimer1);
                btnDelete.setVisibility(View.VISIBLE);
                btnSave.setText("SAVE NEW TIMER");
                btnSaveReplace.setVisibility(View.VISIBLE);
            }else {
                setColorIsNewCreate();
            }
            isNew = false;

        } else {
            setColorIsNewCreate();
            if (isLoad) {
                btnDelete.setVisibility(View.VISIBLE);
                btnSave.setText("SAVE NEW TIMER");
                btnSaveReplace.setVisibility(View.VISIBLE);
            }
        }

        return view;
    }

    private void setColorIsNewCreate() {
        imgWarpUpColor.setBackgroundResource(mColorWampUp);
        imgHighColor.setBackgroundResource(mColorHigh);
        imgLowColor.setBackgroundResource(mColorLow);
        imgCoolDownColor.setBackgroundResource(mColorCoolDown);
        imgWarpUpColor.setTag(mColorWampUp);
        imgHighColor.setTag(mColorHigh);
        imgLowColor.setTag(mColorLow);
        imgCoolDownColor.setTag(mColorCoolDown);

    }

    private void setDataEdt(LoadTimerModel loadTimer) {
        edtTimerName.setText(loadTimer.getEdtTimerName());
        edtNumberOfSet.setText(loadTimer.getEdtNumberOfSet());

        edtWarpUpName.setText(loadTimer.getEdtWarpUpName());
        edtWarpUpDuration.setText(setTextDuration(loadTimer.getEdtWarpUpDuration()));

        edtHighName.setText(loadTimer.getEdtHighName());
        edtHighDuration.setText(setTextDuration(loadTimer.getEdtHighDuration()));

        edtLowName.setText(loadTimer.getEdtLowName());
        edtLowDuration.setText(setTextDuration(loadTimer.getEdtLowDuration()));

        edtCoolDownName.setText(loadTimer.getEdtCoolDownName());
        edtCoolDownDuration.setText(setTextDuration(loadTimer.getEdtCoolDownDuration()));

        imgWarpUpColor.setBackgroundResource(loadTimer.getImgWarpUpColor());
        imgHighColor.setBackgroundResource(loadTimer.getImgHighColor());
        imgLowColor.setBackgroundResource(loadTimer.getImgLowColor());
        imgCoolDownColor.setBackgroundResource(loadTimer.getImgCoolDownColor());
        imgWarpUpColor.setTag(loadTimer.getImgWarpUpColor());
        imgHighColor.setTag(loadTimer.getImgHighColor());
        imgLowColor.setTag(loadTimer.getImgLowColor());
        imgCoolDownColor.setTag(loadTimer.getImgCoolDownColor());
        mColorWampUp = getImageResource(imgWarpUpColor);
        mColorHigh = getImageResource(imgHighColor);
        mColorLow = getImageResource(imgLowColor);
        mColorCoolDown = getImageResource(imgCoolDownColor);
    }

    // format string duration when load timer data
    private String setTextDuration(String duration) {
        String totalDuration = "";
        int durationInt = Integer.parseInt(duration);
        int m = (int) (durationInt / 60);
        int s = (int) (durationInt % 60);
        if (m < 60 && m > 9) {
            if (s < 10 && s != 0) {
                totalDuration = (m + "0" + s);
            } else if (s == 0) {
                totalDuration = (m + "0" + "0");
            } else {
                totalDuration = (m + "" + s + "");
            }
        } else if (m <= 9 && m >= 1) {
            if (s < 10 && s != 0) {
                totalDuration = ("0" + m + "0" + s);
            } else if (s == 0) {
                totalDuration = ("0" + m + "0" + "0");
            } else {
                totalDuration = ("0" + m + "" + s + "");
            }

        } else if (m < 1) {
            if (s < 10 && s != 0) {
                totalDuration = ("0" + "0" + "0" + s);
            } else if (s == 0) {
                totalDuration = ("0" + "0" + "0" + "0");
            } else {
                totalDuration = ("0" + "0" + "" + s);
            }

        } else if (m > 99) {
            totalDuration = ("9999");
        }
        return totalDuration;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //click layout focus edittext
        layoutTimerName.setOnClickListener(this);
        layoutNumberOfSet.setOnClickListener(this);
        layoutWarpUpName.setOnClickListener(this);
        layoutWarpUpDuration.setOnClickListener(this);
        layoutHighName.setOnClickListener(this);
        layoutHighDuration.setOnClickListener(this);
        layoutLowName.setOnClickListener(this);
        layoutLowDuration.setOnClickListener(this);
        layoutCoolDownName.setOnClickListener(this);
        layoutCoolDownDuration.setOnClickListener(this);
        //btn click
        btnCancel.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnSaveReplace.setOnClickListener(this);
        //imgcolor click
        imgWarpUpColor.setOnClickListener(this);
        imgHighColor.setOnClickListener(this);
        imgLowColor.setOnClickListener(this);
        imgCoolDownColor.setOnClickListener(this);
        //check input edit text
        checkInputDuration(edtWarpUpDuration);
        checkInputDuration(edtHighDuration);
        checkInputDuration(edtLowDuration);
        checkInputDuration(edtCoolDownDuration);


    }

    private boolean checkInputDuration(final EditText editText) {
        isDurationType = true;
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 5){
                    String durationMinString = charSequence.toString().charAt(0) + "" + charSequence.toString().charAt(1);
                    String durationSecordsString = charSequence.toString().charAt(3) + "" + charSequence.toString().charAt(4);
                    if (Integer.parseInt(durationMinString) == 60){
                        if (Integer.parseInt(durationSecordsString) == 0){
                            isDurationType = true;
                        }else {
                            Toast.makeText(getContext(),"Max time is 60:00 minute , Please retype duration !",Toast.LENGTH_SHORT).show();
                            isDurationType = false;
                        }
                    }else if (Integer.parseInt(durationMinString) < 60){
                        if (Integer.parseInt(durationSecordsString) > 59){
                            isDurationType = false;
                            Toast.makeText(getContext(),"Max seconds is 59 , Please retype duration seconds !",Toast.LENGTH_SHORT).show();
                        }else {
                            isDurationType = true;
                        }
                    }else if (Integer.parseInt(durationMinString) > 60){
                        isDurationType = false;
                        Toast.makeText(getContext(),"Max time is 60:00 minute , Please retype duration !",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    isDurationType = true;
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        return isDurationType;
    }

    @Override
    public void onClick(View view) {
        //create fragment replace
        FragmentSelectColor fragmentColor = new FragmentSelectColor();
        TimerFragment fragmentStart = new TimerFragment();
        // Model save data and start Timer
        SubTimer timer = new SubTimer();
        SubStyles styles = new SubStyles();
        switch (view.getId()) {
            //cancel
            case R.id.toolbar_cancel_txt:
                onBackScreenHome();
                break;
            //start timer
            case R.id.toolbar_start_img:
                Log.d("SSS", "onClick: "+checkInputDuration(edtWarpUpDuration));
                if (checkEmptyDataInput() == 1 && checkDurationType()) {
                    if ( checkInputDuration(edtWarpUpDuration)&&
                            checkInputDuration(edtHighDuration)&&
                            checkInputDuration(edtLowDuration)&&
                            checkInputDuration(edtCoolDownDuration)){
                        sendDataToStart(fragmentStart, timer, styles);
                        onShowFragment.onShowFragment(fragmentStart, "start");
                    }else {
                        Toast.makeText(getActivity(),"Duration Type Error !",Toast.LENGTH_SHORT).show();
                    }

                }
                break;
            //save data
            case R.id.create_btn_save:
                //check input != null and check format duration
                if (checkEmptyDataInput() == 1 && checkDurationType()) {
                    if ( checkInputDuration(edtWarpUpDuration)&&
                            checkInputDuration(edtHighDuration)&&
                            checkInputDuration(edtLowDuration)&&
                            checkInputDuration(edtCoolDownDuration)){
                        //save to table Timer
                        saveTimer();
                        //save to table Styles
                        saveStyles(Keys.WAMP_UP, edtWarpUpName.getText().toString(), durationWarpUp, mColorWampUp);
                        saveStyles(Keys.HIGH_INTENSITY, edtHighName.getText().toString(), durationHigh, mColorHigh);
                        saveStyles(Keys.LOW_INTENSITY, edtLowName.getText().toString(), durationLow, mColorLow);
                        saveStyles(Keys.COOL_DOWN, edtCoolDownName.getText().toString(), durationCoolDown, mColorCoolDown);
                        sendBroadCardHomeScreenUpdate();
                        //intent key to home screen update list load timer
                        Toast.makeText(getActivity(), "Save Complete !", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(),"Duration Type Error !",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            //delete load timer
            case R.id.create_btn_delete:
                //show arlegt dialog comit
                showdiaglog();
                break;
            //save replace this timer
            case R.id.create_btn_savereplace:
                if (checkEmptyDataInput() == 1 && checkDurationType()) {
                    if ( checkInputDuration(edtWarpUpDuration)&&
                            checkInputDuration(edtHighDuration)&&
                            checkInputDuration(edtLowDuration)&&
                            checkInputDuration(edtCoolDownDuration)){
                        Styles styles1 = saveStylesReplace(Keys.WAMP_UP, edtWarpUpName.getText().toString(), durationWarpUp, mColorWampUp);
                        Styles styles2 = saveStylesReplace(Keys.HIGH_INTENSITY, edtHighName.getText().toString(), durationHigh, mColorHigh);
                        Styles styles3 = saveStylesReplace(Keys.LOW_INTENSITY, edtLowName.getText().toString(), durationLow, mColorLow);
                        Styles styles4 = saveStylesReplace(Keys.COOL_DOWN, edtCoolDownName.getText().toString(), durationCoolDown, mColorCoolDown);
                        List<Styles> stylesList = new ArrayList<>();
                        stylesList.add(styles1);
                        stylesList.add(styles2);
                        stylesList.add(styles3);
                        stylesList.add(styles4);
                        AsyncTaskUpdateReplace asyncTaskUpdateReplace = new AsyncTaskUpdateReplace(FragmentCreateNewTimer.this);
                        asyncTaskUpdateReplace.execute(stylesList);
                        Toast.makeText(getActivity(), "Save Replace Complete !", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(),"Duration Type Error !",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            //change color timer
            case R.id.create_warmup_color_img:
                coutColor = 1;
                sendBundleColor(coutColor, fragmentColor);
                onShowFragment.onShowFragment(fragmentColor, "color");
                break;
            case R.id.create_high_color_img:
                coutColor = 2;
                sendBundleColor(coutColor, fragmentColor);
                onShowFragment.onShowFragment(fragmentColor, "color");
                break;
            case R.id.create_low_color_img:
                coutColor = 3;
                sendBundleColor(coutColor, fragmentColor);
                onShowFragment.onShowFragment(fragmentColor, "color");
                break;
            case R.id.create_cooldown_color_img:
                coutColor = 4;
                sendBundleColor(coutColor, fragmentColor);
                onShowFragment.onShowFragment(fragmentColor, "color");
                break;
            //change focus edittext to layout parent
            case R.id.create_layout_name_timer_edt:
                showInputKeyboard(edtTimerName);
                break;
            case R.id.create_layout_num_of_set_edt:
                showInputKeyboard(edtNumberOfSet);
                break;
            case R.id.create_layout_warmup_name_edt:
                showInputKeyboard(edtWarpUpName);
                break;
            case R.id.create_layout_warmup_duration_edt:
                showInputKeyboard(edtWarpUpDuration);
                break;
            case R.id.create_layout_high_name_edt:
                showInputKeyboard(edtHighName);
                break;
            case R.id.create_layout_high_duration_edt:
                showInputKeyboard(edtHighDuration);
                break;
            case R.id.create_layout_low_name_edt:
                showInputKeyboard(edtLowName);
                break;
            case R.id.create_layout_low_duration_edt:
                showInputKeyboard(edtLowDuration);
                break;
            case R.id.create_layout_cooldown_name_edt:
                showInputKeyboard(edtCoolDownName);
                break;
            case R.id.create_layout_cooldown_duration_edt:
                showInputKeyboard(edtCoolDownDuration);
                break;


        }
    }

    private void sendBundleColor(int coutColor, FragmentSelectColor selectColor) {
        Bundle bundle1 = new Bundle();
        bundle1.putInt("count", coutColor);
        bundle1.putInt("color1", getImageResource(imgWarpUpColor));
        bundle1.putInt("color2", getImageResource(imgHighColor));
        bundle1.putInt("color3", getImageResource(imgLowColor));
        bundle1.putInt("color4", getImageResource(imgCoolDownColor));
        selectColor.setArguments(bundle1);
    }

    public void sendBroadCardHomeScreenUpdate() {
        Intent intent = new Intent(Keys.ACTION_UPDATE_LIST_HOME);
        intent.putExtra(Keys.KEY_UPDATE, 1);
        getActivity().sendBroadcast(intent);
    }

    // show dialog deletel
    private void showdiaglog() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getContext());
        }
        builder.setTitle("Delete Timer")
                .setMessage("Are you sure you want to delete this timer ?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        AsyncTaskDeletel asyncTaskDeletel = new AsyncTaskDeletel(FragmentCreateNewTimer.this);
                        asyncTaskDeletel.execute();
                        Toast.makeText(getContext(), "Delete Complete !", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    // Check null input edit text
    private int checkEmptyDataInput() {
        int check = 0;
        if (TextUtils.isEmpty(edtTimerName.getText().toString()) || TextUtils.isEmpty(edtNumberOfSet.getText().toString()) ||
                TextUtils.isEmpty(edtWarpUpName.getText().toString()) ||
                TextUtils.isEmpty(edtHighName.getText().toString()) || TextUtils.isEmpty(edtHighDuration.getText().toString()) ||
                TextUtils.isEmpty(edtLowName.getText().toString()) || TextUtils.isEmpty(edtLowDuration.getText().toString()) ||
                TextUtils.isEmpty(edtCoolDownName.getText().toString())) {
            Toast.makeText(getContext(), "You can just ignore duration warm up and duration cool down " + "\n" + " Please enter all the remaining fields !", Toast.LENGTH_SHORT).show();
        } else if ((!TextUtils.isEmpty(edtWarpUpDuration.getText().toString()) && edtWarpUpDuration.getText().toString().length() < 5) ||
                (!TextUtils.isEmpty(edtCoolDownDuration.getText().toString()) && edtCoolDownDuration.getText().toString().length() < 5)) {
            Toast.makeText(getContext(), "Wrong format !\n Please check the format duration warm up and duration cool down", Toast.LENGTH_SHORT).show();
        } else if (edtHighDuration.getText().toString().length() < 5 || edtLowDuration.getText().toString().length() < 5) {
            Toast.makeText(getContext(), "Wrong format !\n Please check the format duration high and duration low", Toast.LENGTH_SHORT).show();
        } else {
            check = 1;
        }
        return check;
    }

    // send data from Screen Create to Timer screen
    private void sendDataToStart(TimerFragment fragmentStart, SubTimer timer, SubStyles styles) {
        if (checkDurationType()) {
            Log.d("ABC", durationWarpUp + "\n" + durationHigh + "\n" + durationLow + "\n" + durationCoolDown);
            int idWampUp = getImageResource(imgWarpUpColor);
            int idHighColor = getImageResource(imgHighColor);
            int idLowColor = getImageResource(imgLowColor);
            int idCoolDownColor = getImageResource(imgCoolDownColor);

            timer.setNameTimer(edtTimerName.getText().toString());
            timer.setNumberOfSet(Integer.parseInt(edtNumberOfSet.getText().toString()));

            styles.setNameWampUp(edtWarpUpName.getText().toString());
            styles.setDuartionWampUp(durationWarpUp);
            styles.setDrawColorWampUp(idWampUp);

            styles.setNameHigh(edtHighName.getText().toString());
            styles.setDuartionHigh(durationHigh);
            styles.setDrawColorHigh(idHighColor);

            styles.setNameLow(edtLowName.getText().toString());
            styles.setDuartionLow(durationLow);
            styles.setDrawColorLow(idLowColor);

            styles.setNameCoolDown(edtCoolDownName.getText().toString());
            styles.setDuartionCoolDown(durationCoolDown);
            styles.setDrawColorCoolDown(idCoolDownColor);

            Bundle bundle = new Bundle();
            bundle.putSerializable("timer", timer);
            bundle.putSerializable("styles", styles);
            fragmentStart.setArguments(bundle);
        }

    }

    // if editext duration Wamp Up and Cooldown = " " -> duration set = 0;
    private long totalDuration(EditText editText) {
        long totalSecons;
        if (TextUtils.isEmpty(editText.getText().toString())) {
            totalSecons = 0;

        } else {
            String durationMinString = editText.getText().toString().charAt(0) + "" + editText.getText().toString().charAt(1);
            String durationSecordsString = editText.getText().toString().charAt(3) + "" + editText.getText().toString().charAt(4);
            long minInhour = (Long.parseLong(durationMinString)) * 60;
            totalSecons = minInhour + (Long.parseLong(durationSecordsString));

        }
        return totalSecons;
    }

    //reveice color drawable to screen SelectColor
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int color1 = intent.getIntExtra(Keys.KEY_COLOR1, -1);
            int color2 = intent.getIntExtra(Keys.KEY_COLOR2, -1);
            int color3 = intent.getIntExtra(Keys.KEY_COLOR3, -1);
            int color4 = intent.getIntExtra(Keys.KEY_COLOR4, -1);
            int count = intent.getIntExtra("count1", -1);
            switch (count) {
                case 1:
                    mColorWampUp = color1;
                    setImgColor(color1, color2, color3, color4);
                    break;
                case 2:
                    mColorHigh = color2;
                    setImgColor(color1, color2, color3, color4);
                    break;
                case 3:
                    mColorLow = color3;
                    setImgColor(color1, color2, color3, color4);
                    break;
                case 4:
                    mColorCoolDown = color4;
                    setImgColor(color1, color2, color3, color4);
                    break;
            }
            if (isLoad){
                btnDelete.setVisibility(View.VISIBLE);
                btnSave.setText("SAVE NEW TIMER");
                btnSaveReplace.setVisibility(View.VISIBLE);
            }
        }
    };

    private void setImgColor(int color1, int color2, int color3, int color4) {
        imgWarpUpColor.setBackgroundResource(color1);
        imgWarpUpColor.setTag(color1);
        imgHighColor.setBackgroundResource(color2);
        imgHighColor.setTag(color2);
        imgLowColor.setBackgroundResource(color3);
        imgLowColor.setTag(color3);
        imgCoolDownColor.setBackgroundResource(color4);
        imgCoolDownColor.setTag(color4);

    }

    //regitster broadcardReceiver
    public void regiterBroadcard() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Keys.ACTION_UPDATE_COLOR);
        getActivity().registerReceiver(broadcastReceiver, intentFilter);
    }

    //unregitster broadcardReceiver
    public void unRegisterBroadCard() {
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegisterBroadCard();
    }

    //closed keyboadinput if screen Create is stop
    @Override
    public void onPause() {
        super.onPause();
        if (viewAble != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(viewAble.getWindowToken(), 0);
        }
    }

    //if layout item click -> change focus to edittext and show keyboard input
    public void showInputKeyboard(EditText editText) {
        viewAble = editText;
        editText.requestFocus();
        editText.setSelection(editText.getText().length());
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    //save Styles
    private void saveStyles(String type, String titleType, long duration, int color) {
        Styles styles = new Styles();
        styles.setTimerID(idTimerObjSave);
        styles.setType(type);
        styles.setTitleType(titleType);
        styles.setDuration(duration);
        styles.setColorType(color);
        AppDatabase.getAppDatabase(getActivity()).stylesDao().insertAll(styles);


    }

    private Styles saveStylesReplace(String type, String titleType, long duration, int color) {
        Styles styles = new Styles();
        styles.setType(type);
        styles.setTitleType(titleType);
        styles.setDuration(duration);
        styles.setColorType(color);
        return styles;
    }

    //save Timer
    private void saveTimer() {
        Timer timer = new Timer();
        timer.setTimerName(edtTimerName.getText().toString());
        timer.setNumberOfSet(Integer.parseInt(edtNumberOfSet.getText().toString()));
        idTimerObjSave = AppDatabase.getAppDatabase(getActivity()).timerDao().insertObj(timer);
    }

    public Timer saveReplaceTimer() {
        Timer timer = new Timer();
        timer.setTimerName(edtTimerName.getText().toString());
        timer.setNumberOfSet(Integer.parseInt(edtNumberOfSet.getText().toString()));

        return timer;
    }

    //check duration High and Low can not be zero
    public boolean checkDurationType() {
        durationWarpUp = totalDuration(edtWarpUpDuration);
        durationHigh = totalDuration(edtHighDuration);
        durationLow = totalDuration(edtLowDuration);
        durationCoolDown = totalDuration(edtCoolDownDuration);
        if (durationHigh > 0 && durationLow > 0) {
            return true;
        } else {
            Toast.makeText(getContext(), "Duration High or Duration Low can not be zero", Toast.LENGTH_SHORT).show();
            return false;

        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        LoadTimerModel loadTimerModel = new LoadTimerModel();
        loadTimerModel.setEdtTimerName(edtTimerName.getText().toString());
        loadTimerModel.setEdtNumberOfSet(edtNumberOfSet.getText().toString());

        loadTimerModel.setEdtWarpUpName(edtWarpUpName.getText().toString());
        long totalWarmUp = totalDuration(edtWarpUpDuration);
        loadTimerModel.setEdtWarpUpDuration(totalWarmUp + "");
        int idWampUp = getImageResource(imgWarpUpColor);
        loadTimerModel.setImgWarpUpColor(idWampUp);

        loadTimerModel.setEdtHighName(edtHighName.getText().toString());
        long totalHigh = totalDuration(edtHighDuration);
        loadTimerModel.setEdtHighDuration(totalHigh + "");
        int idHighColor = getImageResource(imgHighColor);
        loadTimerModel.setImgHighColor(idHighColor);

        loadTimerModel.setEdtLowName(edtLowName.getText().toString());
        long totalLow = totalDuration(edtLowDuration);
        loadTimerModel.setEdtLowDuration(totalLow + "");
        int idLowColor = getImageResource(imgLowColor);
        loadTimerModel.setImgLowColor(idLowColor);

        loadTimerModel.setEdtCoolDownName(edtCoolDownName.getText().toString());
        long totalCoolDown = totalDuration(edtCoolDownDuration);
        loadTimerModel.setEdtCoolDownDuration(totalCoolDown + "");
        int idCoolDownColor = getImageResource(imgCoolDownColor);
        loadTimerModel.setImgCoolDownColor(idCoolDownColor);
        outState.putSerializable("Save", loadTimerModel);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            LoadTimerModel loadTimer1 = (LoadTimerModel) savedInstanceState.getSerializable("Save");
            setDataEdt(loadTimer1);
        }
    }

    private int getImageResource(ImageView iv) {
        return (Integer) iv.getTag();
    }

    public AppDatabase createDataBaseFragmentCreate() {
        return AppDatabase.getAppDatabase(getContext());
    }

    public long getIdLoadTimer() {
        return id;
    }

    public void onBackScreenHome() {
        getActivity().finish();
    }
}
