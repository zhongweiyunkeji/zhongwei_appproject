package com.cdhxqh.travel_ticket_app.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.api.HttpManager;
import com.cdhxqh.travel_ticket_app.api.HttpRequestHandler;
import com.cdhxqh.travel_ticket_app.model.Ecs_brand;
import com.cdhxqh.travel_ticket_app.model.SpotBookModel;
import com.cdhxqh.travel_ticket_app.utils.MessageUtils;
import com.cdhxqh.travel_ticket_app.utils.TimeCountUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/28.
 */
public class IntroductionActivity extends BaseActivity{
    /**
     * 景区简介标题
     */
    private TextView text_introduction_tittle;

    /**
     * 景区简介图片
     */
    private ImageView image_introduction_id;

    /**
     * 景区简介详情
     */
    private TextView text_introduction_desc;
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

//    private ProgressDialog progressDialog;
//
//    ArrayList<SpotBookModel> datas = new ArrayList<SpotBookModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        findViewById();
        initView();
//        text();
    }

    /**
     * 绑定控件id
     */
    @Override
    protected void findViewById() {
        text_introduction_tittle = (TextView) findViewById(R.id.text_introduction_tittle);
        image_introduction_id = (ImageView) findViewById(R.id.image_introduction_id);
        text_introduction_desc= (TextView) findViewById(R.id.text_introduction_desc);

        /**
         * 标题标签相关id
         */
        backImageView = (ImageView) findViewById(R.id.back_imageview_id);
        titleTextView = (TextView) findViewById(R.id.title_text_id);
        seachImageView = (ImageView) findViewById(R.id.title_search_id);
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initView() {
        //设置标签页显示方式
        backImageView.setVisibility(View.VISIBLE);
        seachImageView.setVisibility(View.GONE);
        titleTextView.setText("景区简介");

        Bundle bundle = new Bundle();
        bundle = this.getIntent().getExtras();
        text_introduction_tittle.setText(bundle.getString("spotId"));
        ImageLoader.getInstance().displayImage(bundle.getString("spotLogo"), image_introduction_id);
        text_introduction_desc.setText(bundle.getString("spotDesc"));

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
            intent.setClass(IntroductionActivity.this, Tickets_Detail_Activity.class);
            startActivityForResult(intent, 0);
        }
    };

}
