package com.cdhxqh.travel_ticket_app.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.ui.adapter.OrdreTrackingAdapter;
import com.cdhxqh.travel_ticket_app.ui.adapter.SearchScenicAdapter;
import com.cdhxqh.travel_ticket_app.ui.widget.ItemDivider;
import com.cdhxqh.travel_ticket_app.zxing.encoding.EncodingHandler;
import com.google.zxing.WriterException;

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

    /**
     * 列表显示
     */
    private RecyclerView order_tracking;

    /**
     * 订单详情
     */
    private OrdreTrackingAdapter ordreTrackingAdapter;

    private RelativeLayout ticket_buy_id;

    private Button order_button_id;


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
        order_tracking = (RecyclerView) findViewById(R.id.order_tracking);
        ticket_buy_id = (RelativeLayout) findViewById(R.id.ticket_buy_id);
        order_button_id = (Button) findViewById(R.id.order_button_id);
    }

    @Override
    protected void initView() {
        //设置标签页显示方式
        backImageView.setVisibility(View.VISIBLE);
        seachImageView.setVisibility(View.GONE);
        titleTextView.setText("订单详情");
        //返回至登录界面事件
        backImageView.setOnClickListener(backImageViewOnClickListener);

        //跳转至购票界面
        ticket_buy_id.setOnClickListener(ticketOnClickListener);

        order_button_id.setOnClickListener(orderOnClickListener);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        order_tracking.setLayoutManager(layoutManager);
        order_tracking.setItemAnimator(new DefaultItemAnimator());

        String[] dataset = new String[3];
//        for(int i = 0; i< dataset.length; i++) {
        dataset[0] = "订单提交成功，带出游";
        dataset[1] = "订单已审核入园";
        dataset[2] = "订单已点评";
//        }
        ordreTrackingAdapter = new OrdreTrackingAdapter(this, dataset);

        order_tracking.setAdapter(ordreTrackingAdapter);
    }

    private void getCode () {
        //根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
        Bitmap qrCodeBitmap = null;
        try {
            qrCodeBitmap = EncodingHandler.createQRCode("123", 350);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        ImageView qrImgImageView=null;
        qrImgImageView.setImageBitmap(qrCodeBitmap);

    }

    /**
     * 返回事件的监听*
     */
    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(Order_Tracking_Activity.this, OrderActivity.class);
            startActivityForResult(intent, 0);
        }
    };

    private View.OnClickListener ticketOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(Order_Tracking_Activity.this, Scenic_Tickets_Activity.class);
            startActivityForResult(intent, 0);
        }
    };

    private View.OnClickListener orderOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(Order_Tracking_Activity.this, Scenic_Tickets_Activity.class);
            startActivityForResult(intent, 0);
        }
    };
}
