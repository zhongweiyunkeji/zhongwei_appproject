package com.cdhxqh.travel_ticket_app.api;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.cdhxqh.travel_ticket_app.config.Constants;
import com.cdhxqh.travel_ticket_app.model.Attractions;
import com.cdhxqh.travel_ticket_app.model.CategoryModel;
import com.cdhxqh.travel_ticket_app.model.Ecs_brand;
import com.cdhxqh.travel_ticket_app.model.OrderModel;
import com.cdhxqh.travel_ticket_app.model.PersistenceHelper;
import com.cdhxqh.travel_ticket_app.model.SpotBookModel;
import com.cdhxqh.travel_ticket_app.ui.activity.Listen_ZhongWei_Activity;
import com.cdhxqh.travel_ticket_app.utils.JsonUtils;
import com.cdhxqh.travel_ticket_app.utils.MessageUtils;
import com.cdhxqh.travel_ticket_app.utils.SafeHandler;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 用于与服务端进行网络交互的类*
 */
public class HttpManager {

    private static final String TAG = "HttpManager";


    private static AsyncHttpClient sClient = null;

    private static String SESSIONID = "";


    /**
     * @param cxt         上下文
     * @param brand_name  景区名称
     * @param showCount   显示条数
     * @param currentPage 当前页数
     * @param refresh     是否刷新
     * @param handler     handler
     */
    public static void getEcs_Brands_list(Context cxt, String brand_name, String showCount, String currentPage, boolean refresh,
                                          HttpRequestHandler<ArrayList<Ecs_brand>> handler) {
        String urlString;

        if (brand_name.equals("")) {
            urlString = Constants.TICKETS_URL + "?" + "showCount=" + showCount + "&" + "currentPage=" + currentPage;
        } else {
            urlString = Constants.TICKETS_URL + "?" + "brand_name=" + brand_name + "showCount=" + showCount + "&" + "currentPage=" + currentPage;
        }
        getEcs_Brands(cxt, urlString, refresh, handler, currentPage);
    }


    /**
     * @param cxt         上下文
     * @param title       景点标题
     * @param showCount   当前条数
     * @param currentPage 当前页数
     * @param refresh     是否刷新
     * @param handler     handler
     */
    public static void getAttractions_list(Context cxt, String title, String brandId, String showCount, String currentPage, boolean refresh,
                                           HttpRequestHandler<ArrayList<Attractions>> handler) {
        String urlString;

        if (title.equals("")) {
            urlString = Constants.ATTRACTIONS_URL + "?" + "brandId=" + brandId + "&" + "showCount=" + showCount + "&" + "currentPage=" + currentPage;
        } else {
            urlString = Constants.ATTRACTIONS_URL + "?" + "title=" + title + "brandId=" + brandId + "&" + "showCount=" + showCount + "&" + "currentPage=" + currentPage;
        }
        getAttractions(cxt, urlString, refresh, handler);
    }


    public static void getCategoryList_list(final Context cxt, final String type, final HttpRequestHandler<ArrayList<CategoryModel>> handler) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("type", type);
        client.get(Constants.CATEGORY_URL, params, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, ErrorType.errorMessage(cxt, ErrorType.ErrorGetNotificationFailure));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    JSONObject jsonObject = new JSONObject(responseString);
                    String errcode = jsonObject.getString("errcode");
                    if (errcode.equals(Constants.REQUEST_SUCCESS)) {
                        String errmsg = jsonObject.getString("errmsg");

                        String result = jsonObject.getString("result");

                        ArrayList<CategoryModel> spotBookModel = null;
                        if (spotBookModel != null && spotBookModel.size() != 0) {
                            SafeHandler.onSuccess(handler, spotBookModel);
                        } else {
                            SafeHandler.onFailure(handler, ErrorType.errorMessage(cxt, ErrorType.ErrorGetNotificationFailure));
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    SafeHandler.onFailure(handler, ErrorType.errorMessage(cxt, ErrorType.ErrorGetNotificationFailure));
                }
            }
        });
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
    public static void getMailPassword(final Context cxt, final String mailCode,
                                       final HttpRequestHandler<Integer> handler) {
        Map<String, String> mapparams = new HashMap<String, String>();
        mapparams.put("email", mailCode);

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
                } else if (code == 2) {
                    SafeHandler.onFailure(handler, "该邮箱未注册");
                } else if (code == 3) {
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
     * @param handler     返回结果处理
     */
    public static void getPhoneCode(final Context cxt, final String phoneNumber,
                                    final HttpRequestHandler<Integer> handler) {
        Map<String, String> mapparams = new HashMap<String, String>();
        mapparams.put("mobilePhone", phoneNumber);

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
                } else if (code == 2) {
                    SafeHandler.onFailure(handler, "该手机未被注册");
                } else if (code == 3) {
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
     * @param handler     返回结果处理
     */
    public static void getPhonePass(final Context cxt, final String phoneNumber,
                                    final HttpRequestHandler<Integer> handler) {
        Map<String, String> mapparams = new HashMap<String, String>();
        mapparams.put("authstring", phoneNumber);

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
                } else if (code == 2) {
                    SafeHandler.onFailure(handler, "验证码失效");
                } else if (code == 3) {
                    SafeHandler.onFailure(handler, "密码重置失败");
                }

            }


            @Override
            public void onFailure(String error) {
                SafeHandler.onFailure(handler, error);
            }
        });
    }

    /**
     * 订单
     * @param context
     * @param url
     * @param datenote
     * @param showCount
     * @param currentPage
     * @param handler
     */

    public static void getOrder_list(final Context context, String url, String datenote, String showCount, String currentPage,
                                     final HttpRequestHandler<ArrayList<OrderModel>> handler) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("datenote", datenote);
        params.put("showCount", showCount);
        params.put("currentPage", currentPage);
        client.get(url, params, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, ErrorType.errorMessage(context, ErrorType.ErrorGetNotificationFailure));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    JSONObject jsonObject = new JSONObject(responseString);
                    String errcode = jsonObject.getString("errcode");
                    if (errcode.equals(Constants.REQUEST_SUCCESS)) {
                        String errmsg = jsonObject.getString("errmsg");

                        String result = jsonObject.getString("result");

                        ArrayList<SpotBookModel> spotBookModel = JsonUtils.parsingSpotBook(result);
                        if (spotBookModel != null && spotBookModel.size() != 0) {
//                            SafeHandler.onSuccess(handler, spotBookModel);
                        } else {
                            SafeHandler.onFailure(handler, ErrorType.errorMessage(context, ErrorType.ErrorGetNotificationFailure));
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    SafeHandler.onFailure(handler, ErrorType.errorMessage(context, ErrorType.ErrorGetNotificationFailure));
                }
            }
        });
    }


    /**
     *
     *景区门票测试
     *
     * @param brandName
     * @param showCount
     * @param currentPage
     */

    public static void getSpotBooking(final Context context, String url, String brandName, String showCount, String currentPage,
                                            final HttpRequestHandler<ArrayList<SpotBookModel>> handler) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("brandName", brandName);
        params.put("showCount", showCount);
        params.put("currentPage", currentPage);
        client.get(url, params, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, ErrorType.errorMessage(context, ErrorType.ErrorGetNotificationFailure));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    JSONObject jsonObject = new JSONObject(responseString);
                    String errcode = jsonObject.getString("errcode");
                    if (errcode.equals(Constants.REQUEST_SUCCESS)) {
                        String errmsg = jsonObject.getString("errmsg");

                        String result = jsonObject.getString("result");

                        ArrayList<SpotBookModel> spotBookModel = JsonUtils.parsingSpotBook(result);
                        if (spotBookModel != null && spotBookModel.size() != 0) {
                            SafeHandler.onSuccess(handler, spotBookModel);
                        } else {
                            SafeHandler.onFailure(handler, ErrorType.errorMessage(context, ErrorType.ErrorGetNotificationFailure));
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    SafeHandler.onFailure(handler, ErrorType.errorMessage(context, ErrorType.ErrorGetNotificationFailure));
                }
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

    public static void getEcs_Brands(final Context ctx, String url, boolean refresh,  final HttpRequestHandler<ArrayList<Ecs_brand>> handler, final String currentPage) {
        final String key = Uri.parse(url).getEncodedQuery();
        if (!refresh) {
            //尝试从缓存中加载
            ArrayList<Ecs_brand> ecs_brands = PersistenceHelper.loadModelList(ctx, key);
            if (ecs_brands != null && ecs_brands.size() > 0) {
                SafeHandler.onSuccess(handler, ecs_brands);
                return;
            }
        }

        final AsyncHttpClient client = getClient(ctx, false);
        client.addHeader("Referer", Constants.TICKETS_URL);
        client.addHeader("Content-Type", "application/x-www-form-urlencoded");
        client.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                Log.i(TAG, "fstatusCode=" + statusCode);
                SafeHandler.onFailure(handler, ErrorType.errorMessage(ctx, ErrorType.ErrorGetNotificationFailure));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseBody) {
                Log.i(TAG, "SstatusCode=" + statusCode + ",responseBody=" + responseBody);
                /**解析实体类**/
                try {
                    JSONObject jsonObject = new JSONObject(responseBody);
                    String errcode = jsonObject.getString("errcode");
                    int totalPage = ((JSONObject)jsonObject.get("result")).getInt("totalPage");
                    if (errcode.equals(Constants.REQUEST_SUCCESS)) {
                        String errmsg = jsonObject.getString("errmsg");

                        String result = jsonObject.getString("result");

                        ArrayList<Ecs_brand> ecs_brands = JsonUtils.parsingBrandsInfo(result);
                        if (ecs_brands != null && ecs_brands.size() != 0) {
                            SafeHandler.onSuccess(handler, ecs_brands);
                            if(((totalPage+1)+"").equals(currentPage) && !"0".equals(""+totalPage)){
                                MessageUtils.showMiddleToast(ctx, "已没有数据可显示");
                            }
                        } else {
                            SafeHandler.onFailure(handler, ErrorType.errorMessage(ctx, ErrorType.ErrorGetNotificationFailure));
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    SafeHandler.onFailure(handler, ErrorType.errorMessage(ctx, ErrorType.ErrorGetNotificationFailure));
                }


            }
        });
    }

    /**
     * 景点列表
     *
     * @param ctx;     上下文
     * @param url;     url
     * @param handler;
     */

    public static void getAttractions(final Context ctx, String url, boolean refresh,
                                      final HttpRequestHandler<ArrayList<Attractions>> handler) {
        Log.i(TAG, "aurl=" + url);
        final String key = Uri.parse(url).getEncodedQuery();
        if (!refresh) {
            //尝试从缓存中加载
            ArrayList<Attractions> attractionses = PersistenceHelper.loadModelList(ctx, key);
            if (attractionses != null && attractionses.size() > 0) {
                SafeHandler.onSuccess(handler, attractionses);
                return;
            }
        }

        final AsyncHttpClient client = getClient(ctx, false);
        client.addHeader("Referer", Constants.TICKETS_URL);
        client.addHeader("Content-Type", "application/x-www-form-urlencoded");
        client.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                Log.i(TAG, "fstatusCode=" + statusCode);
                SafeHandler.onFailure(handler, ErrorType.errorMessage(ctx, ErrorType.ErrorGetNotificationFailure));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseBody) {
                Log.i(TAG, "SstatusCode=" + statusCode + ",responseBody=" + responseBody);

                /**解析实体类**/
                try {
                    JSONObject jsonObject = new JSONObject(responseBody);
                    String errcode = jsonObject.getString("errcode");
                    if (errcode.equals(Constants.REQUEST_SUCCESS)) {
                        String errmsg = jsonObject.getString("errmsg");

                        String result = jsonObject.getString("result");

                        ArrayList<Attractions> attractionses = JsonUtils.parsingAttractions(result);
                        if(attractionses!=null&&attractionses.size()!=0){
                            SafeHandler.onSuccess(handler,attractionses);
                        }else {
                            SafeHandler.onFailure(handler, ErrorType.errorMessage(ctx, ErrorType.ErrorGetNotificationFailure));
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    SafeHandler.onFailure(handler, ErrorType.errorMessage(ctx, ErrorType.ErrorGetNotificationFailure));
                }


            }
        });
    }


    private static AsyncHttpClient getClient(Context context, boolean mobile) {
        if (sClient == null) {
            sClient = new AsyncHttpClient();
            sClient.setEnableRedirects(false);
            sClient.setCookieStore(new PersistentCookieStore(context));
            sClient.addHeader("Cache-Control", "max-age=0");
            sClient.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            sClient.addHeader("Accept-Charset", "utf-8, iso-8859-1, utf-16, *;q=0.7");
            sClient.addHeader("Accept-Language", "zh-CN, en-US");
            sClient.addHeader("X-Requested-With", "com.android.browser");
        }

        if (mobile)
            sClient.setUserAgent("Mozilla/5.0 (Linux; U; Android 4.2.1; en-us; M040 Build/JOP40D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
        else
            sClient.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko");

        return sClient;
    }

    private static AsyncHttpClient getClient(Context context) {
        return getClient(context, true);
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
            params.put(entry .getKey(), entry.getValue());
        }


        client.post(url, params, new TextHttpResponseHandler() {


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i(TAG, "FstatusCode=" + statusCode);
                SafeHandler.onFailure(handler, ErrorType.errorMessage(cxt, ErrorType.ErrorApiForbidden));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                for(Header h : headers){
                    String name = h.getName();
                    if("Set-Cookie".equals(name)){
                        String cookie = h.getValue();
                        SESSIONID = cookie.split(";")[0].split("=")[1];
                        Log.e(TAG, " COOKIE----------------------------> "+cookie);
                        break;
                    }
                }
                Log.i(TAG, "SstatusCode=" + statusCode);
                if (statusCode == 200) {
                    SafeHandler.onSuccess(handler, responseString);
                }
            }
        });
    }









}
