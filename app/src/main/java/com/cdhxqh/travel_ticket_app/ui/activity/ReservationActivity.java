package com.cdhxqh.travel_ticket_app.ui.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;

import java.util.Calendar;

/**
 * Created by Administrator on 2015/7/28.
 */
public class ReservationActivity extends BaseActivity{
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
     * 增票按钮
     */
    private Button reservation_add_button;

    /**
     * 减票按钮
     */
    private Button reservation_reduce_button;

    /**
     * 门票数量文本框
     */
    private EditText reservation_display_num;

    /**
     * 总价格显示
     */
    private TextView total_fare_id;

    /**
     * 门票数量
     */
    private int bookingNum;

    /**
     * 出行日期按钮
     */
    private TextView outDate_id;

    /**
     *门票单价
     */
    private int unit_fare = 50;

    /**
     *出行日期显示
     */
    private TextView out_date_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        reservation_add_button = (Button) findViewById(R.id.reservation_add_button);
        reservation_reduce_button = (Button) findViewById(R.id.reservation_reduce_button);
        reservation_display_num = (EditText)findViewById(R.id.reservation_display_num);
        total_fare_id = (TextView) findViewById(R.id.total_fare_id);
        outDate_id = (TextView) findViewById(R.id.outDate_id);
        out_date_id = (TextView) findViewById(R.id.out_date_id);

        /**
         * 标题标签相关id
         */
        backImageView = (ImageView) findViewById(R.id.back_imageview_id);
        titleTextView = (TextView) findViewById(R.id.title_text_id);
        seachImageView = (ImageView) findViewById(R.id.title_search_id);
    }

    @Override
    protected void initView() {
        //设置标签页显示方式
        backImageView.setVisibility(View.VISIBLE);
        seachImageView.setVisibility(View.GONE);
        titleTextView.setText("预订信息填写");
        //返回至登录界面事件
        backImageView.setOnClickListener(backImageViewOnClickListener);
        /**
         *加票事件
         */
        reservation_add_button.setOnClickListener(addOnClickListener);
        /**
         *减票事件
         */
        reservation_reduce_button.setOnClickListener(reduceOnClickListener);
        /**
         * 门票数量文本框事件
         */
        reservation_display_num.addTextChangedListener(changeChangedListener);
        /**
         * 出行日期
         */
        outDate_id.setOnClickListener(outDateOnClickListener);
    }

    /**
     * 返回事件的监听*
     */
    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(ReservationActivity.this, Tickets_Detail_Activity.class);
            startActivityForResult(intent, 0);
        }
    };

    /**
     * 出行日期
     */
    private View.OnClickListener outDateOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showDialog(1);
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 1:
                Log.v("Test", "--------start---------->");
                Calendar c = Calendar.getInstance();
                return new DatePickerDialog(this, onDateSetListener, c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1, c.get(Calendar.DAY_OF_MONTH));
            // return new TimePickerDialog(this,onTimeSetListener,22,3, true);
        }
        return super.onCreateDialog(id);
    }

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            out_date_id.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
        }
    };

/**
     * 加票事件
     */
    private View.OnClickListener addOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            bookingNum =  new Integer(reservation_display_num.getText().toString());
            reservation_display_num.setText(String.valueOf(bookingNum + 1));
        }
    };

    /**
     * 减票事件
     */
    private View.OnClickListener reduceOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            bookingNum = new Integer(reservation_display_num.getText().toString());
            if(bookingNum > 1)
            reservation_display_num.setText(String.valueOf(bookingNum - 1));
        }
    };

    /**
     *  门票数量文本框事件
     */
    private TextWatcher changeChangedListener = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(reservation_display_num.getText().toString() != null && !reservation_display_num.getText().toString().equals("")) {
//                if(Math.abs(new Integer(reservation_display_num.getText().toString())) != new Integer(reservation_display_num.getText().toString())){
//                    reservation_display_num.setText(Math.abs(new Integer(reservation_display_num.getText().toString())));
//                }
                bookingNum = new Integer(reservation_display_num.getText().toString());
                total_fare_id.setText(String.valueOf(bookingNum * unit_fare));
            } else {
                total_fare_id.setText(String.valueOf(0));
            }
        }
    };
}
