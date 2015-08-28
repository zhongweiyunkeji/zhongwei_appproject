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
        text_introduction_tittle.setText("周边游");
//        ImageLoader.getInstance().displayImage(bundle.getString("spotLogo"), image_introduction_id);
        text_introduction_desc.setText("杨纪淡淡道。他一点也不怕大阿修罗会掐断意识连接，论心情的急切程度，大阿修罗恐怕比自己都要厉害的多。\n" +
                "\n" +
                "如果自己放弃的话，恐怕大阿修罗会是第一个不答应的。\n" +
                "\n" +
                "话是这么说，不过杨纪还是很快将精力集中到了突然℉℉，x.变化的视野出现的东西，就在万年红心桃木法器的底部核心，透过大阿修罗的能力，杨纪看到了完全截然不同的东西。");

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
