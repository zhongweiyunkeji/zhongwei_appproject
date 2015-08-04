package com.cdhxqh.travel_ticket_app.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Administrator on 2015/8/4.
 */
public class OrdreTrackingAdapter extends RecyclerView.Adapter<OrdreTrackingAdapter.ViewHolder> {
    Context mContext;
    String[] datasets;

    public OrdreTrackingAdapter(Context paramContext, String[] dataset) {
        this.mContext = paramContext;
        datasets = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_order_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        LinearLayout.LayoutParams lytp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if(i == 0) {
            holder.img_a_id.setImageResource(R.drawable.ic_node_up);
        }else if(i == getItemCount()-1){
            holder.img_a_id.setImageResource(R.drawable.ic_node_bottom);
            lytp.gravity = Gravity.BOTTOM;
            holder.l_s_id.setLayoutParams(lytp);
        }else {
            holder.img_a_id.setImageResource(R.drawable.ic_node_cen);
            lytp.gravity = Gravity.CENTER_VERTICAL;
            holder.l_s_id.setLayoutParams(lytp);
        }
        holder.text_b_id.setText(datasets[i]);
        holder.text_c_id.setText(datasets[i]);
    }

    @Override
    public int getItemCount() {
        return datasets.length;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * 布局文件*
         */
        private ImageView img_a_id;
        /**
         * 商品图标
         */
        public TextView text_b_id;
        /**
         * 景区名称
         */
        public TextView text_c_id;

        /**
         * 布局
         */
        public LinearLayout l_s_id;

        public ViewHolder(View view) {
            super(view);

            img_a_id = (ImageView) view.findViewById(R.id.img_a_id);
            text_b_id = (TextView) view.findViewById(R.id.text_b_id);
            text_c_id = (TextView) view.findViewById(R.id.text_c_id);
            l_s_id = (LinearLayout) view.findViewById(R.id.l_s_id);
        }
    }

}
