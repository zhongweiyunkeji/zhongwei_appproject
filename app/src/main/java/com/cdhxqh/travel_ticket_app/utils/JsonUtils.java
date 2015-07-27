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

    /**邮箱验证**/
    public static int parsingEmailStr(final Context cxt, String data) {
        try {
            JSONObject json = new JSONObject(data);
            String errcode = json.getString("errcode");
            Log.i(TAG,"errcode="+errcode);
            if (errcode.equals(Constants.SUCCESS_EMAIL)) {
                return 1; //成功
            } else if(errcode.equals(Constants.FAILURE_EMAIL)){
                return 2; //邮箱未注册
            } else {
                return 3; //邮箱注册失败
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**获取验证码**/
    public static int parsingPhoneCode(final Context cxt, String data) {
        try {
            JSONObject json = new JSONObject(data);
            String errcode = json.getString("errcode");
            Log.i(TAG, "errcode=" + errcode);
            if (errcode.equals(Constants.SUCCESS_LINE)) {
                return 1; //成功
            } else {
                return 0; //成功
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**获取验证码**/
    public static int parsingLinePass(final Context cxt, String data) {
        try {
            JSONObject json = new JSONObject(data);
            String errcode = json.getString("errcode");
            Log.i(TAG, "errcode=" + errcode);
            if (errcode.equals(Constants.SUCCESS_LINE_PASS)) {
                return 1; //成功
            }else if(errcode.equals(Constants.RUNTIME_LINE_PASS)){
                return 2; //验证码失效
            } else if(errcode.equals(Constants.RUNTIME_LINE_PASS)){
                return 3; //验证码验证失败
            } else {
                return 0;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
