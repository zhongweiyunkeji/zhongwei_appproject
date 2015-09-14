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

    /**开放时间**/
    public String valid_date;
    /**经度**/
    public String longitude;
    /**纬度**/
    public String latitude;

    /**景区地址**/
    public String address;

    /**预定须知**/
    public String po_notice;

    /**最低票价**/
    public double minprice;

    // 是否显示看中卫、听中卫右上角图标
    public boolean showIcon = false;

    public int videocount; // 视频总数
    public int voicecount; // 音频总数


    public void parse(JSONObject jsonObject) throws JSONException {
        Log.i(TAG, "jsonObject=" + jsonObject.toString());
        brand_id = jsonObject.getInt("brand_id");
        brand_name = jsonObject.getString("brand_name");
        brand_logo = jsonObject.getString("brand_logo");
        brand_desc = jsonObject.getString("brand_desc");
        site_url = jsonObject.getString("site_url");
        sort_order = jsonObject.getString("sort_order");
        is_show = jsonObject.getString("is_show");
        valid_date = jsonObject.getString("valid_date");
        longitude = jsonObject.getString("longitude");
        latitude = jsonObject.getString("latitude");
        address = jsonObject.getString("address");
        po_notice = jsonObject.getString("po_notice");
        minprice = jsonObject.getDouble("minprice");
        videocount = jsonObject.getInt("videocount");
        voicecount = jsonObject.getInt("voicecount");
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
        valid_date = in.readString();
        longitude = in.readString();
        latitude = in.readString();
        address = in.readString();
        po_notice = in.readString();
        minprice = in.readDouble();
        videocount = in.readInt();
        voicecount = in.readInt();
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
        dest.writeString(valid_date);
        dest.writeString(longitude);
        dest.writeString(latitude);
        dest.writeString(address);
        dest.writeString(po_notice);
        dest.writeDouble(minprice);
        dest.writeInt(videocount);
        dest.writeInt(voicecount);
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

    public String getValid_date() {
        return valid_date;
    }

    public void setValid_date(String valid_date) {
        this.valid_date = valid_date;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getPo_notice() {
        return po_notice;
    }

    public void setPo_notice(String po_notice) {
        this.po_notice = po_notice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public double getMinprice() {
        return minprice;
    }

    public void setMinprice(double minprice) {
        this.minprice = minprice;
    }

    public int getVideocount() {
        return videocount;
    }

    public int getVoicecount() {
        return voicecount;
    }

    public void setVideocount(int videocount) {
        this.videocount = videocount;
    }

    public void setVoicecount(int voicecount) {
        this.voicecount = voicecount;
    }
}
