package com.cdhxqh.travel_ticket_app.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.ui.adapter.OrdreTrackingAdapter;
import com.cdhxqh.travel_ticket_app.ui.adapter.SearchScenicAdapter;
import com.cdhxqh.travel_ticket_app.ui.widget.ItemDivider;

/**
 * Created by Administrator on 2015/8/4.
 */
public class Order_Tracking_Activity extends BaseActivity{
    /**
     * 列表显示
     */
    private RecyclerView order_tracking;

    /**
     * 订单详情
     */
    private OrdreTrackingAdapter ordreTrackingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        order_tracking = (RecyclerView) findViewById(R.id.order_tracking);
    }

    @Override
    protected void initView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        order_tracking.setLayoutManager(layoutManager);
        order_tracking.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        order_tracking.addItemDecoration(new ItemDivider(this, ItemDivider.VERTICAL_LIST));

        String[] dataset = new String[3];
        for(int i = 0; i< dataset.length; i++) {
            dataset[i] = "item" + i;
        }
        ordreTrackingAdapter = new OrdreTrackingAdapter(this, dataset);

        order_tracking.setAdapter(ordreTrackingAdapter);
    }
}
