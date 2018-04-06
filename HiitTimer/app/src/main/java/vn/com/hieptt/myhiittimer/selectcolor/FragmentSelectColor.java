package vn.com.hieptt.myhiittimer.selectcolor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.iui.myhiittimer.R;
import vn.com.hieptt.myhiittimer.createtimer.OnShowFragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentSelectColor extends Fragment implements OnItemclick, View.OnClickListener {
    private RecyclerView rvSelectColor;
    private List<ItemRvSelectColor> list;
    private int mPosition =  -1;
    private AdapterSelectColor adapterSelectColor;
    private int mLastPosition = 0;
    private OnShowFragment onShowFragment;
    private int mColor = -1;
    private ImageView btnBack;
    private int count,color1,color2,color3,color4;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onShowFragment = (OnShowFragment) context;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.select_color_layout, container, false);
         btnBack = view.findViewById(R.id.select_color_toolbar_back_img);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       Bundle bundle = getArguments();
       if (bundle != null){
           count = bundle.getInt("count");
           color1 = bundle.getInt("color1");
           color2 =  bundle.getInt("color2");
           color3 =  bundle.getInt("color3");
           color4 = bundle.getInt("color4");
       }

        addList();
        if (savedInstanceState != null){
            int position = savedInstanceState.getInt("S");
            mColor = savedInstanceState.getInt("SS");
            list.get(position).setSelected(true);
            mPosition = position;

        }else {
            if (count>-1){
                int position = getPositionColor();
                list.get(position).setSelected(true);
                mPosition = position;
            }
        }
        rvSelectColor = view.findViewById(R.id.rv_select_color);
        adapterSelectColor = new AdapterSelectColor(list, getContext(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvSelectColor.setLayoutManager(layoutManager);
        rvSelectColor.setAdapter(adapterSelectColor);
        btnBack.setOnClickListener(this);

    }

    private int getPositionColor() {
        int position = -1;
        switch (count){
            case 1 :
                position = getColor(color1);
                break;
            case 2 :
                position = getColor(color2);
                break;
            case 3 :
                position = getColor(color3);
                break;
            case 4 :
                position = getColor(color4);
                break;
        }
        return position;
    }

    private int getColor(int color) {
        int j = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getImgColor() == color){
                j = i;
            }
        }
        return j;
    }

    @Override
    public void OnClick(View view, int position) {
        list.get(position).setSelected(true);
        if (mPosition > -1){
            list.get(mPosition).setSelected(false);
            if (position == mLastPosition){
                if (list.get(position).isSelected()){
                    list.get(position).setSelected(false);
                }else {
                    list.get(position).setSelected(true);
                }
            }
        }
        mPosition = position;
        mLastPosition = position;
        adapterSelectColor.notifyDataSetChanged();
        showColor(position);


    }

    private void showColor(int position) {
        switch (position){
            case 0 :
                mColor = R.drawable.draw_color_defaut;
                break;
            case 1 :
                mColor = R.drawable.draw_color_red;
                break;
            case  2 :
                mColor = R.drawable.draw_color_orange;
                break;
            case  3 :
                mColor = R.drawable.draw_color_yellow;
                break;
            case 4 :
                mColor = R.drawable.draw_color_green;
                break;
            case 5 :
                mColor = R.drawable.draw_color_teal;
                break;
            case  6 :
                mColor = R.drawable.draw_color_blue;
                break;
            case  7 :
                mColor = R.drawable.draw_color_purple;
                break;
            case 8 :
                mColor = R.drawable.draw_color_magenta;
                break;
            case  9 :
                mColor = R.drawable.draw_color_deep_purple;
                break;

        }
    }

    private void addList() {
        list = new ArrayList<>();
        list.add(new ItemRvSelectColor("Default", R.drawable.draw_color_defaut, R.drawable.ic_check, false));
        list.add(new ItemRvSelectColor("Red", R.drawable.draw_color_red, R.drawable.ic_check, false));
        list.add(new ItemRvSelectColor("Orange", R.drawable.draw_color_orange, R.drawable.ic_check, false));
        list.add(new ItemRvSelectColor("Yellow", R.drawable.draw_color_yellow, R.drawable.ic_check, false));
        list.add(new ItemRvSelectColor("Green", R.drawable.draw_color_green, R.drawable.ic_check, false));
        list.add(new ItemRvSelectColor("Teal", R.drawable.draw_color_teal, R.drawable.ic_check, false));
        list.add(new ItemRvSelectColor("Blue", R.drawable.draw_color_blue, R.drawable.ic_check, false));
        list.add(new ItemRvSelectColor("Purple", R.drawable.draw_color_purple, R.drawable.ic_check, false));
        list.add(new ItemRvSelectColor("Magenta", R.drawable.draw_color_magenta, R.drawable.ic_check, false));
        list.add(new ItemRvSelectColor("Deep Purple", R.drawable.draw_color_deep_purple, R.drawable.ic_check, false));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mPosition != -1){
            outState.putInt("S",mPosition);
            outState.putInt("SS",mColor);
        }
    }
    public void sendColor (int drawColor){
        Intent intent = new Intent(Keys.ACTION_UPDATE_COLOR);
        intent.putExtra("count1",count);
        if (count  == -1 || mColor == -1){
            intent.putExtra(Keys.KEY_COLOR1,color1);
            intent.putExtra(Keys.KEY_COLOR2,color2);
            intent.putExtra(Keys.KEY_COLOR3,color3);
            intent.putExtra(Keys.KEY_COLOR4,color4);
        }else {
            switch (count){
                case 1 :
                    intent.putExtra(Keys.KEY_COLOR1,drawColor);
                    intent.putExtra(Keys.KEY_COLOR2,color2);
                    intent.putExtra(Keys.KEY_COLOR3,color3);
                    intent.putExtra(Keys.KEY_COLOR4,color4);
                    break;
                case 2:
                    intent.putExtra(Keys.KEY_COLOR1,color1);
                    intent.putExtra(Keys.KEY_COLOR2,drawColor);
                    intent.putExtra(Keys.KEY_COLOR3,color3);
                    intent.putExtra(Keys.KEY_COLOR4,color4);
                    break;
                case 3 :
                    intent.putExtra(Keys.KEY_COLOR1,color1);
                    intent.putExtra(Keys.KEY_COLOR2,color2);
                    intent.putExtra(Keys.KEY_COLOR3,drawColor);
                    intent.putExtra(Keys.KEY_COLOR4,color4);
                    break;
                case 4 :
                    intent.putExtra(Keys.KEY_COLOR1,color1);
                    intent.putExtra(Keys.KEY_COLOR2,color2);
                    intent.putExtra(Keys.KEY_COLOR3,color3);
                    intent.putExtra(Keys.KEY_COLOR4,drawColor);
                    break;

            }
        }
        getActivity().sendBroadcast(intent);

    }

    @Override
    public void onClick(View view) {
        sendColor(mColor);
        getActivity().onBackPressed();
    }

    @Override
    public void onDestroyView() {
        sendColor(mColor);
        super.onDestroyView();
    }
}
