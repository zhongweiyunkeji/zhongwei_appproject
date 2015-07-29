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
import com.cdhxqh.travel_ticket_app.model.Attractions;
import com.cdhxqh.travel_ticket_app.model.Ecs_brand;
import com.cdhxqh.travel_ticket_app.ui.activity.ScenicMapActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;


/**
 * 景点的Adapter
 */
public class AttractionsListAdapter extends RecyclerView.Adapter<AttractionsListAdapter.ViewHolder> {
    private static final String TAG = "AttractionsListAdapter";
    Context mContext;

    ArrayList<Attractions> attractionses = new ArrayList<Attractions>();


    public AttractionsListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.arrractions_adapter, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Attractions attractions= attractionses.get(i);
        Log.i(TAG,"attractions.image="+attractions.image);

        ImageLoader.getInstance().displayImage(attractions.image,viewHolder.imageView);
        viewHolder.nameText.setText(attractions.title);
//        viewHolder.distanceText.setText(attractions.);

        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                Bundle bundle = new Bundle();
//                bundle.putInt("brand_id", ecs_brand.brand_id);
//                bundle.putString("brand_name", ecs_brand.brand_name);
//                bundle.putString("longitude", ecs_brand.longitude);
//                bundle.putString("latitude", ecs_brand.latitude);
//                intent.putExtras(bundle);
//                intent.setClass(mContext, ScenicMapActivity.class);
//                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return attractionses.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * 布局文件*
         */
        RelativeLayout relativeLayout;
        /**
         * 景点图标
         */
        public ImageView imageView;
        /**
         * 景点名称
         */
        public TextView nameText;
        /**
         * 距离位置
         */
        public TextView distanceText;

        public ViewHolder(View view) {
            super(view);

            relativeLayout = (RelativeLayout) view.findViewById(R.id.content_id);
            imageView = (ImageView) view.findViewById(R.id.brand_image_id);
            nameText = (TextView) view.findViewById(R.id.brand_name_id);
            distanceText = (TextView) view.findViewById(R.id.brand_time_id);

        }
    }


    public void update(ArrayList<Attractions> data, boolean merge) {
        Log.i(TAG, "mItems=" + attractionses.size());
        if (merge && attractionses.size() > 0) {
            for (int i = 0; i < attractionses.size(); i++) {
                Attractions obj = attractionses.get(i);
                boolean exist = false;
                for (int j = 0; j < data.size(); j++) {
                    if (data.get(j).article_id == obj.article_id) {
                        exist = true;
                        break;
                    }
                }
                if (exist) continue;
                data.add(obj);
            }
        }
        attractionses = data;

        notifyDataSetChanged();
    }


}