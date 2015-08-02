package com.cdhxqh.travel_ticket_app.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;

/**
 * Created by Administrator on 2015/7/28.
 */
public class ReservationActivity extends BaseActivity{
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
     *门票单价
     */
    private int unit_fare = 50;

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
    }

    @Override
    protected void initView() {
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
    }

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
