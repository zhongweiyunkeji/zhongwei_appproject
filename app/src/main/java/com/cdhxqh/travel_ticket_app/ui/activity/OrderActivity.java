package com.cdhxqh.travel_ticket_app.ui.activity;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.api.HttpManager;
import com.cdhxqh.travel_ticket_app.api.HttpRequestHandler;
import com.cdhxqh.travel_ticket_app.config.Constants;
import com.cdhxqh.travel_ticket_app.model.OrderGoods;
import com.cdhxqh.travel_ticket_app.model.OrderModel;
import com.cdhxqh.travel_ticket_app.ui.adapter.OrderThreeAdapter;
import com.cdhxqh.travel_ticket_app.ui.fragment.OrderThreeInFragment;
import com.cdhxqh.travel_ticket_app.ui.fragment.OrderThreeOutFragment;
import com.cdhxqh.travel_ticket_app.utils.MessageUtils;

import android.support.v4.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单列表*
 */

public class OrderActivity extends BaseActivity {
    /**
     * 标题*
     */
    TextView titleTextView;

    // 获取TabHost对象
    TabHost tabHost;

    /**
     * 三个月内*
     */
    OrderThreeInFragment orderThreeInFragment;
    /**
     * 三个月前*
     */
    OrderThreeOutFragment orderThreeOutFragment;
    /**
     * FragmentTransaction*
     */
    FragmentTransaction fragmentTransaction;

    /**
     * 三个月内*
     */
    TextView inttextView;
    /**
     * 三个月前*
     */
    TextView outtextView;

    private ProgressDialog progressDialog;

    LinearLayout order_title;

    /**
     * 返回按钮*
     */
    private ImageView backImageView;

    // 3个月内
    int currntPageIn = 1;
    final static int showCountIn = 10;
    SwipeRefreshLayout swipeRefreshLayoutIn;

    // 3个月前
    int currntPageOut = 1;
    final static int showCountOut = 10;

    LinearLayout laout;  // 提示信息

    ImageView searchIcon;

    boolean flag = false;

    SimpleDateFormat formart = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        getDate();
        findViewById();
        initView();

    }

    public void getDate() {
        if (getIntent() != null) {
            Intent intent =getIntent();
            flag = intent.getBooleanExtra("unPayMent", false);
            if (flag) {
                unPayMent();
            }
        }
    }

    @Override
    protected void findViewById() {
        titleTextView = (TextView) findViewById(R.id.title_text_id);
        inttextView = (TextView) findViewById(R.id.order_in_text);
        outtextView = (TextView) findViewById(R.id.order_out_text);
        laout = (LinearLayout) findViewById(R.id.activity_order_hint_layout);
        searchIcon = (ImageView) findViewById(R.id.title_search_id);
        order_title = (LinearLayout) findViewById(R.id.order_title);
        backImageView = (ImageView) findViewById(R.id.back_imageview_id);
    }

    @Override
    protected void initView() {
        titleTextView.setText(getString(R.string.order_title_text));
        searchIcon.setVisibility(View.GONE);
        inttextView.setOnClickListener(inttextViewOnClickListener);
        outtextView.setOnClickListener(outtextViewOnClickListener);
        OnTabSelected("three_in");
        //返回至登录界面事件
        backImageView.setOnClickListener(backImageViewOnClickListener);
    }

    /**
     * 三个月内*
     */
    private View.OnClickListener inttextViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            OnTabSelected("three_in");
        }
    };


    /**
     * 三个月前*
     */
    private View.OnClickListener outtextViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            OnTabSelected("three_out");

        }
    };


    void OnTabSelected(String tabName) {
        laout.setVisibility(View.GONE);
        if (tabName == "three_in") {
            if (null == orderThreeInFragment) {
                orderThreeInFragment = new OrderThreeInFragment();
            }
            if (!flag) {
                backImageView.setVisibility(View.GONE);
                inttextView.setTextColor(getResources().getColor(R.color.white));  // 设置字体为白色
                outtextView.setTextColor(getResources().getColor(R.color.green_color));        // 设置字体为绿色
                FragmentTransaction localFragmentTransaction = getFragmentManager().beginTransaction();  //
                localFragmentTransaction.replace(R.id.container, orderThreeInFragment, "three_in");
                localFragmentTransaction.commit();

                //inttextView.setBackgroundColor(getResources().getColor(R.color.green_color));
                //outtextView.setBackgroundColor(getResources().getColor(R.color.white));
                inttextView.setBackgroundResource(R.drawable.tabs_left_sel);
                outtextView.setBackgroundResource(R.drawable.tabs_right_nol);

                if (1 == currntPageIn) {
                    requestOrderList(true, "after");
                }
            }else {
                backImageView.setVisibility(View.VISIBLE);
                inttextView.setTextColor(getResources().getColor(R.color.white));  // 设置字体为白色
                outtextView.setTextColor(getResources().getColor(R.color.green_color));        // 设置字体为绿色
                FragmentTransaction localFragmentTransaction = getFragmentManager().beginTransaction();  //
                localFragmentTransaction.replace(R.id.container, orderThreeInFragment, "three_in");
                localFragmentTransaction.commit();

                //inttextView.setBackgroundColor(getResources().getColor(R.color.green_color));
                //outtextView.setBackgroundColor(getResources().getColor(R.color.white));
                inttextView.setBackgroundResource(R.drawable.tabs_left_sel);
                outtextView.setBackgroundResource(R.drawable.tabs_right_nol);
                order_title.setVisibility(View.GONE);
            }
            if (orderThreeInFragment.getAdapter() != null && orderThreeInFragment.getAdapter().getGroupList().size() == 0) {
                laout.setVisibility(View.VISIBLE);
            }
        } else if (tabName == "three_out") {
            if (null == orderThreeOutFragment) {
                orderThreeOutFragment = new OrderThreeOutFragment();
            }

            outtextView.setTextColor(getResources().getColor(R.color.white));  // 设置字体为白色
            inttextView.setTextColor(getResources().getColor(R.color.green_color));        // 设置字体为绿色
            FragmentTransaction localFragmentTransaction = getFragmentManager().beginTransaction();
            localFragmentTransaction.replace(R.id.container, orderThreeOutFragment, "three_out");
            localFragmentTransaction.commit();

            //inttextView.setBackgroundColor(getResources().getColor(R.color.white));
            //outtextView.setBackgroundColor(getResources().getColor(R.color.green_color));
            inttextView.setBackgroundResource(R.drawable.tabs_left_nol);
            outtextView.setBackgroundResource(R.drawable.tabs_right_sel
            );

            if (1 == currntPageOut) {
                requestOrderList(true, "before");
            }
            if (orderThreeOutFragment.getAdapter() != null && orderThreeOutFragment.getAdapter().getGroupList().size() == 0) {
                laout.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            //do something...
            if(flag = false) {
                exit(OrderActivity.this);
            }else {
                finish();
            }
            flag = false;
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 返回事件的监听*
     */
    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    /**
     * 创建progressDialog*
     */
    private void createProgressDialog() {
        progressDialog = ProgressDialog.show(OrderActivity.this, null, getString(R.string.please_loading_hint), true, true);
    }

    public void requestOrderList(boolean refersh, String type) {
        if (orderThreeInFragment != null && orderThreeInFragment.getSwipeRefreshLayoutIn() != null) {
            orderThreeInFragment.getSwipeRefreshLayoutIn().setRefreshing(false);
        }
        if (orderThreeOutFragment != null && orderThreeOutFragment.getSwipeRefreshLayoutOut() != null) {
            orderThreeOutFragment.getSwipeRefreshLayoutOut().setRefreshing(false);
        }
        createProgressDialog();
        SharedPreferences myShared = getSharedPreferences(Constants.USER_INFO, Context.MODE_PRIVATE);
        if ("after".equals(type)) {  // 3个月内
            HttpManager.getOrder_list(myShared.getString(Constants.SESSIONIDTRUE, ""), this, Constants.OTDER_LIST_URL, type, showCountIn + "", currntPageIn + "", handlerIn);
        } else if ("before".equals(type)) {// 3个月前
            HttpManager.getOrder_list(myShared.getString(Constants.SESSIONIDTRUE, ""), this, Constants.OTDER_LIST_URL, type, showCountOut + "", currntPageOut + "", handlerOut);
        }
    }

    public void unPayMent() {
        createProgressDialog();
        SharedPreferences myShared = getSharedPreferences(Constants.USER_INFO, Context.MODE_PRIVATE);
        HttpManager.unPayMent(myShared.getString(Constants.SESSIONIDTRUE, ""), this, Constants.OTDER_LIST_URL, "0", showCountOut + "", currntPageOut + "", handlerIn);
    }

    private HttpRequestHandler<String> handlerIn = new HttpRequestHandler<String>() {
        @Override
        public void onSuccess(String data) {
            progressDialog.dismiss();
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(data);
                JSONObject result = ((JSONObject) jsonObject.get("result"));
                String serverurl = result.getString("serverurl");
                int totalPage = result.getInt("totalPage");
                JSONArray orderlist = result.getJSONArray("orderlist");
                String qrcodeurl = result.getString("qrcodeurl") + "/"; // 二维码地址
                int length = orderlist.length();

                List<OrderModel> groupList = new ArrayList<OrderModel>(0);
                Map<String, List<OrderGoods>> itemList = new HashMap<String, List<OrderGoods>>(0);

                for (int index = 0; index < length; index++) {
                    JSONObject subObject = (JSONObject) orderlist.get(index);
                    String orderSn = subObject.getString("orderSn");           // 订单号
                    String orderStatus = subObject.getString("orderStatus");  // 订单状态
                    Long addTime = subObject.getLong("addTime");        // 购买时间
                    double goodsAmount = subObject.getDouble("goodsAmount");// 总额
                    String consignee = subObject.getString("consignee"); // 出游人
                    String mobile = subObject.getString("mobile");       // 手机号
                    String createTimt = "";
                    try {
                        createTimt = formart.format(new java.util.Date(addTime * 1000));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    OrderModel orderModel = new OrderModel(orderSn, 0, 0, 0, orderStatus, createTimt, goodsAmount);
                    groupList.add(orderModel);

                    JSONArray subArray = subObject.getJSONArray("orderGoods");
                    // Log.e(TAG, " ----------------------------> "+subObject);
                    if (subArray != null) {
                        int size = subArray.length();
                        if (size > 0) {
                            List<OrderGoods> goodList = new ArrayList<OrderGoods>(0);
                            itemList.put(orderModel.getOrderSn(), goodList);   // 注意此处不能使用orderSn!!!!!!
                            for (int k = 0; k < size; k++) {
                                JSONObject obj = (JSONObject) subArray.get(k);
                                int goodsId = obj.getInt("goodsId");    // id
                                String goodsName = obj.getString("goodsName");  // 景点标题
                                int goodsNumber = obj.getInt("goodsNumber");   // 总数量
                                double goodsPrice = obj.getDouble("goodsPrice");     // 购买价格
                                String status = obj.getString("status");        // 景点状态
                                String imgurl = obj.getString("goodsAttr");    // 景点图片
                                String cardSn = ((obj.getString("card_sn") == null) ? "" : obj.getString("card_sn")) + ".png";      // 二维码名称
                                OrderGoods goods = new OrderGoods(goodsId + "", goodsName, goodsNumber, goodsPrice, orderSn, serverurl + imgurl, status, consignee, mobile, qrcodeurl + cardSn);
                                goodList.add(goods);
                            }
                            OrderGoods other = new OrderGoods();
                            //other.setOrderSn("$$$$$$$$$$$$$$$$$$$$$$$$程序内部判断标示$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                            goodList.add(other);  // 添加末尾的按钮组(不能省略)
                        }
                    }
                }

                OrderThreeAdapter adapter = orderThreeInFragment.getAdapter();
                adapter.update(groupList, itemList);
                if (adapter.getGroupList().size() == 0) {
                    laout.setVisibility(View.VISIBLE);
                } else {
                    laout.setVisibility(View.GONE);
                    currntPageIn++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSuccess(String data, int totalPages, int currentPage) {
            progressDialog.dismiss();
        }

        @Override
        public void onFailure(String error) {
            OrderThreeAdapter adapter = orderThreeInFragment.getAdapter();
            if (adapter.getGroupList().size() == 0) {
                laout.setVisibility(View.VISIBLE);
            }
            MessageUtils.showErrorMessage(OrderActivity.this, error);
            progressDialog.dismiss();
        }
    };

    private HttpRequestHandler<String> handlerOut = new HttpRequestHandler<String>() {
        @Override
        public void onSuccess(String data) {
            progressDialog.dismiss();
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(data);
                JSONObject result = ((JSONObject) jsonObject.get("result"));
                String serverurl = result.getString("serverurl");
                int totalPage = result.getInt("totalPage");
                JSONArray orderlist = result.getJSONArray("orderlist");
                String qrcodeurl = result.getString("qrcodeurl") + "/"; // 二维码地址
                int length = orderlist.length();

                List<OrderModel> groupList = new ArrayList<OrderModel>(0);
                Map<String, List<OrderGoods>> itemList = new HashMap<String, List<OrderGoods>>(0);

                for (int index = 0; index < length; index++) {
                    JSONObject subObject = (JSONObject) orderlist.get(index);
                    String orderSn = subObject.getString("orderSn");           // 订单号
                    String orderStatus = subObject.getString("orderStatus");  // 订单状态
                    Long addTime = subObject.getLong("addTime");        // 购买时间
                    double goodsAmount = subObject.getDouble("goodsAmount");// 总额
                    String createTimt = "";
                    try {
                        createTimt = formart.format(new java.util.Date(addTime * 1000));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    OrderModel orderModel = new OrderModel(orderSn, 0, 0, 0, orderStatus, createTimt, goodsAmount);
                    groupList.add(orderModel);

                    JSONArray subArray = subObject.getJSONArray("orderGoods");
                    // Log.e(TAG, " ----------------------------> "+subObject);
                    if (subArray != null) {
                        int size = subArray.length();
                        if (size > 0) {
                            List<OrderGoods> goodList = new ArrayList<OrderGoods>(0);
                            itemList.put(orderModel.getOrderSn(), goodList);   // 注意此处不能使用orderSn!!!!!!
                            for (int k = 0; k < size; k++) {
                                JSONObject obj = (JSONObject) subArray.get(k);
                                int goodsId = obj.getInt("goodsId");    // id
                                String goodsName = obj.getString("goodsName");  // 景点标题
                                int goodsNumber = obj.getInt("goodsNumber");   // 总数量
                                int goodsPrice = obj.getInt("goodsPrice");     // 购买价格
                                String status = obj.getString("status");        // 景点标题
                                String imgurl = obj.getString("goodsAttr");    // 景点图片
                                String consignee = obj.getString("consignee"); // 出游人
                                String mobile = obj.getString("mobile");       // 手机号
                                String cardSn = ((obj.getString("card_sn") == null) ? "" : obj.getString("card_sn")) + ".png";      // 二维码名称
                                OrderGoods goods = new OrderGoods(goodsId + "", goodsName, goodsNumber, goodsPrice, orderSn, serverurl + imgurl, status, consignee, mobile, qrcodeurl + cardSn);
                                goodList.add(goods);
                            }
                            OrderGoods other = new OrderGoods();
                            //other.setOrderSn("$$$$$$$$$$$$$$$$$$$$$$$$程序内部判断标示$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                            goodList.add(other);  // 添加末尾的按钮组(不能省略)
                        }
                    }
                }

                OrderThreeAdapter adapter = orderThreeOutFragment.getAdapter();
                adapter.update(groupList, itemList);
                if (adapter.getGroupList().size() == 0) {
                    laout.setVisibility(View.VISIBLE);
                } else {
                    laout.setVisibility(View.GONE);
                    currntPageOut++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSuccess(String data, int totalPages, int currentPage) {
            progressDialog.dismiss();
        }

        @Override
        public void onFailure(String error) {
            OrderThreeAdapter adapter = orderThreeOutFragment.getAdapter();
            if (adapter.getGroupList().size() == 0) {
                laout.setVisibility(View.VISIBLE);
            }
            MessageUtils.showErrorMessage(OrderActivity.this, error);
            progressDialog.dismiss();
        }
    };


    public LinearLayout getLaout() {
        return laout;
    }


}
