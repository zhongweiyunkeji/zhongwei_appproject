package com.cdhxqh.travel_ticket_app.ui.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.model.Ec_goods;
import com.cdhxqh.travel_ticket_app.model.OrderGoods;
import com.cdhxqh.travel_ticket_app.model.OrderModel;
import com.cdhxqh.travel_ticket_app.ui.adapter.OrderListAdapter;
import com.cdhxqh.travel_ticket_app.ui.adapter.OrderThreeInAdapter;
import com.cdhxqh.travel_ticket_app.ui.widget.ItemDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 三个月内的订单*
 */
public class OrderThreeInFragment extends BaseFragment {
    private static final String TAG="OrderThreeInFragment";

    ExpandableListView expandableListView;
    OrderThreeInAdapter adapter;
    TimerTask task;
    Timer timer = new Timer();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_three_in, container, false);
        findViewByIds(view);
        return view;
    }

    private void findViewByIds(View view) {
        expandableListView = (ExpandableListView)view.findViewById(R.id.order_three_in_listview);
    }

    /**
     * 入口函数
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();

        /**封装数据**/
        // addGoods();

    }

    private void initView() {
        if(adapter == null){
            adapter = new OrderThreeInAdapter(this.getActivity());
        }
        expandableListView.setAdapter(adapter);
        expandableListView.setGroupIndicator(null);  // 去掉左边展开和关闭的图标
    }

    /**
     * 封装数据*
     */
    private void addGoods() {

        List<OrderModel> moduleList = new ArrayList<OrderModel>(0);
        Map<String, List<OrderGoods>> itemMap = new HashMap<String, List<OrderGoods>>(0);
        for(int i=0; i<10; i++){
            String sn = "订单号"+i+" :PM-LY0123456";
            OrderModel module = new OrderModel(sn, 0, 0, 0, "已支付", "2015-08-05 11:30");
            moduleList.add(module);

            List<OrderGoods> list = new ArrayList<OrderGoods>(0);
            for(int j=0; j<2; j++){
                OrderGoods goods = new OrderGoods( "沙坡头"+i+""+"j", 0, 188.0, sn, "http://e.hiphotos.baidu.com/image/pic/item/5fdf8db1cb134954b5a93c8d554e9258d0094aa0.jpg");
                list.add(goods);
            }
            OrderGoods other = new OrderGoods();
            list.add(other);  // 添加末尾的按钮组(不能省略)
            itemMap.put(sn, list);
        }

        adapter.update(moduleList, itemMap);


        task = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };

        // timer.schedule(task, 10000, 10000);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    Handler handler = new Handler(){
        @Override
        public void dispatchMessage(Message msg) {
            // adapter.clearData();  // 清除数据
            List<OrderModel> moduleList = new ArrayList<OrderModel>(0);
            Map<String, List<OrderGoods>> itemMap = new HashMap<String, List<OrderGoods>>(0);
            for(int i=0; i<10; i++){
                String sn = "订单号"+i+" :PM-LY0123456KKK";
                OrderModel module = new OrderModel(sn, 0, 0, 0, "已支付", "2015-08-10 11:30");
                moduleList.add(module);

                List<OrderGoods> list = new ArrayList<OrderGoods>(0);
                for(int j=0; j<2; j++){
                    OrderGoods goods = new OrderGoods("沙坡头"+i+""+"j", 0, 188.0, sn, "http://e.hiphotos.baidu.com/image/pic/item/5fdf8db1cb134954b5a93c8d554e9258d0094aa0.jpg");
                    list.add(goods);
                }
                OrderGoods other = new OrderGoods();
                list.add(other);  // 添加末尾的按钮组(不能省略)
                itemMap.put(sn, list);
            }

            adapter.update(moduleList, itemMap);
        }
    };

    public OrderThreeInAdapter getAdapter() {
        return adapter;
    }
}
