package com.cdhxqh.travel_ticket_app.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.ui.adapter.OrdreTrackingAdapter;
import com.cdhxqh.travel_ticket_app.ui.adapter.SearchScenicAdapter;
import com.cdhxqh.travel_ticket_app.ui.widget.ItemDivider;

/**
 * Created by Administrator on 2015/8/4.
 */
public class Order_Tracking_Activity extends BaseActivity{
    /**
     * 返回按钮*
     */
    private ImageView backImageView;

    /**
     * 标题*
     */
    private TextView titleTextView;


    /**
     * 搜索*
     */
    private ImageView seachImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        findViewById();
        initView();
    }


    @Override
    protected void findViewById() {
        /**
         * 标题标签相关id
         */
        backImageView = (ImageView) findViewById(R.id.back_imageview_id);
        titleTextView = (TextView) findViewById(R.id.title_text_id);
        seachImageView = (ImageView) findViewById(R.id.title_search_id);
    }

    @Override
    protected void initView() {
        //设置标签页显示方式
        backImageView.setVisibility(View.VISIBLE);
        seachImageView.setVisibility(View.GONE);
        titleTextView.setText("订单详情");
        //返回至登录界面事件
        backImageView.setOnClickListener(backImageViewOnClickListener);
    }

    /**
     * 返回事件的监听*
     */
    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(Order_Tracking_Activity.this, Order_Tracking_Activity.class);
            startActivityForResult(intent, 0);
        }
    };
}
