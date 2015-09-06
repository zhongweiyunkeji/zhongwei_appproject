package com.cdhxqh.travel_ticket_app.ui.widget.hotelWineShop.base;

/**
 * Created by Administrator on 2015/9/3.
 */
public class SplitString {
    static String regex1 = "<Property";

    /**
     * 获取酒店数据
     * @param str
     * @return
     */
    public static String[] getHotels(String str) {
        String[] strs = str.split(regex1);
        String[] hotel = new String[strs.length-1];
        for(int i=0; i<strs.length-1; i++) {
            hotel[i] = strs[i+1];
        }
        return hotel;
    }

}
