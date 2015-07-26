package com.cdhxqh.travel_ticket_app.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.model.Ec_goods;
import com.cdhxqh.travel_ticket_app.ui.adapter.Class_adapter;
import com.cdhxqh.travel_ticket_app.ui.adapter.GoodsListAdapter;
import com.cdhxqh.travel_ticket_app.ui.widget.ItemDivider;

import java.util.ArrayList;

public class ClassActivity extends BaseActivity {

    /**
     * 标题*
     */
    TextView titleTextView;
    /**
     * 搜索*
     */
    ImageView searchImageView;

    /**
     * gridview*
     */
    GridView gridView;

    /**
     * class_adapter*
     */
    Class_adapter class_adapter;


    /**
     * mRecyclerView*
     */
    RecyclerView mRecyclerView;


    SwipeRefreshLayout mSwipeLayout;


    GoodsListAdapter goodsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {

        titleTextView = (TextView) findViewById(R.id.title_text_id);
        searchImageView = (ImageView) findViewById(R.id.title_search_id);
        gridView = (GridView) findViewById(R.id.class_grid_text_id);
//        mSwipeLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_container);
        mRecyclerView = (RecyclerView) findViewById(R.id.list_topics);



    }

    @Override
    protected void initView() {
        titleTextView.setText(getString(R.string.class_title_text));
        class_adapter = new Class_adapter(ClassActivity.this);
        gridView.setAdapter(class_adapter);
        class_adapter.update(addData());




        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        mRecyclerView.addItemDecoration(new ItemDivider(this,
                ItemDivider.VERTICAL_LIST));


        goodsListAdapter = new GoodsListAdapter(this);

        mRecyclerView.setAdapter(goodsListAdapter);
        goodsListAdapter.update(addGoods(), true);

//        mSwipeLayout.setRefreshing(false);

    }


    /**
     * 封装测试数据*
     */
    private ArrayList<String> addData() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("名胜古迹");
        list.add("古镇");
        list.add("博物馆");
        list.add("自然风光");
        list.add("滑雪");
        list.add("漂流");
        list.add("主题乐园");
        list.add("动物园");
        list.add("植物园");
        list.add("海洋馆");
        list.add("城市观光");
        list.add("影视基地");
        list.add("温泉");
        list.add("游船");
        list.add("科技馆");
        list.add("游船");
        list.add("展览");
        list.add("赏花");
        return list;
    }


    /**
     * 添加商品测试数据*
     */
    private ArrayList<Ec_goods> addGoods() {
        ArrayList<Ec_goods> goodsList = new ArrayList<Ec_goods>();

        for (int i = 0; i < 10; i++) {
            Ec_goods ec_goods = new Ec_goods();
            ec_goods.setGood_name("沙坡头" + i);
            ec_goods.setGood_time("8:00 - 17:30");
            ec_goods.setGood_pay("￥ 32");
            goodsList.add(ec_goods);
        }

        return goodsList;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_class, menu);
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
            exit(ClassActivity.this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
