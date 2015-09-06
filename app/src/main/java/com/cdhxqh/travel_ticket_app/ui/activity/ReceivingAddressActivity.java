package com.cdhxqh.travel_ticket_app.ui.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.model.AddressSelect;
import com.cdhxqh.travel_ticket_app.ui.widget.DateTimePicker;
import com.cdhxqh.travel_ticket_app.ui.widget.DateTimePickerDialog;

/**
 * Created by Administrator on 2015/8/29.
 */
public class ReceivingAddressActivity extends BaseActivity{
    /**
     * 收货地址
     */
    private TextView address;
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
        setContentView(R.layout.activity_receiving_address);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        address = (TextView) findViewById(R.id.address);
        back_id = (Button) findViewById(R.id.back_id);

        /**
         * 标题标签相关id
         */
        backImageView = (ImageView) findViewById(R.id.back_imageview_id);
        titleTextView = (TextView) findViewById(R.id.title_text_id);
        seachImageView = (ImageView) findViewById(R.id.title_search_id);
    }

    @Override
    protected void initView() {
        back_id.setText("完成");
        //设置标签页显示方式
        backImageView.setVisibility(View.VISIBLE);
        seachImageView.setVisibility(View.GONE);
        titleTextView.setText("收货地址");
        backImageView.setOnTouchListener(backImageViewOnTouchListener);
        //返回至登录界面事件
        backImageView.setOnClickListener(backImageViewOnClickListener);
        //完成
        back_id.setOnClickListener(backOnClickListener);

        address.setOnClickListener(addressOnClickListener);
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

    private View.OnClickListener addressOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DateTimePickerDialog dialog = new DateTimePickerDialog(ReceivingAddressActivity.this);
            dialog.setOnDateTimeSetListener(new DateTimePickerDialog.OnDateTimeSetListener() {
                public void OnDateTimeSet(AddressSelect provinces, AddressSelect citys, AddressSelect towns) {
                    address.setText(provinces.getName() + citys.getName() + towns.getName());
                }
            });
            dialog.show();
        }
    };

    private View.OnClickListener backOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
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
