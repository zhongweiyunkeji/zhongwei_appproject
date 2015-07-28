package com.cdhxqh.travel_ticket_app.api;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.cdhxqh.travel_ticket_app.config.Constants;
import com.cdhxqh.travel_ticket_app.model.Ecs_brand;
import com.cdhxqh.travel_ticket_app.model.PersistenceHelper;
import com.cdhxqh.travel_ticket_app.ui.activity.Listen_ZhongWei_Activity;
import com.cdhxqh.travel_ticket_app.utils.JsonUtils;
import com.cdhxqh.travel_ticket_app.utils.SafeHandler;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 用于与服务端进行网络交互的类*
 */
public class HttpManager {

    private static final String TAG = "HttpManager";


    //景区门票
    public static void getEcs_Brands_list(Context cxt, String brand_name, String showCount, String currentPage, boolean refresh,
                                    HttpRequestHandler<ArrayList<Ecs_brand>> handler) {
        String urlString;

        if (brand_name.equals("")) {
            urlString = Constants.TICKETS_URL + "?" + "showCount=" + showCount + "&" + "currentPage=" + currentPage;
        } else {
            urlString = Constants.TICKETS_URL + "?" + "brand_name=" + brand_name + "showCount=" + showCount + "&" + "currentPage=" + currentPage;
        }
        getEcs_Brands(cxt, urlString, refresh, handler);
    }


    /**
     * 使用用户名密码登录
     *
     * @param cxt
     * @param username 用户名
     * @param password 密码
     * @param handler  返回结果处理
     */
    public static void loginWithUsername(final Context cxt, final String username, final String password,
                                         final HttpRequestHandler<Integer> handler) {
        Map<String, String> mapparams = new HashMap<String, String>();
        mapparams.put("loginName", username);
        mapparams.put("password", password);


        requestOnceWithURLString(cxt, Constants.LOGIN_URL, mapparams, new HttpRequestHandler<String>() {
            @Override
            public void onSuccess(String data, int totalPages, int currentPage) {
                Log.i(TAG, "data=" + data);
            }

            @Override
            public void onSuccess(String data) {
                Log.i(TAG, "data=" + data);
                //解析返回的Json数据
                boolean code = JsonUtils.parsingAuthStr(cxt, data);
//
                if (code == true) {
                    SafeHandler.onSuccess(handler, 200);
                } else {
                    SafeHandler.onFailure(handler, "登陆失败");
                }

            }


            @Override
            public void onFailure(String error) {
                SafeHandler.onFailure(handler, error);
            }
        });
    }

    /**
     * 使用邮箱号获取密码
     *
     * @param cxt
     * @param mailCode 邮箱号
     * @param handler  返回结果处理
     */
    public static void getMailPassword(final Context cxt,final String mailCode,
                                         final HttpRequestHandler<Integer> handler) {
        Map<String, String> mapparams=new HashMap<String,String>();
        mapparams.put("email",mailCode);

        requestOnceWithURLString(cxt, Constants.PHONEPASS_URL, mapparams, new HttpRequestHandler<String>() {
            @Override
            public void onSuccess(String data, int totalPages, int currentPage) {
                Log.i(TAG, "data=" + data);
            }

            @Override
            public void onSuccess(String data) {
                Log.i(TAG, "data=" + data);
                //解析返回的Json数据
                int code = JsonUtils.parsingEmailStr(cxt, data);
//
                if (code == 1) {
                    SafeHandler.onSuccess(handler, 200);
                } else if(code ==2) {
                    SafeHandler.onFailure(handler, "该邮箱未注册");
                } else if(code == 3) {
                    SafeHandler.onFailure(handler, "邮箱发送失败");
                }

            }


            @Override
            public void onFailure(String error) {
                SafeHandler.onFailure(handler, error);
            }
        });
    }

    /**
     * 使用手机号获取验证码
     *
     * @param cxt
     * @param phoneNumber 手机号
     * @param handler  返回结果处理
     */
    public static void getPhoneCode(final Context cxt,final String phoneNumber,
                                       final HttpRequestHandler<Integer> handler) {
        Map<String, String> mapparams=new HashMap<String,String>();
        mapparams.put("mobilePhone",phoneNumber);

        requestOnceWithURLString(cxt, Constants.PHONEPASS_URL, mapparams, new HttpRequestHandler<String>() {
            @Override
            public void onSuccess(String data, int totalPages, int currentPage) {
                Log.i(TAG, "data=" + data);
            }

            @Override
            public void onSuccess(String data) {
                Log.i(TAG, "data=" + data);
                //解析返回的Json数据
                int code = JsonUtils.parsingPhoneCode(cxt, data);
//
                if (code == 1) {
                    SafeHandler.onSuccess(handler, 200);
                } else if(code ==2) {
                    SafeHandler.onFailure(handler, "验证码失效");
                } else if(code == 3) {
                    SafeHandler.onFailure(handler, "验证失败");
                }

            }


            @Override
            public void onFailure(String error) {
                SafeHandler.onFailure(handler, error);
            }
        });
    }
    /**
     * 使用验证码获取新密码
     *
     * @param cxt
     * @param phoneNumber 验证码
     * @param handler  返回结果处理
     */
    public static void getPhonePass(final Context cxt,final String phoneNumber,
                                    final HttpRequestHandler<Integer> handler) {
        Map<String, String> mapparams=new HashMap<String,String>();
        mapparams.put("authstring",phoneNumber);

        requestOnceWithURLString(cxt, Constants.PHONELINE_URL, mapparams, new HttpRequestHandler<String>() {
            @Override
            public void onSuccess(String data, int totalPages, int currentPage) {
                Log.i(TAG, "data=" + data);
            }

            @Override
            public void onSuccess(String data) {
                Log.i(TAG, "data=" + data);
                //解析返回的Json数据
                int code = JsonUtils.parsingLinePass(cxt, data);
//
                if (code == 1) {
                    SafeHandler.onSuccess(handler, 200);
                }

            }


            @Override
            public void onFailure(String error) {
                SafeHandler.onFailure(handler, error);
            }
        });
    }


    /**
     * 景区列表
     *
     * @param ctx;
     * @param url;
     * @param handler; *
     */

    public static void getEcs_Brands(Context ctx, String url, boolean refresh,
                                     final HttpRequestHandler<ArrayList<Ecs_brand>> handler) {


        Uri uri = Uri.parse(Constants.TICKETS_URL);
        String path = uri.getLastPathSegment();
        String param = uri.getEncodedQuery();
        String key = path;
        if (param != null)
            key += param;

        if (!refresh) {
            //尝试从缓存中加载
            ArrayList<Ecs_brand> topics = PersistenceHelper.loadModelList(ctx, key);
            if (topics != null && topics.size() > 0) {
                SafeHandler.onSuccess(handler, topics);
                return;
            }
        }

        new AsyncHttpClient().get(ctx, Constants.TICKETS_URL,
                new WrappedJsonHttpResponseHandler<Ecs_brand>(ctx, Ecs_brand.class, key, handler));
    }


    /**
     * 公用的URl连接
     *
     * @param cxt
     * @param mapparams
     * @param handler   *
     */

    public static void requestOnceWithURLString(final Context cxt, final String url, final Map<String, String> mapparams,
                                                 final HttpRequestHandler<String> handler) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        for (Map.Entry<String, String> entry : mapparams.entrySet()) {
            params.put(entry.getKey(), entry.getValue());
        }

        client.post(url, params, new TextHttpResponseHandler() {


            @Override
               public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i(TAG, "FstatusCode=" + statusCode);
                SafeHandler.onFailure(handler, ErrorType.errorMessage(cxt, ErrorType.ErrorApiForbidden));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i(TAG, "SstatusCode=" + statusCode);
                if (statusCode == 200) {
                    SafeHandler.onSuccess(handler, responseString);
                }
            }
        });
    }


}
