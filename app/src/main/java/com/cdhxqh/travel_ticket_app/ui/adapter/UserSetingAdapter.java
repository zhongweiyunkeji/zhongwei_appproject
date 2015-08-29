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
import com.cdhxqh.travel_ticket_app.ui.activity.Activity_company_introduce;
import com.cdhxqh.travel_ticket_app.ui.activity.InvoiceTitleActivity;
import com.cdhxqh.travel_ticket_app.ui.activity.ReceivingAddressActivity;
import com.cdhxqh.travel_ticket_app.ui.activity.SettingActivity;
import com.cdhxqh.travel_ticket_app.ui.activity.TicketCheckActivity;

import java.util.ArrayList;


/**
 * Created by yw on 2015/4/28.
 */
public class UserSetingAdapter extends RecyclerView.Adapter<UserSetingAdapter.ViewHolder> {
    private static final String TAG = "UserSetingAdapter";
    Context mContext;
    int a = 0;

    ArrayList<String> names=new ArrayList<String>();

    int[] images = { R.drawable.ic_wee, R.drawable.ic_menu_map,R.drawable.ic_menu_users, R.drawable.ic_menu_bill, R.drawable.ic_menu_seting, R.drawable.ic_wee};

    public UserSetingAdapter(Context context,ArrayList<String> strings) {
        mContext = context;
        names=strings;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_seting_item, viewGroup, false);
        if("验票".equals(names.get(a))){
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, TicketCheckActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }else if("关于我们".equals(names.get(a))){
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, Activity_company_introduce.class);
                    mContext.startActivity(intent);
                }
            });
        }else if("收货地址".equals(names.get(a))){
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ReceivingAddressActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
        else if("发票抬头".equals(names.get(a))){
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, InvoiceTitleActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
        else if("设置".equals(names.get(a))){
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, SettingActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }
        a++;
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