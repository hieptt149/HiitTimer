package vn.com.hieptt.myhiittimer.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iui.myhiittimer.R;

import java.util.List;

/**
 * Created by Admin on 1/25/2018.
 */

public class AdapterRvHome extends RecyclerView.Adapter<AdapterRvHome.ViewHolder> {
    private Context context;
    private List<ItemHomeRv> list;
    protected OnItemHomeListner onItemHomeListner;

    public AdapterRvHome(Context context, List<ItemHomeRv> list, OnItemHomeListner onItemHomeListner) {
        this.context = context;
        this.list = list;
        this.onItemHomeListner = onItemHomeListner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_item_rv, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemHomeRv itemHomeRv = list.get(position);
        holder.txtName.setText(itemHomeRv.getNameTimer());
        long totalTimer = itemHomeRv.getTotalTimer();

        int h = (int) (totalTimer / 3600);
        int m = (int) ((totalTimer % 3600) / 60);
        int s = (int) ((totalTimer % 3600) % 60);

        holder.txtName.setText(itemHomeRv.getNameTimer());

        String hour, minutes, second;
        if (h > 9) {
            hour = "" + h;
        } else {
            hour = "0" + h;
        }
        if (m > 9) {
            minutes = "" + m;
        } else {
            minutes = "0" + m;
        }
        if (s > 9) {
            second = "" + s;
        } else {
            second = "0" + s;
        }

        holder.txtTime.setText(hour + ":" + minutes + ":" + second);

    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtName, txtTime;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.item_rv_home_nametimer);
            txtTime = itemView.findViewById(R.id.item_rv_home_totaltimer);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemHomeListner.OnItemHomeClickLisner(view, getAdapterPosition());
        }
    }

}
