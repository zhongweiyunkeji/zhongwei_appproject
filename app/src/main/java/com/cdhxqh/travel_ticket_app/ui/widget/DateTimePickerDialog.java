package com.cdhxqh.travel_ticket_app.ui.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.text.format.DateUtils;

import com.cdhxqh.travel_ticket_app.model.AddressSelect;

import java.util.Calendar;

public class DateTimePickerDialog extends AlertDialog implements OnClickListener {
    private DateTimePicker mDateTimePicker;
    private OnDateTimeSetListener mOnDateTimeSetListener;
    AddressSelect provinces, citys, towns;

    @SuppressWarnings("deprecation")
    public DateTimePickerDialog(Context context) {
        super(context);
        mDateTimePicker = new DateTimePicker(context);
        setView(mDateTimePicker);
        mDateTimePicker.setOnDateTimeChangedListener(new DateTimePicker.OnDateTimeChangedListener() {
            @Override
            public void onDateTimeChanged(AddressSelect province, AddressSelect city, AddressSelect town) {
                provinces = province;
                citys = city;
                towns = town;
            }
        });
        setButton("设置", this);
        setButton2("取消", (OnClickListener) null);
        updateTitle();
    }

    public interface OnDateTimeSetListener {
        void OnDateTimeSet(AddressSelect provinces, AddressSelect citys, AddressSelect towns);
    }

    private void updateTitle() {
//        setTitle("设置星期");
    }

    public void setOnDateTimeSetListener(OnDateTimeSetListener callBack) {
        mOnDateTimeSetListener = callBack;
    }

    public void onClick(DialogInterface arg0, int arg1) {
        if (mOnDateTimeSetListener != null) {
            mOnDateTimeSetListener.OnDateTimeSet(provinces, citys, towns);
        }
    }
}
