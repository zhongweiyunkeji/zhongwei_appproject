package com.cdhxqh.travel_ticket_app.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/7/31.
 */
public class CategoryModel  implements Parcelable {
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 订单号
     */
    private String out_trade_no;
    /**
     * 商品名称
     */
    private String subject;
    /**
     * 商品详情
     */
    private String body;
    /**
     * 总价钱
     */
    private String total_fee;

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderId);
        dest.writeString(out_trade_no);
        dest.writeString(subject);
        dest.writeString(body);
        dest.writeString(total_fee);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public static final Parcelable.Creator<CategoryModel> CREATOR = new Creator(){

        @Override
        public CategoryModel createFromParcel(Parcel source) {
            // 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
            CategoryModel p = new CategoryModel();
            p.setOrderId(source.readString());
            p.setOut_trade_no(source.readString());
            p.setSubject(source.readString());
            p.setBody(source.readString());
            p.setTotal_fee(source.readString());

            return p;
        }



        @Override
        public CategoryModel[] newArray(int size) {
            return new CategoryModel[size];
        }
    };


}
