package com.cdhxqh.travel_ticket_app.model.hotel;

/**
 * Created by Administrator on 2015/9/5.
 */
public class RoomImgModel {
    /**
     * 地址
     */
    String url;
    /**
     * 类型
     */
    String Caption;

    /**
     * 图片编号
     */
    String InvBlockCode;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCaption() {
        return Caption;
    }

    public void setCaption(String caption) {
        Caption = caption;
    }

    public String getInvBlockCode() {
        return InvBlockCode;
    }

    public void setInvBlockCode(String invBlockCode) {
        InvBlockCode = invBlockCode;
    }
}
