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

import java.util.ArrayList;

import com.cdhxqh.travel_ticket_app.ui.activity.ReservationActivity;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * 商品列表Adapter
 */
public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.ViewHolder> {
    private static final String TAG = "GoodsListAdapter";
    Context mContext;

    ArrayList<Ec_goods> ec_goodses = new ArrayList<Ec_goods>();


    public GoodsListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.goods_adapter, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Ec_goods ec_goods = ec_goodses.get(i);


        ImageLoader.getInstance().displayImage(ec_goods.getBrand_logo(), viewHolder.imageView);
        viewHolder.nameText.setText(ec_goods.getGood_name());
        viewHolder.timeText.setText(ec_goods.getGood_time());
        viewHolder.payText.setText(ec_goods.getGood_price());

        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("tittle_reservation", ec_goods.getGood_name());
                bundle.putString("end_date_id_a", "2015-12-31 17:00:00");
                bundle.putString("bookingNum", ec_goods.getGood_price());
                bundle.putString("unit_fare", ec_goods.getGood_price());
                bundle.putString("goodsId", String.valueOf(ec_goods.getId()));
                intent.putExtras(bundle);
                intent.setClass(mContext, ReservationActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ec_goodses.size();
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
         * 景点名称
         */
        public TextView nameText;
        /**
         * 营业时间*
         */
        public TextView timeText;
        /**
         * 价格*
         */
        public TextView payText;

        public ViewHolder(View view) {
            super(view);

            relativeLayout = (RelativeLayout) view.findViewById(R.id.content_id);
            imageView= (ImageView) view.findViewById(R.id.goods_image_id);
            nameText = (TextView) view.findViewById(R.id.goods_name_id);
            timeText = (TextView) view.findViewById(R.id.goods_time_id);
            payText = (TextView) view.findViewById(R.id.goods_pay_id);

        }
    }




    public void update(ArrayList<Ec_goods> data, boolean merge) {
        Log.i(TAG, "mItems=" + ec_goodses.size());
        if (merge && ec_goodses.size() > 0) {
            for (int i = 0; i < ec_goodses.size(); i++) {
                Ec_goods obj = ec_goodses.get(i);
                boolean exist = false;
                for (int j = 0; j < data.size(); j++) {
                    if (data.get(j).id == obj.id) {
                        exist = true;
                        break;
                    }
                }
                if (exist) continue;
                data.add(obj);
            }
        }
        ec_goodses = data;

        notifyDataSetChanged();
    }


}