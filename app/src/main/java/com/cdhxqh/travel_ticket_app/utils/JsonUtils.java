package com.cdhxqh.travel_ticket_app.utils;

import android.content.Context;
import android.util.Log;

import com.cdhxqh.travel_ticket_app.config.Constants;
import com.cdhxqh.travel_ticket_app.model.Attractions;
import com.cdhxqh.travel_ticket_app.model.Ecs_brand;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 15/7/26.
 */
public class JsonUtils {
    private static final String TAG = "JsonUtils";

    /**
     * 解析登陆信息*
     * data json数据
     * isChecked 是否保存密码
     */
    public static boolean parsingAuthStr(final Context cxt, String data) {
        try {
            JSONObject json = new JSONObject(data);
            String errcode = json.getString("errcode");
            Log.i(TAG, "errcode=" + errcode);
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

    /**
     * 邮箱验证*
     */
    public static int parsingEmailStr(final Context cxt, String data) {
        try {
            JSONObject json = new JSONObject(data);
            String errcode = json.getString("errcode");
            Log.i(TAG, "errcode=" + errcode);
            if (errcode.equals(Constants.SUCCESS_EMAIL)) {
                return 1; //成功
            } else if (errcode.equals(Constants.FAILURE_EMAIL)) {
                return 2; //邮箱未注册
            } else {
                return 3; //邮箱注册失败
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取验证码*
     */
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

    /**
     * 获取验证码*
     */
    public static int parsingLinePass(final Context cxt, String data) {
        try {
            JSONObject json = new JSONObject(data);
            String errcode = json.getString("errcode");
            Log.i(TAG, "errcode=" + errcode);
            if (errcode.equals(Constants.SUCCESS_LINE_PASS)) {
                return 1; //成功
            } else if (errcode.equals(Constants.RUNTIME_LINE_PASS)) {
                return 2; //验证码失效
            } else if (errcode.equals(Constants.RUNTIME_LINE_PASS)) {
                return 3; //验证码验证失败
            } else {
                return 0;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 获取景区门票信息*
     */
    public static ArrayList<Ecs_brand> parsingBrandsInfo(String result) {

        ArrayList<Ecs_brand> models = new ArrayList<Ecs_brand>();
        try {
            JSONObject json = new JSONObject(result);

            String logourl = json.getString("logourl");


            JSONArray brandlist = json.getJSONArray("brandlist");

            Log.i(TAG, "logourl=" + logourl + ",brandlist=" + brandlist.toString());
            for (int i = 0; i < brandlist.length(); i++) {
                JSONObject jsonObject = (JSONObject) brandlist.get(i);
                Ecs_brand ecs_brand = new Ecs_brand();
                ecs_brand.setBrand_id(jsonObject.getInt("brand_id"));
                ecs_brand.setIs_show(jsonObject.getBoolean("is_show") + "");
                ecs_brand.setBrand_name(jsonObject.getString("brand_name"));
                ecs_brand.setBrand_desc(jsonObject.getString("brand_desc"));
                ecs_brand.setSort_order(jsonObject.getString("sort_order"));
                ecs_brand.setSite_url(jsonObject.getString("site_url"));
                ecs_brand.setBrand_logo(logourl+jsonObject.getString("brand_logo"));
                ecs_brand.setValid_date(jsonObject.getString("valid_date"));

                ecs_brand.setLongitude(jsonObject.getString("longitude"));
                ecs_brand.setLatitude(jsonObject.getString("latitude"));
                models.add(ecs_brand);
            }


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
         Log.i(TAG,"*****1");
        return models;
    }



    /**
     * 获取景点列表
     */
    public static ArrayList<Attractions> parsingAttractions(String result) {

        ArrayList<Attractions> models = new ArrayList<Attractions>();
        try {
            JSONObject json = new JSONObject(result);

            //服务器url
            String serverurl = json.getString("serverurl");


            JSONArray attractionslist = json.getJSONArray("attractionslist");

            Log.i(TAG, "serverurl=" + serverurl + ",attractionslist=" + attractionslist.toString());
            for (int i = 0; i < attractionslist.length(); i++) {
                JSONObject jsonObject = (JSONObject) attractionslist.get(i);
                Attractions attractions = new Attractions();
                attractions.setArticle_id(jsonObject.getInt("article_id"));
                attractions.setTitle(jsonObject.getString("title"));
                attractions.setContent(jsonObject.getString("content"));
                attractions.setDescription(jsonObject.getString("description"));
                attractions.setLatitude(jsonObject.getString("latitude"));
                attractions.setLongitude(jsonObject.getString("longitude"));
                attractions.setFile_url(serverurl+jsonObject.get("file_url"));
                attractions.setImage(jsonObject.getString("image"));

                models.add(attractions);
            }


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        Log.i(TAG,"*****1");
        return models;
    }








}
