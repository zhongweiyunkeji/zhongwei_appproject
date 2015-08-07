package com.cdhxqh.travel_ticket_app.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.model.Ec_goods;
import com.cdhxqh.travel_ticket_app.model.Ecs_brand;
import com.cdhxqh.travel_ticket_app.ui.activity.ScenicMapActivity;
import com.cdhxqh.travel_ticket_app.ui.activity.Tickets_Detail_Activity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;


/**
 * 景区的Adapter
 */
public class BrandListAdapter extends RecyclerView.Adapter<BrandListAdapter.ViewHolder> {
    private static final String TAG = "BrandListAdapter";
    Context mContext;

    ArrayList<Ecs_brand> ecs_brands = new ArrayList<Ecs_brand>();

    int mark;


    public BrandListAdapter(Context context, int mark) {

        this.mContext = context;
        this.mark = mark;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.brand_adapter, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Ecs_brand ecs_brand = ecs_brands.get(i);
        Log.i(TAG,"mark="+mark);
        ImageLoader.getInstance().displayImage(ecs_brand.brand_logo, viewHolder.imageView);
        viewHolder.nameText.setText(ecs_brand.brand_name);
        viewHolder.timeText.setText(ecs_brand.valid_date);
        if (mark == 1000) {
            viewHolder.linearLayout.setVisibility(View.VISIBLE);
            viewHolder.priceText.setText(getprice(ecs_brand.minprice));

        } else {
            viewHolder.linearLayout.setVisibility(View.GONE);
        }

        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                Bundle bundle = new Bundle();

                if (mark == 1000) { //门票详情
                    bundle.putInt("brand_id", ecs_brand.brand_id);
                    bundle.putString("brand_name", ecs_brand.brand_name);
                    bundle.putParcelable("ecs_brand",ecs_brand);
                    intent.putExtras(bundle);
                    intent.setClass(mContext, Tickets_Detail_Activity.class);
                    mContext.startActivity(intent);

                } else if (mark == 1001) { //地图详情

                    bundle.putInt("brand_id", ecs_brand.brand_id);
                    bundle.putString("brand_name", ecs_brand.brand_name);
                    bundle.putString("longitude", ecs_brand.longitude);
                    bundle.putString("latitude", ecs_brand.latitude);
                    intent.putExtras(bundle);
                    intent.setClass(mContext, ScenicMapActivity.class);
                    mContext.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return ecs_brands.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * 布局文件*
         */
        RelativeLayout relativeLayout;
        /**
         * 商品图标
         */
        public ImageView imageView;
        /**
         * 景区名称
         */
        public TextView nameText;
        /**
         * 景区开放时间
         */
        public TextView timeText;

        /**
         * 价格布局*
         */
        LinearLayout linearLayout;
        /**
         * 价格表*
         */
        TextView priceText;

        public ViewHolder(View view) {
            super(view);

            relativeLayout = (RelativeLayout) view.findViewById(R.id.content_id);
            imageView = (ImageView) view.findViewById(R.id.brand_image_id);
            nameText = (TextView) view.findViewById(R.id.brand_name_id);
            timeText = (TextView) view.findViewById(R.id.brand_time_id);
            linearLayout = (LinearLayout) view.findViewById(R.id.linearlayout_price_id);
            priceText = (TextView) view.findViewById(R.id.brand_price_di);

        }
    }


    public void update(ArrayList<Ecs_brand> data, boolean merge) {
        if (merge && ecs_brands.size() > 0) {
            for (int i = 0; i < ecs_brands.size(); i++) {
                Ecs_brand obj = ecs_brands.get(i);
                boolean exist = false;
                for (int j = 0; j < data.size(); j++) {
                    if (data.get(j).brand_id == obj.brand_id) {
                        exist = true;
                        break;
                    }
                }
                if (exist) continue;
                data.add(obj);
            }
        }
        ecs_brands = data;

        notifyDataSetChanged();
    }


    /**
     * 价格去掉小数位*
     */
    private String getprice(double p) {
        String newP="0";
        if (p != 0) {
            if (p % 1.0 == 0) {
                newP = (long) p + "";
            }
        }
        return newP;
    }


}