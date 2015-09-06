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
import com.cdhxqh.travel_ticket_app.model.hotel.HotelOneModel;
import com.cdhxqh.travel_ticket_app.model.hotel.RoomContent;
import com.cdhxqh.travel_ticket_app.ui.adapter.HotelAdapter;
import com.cdhxqh.travel_ticket_app.ui.adapter.RoomContentAdapter;
import com.cdhxqh.travel_ticket_app.ui.widget.ItemDivider;
import com.cdhxqh.travel_ticket_app.ui.widget.hotelWineShop.base.HttpAccessAdapter;
import com.cdhxqh.travel_ticket_app.ui.widget.hotelWineShop.utils.XMLSplit;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/9/4.
 */
public class HotelContentActivity extends BaseActivity {
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
     * 酒店编码
     */
    private String hotelCode;

    /**
     * 房间列
     */
    private HotelOneModel hotelOneModel;

    /**
     * list
     */
    private RecyclerView hotel_content;
    /**
     * 时间
     */
    private TextView ArrivalAndDeparture;
    /**
     * 取消预订
     */
    private TextView Cancel;
    /**
     * 许可
     */
    private TextView DepositAndPrepaid;
    /**
     * 宠物
     */
    private TextView Pet;
    /**
     * 信用卡
     */
    private TextView Requirements;

    /**
     * 适配器
     */
    private RoomContentAdapter RoomContentAdapter;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_content);
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

        hotel_content = (RecyclerView) findViewById(R.id.hotel_content);
        ArrivalAndDeparture = (TextView) findViewById(R.id.ArrivalAndDeparture);
        Cancel = (TextView) findViewById(R.id.Cancel);
        DepositAndPrepaid = (TextView) findViewById(R.id.DepositAndPrepaid);
        Pet = (TextView) findViewById(R.id.Pet);
        Requirements = (TextView) findViewById(R.id.Requirements);
    }

    @Override
    protected void initView() {
        //设置标签页显示方式
        backImageView.setVisibility(View.VISIBLE);
        seachImageView.setVisibility(View.GONE);
        titleTextView.setText("房间信息");

        backImageView.setOnTouchListener(backImageViewOnTouchListener);
        //返回至登录界面事件
        backImageView.setOnClickListener(backImageViewOnClickListener);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        //添加分割线
        hotel_content.addItemDecoration(new ItemDivider(this, ItemDivider.VERTICAL_LIST));
        hotel_content.setLayoutManager(layoutManager);
        hotel_content.setItemAnimator(new DefaultItemAnimator());

        RoomContentAdapter = new RoomContentAdapter(this);

        hotel_content.setAdapter(RoomContentAdapter);

        hotelCode = getIntent().getStringExtra("hotelCode");
        if (NetWorkUtil.IsNetWorkEnable(HotelContentActivity.this)) {
            /**
             * 加载中
             */
            progressDialog = ProgressDialog.show(HotelContentActivity.this, null,
                    getString(R.string.loading), true, true);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    Message message = new Message();

                    StringBuilder sb = new StringBuilder("<HotelRequest>");
                    sb.append("<RequestBody xmlns:ns=\"http://www.opentravel.org/OTA/2003/05\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">");
                    sb.append("<OTA_HotelDescriptiveInfoRQ Version=\"1.0\" xsi:schemaLocation=\"http://www.opentravel.org/OTA/2003/05 OTA_HotelDescriptiveInfoRQ.xsd\" xmlns=\"http://www.opentravel.org/OTA/2003/05\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
                    sb.append("<HotelDescriptiveInfos>");
                    sb.append("<HotelDescriptiveInfo HotelCode=\"" + hotelCode + "\" PositionTypeCode=\"502\">");
                    sb.append("<HotelInfo SendData=\"true\" />");
                    sb.append("<FacilityInfo SendGuestRooms=\"true\"/>");
                    sb.append("<AreaInfo SendAttractions=\"true\" SendRecreations=\"true\"/>");
                    sb.append("<ContactInfo SendData=\"true\"/>");
                    sb.append("<MultimediaObjects SendData=\"true\"/>");
                    sb.append("</HotelDescriptiveInfo>");

//                sb.append("<HotelDescriptiveInfo HotelCode=\"671\">");
//                sb.append("<HotelInfo SendData=\"true\"/>");
//                sb.append("<FacilityInfo SendGuestRooms=\"true\"/>");
//                sb.append("<AreaInfo SendAttractions=\"true\" SendRecreations=\"true\"/>");
//                sb.append("<MultimediaObjects SendData=\"true\"/>");
//                sb.append("</HotelDescriptiveInfo>");

                    sb.append("</HotelDescriptiveInfos>");
                    sb.append("</OTA_HotelDescriptiveInfoRQ>");
                    sb.append("</RequestBody>");
                    sb.append("</HotelRequest>");

                    String xml = HttpAccessAdapter.getData("OTA_HotelSearch", sb.toString(), "http://openapi.ctrip.com/Hotel/OTA_HotelDescriptiveInfo.asmx", HotelContentActivity.this);
                    message.obj = xml;
                    mHandler.sendMessage(message);
                }
            });
            thread.start();
        }
    }

    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String a = (String) msg.obj;

            hotelOneModel = XMLSplit.xmlContentSplit(a);

            ArrivalAndDeparture.setText(hotelOneModel.getArrivalAndDeparture());
            Cancel.setText(hotelOneModel.getCancel());
            DepositAndPrepaid.setText(hotelOneModel.getArrivalAndDeparture());
            Pet.setText(hotelOneModel.getPet());
            Requirements.setText(hotelOneModel.getRequirements());

            progressDialog.dismiss();
            RoomContentAdapter.update(hotelOneModel.getRoomContentList(), hotelOneModel.getRoomImgModelList());
            RoomContentAdapter.dataChanged();
            super.handleMessage(msg);
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
