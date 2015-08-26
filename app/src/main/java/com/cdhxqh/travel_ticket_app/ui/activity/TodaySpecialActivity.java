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
import com.cdhxqh.travel_ticket_app.model.Ec_goods;
import com.cdhxqh.travel_ticket_app.ui.adapter.GoodsListAdapter;
import com.cdhxqh.travel_ticket_app.ui.widget.ItemDivider;
import com.cdhxqh.travel_ticket_app.utils.MessageUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/8/26.
 */
public class TodaySpecialActivity extends BaseActivity{
    /**
     * list
     */
    RecyclerView special_today;

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

    GoodsListAdapter specialTodayAdapter;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_special);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        special_today = (RecyclerView) findViewById(R.id.special_today);

        /**
         * 标题标签相关id
         */
        backImageView = (ImageView) findViewById(R.id.back_imageview_id);
        titleTextView = (TextView) findViewById(R.id.title_text_id);
        seachImageView = (ImageView) findViewById(R.id.title_search_id);
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

    @Override
    protected void initView() {
        //设置标签页显示方式
        backImageView.setVisibility(View.VISIBLE);
        seachImageView.setVisibility(View.GONE);
        titleTextView.setText("今日特价");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        special_today.setLayoutManager(layoutManager);
        special_today.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        special_today.addItemDecoration(new ItemDivider(this,
                ItemDivider.VERTICAL_LIST));


        specialTodayAdapter = new GoodsListAdapter(this);

        special_today.setAdapter(specialTodayAdapter);
        hotSpot();
    }

    /**
     * 热门景点
     */
    private void hotSpot () {
        /**
         * 加载中
         */
        progressDialog = ProgressDialog.show(TodaySpecialActivity.this, null,
                getString(R.string.loading), true, true);

        HttpManager.geHot(this,
                "isPromote",
                "true",
                new HttpRequestHandler<ArrayList<Ec_goods>>() {
                    @Override
                    public void onSuccess(ArrayList<Ec_goods> data) {
//                        MessageUtils.showMiddleToast(ClassActivity.this, "邮箱发送成功");
                        progressDialog.dismiss();
                        specialTodayAdapter.update(data, true);

                    }

                    @Override
                    public void onSuccess(ArrayList<Ec_goods> data, int totalPages, int currentPage) {
                        Log.i(TAG, "22222");
                    }

                    @Override
                    public void onFailure(String error) {
                        MessageUtils.showErrorMessage(TodaySpecialActivity.this, error);
                        progressDialog.dismiss();
                    }
                });
    }
}
