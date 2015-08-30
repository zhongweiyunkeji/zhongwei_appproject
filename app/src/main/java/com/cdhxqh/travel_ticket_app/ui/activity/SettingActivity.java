package com.cdhxqh.travel_ticket_app.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.api.DataCleanManager;
import com.cdhxqh.travel_ticket_app.api.NetWorkUtil;
import com.cdhxqh.travel_ticket_app.app.SwitchButton;
import com.cdhxqh.travel_ticket_app.app.SwitchButtonIs;
import com.cdhxqh.travel_ticket_app.utils.AccountUtils;

/**
 * Created by Administrator on 2015/8/18.
 */
public class SettingActivity extends BaseActivity {
    /**
     * 2G或3G
     */
    private RelativeLayout install_flow;

    /**
     * 清除缓存
     */
    private RelativeLayout catchs;


    /**
     * 是否的滑动开关
     */
    SwitchButtonIs wiperSwitch;

//    /**
//     *
//     */
//    DataCleanManager dataCleanManager;



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
     * 完成
     */
    private Button back_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_settings);
        findViewById();
        initView();
    }

    protected void findViewById() {
        install_flow = (RelativeLayout)findViewById(R.id.install_flow);
        catchs = (RelativeLayout) findViewById(R.id.catchs);
        wiperSwitch = (SwitchButtonIs) findViewById(R.id.wiperSwitch);
        back_id = (Button) findViewById(R.id.back_id);

        /**
         * 标题标签相关id
         */
        backImageView = (ImageView) findViewById(R.id.back_imageview_id);
        titleTextView = (TextView) findViewById(R.id.title_text_id);
        seachImageView = (ImageView) findViewById(R.id.title_search_id);
        back_id = (Button) findViewById(R.id.back_id);
    }

    protected void initView() {
        catchs.setOnClickListener(catchsOnClickListener);
        back_id.setOnClickListener(backOnClickListener);
        back_id.setText("退出");

        //设置标签页显示方式
        backImageView.setVisibility(View.VISIBLE);
        seachImageView.setVisibility(View.GONE);
        titleTextView.setText("设置");
        backImageView.setOnTouchListener(backImageViewOnTouchListener);
        //返回至登录界面事件
        backImageView.setOnClickListener(backImageViewOnClickListener);
        wiperSwitch.setOnClickListener(new SwitchButton.OnClickListener() {

            @Override
            public void onClick(View v) {
                Dialog();
            }
        });

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
     * 返回事件的监听*
     */
    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    /**
     * 清除缓存
     */
    private View.OnClickListener catchsOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            DataCleanManager.cleanInternalCache(SettingActivity.this);
            DataCleanManager.cleanSharedPreference(SettingActivity.this);
//            DataCleanManager.cleanDatabases(activity);
//            DataCleanManager.cleanSharedPreference(activity);
        }
    };

    /**
     * 2G或3G模式
     */
    public void getNetWork() {
        String type = NetWorkUtil.getCurrentNetworkType(SettingActivity.this);
        if (type.equals("2G") || type.equals("3G")) {
            Dialog();
        }
    }

    /**
     * 回退按钮
     */
    private View.OnClickListener backOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SettingActivity.this.finish();
        }
    };

    /**
     * 确认或取消框
     */
    private void Dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        builder.setTitle("打开后非wife网络环境下将不显示图片喔，确定打开吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //这里添加点击确定后的逻辑
//                showDialog("你选择了确定");
                wiperSwitch.setState(true);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                wiperSwitch.setState(false);
            }
        });
        builder.create().show();
    }

}
