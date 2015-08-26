package com.cdhxqh.travel_ticket_app.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 景区门票表
 */
public class Ec_goods extends Zw_Model implements Parcelable {
    private static final String TAG = "Ec_goods";
    private static final long serialVersionUID = 2015050105L;


    /**
     * id*
     */
    public int id;

    /**
     * 名称*
     */
    public String good_name;
    /**
     * 时间*
     */
    public String good_time;
    /**
     * 价格*
     */
    public double good_pay;

    /**
     * 景点级别*
     */
    public String good_level;

    /**
     * 订购时间*
     */
    public String good_order_time;

    /**
     * 门票数量*
     */
    public String good_order_number;

    /**
     * 订单状态*
     */
    public String good_order_state;

    public String getBrand_logo() {
        return brand_logo;
    }

    public void setBrand_logo(String brand_logo) {
        this.brand_logo = brand_logo;
    }

    public String getGood_price() {
        return good_price;
    }

    public void setGood_price(String good_price) {
        this.good_price = good_price;
    }

    /**
     * 价格*
     */
    public String good_price;

    /**
     * 景区Logo
     */
    public String brand_logo;

    public void parse(JSONObject jsonObject) throws JSONException {
        Log.i(TAG, "jsonObject=" + jsonObject.toString());
        id = jsonObject.getInt("id");
        good_name = jsonObject.getString("good_name");
        good_time = jsonObject.getString("good_time");
        good_pay = jsonObject.getDouble("good_pay");
        good_level = jsonObject.getString("good_level");
        good_order_time = jsonObject.getString("good_order_time");
        good_order_number = jsonObject.getString("good_order_number");
        good_order_state = jsonObject.getString("good_order_state");

    }

    public Ec_goods() {
    }

    private Ec_goods(Parcel in) {
        id = in.readInt();
        good_name = in.readString();
        good_time = in.readString();
        good_pay = in.readDouble();
        good_level = in.readString();
        good_order_time = in.readString();
        good_order_number = in.readString();
        good_order_state = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(good_name);
        dest.writeString(good_time);
        dest.writeDouble(good_pay);
        dest.writeString(good_level);
        dest.writeString(good_order_time);
        dest.writeString(good_order_number);
        dest.writeString(good_order_state);
    }

    public static final Creator<Ec_goods> CREATOR = new Creator<Ec_goods>() {
        @Override
        public Ec_goods createFromParcel(Parcel source) {
            return new Ec_goods(source);
        }

        @Override
        public Ec_goods[] newArray(int size) {
            return new Ec_goods[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGood_name() {
        return good_name;
    }

    public void setGood_name(String good_name) {
        this.good_name = good_name;
    }

    public String getGood_time() {
        return good_time;
    }

    public void setGood_time(String good_time) {
        this.good_time = good_time;
    }

    public double getGood_pay() {
        return good_pay;
    }

    public void setGood_pay(double good_pay) {
        this.good_pay = good_pay;
    }


    public String getGood_level() {
        return good_level;
    }

    public void setGood_level(String good_level) {
        this.good_level = good_level;
    }

    public String getGood_order_time() {
        return good_order_time;
    }

    public void setGood_order_time(String good_order_time) {
        this.good_order_time = good_order_time;
    }

    public String getGood_order_number() {
        return good_order_number;
    }

    public void setGood_order_number(String good_order_number) {
        this.good_order_number = good_order_number;
    }

    public String getGood_order_state() {
        return good_order_state;
    }

    public void setGood_order_state(String good_order_state) {
        this.good_order_state = good_order_state;
    }
}
