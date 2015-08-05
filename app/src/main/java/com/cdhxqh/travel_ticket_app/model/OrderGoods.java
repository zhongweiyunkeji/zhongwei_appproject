package com.cdhxqh.travel_ticket_app.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hexian on 2015/8/4.
 * 电子票
 */
public class OrderGoods implements Parcelable {

    private String orderSn;     // 订单号
    private String goodsName;   // 标题
    private int goodsNumber;   // 电子票总数量
    private double goodsPrice; // 电子票单价
    private String imgurl;      // 图片

    public OrderGoods(){

    }

    public OrderGoods( String goodsName, int goodsNumber, double goodsPrice, String orderSn, String imgurl) {
        this.goodsName = goodsName;
        this.goodsNumber = goodsNumber;
        this.goodsPrice = goodsPrice;
        this.orderSn = orderSn;
        this.imgurl = imgurl;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(int goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
