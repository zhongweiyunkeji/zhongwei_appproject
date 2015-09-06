package com.cdhxqh.travel_ticket_app.ui.widget;

import android.content.Context;
import android.text.format.DateFormat;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;


import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.model.AddressSelect;

import java.util.ArrayList;
import java.util.Calendar;

public class DateTimePicker extends FrameLayout {
    private final NumberPicker province;
    private final NumberPicker town;
    private final NumberPicker name;
    private OnDateTimeChangedListener mOnDateTimeChangedListener;
    DataBase dataBase = new DataBase();
    ArrayList<AddressSelect> provinceList = null;
    ArrayList<AddressSelect> nameList = null;
    ArrayList<AddressSelect> townList = null;
    AddressSelect privinces, citys;

    public DateTimePicker(Context context) {
        super(context);
        inflate(context, R.layout.activity_address, this);

        /**
         * 省份
         */
        province = (NumberPicker) this.findViewById(R.id.province);
        provinceList = dataBase.getProvince();
        privinces = provinceList.get(0);
        province.setDisplayedValues(getValue(provinceList));
        province.setMaxValue(provinceList.size() - 1);
        province.setMinValue(0);
        province.setOnValueChangedListener(mOnProvinceChangedListener);

        name = (NumberPicker) this.findViewById(R.id.city);
        nameList = dataBase.getCity("2");
        citys = nameList.get(0);
        name.setDisplayedValues(getValue(nameList));
        name.setMaxValue(nameList.size() - 1);
        name.setMinValue(0);
        name.setOnValueChangedListener(mOnNameChangedListener);

        town = (NumberPicker) this.findViewById(R.id.town);
        townList = dataBase.getTown("52");
        town.setDisplayedValues(getValue(townList));
        town.setMaxValue(townList.size() - 1);
        town.setMinValue(0);
        town.setOnValueChangedListener(mOnTownChangedListener);
    }

    private String[] getValue(ArrayList<AddressSelect> addressList) {
        String[] array = new String[addressList.size()];
        for (int i = 0; i < addressList.size(); i++) {
            array[i] = addressList.get(i).getName();
        }
        return array;
    }

    private OnValueChangeListener mOnProvinceChangedListener = new OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            /**
             * 市联动
             */
            privinces = provinceList.get(newVal);
            String id = provinceList.get(newVal).getId();
            nameList = dataBase.getCity(id);
            if (nameList.size() - 1 > name.getMaxValue()) {
                name.setDisplayedValues(getValue(nameList));
                name.setMaxValue(nameList.size() - 1);
            } else {
                name.setMaxValue(nameList.size() - 1);
                name.setDisplayedValues(getValue(nameList));
            }
            name.setMinValue(0);

            /**
             * 区联动
             */
            citys = nameList.get(0);
            String idTown = nameList.get(0).getId();
            townList = dataBase.getTown(idTown);
            if (townList.size() - 1 > town.getMaxValue()) {
                town.setDisplayedValues(getValue(townList));
                town.setMaxValue(townList.size() - 1);
            } else {
                town.setMaxValue(townList.size() - 1);
                town.setDisplayedValues(getValue(townList));
            }
            town.setMinValue(0);
            onDateTimeChanged(privinces, citys, townList.get(0));
        }
    };

    private OnValueChangeListener mOnNameChangedListener = new OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            /**
             * 区联动
             */
            citys = nameList.get(newVal);
            String idTown = nameList.get(newVal).getId();
            townList = dataBase.getTown(idTown);
            if (townList.size() - 1 > town.getMaxValue()) {
                town.setDisplayedValues(getValue(townList));
                town.setMaxValue(townList.size() - 1);
            } else {
                town.setMaxValue(townList.size() - 1);
                town.setDisplayedValues(getValue(townList));
            }
            town.setMinValue(0);
            onDateTimeChanged(privinces, citys, townList.get(0));
        }
    };

    private OnValueChangeListener mOnTownChangedListener = new OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            onDateTimeChanged(privinces, citys, townList.get(newVal));
        }
    };

    public interface OnDateTimeChangedListener {
        void onDateTimeChanged(AddressSelect province, AddressSelect city, AddressSelect town);
    }

    public void setOnDateTimeChangedListener(OnDateTimeChangedListener callback) {
        mOnDateTimeChangedListener = callback;
        onDateTimeChanged(privinces, citys, townList.get(0));
    }

    private void onDateTimeChanged(AddressSelect province, AddressSelect city, AddressSelect town) {
        if (mOnDateTimeChangedListener != null) {
            mOnDateTimeChangedListener.onDateTimeChanged(province, city, town);
        }
    }
}
