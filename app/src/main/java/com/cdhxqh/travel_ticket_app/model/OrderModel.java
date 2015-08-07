package com.cdhxqh.travel_ticket_app.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/7/31.
 * 订单
 */
public class OrderModel implements Parcelable {

    private String orderSn;       // 订单号
    private int orderStatus;     // 订单状态
    private String addTime;       // 订购时间  (数据库中是以秒为单位)
    private int shippingStatus;
    private int payStatus;       // 支付状态
    private String status;        // 订单状态
    private double goodsAmount; // 订单总额

    public OrderModel(){

    }

    public OrderModel( String orderSn, int orderStatus, int payStatus, int shippingStatus, String status, String addTime, double goodsAmount) {
        this.orderSn = orderSn;
        this.orderStatus = orderStatus;
        this.payStatus = payStatus;
        this.shippingStatus = shippingStatus;
        this.status = status;
        this.addTime = addTime;
        this.goodsAmount = goodsAmount;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getOrderSn() {
        return "订单号:"+orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public int getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(int shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public double getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(double goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public String getStatus() {
        // 0： 订单未确认  1：已付款   2：已取消订单    3：无效订单   4：已退货
        if("0".equals(status)){
            return "订单未确认";
        }
        if("1".equals(status)){
            return "已付款";
        }
        if("2".equals(status)){
            return "已取消";
        }
        if("3".equals(status)){
            return "无效订单";
        }
        if("4".equals(status)){
            return "已退货";
        }

        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Parcelable对象接口方法
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Parcelable对象接口方法
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderSn);
        dest.writeString(addTime);
        dest.writeInt(orderStatus);
        dest.writeString(status);
        dest.writeInt(shippingStatus);
        dest.writeInt(payStatus);
        dest.writeDouble(goodsAmount);
    }
}
