package com.cdhxqh.travel_ticket_app.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.cdhxqh.travel_ticket_app.model.VideoModel;
import com.cdhxqh.travel_ticket_app.ui.adapter.HotelAdapter;
import com.cdhxqh.travel_ticket_app.ui.widget.ItemDivider;
import com.cdhxqh.travel_ticket_app.utils.MessageUtils;
import com.cdhxqh.travel_ticket_app.utils.NetWorkHelper;
import com.cdhxqh.travel_ticket_app.utils.TimeCountUtil;

import java.util.ArrayList;

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


    private static int showCount = 10;
    private static int currentPage = 1;

    /**
     *list
     */
    RecyclerView hotel;

    /**
     * 适配器
     */
    private HotelAdapter hotelAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    private ProgressDialog progressDialog;

    String brandid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_around_play);
        findViewById();
        initView();

//        requestEcsBrands(true);
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
//        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_container);
    }

    @Override
    protected void initView() {
//        swipeRefreshLayout.setVisibility(View.VISIBLE);
        backImageView.setOnTouchListener(backImageViewOnTouchListener);
        //返回至登录界面事件
        backImageView.setOnClickListener(backImageViewOnClickListener);

        titleTextView.setText("视频列表");

        //设置标签页显示方式
        backImageView.setVisibility(View.VISIBLE);
        seachImageView.setVisibility(View.GONE);
        brandid = getIntent().getStringExtra("brandid");
        String brandName = getIntent().getStringExtra("brandName");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        //添加分割线
        hotel.addItemDecoration(new ItemDivider(this, ItemDivider.VERTICAL_LIST));
        hotel.setLayoutManager(layoutManager);
        hotel.setItemAnimator(new DefaultItemAnimator());

        hotelAdapter = new HotelAdapter(this, brandName);

        hotel.setAdapter(hotelAdapter);
        getVideo(brandid);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                requestEcsBrands(true);
//            }
//        });
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

    /**
     * 创建progressDialog*
     */
    private void createProgressDialog() {
        progressDialog = ProgressDialog.show(VideoListActivity.this, null, getString(R.string.please_loading_hint), true, true);
    }

    private void requestEcsBrands(boolean refresh) {
        if (NetWorkHelper.isNetAvailable(this)) {
            swipeRefreshLayout.setRefreshing(false);
            createProgressDialog();
            HttpManager.getVideo(this,
                    brandid,
                    String.valueOf(showCount),
                    String.valueOf(currentPage),
                    handerle);
        } else {
            MessageUtils.showErrorMessage(this, getResources().getString(R.string.error_network_exception));
        }
    }


    private void getVideo (String brandid) {
        /**
         * 加载中
         */
        progressDialog = ProgressDialog.show(VideoListActivity.this, null, getString(R.string.please_loading_hint), true, true);

        HttpManager.getVideo(this,
                brandid,
                String.valueOf(showCount),
                String.valueOf(currentPage),
                handerle);
    }

    HttpRequestHandler handerle = new HttpRequestHandler<ArrayList<VideoModel>>() {
        @Override
        public void onSuccess(ArrayList<VideoModel> data) {
            currentPage++;
//            hotelAdapter.update(data);
            progressDialog.dismiss();
        }

        @Override
        public void onSuccess(ArrayList<VideoModel> data, int totalPages, int currentPage) {
            VideoListActivity.this.currentPage++;
            progressDialog.dismiss();
            hotelAdapter.update(data);
            hotelAdapter.dataChanged();
            Log.i(TAG, "222222");
        }

        @Override
        public void onFailure(String error) {
            MessageUtils.showErrorMessage(VideoListActivity.this, error);
            progressDialog.dismiss();
        }
    };

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
