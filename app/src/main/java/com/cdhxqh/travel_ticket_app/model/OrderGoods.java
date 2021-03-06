package com.cdhxqh.travel_ticket_app.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hexian on 2015/8/4.
 * 电子票
 */
public class OrderGoods implements Parcelable {

    private String goodsId;
    private String orderSn;     // 订单号
    private String goodsName;   // 标题
    private int goodsNumber;   // 电子票总数量
    private double goodsPrice; // 电子票单价
    private String imgurl;      // 图片
    private String status;      // 状态
    private String consignee;  // 出游人
    private String mobile;      // 手机号
    private Double amount;      // 总金额
    private String qecode;      // 服务器二维码url地址

    public OrderGoods(){

    }

    public OrderGoods( String goodsId, String goodsName, int goodsNumber, double goodsPrice, String orderSn, String imgurl, String status, String consignee, String mobile, String qecode) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsNumber = goodsNumber;
        this.goodsPrice = goodsPrice;
        this.orderSn = orderSn;
        this.imgurl = imgurl;
        this.status = status;
        this.consignee = consignee;
        this.mobile = mobile;
        this.qecode = qecode;
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

    public String getStatus() {
        // 0为其他；1为待出游；2为已出游；3为已点评
        if("0".equals(status)){
            return "其他";
        } else
        if("1".equals(status)){
            return "待出游";
        } else
        if("2".equals(status)){
            return "已出游";
        } else
        if("3".equals(status)){
            return "已点评";
        }

        return status;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getConsignee() {
        return consignee;
    }

    public String getMobile() {
        return mobile;
    }

    public Double getAmount() {
        return goodsPrice*goodsNumber;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getQecode() {
        return qecode;
    }

    public void setQecode(String qecode) {
        this.qecode = qecode;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(goodsId);
        dest.writeString(orderSn);
        dest.writeString(goodsName);
        dest.writeString(imgurl);
        dest.writeString(status);
        dest.writeInt(goodsNumber);
        dest.writeDouble(goodsPrice);
        dest.writeString(consignee);
        dest.writeString(mobile);
    }

}
