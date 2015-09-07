package com.cdhxqh.travel_ticket_app.ui.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2015/9/7.
 */
public class IntelligentAnalyticsActivity extends BaseActivity{
    private ImageView today_0, today_1, today_2, today_3, today_4;
    private ImageView content_0, content_1, content_2, content_3, content_4, content_5;
    private Map<Integer, Integer> map = new HashMap<Integer, Integer>();

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
        setContentView(R.layout.activity_analytics);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        today_0 = (ImageView) findViewById(R.id.today_0);
        today_1 = (ImageView) findViewById(R.id.today_1);
        today_2 = (ImageView) findViewById(R.id.today_2);
        today_3 = (ImageView) findViewById(R.id.today_3);
        today_4 = (ImageView) findViewById(R.id.today_4);

        content_0 = (ImageView) findViewById(R.id.content_0);
        content_1 = (ImageView) findViewById(R.id.content_1);
        content_2 = (ImageView) findViewById(R.id.content_2);
        content_3 = (ImageView) findViewById(R.id.content_3);
        content_4 = (ImageView) findViewById(R.id.content_4);
        content_5 = (ImageView) findViewById(R.id.content_5);

        /**
         * 标题标签相关id
         */
        backImageView = (ImageView) findViewById(R.id.back_imageview_id);
        titleTextView = (TextView) findViewById(R.id.title_text_id);
        seachImageView = (ImageView) findViewById(R.id.title_search_id);
    }

    @Override
    protected void initView() {
        String tittle = getIntent().getStringExtra("spotTitle");
        //设置标签页显示方式
        backImageView.setVisibility(View.VISIBLE);
        seachImageView.setVisibility(View.GONE);
        titleTextView.setText(tittle);

        map.put(0, R.drawable.number0);
        map.put(1, R.drawable.number1);
        map.put(2, R.drawable.number2);
        map.put(3, R.drawable.number3);
        map.put(4, R.drawable.number4);
        map.put(5, R.drawable.number5);
        map.put(6, R.drawable.number6);
        map.put(7, R.drawable.number7);
        map.put(8, R.drawable.number8);
        map.put(9, R.drawable.number9);

        int i = 0;
        while (i == 0) {
           i = new Random().nextInt(10);
        }
        today_0.setBackgroundResource(map.get(i));
        today_1.setBackgroundResource(map.get(new Random().nextInt(10)));
        today_2.setBackgroundResource(map.get(new Random().nextInt(10)));
        today_3.setBackgroundResource(map.get(new Random().nextInt(10)));

        content_0.setBackgroundResource(map.get(new Random().nextInt(10)));
        content_1.setBackgroundResource(map.get(new Random().nextInt(10)));
        content_2.setBackgroundResource(map.get(new Random().nextInt(10)));
        content_3.setBackgroundResource(map.get(new Random().nextInt(10)));
        content_4.setBackgroundResource(map.get(new Random().nextInt(10)));

        backImageView.setOnTouchListener(backImageViewOnTouchListener);
        //返回至登录界面事件
        backImageView.setOnClickListener(backImageViewOnClickListener);
    }
    /**
     * 返回事件的监听*
     */
    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private View.OnTouchListener backImageViewOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.setBackgroundColor(getResources().getColor(R.color.list_item_read));
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                v.setBackgroundColor(getResources().getColor(R.color.clarity));
            }
            return false;
        }
    };
}
