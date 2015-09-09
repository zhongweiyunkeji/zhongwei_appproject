package com.cdhxqh.travel_ticket_app.ui.adapter;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.model.hotel.HotelModel;
import com.cdhxqh.travel_ticket_app.ui.activity.HotelContentActivity;
import com.cdhxqh.travel_ticket_app.ui.widget.hotelWineShop.utils.Gps;
import com.cdhxqh.travel_ticket_app.utils.MessageUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.vov.vitamio.utils.Log;

/**
 * Created by Administrator on 2015/8/10.
 */
public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> {
    Context mContext;
    ArrayList<HotelModel> hotelList = new ArrayList<HotelModel>();
    double[] gps;
    private static final double EARTH_RADIUS = 6378137.0;
    Map<String, String> img = new HashMap<String, String>();
    int absent;


    public HotelAdapter(Context paramContext, Map<String, String> imgs, double[] a) {
        img = imgs;

        gps = a;

//        gps = Gps.getGps(paramContext);

        this.mContext = paramContext;
    }

    public void update(ArrayList<HotelModel> hotel) {
        hotelList = hotel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_type_of_hotel_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {
        holder.hotelName.setText(hotelList.get(i).getHotelName());
        holder.hotelAddress.setText(hotelList.get(i).getAddress());
        holder.hotelRate.setText(hotelList.get(i).getHotelStarRate() + " 星");
        ImageLoader.getInstance().displayImage(img.get(hotelList.get(i).getHotelCode()), holder.hotelPic);

        if(absent != 1) {
            if (hotelList.get(i).getLatitude() != null && hotelList.get(i).getLongitude() != null && gps[0] != 0.0 && gps[1]!= 0.0) {
                double Latitude_a = Double.parseDouble(hotelList.get(i).getLatitude());
                double Longitude_a = Double.parseDouble(hotelList.get(i).getLongitude());
                double Latitude_b = gps[0];
                double Longitude_b = gps[1];
                double a = getDistance(Latitude_a, Longitude_a, Latitude_b, Longitude_b);
                DecimalFormat df = new DecimalFormat("0.00");
                String db = df.format(a / 1000);
                holder.hotelSpace.setText("距您" + db + "公里");
            }
        }else {
            holder.hotelSpace.setVisibility(View.GONE);
        }


        holder.hotel_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("hotelCode", hotelList.get(i).getHotelCode());
                intent.setClass(mContext, HotelContentActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * 图片
         */
        private ImageView hotelPic;
        /**
         * id
         */
        private LinearLayout hotel_id;
        /**
         * 酒店名
         */
        private TextView hotelName;
        /**
         * 酒店地址
         */
        private TextView hotelAddress;

        /**
         *酒店星级
         */
        private TextView hotelRate;

        /**
         *酒店距离
         */
        private TextView hotelSpace;

        public ViewHolder(View view) {
            super(view);
            hotelPic = (ImageView)view.findViewById(R.id.hotelPic);
            hotelName = (TextView) view.findViewById(R.id.hotelName);
            hotelAddress = (TextView) view.findViewById(R.id.hotelAddress);
            hotelRate = (TextView) view.findViewById(R.id.hotelRate);
            hotelSpace = (TextView) view.findViewById(R.id.hotelSpace);
            hotel_id = (LinearLayout) view.findViewById(R.id.hotel_id);
        }
    }

    // 刷新list
    public void dataChanged() {
        // 通知listView刷新
        notifyDataSetChanged();
    }

    // 返回单位是米
    public double getDistance(double longitude1, double latitude1,
                              double longitude2, double latitude2) {
        double Lat1 = rad(latitude1);
        double Lat2 = rad(latitude2);
        double a = Lat1 - Lat2;
        double b = rad(longitude1) - rad(longitude2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(Lat1) * Math.cos(Lat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }
}
