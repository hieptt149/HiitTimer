package vn.com.hieptt.myhiittimer.selectcolor;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iui.myhiittimer.R;

import java.util.List;

/**
 * Created by Admin on 1/17/2018.
 */

public class AdapterSelectColor extends RecyclerView.Adapter<AdapterSelectColor.ViewHolder> {
    private List<ItemRvSelectColor> list;
    private Context context;
    private OnItemclick onItemclick;

    public AdapterSelectColor(List<ItemRvSelectColor> list, Context context,OnItemclick onItemclick) {
        this.list = list;
        this.context = context;
        this.onItemclick = onItemclick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.selec_color_item_rv,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemRvSelectColor color = list.get(position);
        holder.titleColor.setText(color.getTitleColor());
        holder.imgCheck.setBackgroundResource(color.getImgCheck());
        holder.imgColor.setBackgroundResource(color.getImgColor());
        if (color.isSelected()){
            holder.imgCheck.setVisibility(View.VISIBLE);
            holder.titleColor.setTextColor(context.getResources().getColor(R.color.colorTextWhite));
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.colorBlack));
        }else {
            holder.imgCheck.setVisibility(View.GONE);
            holder.titleColor.setTextColor(context.getResources().getColor(R.color.colorText));
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.colorBackground));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titleColor;
        private ImageView imgCheck;
        private ImageView imgColor;
        private ConstraintLayout layout;
        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.item_rv_layout);
            titleColor = itemView.findViewById(R.id.item_rv_select_title_color);
            imgCheck = itemView.findViewById(R.id.item_rv_check_img);
            imgCheck.setVisibility(View.GONE);
            imgColor = itemView.findViewById(R.id.item_rv_select_color_img);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemclick.OnClick(itemView,getAdapterPosition());
        }

    }
}
