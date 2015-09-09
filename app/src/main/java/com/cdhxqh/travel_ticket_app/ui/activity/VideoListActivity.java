package com.cdhxqh.travel_ticket_app.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.api.HttpManager;
import com.cdhxqh.travel_ticket_app.api.HttpRequestHandler;
import com.cdhxqh.travel_ticket_app.ui.adapter.HotelAdapter;
import com.cdhxqh.travel_ticket_app.ui.widget.ItemDivider;
import com.cdhxqh.travel_ticket_app.utils.MessageUtils;
import com.cdhxqh.travel_ticket_app.utils.TimeCountUtil;

/**
 * Created by Administrator on 2015/9/9.
 */
public class VideoListActivity extends BaseActivity {
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

    private ProgressDialog progressDialog;

    private static int showCount = 5;
    private static int currentPage = 1;

    /**
     *list
     */
    RecyclerView hotel;

    /**
     * 适配器
     */
    private HotelAdapter hotelAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_around_play);
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
        hotel = (RecyclerView) findViewById(R.id.hotel);
    }

    @Override
    protected void initView() {
        backImageView.setOnTouchListener(backImageViewOnTouchListener);
        //返回至登录界面事件
        backImageView.setOnClickListener(backImageViewOnClickListener);

        titleTextView.setText("视频列表");

        //设置标签页显示方式
        backImageView.setVisibility(View.VISIBLE);
        seachImageView.setVisibility(View.GONE);
        String brandid = getIntent().getStringExtra("brandid");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        //添加分割线
        hotel.addItemDecoration(new ItemDivider(this, ItemDivider.VERTICAL_LIST));
        hotel.setLayoutManager(layoutManager);
        hotel.setItemAnimator(new DefaultItemAnimator());




        getVideo(brandid);
    }

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


    private void getVideo (String brandid) {
        /**
         * 加载中
         */
        progressDialog = ProgressDialog.show(VideoListActivity.this, null,
                getString(R.string.loading), true, true);

        HttpManager.getVideo(this,
                brandid,
                String.valueOf(showCount),
                String.valueOf(currentPage),
                new HttpRequestHandler<Integer>() {
                    @Override
                    public void onSuccess(Integer data) {

                    }

                    @Override
                    public void onSuccess(Integer data, int totalPages, int currentPage) {
                        Log.i(TAG, "22222");
                    }

                    @Override
                    public void onFailure(String error) {
                        MessageUtils.showErrorMessage(VideoListActivity.this, error);
                        progressDialog.dismiss();
                    }
                });
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
}
