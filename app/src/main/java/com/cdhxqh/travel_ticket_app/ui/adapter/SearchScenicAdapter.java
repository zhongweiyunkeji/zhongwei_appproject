package com.cdhxqh.travel_ticket_app.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.model.Ecs_brand;
import com.cdhxqh.travel_ticket_app.ui.activity.Play_Video_Activity;
import com.cdhxqh.travel_ticket_app.ui.activity.ScenicMapActivity;
import com.cdhxqh.travel_ticket_app.ui.activity.Tickets_Detail_Activity;
import com.cdhxqh.travel_ticket_app.ui.activity.VideoListActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by hexian on 2015/8/4.
 */
public class SearchScenicAdapter extends RecyclerView.Adapter<SearchScenicAdapter.ViewHolder> {
    public static final String TAG = "SearchScenicAdapter";
    private ArrayList<Ecs_brand> list = new ArrayList(0);
    Context mContext;
    int mark;

    public SearchScenicAdapter(Context paramContext, int mark) {
        this.mark = mark;
        this.mContext = paramContext;
    }

    public int getItemCount() {
        return this.list.size();
    }

    public ArrayList<Ecs_brand> getList() {
        return this.list;
    }

    public void onBindViewHolder(SearchScenicAdapter.ViewHolder paramViewHolder, int paramInt) {
        final Ecs_brand localEcs_brand = (Ecs_brand) this.list.get(paramInt);

        Log.i(TAG, "mark=" + mark);

        if (1000 == mark) { // 景区门票
            ImageLoader.getInstance().displayImage(localEcs_brand.brand_logo, paramViewHolder.imageView);
            paramViewHolder.nameText.setText(localEcs_brand.brand_name);
            paramViewHolder.timeText.setText(localEcs_brand.valid_date);
            paramViewHolder.linearLayout.setVisibility(View.VISIBLE);
            paramViewHolder.priceText.setText(getPrice(localEcs_brand.minprice));

            paramViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putInt("brand_id", localEcs_brand.brand_id);
                    bundle.putString("brand_name", localEcs_brand.brand_name);
                    bundle.putParcelable("ecs_brand", localEcs_brand);
                    intent.putExtras(bundle);
                    intent.setClass(mContext, Tickets_Detail_Activity.class);
                    mContext.startActivity(intent);
                }
            });

        } else if (1001 == mark) { // 地图
            ImageLoader.getInstance().displayImage(localEcs_brand.brand_logo, paramViewHolder.leftImageView);
            paramViewHolder.titleTextView.setText(localEcs_brand.brand_name);
            paramViewHolder.detailTextView.setText(localEcs_brand.brand_desc);

            paramViewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putInt("brand_id", localEcs_brand.brand_id);
                    bundle.putString("brand_name", localEcs_brand.brand_name);
                    bundle.putString("longitude", localEcs_brand.longitude);
                    bundle.putString("latitude", localEcs_brand.latitude);
                    intent.putExtras(bundle);
                    intent.setClass(mContext, ScenicMapActivity.class);
                    mContext.startActivity(intent);
                }
            });
        } else if (1002 == mark) { //看中卫
            ImageLoader.getInstance().displayImage(localEcs_brand.brand_logo, paramViewHolder.leftImageView);
            paramViewHolder.titleTextView.setText(localEcs_brand.brand_name);
            paramViewHolder.detailTextView.setText(localEcs_brand.brand_desc);

            paramViewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("brandid", String.valueOf(localEcs_brand.brand_id));
                    bundle.putString("brandName", localEcs_brand.brand_name);
                    intent.putExtras(bundle);
                    intent.setClass(mContext, Play_Video_Activity.class);
                    mContext.startActivity(intent);
                }
            });
        }

    }

    public SearchScenicAdapter.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        View v = null;
        if (mark == 1000) {//门票详情
            v = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.brand_adapter, paramViewGroup, false);
        } else {//地图详情
            v = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.activity_scenic_search_item, paramViewGroup, false);
        }
        return new ViewHolder(v);
        // return new SearchScenicAdapter1.ViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.activity_scenic_search_item, paramViewGroup, false));
    }

    public void update(ArrayList<Ecs_brand> ecs_brands) {
        for (int i = ecs_brands.size()-1; i >= 0; i--) {
            Ecs_brand obj = ecs_brands.get(i);
            boolean exist = false;
            for (int j = list.size()-1; j >= 0; j--) {
                if (list.get(j).brand_id == obj.brand_id) {
                    exist = true;
                    break;
                }
            }
            if (exist) continue;
            list.add(0, obj);
        }
        ecs_brands = list;

        notifyDataSetChanged();
    }

    /**
     * 设置价格为无
     */
    private String getPrice(double p) {
        if (p == 0.0) {
            return "暂无";
        }else {
            return String.valueOf(p);
        }
    }

    /**
     * 价格去掉小数位*
     */
    private String getprice(double p) {
        String newP = "0";
        if (p != 0) {
            if (p % 1.0 == 0) {
                newP = (long) p + "";
            }
        }
        return newP;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // 景区门票详情
        RelativeLayout relativeLayout;
        public ImageView imageView;
        public TextView nameText;
        public TextView timeText;
        LinearLayout linearLayout;
        TextView priceText;


        // 听中卫描述
        LinearLayout layout;
        TextView detailTextView;
        ImageView leftImageView;
        ImageView rightImageView;
        TextView titleTextView;


        public ViewHolder(View paramView) {
            super(paramView);

            // 景区门票详情
            relativeLayout = (RelativeLayout) paramView.findViewById(R.id.content_id);
            imageView = (ImageView) paramView.findViewById(R.id.brand_image_id);
            nameText = (TextView) paramView.findViewById(R.id.brand_name_id);
            timeText = (TextView) paramView.findViewById(R.id.brand_time_id);
            linearLayout = (LinearLayout) paramView.findViewById(R.id.linearlayout_price_id);
            priceText = (TextView) paramView.findViewById(R.id.brand_price_di);

            // 听中卫描述
            layout = ((LinearLayout) paramView.findViewById(R.id.scenic_detail_main_layout));
            this.leftImageView = ((ImageView) paramView.findViewById(R.id.scenic_item_left));
            this.rightImageView = ((ImageView) paramView.findViewById(R.id.scenic_item_right));
            this.titleTextView = ((TextView) paramView.findViewById(R.id.scenic_search_title));
            this.detailTextView = ((TextView) paramView.findViewById(R.id.scenic_search_detail));
        }
    }

}
