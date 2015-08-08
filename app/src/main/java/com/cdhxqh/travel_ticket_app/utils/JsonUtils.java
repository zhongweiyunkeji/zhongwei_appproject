package com.cdhxqh.travel_ticket_app.utils;

import android.content.Context;
import android.util.Log;

import com.cdhxqh.travel_ticket_app.config.Constants;
import com.cdhxqh.travel_ticket_app.model.Attractions;
import com.cdhxqh.travel_ticket_app.model.CategoryModel;
import com.cdhxqh.travel_ticket_app.model.Ec_user;
import com.cdhxqh.travel_ticket_app.model.Ecs_brand;
import com.cdhxqh.travel_ticket_app.model.GoodsList;
import com.cdhxqh.travel_ticket_app.model.PersistenceHelper;
import com.cdhxqh.travel_ticket_app.model.SpotBookModel;

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
            if (errcode.equals(Constants.SUCCESS_LOGIN)) {
                JSONObject result = json.getJSONObject("result");
                Log.i(TAG, "result=" + result);
                Ec_user ec_user = new Ec_user();
                int userId = result.getInt("userId");
                ec_user.setUserId(userId);
                ec_user.setEmail(result.getString("email"));
                ec_user.setUserName(result.getString("userName"));
                ec_user.setPassword(result.getString("password"));
                ec_user.setMobilePhone(result.getString("mobilePhone"));

//                PersistenceHelper.saveModel(cxt, ec_user, Constants.USER_CACHE+userId);
                AccountUtils.writeLoginMember(cxt, ec_user);

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
     * 创建订单*
     */
    public static CategoryModel reservationStr(final Context cxt, String data) {
        CategoryModel categoryModel = null;
        try {
            JSONObject json = new JSONObject(data);
            categoryModel = new CategoryModel();
            categoryModel.setOrderId(json.getString("orderId"));
            categoryModel.setTotal_fee(json.getString("goodsAmount"));
            categoryModel.setOut_trade_no(json.getString("orderSn"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return categoryModel;
    }

    /**
     * 判断库存是否充足
     */
    public static int parsingStock(String data) {
        try {
            JSONObject json = new JSONObject(data);
            String errcode = json.getString("errcode");
            if (errcode.equals(Constants.STOCK_SUCCESS)) {
                return 1; //库存充足
            } else if (errcode.equals(Constants.STOCK_FAILE)) {
                return 2; //库存不足
            } else {
                return 0;
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
            if (errcode.equals(Constants.SUCCESS_LINE)) {
                return 1; //成功
            } else if (errcode.equals(Constants.SUCCESS_PHONE_CODE)) {
                return 2; //该手机未被注册
            } else {
                return 3;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 手机设置密码*
     */
    public static int parsingLinePass(final Context cxt, String data) {
        try {
            JSONObject json = new JSONObject(data);
            String errcode = json.getString("errcode");
            if (errcode.equals(Constants.SUCCESSE_LINE_PASS)) {
                return 1; //成功
            } else if (errcode.equals(Constants.RUNTIME_LINE_PASS)) {
                return 2; //验证码失效
            } else if (errcode.equals(Constants.PHONE_LINE_PASS)) {
                return 3; //密码重置失败
            } else {
                return 0;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 获取景区信息*
     */
    public static ArrayList<Ecs_brand> parsingBrandsInfo(String result) {

        ArrayList<Ecs_brand> models = new ArrayList<Ecs_brand>();
        try {
            JSONObject json = new JSONObject(result);

            String logourl = json.getString("logourl");


            JSONArray brandlist = json.getJSONArray("brandlist");

            for (int i = 0; i < brandlist.length(); i++) {
                JSONObject jsonObject = (JSONObject) brandlist.get(i);
                Ecs_brand ecs_brand = new Ecs_brand();
                ecs_brand.setBrand_id(jsonObject.getInt("brand_id"));
                ecs_brand.setIs_show(jsonObject.getBoolean("is_show") + "");
                ecs_brand.setBrand_name(jsonObject.getString("brand_name"));
                ecs_brand.setBrand_desc(jsonObject.getString("brand_desc"));
                ecs_brand.setSort_order(jsonObject.getString("sort_order"));
                ecs_brand.setSite_url(jsonObject.getString("site_url"));
                ecs_brand.setBrand_logo(logourl + jsonObject.getString("brand_logo"));
                ecs_brand.setValid_date(jsonObject.getString("valid_date"));
                ecs_brand.setLongitude(jsonObject.getString("longitude"));
                ecs_brand.setLatitude(jsonObject.getString("latitude"));

                ecs_brand.setAddress(jsonObject.getString("address"));
                ecs_brand.setPo_notice(jsonObject.getString("po_notice"));

                ecs_brand.setMinprice(jsonObject.getDouble("minprice"));
                models.add(ecs_brand);
            }


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
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

            for (int i = 0; i < attractionslist.length(); i++) {
                JSONObject jsonObject = (JSONObject) attractionslist.get(i);
                Attractions attractions = new Attractions();
                attractions.setArticle_id(jsonObject.getInt("article_id"));
                attractions.setTitle(jsonObject.getString("title"));
                attractions.setContent(jsonObject.getString("content"));
                attractions.setDescription(jsonObject.getString("description"));
                attractions.setLatitude(jsonObject.getString("latitude"));
                attractions.setLongitude(jsonObject.getString("longitude"));

                String file_url = jsonObject.get("file_url").toString().trim();
                Log.i(TAG, "file_url=" + file_url);
                if (!file_url.equals("")) {
                    attractions.setFile_url(file_url);
                }
                attractions.setImage(jsonObject.getString("image"));

                models.add(attractions);
            }


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        Log.i(TAG, "*****1");
        return models;
    }


    /**
     * 景点门票及介绍
     */
    public static ArrayList<SpotBookModel> parsingSpotBook(String result) {
        ArrayList<SpotBookModel> model = new ArrayList<SpotBookModel>();

        try {
            JSONObject json = new JSONObject(result);

            String logourl = json.getString("logourl");

            JSONArray bookBook = json.getJSONArray("brandlist");

            for (int i = 0; i < bookBook.length(); i++) {
                JSONObject jsonObject = (JSONObject) bookBook.get(i);
                SpotBookModel spotBookModel = new SpotBookModel();
                spotBookModel.setSpotImage(logourl + jsonObject.getString("brand_logo"));
                spotBookModel.setSpotDesc(jsonObject.getString("brand_desc"));
                spotBookModel.setSpotTittle(jsonObject.getString("brand_name"));
                model.add(spotBookModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return model;
    }

    /**
     * 获取门票列表
     */
    public static ArrayList<GoodsList> parsingGoodsList(String result) {
        Log.i(TAG, "result=" + result);
        ArrayList<GoodsList> list = new ArrayList<GoodsList>();
        try {
            JSONObject json = new JSONObject(result);
            JSONArray goodsarray = json.getJSONArray("goodslist");
            for (int i = 0; i < goodsarray.length(); i++) {
                JSONObject jsonObject = (JSONObject) goodsarray.get(i);
                GoodsList goodsList = new GoodsList();
                if (jsonObject.getString("catName").equals("null")) {
                    goodsList.setCatName("其它");
                    Log.i(TAG, "catName=其它");
                } else {
                    goodsList.setCatName(jsonObject.getString("catName"));
                    Log.i(TAG, "catName=null");
                }
                goodsList.setGoods_id(jsonObject.getInt("goodsId") + "");
                goodsList.setGoods_name(jsonObject.getString("goodsName"));
                goodsList.setShop_price(jsonObject.getString("shopPrice"));
                Log.i(TAG, "shopPrice=" + jsonObject.getString("shopPrice"));
                list.add(goodsList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }


}
