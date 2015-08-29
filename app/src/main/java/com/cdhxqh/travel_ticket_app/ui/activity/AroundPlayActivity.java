package com.cdhxqh.travel_ticket_app.ui.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Administrator on 2015/8/28.
 */
public class AroundPlayActivity extends BaseActivity{
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
        setContentView(R.layout.activity_around_play);
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
        backImageView.setOnTouchListener(backImageViewOnTouchListener);
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
     * 初始化控件
     */
    @Override
    protected void initView() {
        //设置标签页显示方式
        backImageView.setVisibility(View.VISIBLE);
        seachImageView.setVisibility(View.GONE);
        titleTextView.setText("周边游");

        Bundle bundle = new Bundle();
        bundle = this.getIntent().getExtras();
        text_introduction_tittle.setText("中卫市");
//        ImageLoader.getInstance().displayImage(bundle.getString("spotLogo"), image_introduction_id);
        text_introduction_desc.setText("沙坡头区\n" +
                "\n" +
                "兴仁镇 蒿川乡 镇罗镇 永康镇 宣和镇 常乐镇 香山乡\n" +
                "\n" +
                "中宁县\n" +
                "\n" +
                "宁安镇 鸣沙镇 石空镇 新堡镇 恩和镇 舟塔乡 白马乡 余丁乡 大战场乡 喊叫水乡\n" +
                "\n" +
                "海原县\n" +
                "\n" +
                 "海城镇 李旺镇 西安镇 黑城镇 史店乡 树台乡 关桥乡 徐套乡 兴隆乡 高崖乡 郑旗乡 贾塘乡 曹洼乡 九彩乡 李俊乡 红羊乡 关庄乡 涵养林总场");

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

}
