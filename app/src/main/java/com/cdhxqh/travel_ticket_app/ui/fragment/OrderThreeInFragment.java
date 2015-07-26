package com.cdhxqh.travel_ticket_app.ui.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.model.Ec_goods;
import com.cdhxqh.travel_ticket_app.ui.adapter.OrderListAdapter;
import com.cdhxqh.travel_ticket_app.ui.widget.ItemDivider;

import java.util.ArrayList;

/**
 * 三个月内的订单*
 */
public class OrderThreeInFragment extends BaseFragment {
    private static final String TAG="OrderThreeInFragment";

    /**
     * recyclerView*
     */
    RecyclerView recyclerView;
    /**
     * OrderListAdapter*
     */
    OrderListAdapter orderListAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_three_in, container, false);
        findViewByIds(view);


        return view;
    }

    private void initView() {


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        recyclerView.addItemDecoration(new ItemDivider(getActivity(),
                ItemDivider.VERTICAL_LIST));
    }

    private void findViewByIds(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.list_topics);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG,"*******76");
        initView();

        orderListAdapter = new OrderListAdapter(getActivity());
        recyclerView.setAdapter(orderListAdapter);

        /**封装数据**/
        addGoods();

        orderListAdapter.update(addGoods(), true);

    }

    /**
     * 封装数据*
     */
    private ArrayList<Ec_goods> addGoods() {

        ArrayList<Ec_goods> goodsList = new ArrayList<Ec_goods>();

        for (int i = 0; i < 10; i++) {
            Ec_goods ec_goods = new Ec_goods();
            ec_goods.setGood_name("沙坡头" + i);
            ec_goods.setGood_level("AAAAA");
            ec_goods.setGood_order_time("2015-7-25");
            ec_goods.setGood_order_number("2 张");

            ec_goods.setGood_time("8:00 - 17:30");
            ec_goods.setGood_pay("￥ 132");
            ec_goods.setGood_order_state("待出游");
            goodsList.add(ec_goods);
        }

        return goodsList;


    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
