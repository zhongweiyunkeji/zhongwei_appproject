package com.cdhxqh.travel_ticket_app.utils;

import android.content.Context;
import android.util.Log;

import com.cdhxqh.travel_ticket_app.config.Constants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by apple on 15/7/26.
 */
public class JsonUtils {
    private static final String TAG="JsonUtils";

    /**
     * 解析登陆信息*
     * data json数据
     * isChecked 是否保存密码
     */
    public static boolean parsingAuthStr(final Context cxt, String data) {
        try {
            JSONObject json = new JSONObject(data);
            String errcode = json.getString("errcode");
            Log.i(TAG,"errcode="+errcode);
            if (errcode.equals(Constants.SUCCESS_LOGIN)) {
                return true;
            } else {
                return false;
            }


        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }


}
