package com.cdhxqh.travel_ticket_app.model;

/**
 * Created by think on 2015/8/6.
 */
public class GoodsList {
    //门票内容
    private String goods_name;//票名
    private String shop_price;//价格
    private String catName;//类型
    private String goods_id;//id
    private String goods_number;//门票数

    public String getGoods_number() {
        return goods_number;
    }

    public void setGoods_number(String goods_number) {
        this.goods_number = goods_number;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public String getShop_price() {
        return shop_price;
    }

    public String getCatName() {
        return catName;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public void setShop_price(String shop_price) {
        this.shop_price = shop_price;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }
}
