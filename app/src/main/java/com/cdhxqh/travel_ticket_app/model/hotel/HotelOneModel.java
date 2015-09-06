package com.cdhxqh.travel_ticket_app.model.hotel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/9/5.
 */
public class HotelOneModel {
    /**
     * 时间
     */
    String ArrivalAndDeparture;
    /**
     * 预定
     */
    String Cancel;
    /**
     * 许可
     */
    String DepositAndPrepaid;
    /**
     * 宠物
     */
    String Pet;
    /**
     * 引用卡
     */
    String Requirements;
    /**
     * 入住时间
     */
    String CheckInTime;
    /**
     * 房间列表
     */
    ArrayList<RoomContent> roomContentList;

    /**
     * 图片
     */
    ArrayList<RoomImgModel> roomImgModelList;

    public ArrayList<RoomImgModel> getRoomImgModelList() {
        return roomImgModelList;
    }

    public void setRoomImgModelList(ArrayList<RoomImgModel> roomImgModelList) {
        this.roomImgModelList = roomImgModelList;
    }

    public String getArrivalAndDeparture() {
        return ArrivalAndDeparture;
    }

    public void setArrivalAndDeparture(String arrivalAndDeparture) {
        ArrivalAndDeparture = arrivalAndDeparture;
    }

    public String getCancel() {
        return Cancel;
    }

    public void setCancel(String cancel) {
        Cancel = cancel;
    }

    public String getDepositAndPrepaid() {
        return DepositAndPrepaid;
    }

    public void setDepositAndPrepaid(String depositAndPrepaid) {
        DepositAndPrepaid = depositAndPrepaid;
    }

    public String getPet() {
        return Pet;
    }

    public void setPet(String pet) {
        Pet = pet;
    }

    public String getRequirements() {
        return Requirements;
    }

    public void setRequirements(String requirements) {
        Requirements = requirements;
    }

    public String getCheckInTime() {
        return CheckInTime;
    }

    public void setCheckInTime(String checkInTime) {
        CheckInTime = checkInTime;
    }

    public ArrayList<RoomContent> getRoomContentList() {
        return roomContentList;
    }

    public void setRoomContentList(ArrayList<RoomContent> roomContentList) {
        this.roomContentList = roomContentList;
    }
}
