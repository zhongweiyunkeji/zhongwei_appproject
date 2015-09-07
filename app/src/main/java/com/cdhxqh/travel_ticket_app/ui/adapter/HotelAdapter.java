package com.cdhxqh.travel_ticket_app.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
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
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/8/10.
 */
public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> {
    Context mContext;
    ArrayList<HotelModel> hotelList = new ArrayList<HotelModel>();
    double[] gps;
    private static final double EARTH_RADIUS = 6378137.0;

    Map<String, String> img = new HashMap<String, String>();


    public HotelAdapter(Context paramContext) {
        gps = Gps.getGps(paramContext);
        this.mContext = paramContext;
        img.put("1776120", "http://Images4.c-ctrip.com/target/hotel/442000/441460/2ce4a9ebc3be4a04a23541a185c91eff_550_412.jpg");
        img.put("1767700", "http://Images4.c-ctrip.com/target/hotel/432000/431103/418b504347b14f46a2ed539d21719ae4_550_412.jpg");
        img.put("1613884", "http://Images4.c-ctrip.com/target/hotel/900000/899699/507007c7dd634837822c941bc5f89fa0_550_412.jpg");
        img.put("740346", "http://Images4.c-ctrip.com/target/hotel/128000/127804/c8196d448a254ed393355e9c1cbe74cb_550_412.jpg");
        img.put("713485", "http://Images4.c-ctrip.com/target/hotel/346000/345332/58edf61b36c34ff5a71924fa42827957_550_412.jpg");
        img.put("1386722", "http://Images4.c-ctrip.com/target/hotel/480000/480000/840f0e68509c4cf39482f7f7d30253d3_550_412.jpg");
        img.put("1171591", "http://Images4.c-ctrip.com/target/fd/hotel/g2/M04/99/C0/Cghzf1U_EbmAF9gKAAZ89yfcf-w757_550_412.jpg");
        img.put("1048214", "http://Images4.c-ctrip.com/target/fd/hotel/g1/M04/8A/6A/CghzfFVR8JWAVUi3AAE2EccaJIc371_550_412.jpg");
        img.put("812986", "http://Images4.c-ctrip.com/target/hotel/469000/468504/0225136815d84e54a66f15accc1e271a_550_412.jpg");
        img.put("803031", "http://Images4.c-ctrip.com/target/hotel/90000/89813/F6007735-FAAA-4EA0-8554-E08BBA9C145B_550_412.jpg");
        img.put("2143578", "http://Images4.c-ctrip.com/target/hotel/121000/120261/ec83ebf43a0b497eb7c576913ba46a06_550_412.jpg");
        img.put("801601", "http://Images4.c-ctrip.com/target/hotel/72000/71068/d8c8a93d9d2c4277be5bf21376cf10f8_550_412.jpg");
        img.put("741968", "http://Images4.c-ctrip.com/target/hotel/136000/135320/EA824AEF1E76462EAE83CABFBFB71917_550_412.Jpg");
        img.put("741957", "http://Images4.c-ctrip.com/target/fd/hotelcomment/g2/M0B/3A/57/CghzgFW25TSAM3e-AAM5hz-d2N0252_550_412.jpg");
        img.put("741940", "http://Images4.c-ctrip.com/target/hotel/346000/345502/f006b7c70e444a38ae7a214f19898da1_550_412.jpg");
        img.put("1327019", "http://Images4.c-ctrip.com/target/fd/hotel/g3/M0B/DE/5E/CggYGVXIOjSAcB9mAAISm5vJbK8880_550_412.jpg");
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
        holder.hotelRate.setText(hotelList.get(i).getHotelStarRate()+" 星" );
        ImageLoader.getInstance().displayImage(img.get(hotelList.get(i).getHotelCode()), holder.hotelPic);

        if(hotelList.get(i).getLatitude() != null && hotelList.get(i).getLongitude()!= null) {
            double Latitude_a = Double.parseDouble(hotelList.get(i).getLatitude());
            double Longitude_a = Double.parseDouble(hotelList.get(i).getLongitude());
            double Latitude_b = gps[0];
            double Longitude_b = gps[1];
            double a = getDistance(Latitude_a, Longitude_a, Latitude_b, Longitude_b);
            DecimalFormat df = new DecimalFormat("0.00");
            String db = df.format(a / 1000);
            holder.hotelSpace.setText("距您"+db + "km");
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
