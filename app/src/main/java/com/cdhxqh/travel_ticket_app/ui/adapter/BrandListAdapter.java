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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.model.Ec_goods;
import com.cdhxqh.travel_ticket_app.model.Ecs_brand;
import com.cdhxqh.travel_ticket_app.ui.activity.ScenicMapActivity;

import java.util.ArrayList;


/**
 * 景区的Adapter
 */
public class BrandListAdapter extends RecyclerView.Adapter<BrandListAdapter.ViewHolder> {
    private static final String TAG = "BrandListAdapter";
    Context mContext;

    ArrayList<Ecs_brand> ecs_brands = new ArrayList<Ecs_brand>();


    public BrandListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.brand_adapter, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Ecs_brand ecs_brand = ecs_brands.get(i);

        viewHolder.nameText.setText(ecs_brand.brand_name);
        viewHolder.descText.setText(ecs_brand.brand_desc);

        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putString("brand_name",ecs_brand.brand_name);
                intent.putExtras(bundle);
                intent.setClass(mContext, ScenicMapActivity.class);
                mContext.startActivity(intent);
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
         * 景区描述
         */
        public TextView descText;

        public ViewHolder(View view) {
            super(view);

            relativeLayout = (RelativeLayout) view.findViewById(R.id.content_id);
            imageView = (ImageView) view.findViewById(R.id.brand_image_id);
            nameText = (TextView) view.findViewById(R.id.brand_name_id);
            descText = (TextView) view.findViewById(R.id.brand_desc_id);

        }
    }


    public void update(ArrayList<Ecs_brand> data, boolean merge) {
        Log.i(TAG, "mItems=" + ecs_brands.size());
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


}