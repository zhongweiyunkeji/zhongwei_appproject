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

/**用于与服务端进行网络交互的类**/
public class HttpManager {

    private static final String TAG="HttpManager";

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
        requestOnceWithURLString(cxt, username, password, new HttpRequestHandler<String>() {
            @Override
            public void onSuccess(String data, int totalPages, int currentPage) {
            Log.i(TAG,"data="+data);
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


    private static void requestOnceWithURLString(final Context cxt, final String username, final String password,
                                                 final HttpRequestHandler<String> handler) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("loginName", username);
        params.put("password", password);
        client.post(Constants.LOGIN_URL, params, new TextHttpResponseHandler() {


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i(TAG, "FstatusCode=" + statusCode);
                SafeHandler.onFailure(handler, ErrorType.errorMessage(cxt, ErrorType.ErrorLoginFailure));
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
