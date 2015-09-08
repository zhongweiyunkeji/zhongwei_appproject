package com.cdhxqh.travel_ticket_app.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.model.hotel.HotelModel;
import com.cdhxqh.travel_ticket_app.model.hotel.RoomContent;
import com.cdhxqh.travel_ticket_app.model.hotel.RoomFacility;
import com.cdhxqh.travel_ticket_app.model.hotel.RoomImgModel;
import com.cdhxqh.travel_ticket_app.ui.activity.HotelContentActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/9/5.
 */
public class RoomContentAdapter extends RecyclerView.Adapter<RoomContentAdapter.ViewHolder> {
    Context mContext;
    ArrayList<RoomContent> hotelList = new ArrayList<RoomContent>();
    ArrayList<RoomImgModel> roomImgModelLists = new ArrayList<RoomImgModel>();

    public RoomContentAdapter(Context paramContext) {
        this.mContext = paramContext;
    }

    public void update(ArrayList<RoomContent> hotel, ArrayList<RoomImgModel> roomImgModelList) {
        hotelList = hotel;
        roomImgModelLists = roomImgModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_hotel_content_raw, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {
        for(int j = 0; j< roomImgModelLists.size(); j++) {
            if(roomImgModelLists.get(j).getInvBlockCode().equals(hotelList.get(i).getInvBlockCode())) {
                ImageLoader.getInstance().displayImage(roomImgModelLists.get(j).getUrl(), holder.roomPic);
                break;
            }
        }
        holder.RoomTypeName.setText(hotelList.get(i).getRoomTypeName());
        if(hotelList.get(i).getQuantity().equals("0")) {
            holder.Quantity.setVisibility(View.GONE);
        }else {
            holder.Quantity.setVisibility(View.GONE);
        }

        holder.RoomSize.setText("房间大小：" + hotelList.get(i).getRoomSize() + "平米");
        holder.StandardOccupancy.setText("可入住人数：" + hotelList.get(i).getStandardOccupancy()+"人");

//        holder.hotel_id.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.putExtra("hotelCode", hotelList.get(i).getHotelCode());
//                intent.setClass(mContext, HotelContentActivity.class);
//                mContext.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * 图片
         */
        ImageView roomPic;
        /**
         * 房间类型名
         */
        TextView RoomTypeName;
        /**
         * 房子类型代码
         */
        TextView RoomTypeCode;
        /**
         * 床型代码
         */
        TextView BedTypeCode;
        /**
         * 住房层数
         */
        TextView Floor;
        /**
         * 是否有窗
         */
        TextView HasWindow;
        /**
         * 基础房型名称
         */
        TextView Name;
        /**
         * 是否有烟
         */
        TextView NonSmoking;
        /**
         * 房间数量
         */
        TextView Quantity;
        /**
         * 房间面积
         */
        TextView RoomSize;
        /**
         * 床的尺寸
         */
        TextView Size;
        /**
         * 入住人数
         */
        TextView StandardOccupancy;
        /**
         * 基础房型
         */
        TextView InvBlockCode;

        /**
         *预订图片
         */
        ImageView order;

        public ViewHolder(View view) {
            super(view);
            roomPic = (ImageView) view.findViewById(R.id.roomPic);
            RoomTypeName = (TextView) view.findViewById(R.id.RoomTypeName);
            Quantity = (TextView) view.findViewById(R.id.Quantity);
            RoomSize = (TextView) view.findViewById(R.id.RoomSize);
            StandardOccupancy = (TextView) view.findViewById(R.id.StandardOccupancy);
        }
    }

    // 刷新list
    public void dataChanged() {
        // 通知listView刷新
        notifyDataSetChanged();
    }
}
