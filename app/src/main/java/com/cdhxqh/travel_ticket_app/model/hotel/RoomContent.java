package com.cdhxqh.travel_ticket_app.model.hotel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/9/4.
 */
public class RoomContent {
    /**
     * 房间类型名
     */
    String RoomTypeName;
    /**
     * 房子类型代码
     */
    String RoomTypeCode;
    /**
     * 床型代码
     */
    String BedTypeCode;
    /**
     * 住房层数
     */
    String Floor;
    /**
     * 是否有窗
     */
    String HasWindow;
    /**
     * 基础房型名称
     */
    String Name;
    /**
     * 是否有烟
     */
    String NonSmoking;
    /**
     * 房间数量
     */
    String Quantity;
    /**
     * 房间面积
     */
    String RoomSize;
    /**
     * 床的尺寸
     */
    String Size;
    /**
     * 入住人数
     */
    String StandardOccupancy;
    /**
     * 基础房型
     */
    String InvBlockCode;
    /**
     * 设置描述
     */
    ArrayList<RoomFacility> roomFacilities;

    public String getRoomTypeName() {
        return RoomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        RoomTypeName = roomTypeName;
    }

    public String getRoomTypeCode() {
        return RoomTypeCode;
    }

    public void setRoomTypeCode(String roomTypeCode) {
        RoomTypeCode = roomTypeCode;
    }

    public String getBedTypeCode() {
        return BedTypeCode;
    }

    public void setBedTypeCode(String bedTypeCode) {
        BedTypeCode = bedTypeCode;
    }

    public String getFloor() {
        return Floor;
    }

    public void setFloor(String floor) {
        Floor = floor;
    }

    public String getHasWindow() {
        return HasWindow;
    }

    public void setHasWindow(String hasWindow) {
        HasWindow = hasWindow;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNonSmoking() {
        return NonSmoking;
    }

    public void setNonSmoking(String nonSmoking) {
        NonSmoking = nonSmoking;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getRoomSize() {
        return RoomSize;
    }

    public void setRoomSize(String roomSize) {
        RoomSize = roomSize;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getStandardOccupancy() {
        return StandardOccupancy;
    }

    public void setStandardOccupancy(String standardOccupancy) {
        StandardOccupancy = standardOccupancy;
    }

    public ArrayList<RoomFacility> getRoomFacilities() {
        return roomFacilities;
    }

    public void setRoomFacilities(ArrayList<RoomFacility> roomFacilities) {
        this.roomFacilities = roomFacilities;
    }

    public String getInvBlockCode() {
        return InvBlockCode;
    }

    public void setInvBlockCode(String invBlockCode) {
        InvBlockCode = invBlockCode;
    }
}
