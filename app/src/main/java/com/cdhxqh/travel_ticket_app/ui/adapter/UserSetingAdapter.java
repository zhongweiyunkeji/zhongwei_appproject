package com.cdhxqh.travel_ticket_app.ui.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.ui.activity.TicketCheckActivity;

import java.util.ArrayList;


/**
 * Created by yw on 2015/4/28.
 */
public class UserSetingAdapter extends RecyclerView.Adapter<UserSetingAdapter.ViewHolder> {
    private static final String TAG = "UserSetingAdapter";
    Context mContext;

    ArrayList<String> names=new ArrayList<String>();

    int[] images = { R.drawable.ic_wee, R.drawable.ic_map,R.drawable.ic_passengers, R.drawable.ic_invoice, R.drawable.ic_seting, R.drawable.ic_wee};

    public UserSetingAdapter(Context context,ArrayList<String> strings) {
        mContext = context;
        names=strings;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_seting_item, viewGroup, false);
        if("办公".equals(names.get(i))){
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, TicketCheckActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final String name = names.get(i);

        viewHolder.imageView.setImageResource(images[i]);
        viewHolder.desctext.setText(name);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * 布局文件*
         */
        RelativeLayout relativeLayout;
        /**
         * 图标
         */
        public ImageView imageView;
        /**
         * 文字
         */
        public TextView desctext;


        public ViewHolder(View view) {
            super(view);

            relativeLayout = (RelativeLayout) view.findViewById(R.id.content_id);
            imageView = (ImageView) view.findViewById(R.id.user_item_icon_id);
            desctext = (TextView) view.findViewById(R.id.user_item_name);

        }
    }


}