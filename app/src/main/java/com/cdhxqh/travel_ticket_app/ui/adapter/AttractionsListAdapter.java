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

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.model.Attractions;
import com.cdhxqh.travel_ticket_app.ui.activity.Attractions_details_Activity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * 景点的Adapter
 */
public class AttractionsListAdapter extends RecyclerView.Adapter<AttractionsListAdapter.ViewHolder> {
    private static final String TAG = "AttractionsListAdapter";
    Context mContext;

    ArrayList<Attractions> attractionses = new ArrayList<Attractions>();

    private String latitude;

    private String longitude;


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
        final Attractions attractions = attractionses.get(i);
        String latitude1=attractions.latitude;
        String longitude2=attractions.longitude;

        String distance="暂无";

        if(!latitude1.equals("")&&!longitude2.equals("")){
             distance=distance("37.523379","105.181925",latitude1,longitude2);
        }

        ImageLoader.getInstance().displayImage(attractions.image, viewHolder.imageView);
        viewHolder.nameText.setText(attractions.title);
        viewHolder.distanceText.setText(distance.equals("")?"暂无":distance+"公里");

        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putParcelable("attractions", attractions);
                intent.putExtras(bundle);
                intent.setClass(mContext, Attractions_details_Activity.class);
                mContext.startActivity(intent);
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


    /**
     * 更新定位的经纬度
     *
     * @param latitude
     * @param longitude *
     */


    public void updateDis(String latitude, String longitude) {
        Log.i(TAG, "latitude=" + latitude + ",longitude=" + longitude);

        this.latitude = latitude;
        this.longitude = longitude;
        notifyDataSetChanged();
    }


    /**
     * 计算距离*
     */
    private String distance(String latitude1, String longitude1, String latitude2, String longitude2) {
        Log.i(TAG, "latitude1=" + latitude1 + ",longitude1=" + longitude1+",latitude2="+latitude2+",longitude2="+longitude2);
        LatLng p1 = new LatLng(Double.parseDouble(latitude1), Double.parseDouble(longitude1));
        LatLng p2 = new LatLng(Double.parseDouble(latitude2), Double.parseDouble(longitude2));



        double distance = DistanceUtil.getDistance(p1, p2);

        /**保留2位小数**/
        DecimalFormat df   = new DecimalFormat("######0.00");
        //转换成公里
        String dkm=(df.format(distance/1000.0)) + "";

        return dkm;
    }

}