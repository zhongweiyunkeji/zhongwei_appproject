package com.cdhxqh.travel_ticket_app.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 景区门票表
 */
public class Ecs_brand extends Zw_Model implements Parcelable {
    private static final String TAG = "Ecs_brand";
    private static final long serialVersionUID = 2015050105L;


    /**
     * 自增Id
     */
    public int brand_id;

    /**
     * 景区名称
     */
    public String brand_name;
    /**
     * 景区Logo
     */
    public String brand_logo;
    /**
     * 景区描述
     */
    public String brand_desc;

    /**
     * 景区网址
     */
    public String site_url;

    public String sort_order;

    public String is_show;


    public void parse(JSONObject jsonObject) throws JSONException {
        Log.i(TAG, "jsonObject=" + jsonObject.toString());
        brand_id = jsonObject.getInt("brand_id");
        brand_name = jsonObject.getString("brand_name");
        brand_logo = jsonObject.getString("brand_logo");
        brand_desc = jsonObject.getString("brand_desc");
        site_url = jsonObject.getString("site_url");
        sort_order = jsonObject.getString("sort_order");
        is_show = jsonObject.getString("is_show");

    }

    public Ecs_brand() {
    }

    private Ecs_brand(Parcel in) {
        brand_id = in.readInt();
        brand_name = in.readString();
        brand_logo = in.readString();
        brand_desc = in.readString();
        site_url = in.readString();
        sort_order = in.readString();
        is_show = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(brand_id);
        dest.writeString(brand_name);
        dest.writeString(brand_logo);
        dest.writeString(brand_desc);
        dest.writeString(site_url);
        dest.writeString(sort_order);
        dest.writeString(is_show);
    }

    public static final Creator<Ecs_brand> CREATOR = new Creator<Ecs_brand>() {
        @Override
        public Ecs_brand createFromParcel(Parcel source) {
            return new Ecs_brand(source);
        }

        @Override
        public Ecs_brand[] newArray(int size) {
            return new Ecs_brand[size];
        }
    };

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getBrand_logo() {
        return brand_logo;
    }

    public void setBrand_logo(String brand_logo) {
        this.brand_logo = brand_logo;
    }

    public String getBrand_desc() {
        return brand_desc;
    }

    public void setBrand_desc(String brand_desc) {
        this.brand_desc = brand_desc;
    }

    public String getSite_url() {
        return site_url;
    }

    public void setSite_url(String site_url) {
        this.site_url = site_url;
    }

    public String getSort_order() {
        return sort_order;
    }

    public void setSort_order(String sort_order) {
        this.sort_order = sort_order;
    }

    public String getIs_show() {
        return is_show;
    }

    public void setIs_show(String is_show) {
        this.is_show = is_show;
    }
}
