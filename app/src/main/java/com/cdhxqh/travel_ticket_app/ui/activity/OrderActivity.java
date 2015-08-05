package com.cdhxqh.travel_ticket_app.ui.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.api.HttpManager;
import com.cdhxqh.travel_ticket_app.api.HttpRequestHandler;
import com.cdhxqh.travel_ticket_app.config.Constants;
import com.cdhxqh.travel_ticket_app.model.OrderGoods;
import com.cdhxqh.travel_ticket_app.model.OrderModel;
import com.cdhxqh.travel_ticket_app.ui.adapter.OrderThreeInAdapter;
import com.cdhxqh.travel_ticket_app.ui.fragment.OrderThreeInFragment;
import com.cdhxqh.travel_ticket_app.ui.fragment.OrderThreeOutFragment;
import com.cdhxqh.travel_ticket_app.utils.MessageUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.SimpleFormatter;

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
     * 三个月外*
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

    // 3个月内
    int currntPageIn = 1;
    final static int showCountIn = 1;

    // 3个月钱
    int currntPageOut = 1;
    final static int showCountOut = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        findViewById();
        initView();

    }

    @Override
    protected void findViewById() {
        titleTextView = (TextView) findViewById(R.id.title_text_id);
        inttextView = (TextView) findViewById(R.id.order_in_text);
        outtextView = (TextView) findViewById(R.id.order_out_text);
    }

    @Override
    protected void initView() {
        titleTextView.setText(getString(R.string.order_title_text));
        inttextView.setOnClickListener(inttextViewOnClickListener);
        outtextView.setOnClickListener(outtextViewOnClickListener);
        OnTabSelected("three_in");
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
        if (tabName == "three_in") {

            if (null == orderThreeInFragment) {
                orderThreeInFragment = new OrderThreeInFragment();
            }

            FragmentTransaction localFragmentTransaction = getFragmentManager().beginTransaction();  //
            localFragmentTransaction.replace(R.id.container, orderThreeInFragment, "three_in");
            localFragmentTransaction.commit();

            inttextView.setBackgroundColor(getResources().getColor(R.color.green_color));
            outtextView.setBackgroundColor(getResources().getColor(R.color.white));


        } else if (tabName == "three_out") {

            orderThreeOutFragment = new OrderThreeOutFragment();


            FragmentTransaction localFragmentTransaction = getFragmentManager().beginTransaction();
            localFragmentTransaction.replace(R.id.container, orderThreeOutFragment, "three_out");
            localFragmentTransaction.commit();

            inttextView.setBackgroundColor(getResources().getColor(R.color.white));
            outtextView.setBackgroundColor(getResources().getColor(R.color.green_color));


        }

        if("three_in".equals(tabName)){
            requestOrderList(true, "after");
        } else
        if("three_out".equals(tabName)){
            requestOrderList(true, "before");
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
            exit(OrderActivity.this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 创建progressDialog*
     */
    private void createProgressDialog() {
        progressDialog = ProgressDialog.show(OrderActivity.this, null, getString(R.string.please_loading_hint), true, true);
    }

    private void requestOrderList(boolean refersh, String type) {
        createProgressDialog();
        if("after".equals(type)){  // 3个月内
            HttpManager.getOrder_list(this, Constants.OTDER_LIST_URL, type, showCountIn+"", currntPageIn+"", handlerIn);
        } else
        if("before".equals(type)){// 3个月前
            HttpManager.getOrder_list(this, Constants.OTDER_LIST_URL, type, showCountOut+"", currntPageOut+"", handlerOut);
        }
    }

    private HttpRequestHandler<String> handlerIn = new HttpRequestHandler<String>() {
        @Override
        public void onSuccess(String data) {
            progressDialog.dismiss();
            JSONObject jsonObject = null;
            currntPageIn++;
            try {
                SimpleDateFormat formart = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                jsonObject = new JSONObject(data);
                JSONObject result = ((JSONObject)jsonObject.get("result"));
                String serverurl = result.getString("serverurl");
                int totalPage = result.getInt("totalPage");
                JSONArray orderlist = result.getJSONArray("orderlist");
                int length = orderlist.length();

                List<OrderModel> groupList = new ArrayList<OrderModel>(0);
                Map<String, List<OrderGoods>> itemList = new HashMap<String, List<OrderGoods>>(0);

                for(int index=0; index<length; index++){
                    JSONObject subObject = (JSONObject)orderlist.get(index);
                    String orderSn = subObject.getString("orderSn");           // 订单号
                    String orderStatus = subObject.getString("orderStatus");  // 订单状态
                    Long addTime =       subObject.getLong("addTime");        // 购买时间
                    String createTimt = "";
                    try {
                        createTimt = formart.format(new java.util.Date(addTime*1000));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    OrderModel orderModel = new OrderModel(orderSn, 0,0,0,orderStatus, createTimt);
                    groupList.add(orderModel);

                    JSONArray subArray = subObject.getJSONArray("orderGoods");
                    // Log.e(TAG, " ----------------------------> "+subObject);
                    if(subArray!=null){
                        int size = subArray.length();
                        if(size>0){
                            List<OrderGoods> goodList = new ArrayList<OrderGoods>(0);
                            itemList.put(orderSn, goodList);
                            for(int k=0; k<size; k++){
                                JSONObject obj = (JSONObject)subArray.get(k);
                                String goodsName = obj.getString("goodsName");  // 景点标题
                                int goodsNumber = obj.getInt("goodsNumber");   // 总数量
                                int goodsPrice = obj.getInt("goodsPrice");     // 购买价格
                                String status = obj.getString("status");        // 景点标题
                                String imgurl = obj.getString("goodsAttr");    // 景点图片
                                OrderGoods goods = new OrderGoods(goodsName, goodsNumber, goodsPrice, orderSn, serverurl+imgurl);
                                goodList.add(goods);
                            }
                            goodList.add(new OrderGoods());  // 添加末尾的按钮组(不能省略)
                        }
                    }
                }

                OrderThreeInAdapter adapter = orderThreeInFragment.getAdapter();
                adapter.update(groupList, itemList);
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
            MessageUtils.showErrorMessage(OrderActivity.this, error);
            progressDialog.dismiss();
        }
    };

    private HttpRequestHandler<String> handlerOut = new HttpRequestHandler<String>() {
        @Override
        public void onSuccess(String data) {
            progressDialog.dismiss();
        }

        @Override
        public void onSuccess(String data, int totalPages, int currentPage) {
            progressDialog.dismiss();
        }

        @Override
        public void onFailure(String error) {
            MessageUtils.showErrorMessage(OrderActivity.this, error);
            progressDialog.dismiss();
        }
    };
}
