package com.cdhxqh.travel_ticket_app.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 * Created by Administrator on 2015/7/25.
 */
public class HttpUtil {
    private static final String TAG="";

    /**
     *
     * @param url         访问的url
     * @param map         参数
     * @param handler    handler类，可为空
     */
    public static void post(String url, Map<String, String> map, Handler handler){
        final Handler h = handler;
        AsyncHttpClient client = new AsyncHttpClient(); //
        client.setTimeout(10000);
        // 创建异步请求的客户端对象
//        String url = "http://172.16.237.200:8080/video/login.do"; // 定义请求的地址
        // 创建请求参数的封装的对象
        RequestParams params = new RequestParams();
        Iterator it=map.keySet().iterator();
        while(it.hasNext()){
            String style =it.next().toString();
            String str=map.get(style);
            params.put(style, str); // 设置请求的参数名和参数值
        }


        // 执行post方法
        client.post(url, params, new AsyncHttpResponseHandler() {
            /**
             * 成功处理的方法
             * statusCode:响应的状态码; headers:相应的头信息 比如 响应的时间，响应的服务器 ;
             * responseBody:响应内容的字节
             */
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String str = "";
                    if(responseBody!=null){
                        str = new String(responseBody, Charset.forName("UTF-8"));
                    }
                    Log.i(TAG, "str=" + str);
                    if(h!=null){
                        h.removeMessages(0);   //清空消息队列里的内容
                        Message m = h.obtainMessage(100, str);  // 从消息池获取消息
                        h.sendMessage(m);    //发送消息
                    }
                }
            }

            /**
             * 失败处理的方法
             * error：响应失败的错误信息封装到这个异常对象中
             */
            @Override
            public void onFailure(int statusCode, Header[] headers,  byte[] responseBody, Throwable error) {
                error.printStackTrace();// 把错误信息打印出轨迹来
                String str = "";
                if(responseBody!=null){
                    str = new String(responseBody, Charset.forName("UTF-8"));
                    Log.i(TAG, "str=" + str);
                }
                if(h!=null){
                    h.removeMessages(0);   //清空消息队列里的内容
                    Message m = h.obtainMessage(100, str);  // 从消息池获取消息
                    h.sendMessage(m);    //发送消息
                }
            }

        });
    }
}
