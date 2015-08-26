package com.cdhxqh.travel_ticket_app.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.model.Ec_goods;

import java.util.ArrayList;


/**
 * 商品列表Adapter
 */
public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {
    private static final String TAG = "OrderListAdapter";
    Context mContext;

    ArrayList<Ec_goods> ec_goodses = new ArrayList<Ec_goods>();


    public OrderListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.orders_adapter, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Ec_goods ec_goods = ec_goodses.get(i);

        viewHolder.nameText.setText(ec_goods.good_name);

        viewHolder.levelText.setText(ec_goods.good_level);

        viewHolder.ordertimeText.setText(ec_goods.good_order_time);


        viewHolder.ordernumberText.setText(ec_goods.good_order_number);


        viewHolder.payText.setText(String.valueOf(ec_goods.good_pay));

        viewHolder.statusText.setText(ec_goods.good_order_state);


        viewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
         * 景点等级*
         */
        public TextView levelText;
        /**
         * 订票时间
         */
        public TextView ordertimeText;
        /**
         * 门票数量*
         */
        public TextView ordernumberText;

        /**
         * 价格*
         */
        public TextView payText;

        /**
         * 状态*
         */
        public TextView statusText;

        /**
         * 删除按钮*
         */
        public Button deleteBtn;

        public ViewHolder(View view) {
            super(view);

            relativeLayout = (RelativeLayout) view.findViewById(R.id.content_id);
            nameText = (TextView) view.findViewById(R.id.goods_name_id);
            levelText = (TextView) view.findViewById(R.id.goods_level_id);
            ordertimeText = (TextView) view.findViewById(R.id.goods_order_time_id);
            ordernumberText = (TextView) view.findViewById(R.id.tickets_number_text);
            payText = (TextView) view.findViewById(R.id.tickets_pay_text);
            statusText = (TextView) view.findViewById(R.id.order_state_text_id);
            deleteBtn = (Button) view.findViewById(R.id.order_delete_btn_id);

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