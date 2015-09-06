package com.cdhxqh.travel_ticket_app.model.hotel;

/**
 * Created by Administrator on 2015/9/4.
 */
public class HotelModel {
    /**
     * hotelCode
     */
    private String hotelCode;
    /**
     * 酒店名
     */
    private String hotelName;
    /**
     * 横坐标
     */
    private String Latitude;
    /**
     * 纵坐标
     */
    private String Longitude;
    /**
     * 地址
     */
    private String address;
    /**
     * 酒店等级
     */
    private String hotelStarRate;

    public String getHotelCode() {
        return hotelCode;
    }

    public void setHotelCode(String hotelCode) {
        this.hotelCode = hotelCode;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getAddress() {
        return address;
    }
    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHotelStarRate() {
        return hotelStarRate;
    }

    public void setHotelStarRate(String hotelStarRate) {
        this.hotelStarRate = hotelStarRate;
    }
}
