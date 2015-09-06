package com.cdhxqh.travel_ticket_app.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.api.NetWorkUtil;
import com.cdhxqh.travel_ticket_app.model.hotel.HotelModel;
import com.cdhxqh.travel_ticket_app.ui.adapter.HotelAdapter;
import com.cdhxqh.travel_ticket_app.ui.widget.ItemDivider;
import com.cdhxqh.travel_ticket_app.ui.widget.hotelWineShop.base.HttpAccessAdapter;
import com.cdhxqh.travel_ticket_app.ui.widget.hotelWineShop.utils.XMLSplit;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/8/28.
 */
public class AroundPlayActivity extends BaseActivity {
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
    /**
     * 酒店列表
     */
    ArrayList<HotelModel> hotelModelArrayList;
    /**
     *list
     */
    RecyclerView hotel;
    /**
     * 适配器
     */
    private HotelAdapter hotelAdapter;

    private ProgressDialog progressDialog;

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
        text_introduction_desc = (TextView) findViewById(R.id.text_introduction_desc);
        hotel = (RecyclerView) findViewById(R.id.hotel);


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
        titleTextView.setText("酒店预订");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        //添加分割线
        hotel.addItemDecoration(new ItemDivider(this, ItemDivider.VERTICAL_LIST));
        hotel.setLayoutManager(layoutManager);
        hotel.setItemAnimator(new DefaultItemAnimator());

        hotelAdapter = new HotelAdapter(this);

        hotel.setAdapter(hotelAdapter);



        //返回至登录界面事件
        backImageView.setOnClickListener(backImageViewOnClickListener);

        if(NetWorkUtil.IsNetWorkEnable(AroundPlayActivity.this)) {
            /**
             * 加载中
             */
            progressDialog = ProgressDialog.show(AroundPlayActivity.this, null,
                    getString(R.string.loading), true, true);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Message message = new Message();

                    StringBuilder sb = new StringBuilder("<HotelRequest>");
                    sb.append("<RequestBody xmlns:ns=\"http://www.opentravel.org/OTA/2003/05\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">");
                    sb.append("<ns:OTA_HotelSearchRQ Version=\"1.0\" PrimaryLangID=\"zh\" xsi:schemaLocation=\"http://www.opentravel.org/OTA/2003/05 OTA_HotelSearchRQ.xsd\" xmlns=\"http://www.opentravel.org/OTA/2003/05\">");
                    sb.append("<ns:Criteria AvailableOnlyIndicator=\"false\">");
                    sb.append("<ns:Criterion>");
                    sb.append("<ns:HotelRef HotelCityCode=\"556\" />");
                    sb.append("<ns:Position PositionTypeCode=\"502\" />");
                    sb.append("<ns:Award Provider=\"HotelStarRate\" Rating=\"3\"/>");
                    sb.append("</ns:Criterion>");
                    sb.append("</ns:Criteria>");
                    sb.append("</ns:OTA_HotelSearchRQ>");
                    sb.append("</RequestBody>");
                    sb.append("</HotelRequest>");

                    String xml = HttpAccessAdapter.getData("OTA_HotelSearch", sb.toString(), "http://openapi.ctrip.com/Hotel/OTA_HotelSearch.asmx?wsdl", AroundPlayActivity.this);
                    message.obj = xml;
                    mHandler.sendMessage(message);
                }
            });
            thread.start();
        }
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

    public Handler mHandler=new Handler()
    {
        public void handleMessage(Message msg)
        {
            String a=(String)msg.obj;
//            String[] hotel = SplitString.getHotels(a);
            hotelModelArrayList = XMLSplit.xmlSplit(a);
            progressDialog.dismiss();
            hotelAdapter.update(hotelModelArrayList);
            hotelAdapter.dataChanged();
            super.handleMessage(msg);
        }
    };

}
