package com.cdhxqh.travel_ticket_app.api;

import android.content.Context;
import android.util.Log;

import com.cdhxqh.travel_ticket_app.config.Constants;
import com.cdhxqh.travel_ticket_app.utils.JsonUtils;
import com.cdhxqh.travel_ticket_app.utils.SafeHandler;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于与服务端进行网络交互的类*
 */
public class HttpManager {

    private static final String TAG = "HttpManager";

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
        Map<String, String> mapparams=new HashMap<String,String>();
        mapparams.put("loginName",username);
        mapparams.put("password",password);


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
     * 公用的URl连接
     *
     * @param cxt
     * @param mapparams
     * @param handler *
     */

    private static void requestOnceWithURLString(final Context cxt, String url, Map<String, String> mapparams,
                                                 final HttpRequestHandler<String> handler) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        for (Map.Entry<String, String> entry : mapparams.entrySet()){
            params.put(entry.getKey(),entry.getValue());
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
