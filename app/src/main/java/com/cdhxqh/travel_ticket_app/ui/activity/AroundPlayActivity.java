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
import java.util.HashMap;
import java.util.Map;

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
        Map<String, String> img = new HashMap<String, String>();
        img.put("1776120", "http://Images4.c-ctrip.com/target/hotel/442000/441460/2ce4a9ebc3be4a04a23541a185c91eff_550_412.jpg");
        img.put("1767700", "http://Images4.c-ctrip.com/target/hotel/432000/431103/418b504347b14f46a2ed539d21719ae4_550_412.jpg");
        img.put("1613884", "http://Images4.c-ctrip.com/target/hotel/900000/899699/507007c7dd634837822c941bc5f89fa0_550_412.jpg");
        img.put("740346", "http://Images4.c-ctrip.com/target/hotel/128000/127804/c8196d448a254ed393355e9c1cbe74cb_550_412.jpg");
        img.put("713485", "http://Images4.c-ctrip.com/target/hotel/346000/345332/58edf61b36c34ff5a71924fa42827957_550_412.jpg");
        img.put("1386722", "http://Images4.c-ctrip.com/target/hotel/480000/480000/840f0e68509c4cf39482f7f7d30253d3_550_412.jpg");
        img.put("1171591", "http://Images4.c-ctrip.com/target/fd/hotel/g2/M04/99/C0/Cghzf1U_EbmAF9gKAAZ89yfcf-w757_550_412.jpg");
        img.put("1048214", "http://Images4.c-ctrip.com/target/fd/hotel/g1/M04/8A/6A/CghzfFVR8JWAVUi3AAE2EccaJIc371_550_412.jpg");
        img.put("812986", "http://Images4.c-ctrip.com/target/hotel/469000/468504/0225136815d84e54a66f15accc1e271a_550_412.jpg");
        img.put("803031", "http://Images4.c-ctrip.com/target/hotel/90000/89813/F6007735-FAAA-4EA0-8554-E08BBA9C145B_550_412.jpg");
        img.put("2143578", "http://Images4.c-ctrip.com/target/hotel/121000/120261/ec83ebf43a0b497eb7c576913ba46a06_550_412.jpg");
        img.put("801601", "http://Images4.c-ctrip.com/target/hotel/72000/71068/d8c8a93d9d2c4277be5bf21376cf10f8_550_412.jpg");
        img.put("741968", "http://Images4.c-ctrip.com/target/hotel/136000/135320/EA824AEF1E76462EAE83CABFBFB71917_550_412.Jpg");
        img.put("741957", "http://Images4.c-ctrip.com/target/fd/hotelcomment/g2/M0B/3A/57/CghzgFW25TSAM3e-AAM5hz-d2N0252_550_412.jpg");
        img.put("741940", "http://Images4.c-ctrip.com/target/hotel/346000/345502/f006b7c70e444a38ae7a214f19898da1_550_412.jpg");
        img.put("1327019", "http://Images4.c-ctrip.com/target/fd/hotel/g3/M0B/DE/5E/CggYGVXIOjSAcB9mAAISm5vJbK8880_550_412.jpg");




        String title = "酒店";
        if(getIntent() != null) {
            title = getIntent().getStringExtra("spotTitle");
        }
        titleTextView.setText(title);

        //设置标签页显示方式
        backImageView.setVisibility(View.VISIBLE);
        seachImageView.setVisibility(View.GONE);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        //添加分割线
        hotel.addItemDecoration(new ItemDivider(this, ItemDivider.VERTICAL_LIST));
        hotel.setLayoutManager(layoutManager);
        hotel.setItemAnimator(new DefaultItemAnimator());

        hotelAdapter = new HotelAdapter(this, img);

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
                    sb.append("<ns:Award Provider=\"HotelStarRate\" Rating=\"4\" />");
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
