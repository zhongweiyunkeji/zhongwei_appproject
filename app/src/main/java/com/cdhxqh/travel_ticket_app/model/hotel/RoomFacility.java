package com.cdhxqh.travel_ticket_app.model.hotel;

/**
 * Created by Administrator on 2015/9/5.
 */
public class RoomFacility {
    /**
     * 设置名
     */
    String FacilityName;
    /**
     * 设施类型
     */
    String FTypeName;
    /**
     * 设施值
     */
    String FacilityValue;

    public String getFacilityName() {
        return FacilityName;
    }

    public void setFacilityName(String facilityName) {
        FacilityName = facilityName;
    }

    public String getFTypeName() {
        return FTypeName;
    }

    public void setFTypeName(String FTypeName) {
        this.FTypeName = FTypeName;
    }

    public String getFacilityValue() {
        return FacilityValue;
    }

    public void setFacilityValue(String facilityValue) {
        FacilityValue = facilityValue;
    }
}
