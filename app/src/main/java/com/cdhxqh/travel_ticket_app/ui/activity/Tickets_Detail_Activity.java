package com.cdhxqh.travel_ticket_app.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.api.HttpManager;
import com.cdhxqh.travel_ticket_app.api.HttpRequestHandler;
import com.cdhxqh.travel_ticket_app.config.Constants;
import com.cdhxqh.travel_ticket_app.model.Ecs_brand;
import com.cdhxqh.travel_ticket_app.model.GoodsList;
import com.cdhxqh.travel_ticket_app.ui.adapter.ReviewListAdapter;
import com.cdhxqh.travel_ticket_app.ui.adapter.Tickets_ExpandableListAdapter;
import com.cdhxqh.travel_ticket_app.ui.widget.CustomExpandableListView;
import com.cdhxqh.travel_ticket_app.ui.widget.FullyLinearLayoutManager;
import com.cdhxqh.travel_ticket_app.ui.widget.ItemDivider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 景区门票详情
 */
public class Tickets_Detail_Activity extends BaseActivity {
    private static final String TAG = "Tickets_Detail_Activity";

    /**
     * 返回按钮*
     */
    ImageView backImageView;
    /**
     * 标题*
     */
    TextView titleTextView;

    /**
     * 景区地址*
     */
    RelativeLayout addrelativeLayout;

    /**地址信息**/
    TextView addTextView;


    /**
     * Ecs_brand*
     */
    private  Ecs_brand ecs_brand=null;

    /**
     * 可扩展的*
     */
    CustomExpandableListView expandableListView;

    /**
     * 点评
     */
    RecyclerView recyclerView;

    ReviewListAdapter reviewadapter;




    /**
     * 景区介绍*
     */
    private RelativeLayout spot_introduction_id;

    TextView tickets_detail_introduce_text_id;

    private RelativeLayout tickets_detail_address;

    private RelativeLayout scenic_spot;

    private RelativeLayout hotel_spot;

    private RelativeLayout video;

    Tickets_ExpandableListAdapter tickets_expandableListAdapter;

    private ProgressDialog progressDialog;
    private List<String> group;
    private ArrayList<GoodsList> arrayList;
    private ArrayList<List<GoodsList>> child;

    private static boolean login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_detail);
        if (ecs_brand == null) {
            getData();
        }
        findViewById();
        initView();
        login = mIsLogin;
    }

    /**
     * 获取上个界面的数据*
     */
    private void getData() {
        ecs_brand = getIntent().getExtras().getParcelable("ecs_brand");
        getgoodslist();
    }

    /**
     * 获取门票数据
     */
    private void getgoodslist() {

        String url = Constants.GOODSLIST_URL + "?" + "brandId=" + ecs_brand.brand_id;
        Log.i(TAG, "url=" + url);
        HttpManager.getbrandlist(this, url, handler);
        progressDialog = ProgressDialog.show(Tickets_Detail_Activity.this, null,
                "请稍候...", true, true);
    }

        private HttpRequestHandler<ArrayList<GoodsList>> handler = new HttpRequestHandler<ArrayList<GoodsList>>() {
            @Override
            public void onSuccess(ArrayList<GoodsList> data) {
                Log.i(TAG, "data=" + data.size());
                arrayList = data;
                progressDialog.dismiss();
                initexpandableView();
            }

        @Override
        public void onSuccess(ArrayList<GoodsList> data, int totalPages, int currentPage) {
            progressDialog.dismiss();
        }

        @Override
        public void onFailure(String error) {
            progressDialog.dismiss();
        }
    };

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.ticket_detail_back_id);
        titleTextView = (TextView) findViewById(R.id.ticket_detail_title_id);
        hotel_spot = (RelativeLayout) findViewById(R.id.hotel_spot);

        addrelativeLayout = (RelativeLayout) findViewById(R.id.address_relativelayout_id);
        addTextView=(TextView)findViewById(R.id.tickets_detail_address_text_id);

        expandableListView = (CustomExpandableListView) findViewById(R.id.expandableListView);
        spot_introduction_id = (RelativeLayout) findViewById(R.id.spot_introduction_id);
        tickets_detail_introduce_text_id = (TextView) findViewById(R.id.tickets_detail_introduce_text_id);
        recyclerView = (RecyclerView) findViewById(R.id.review_list);

        final FullyLinearLayoutManager manager = new FullyLinearLayoutManager(Tickets_Detail_Activity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        manager.setSmoothScrollbarEnabled(true);
        manager.scrollToPosition(0);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new ItemDivider(this, ItemDivider.VERTICAL_LIST));
        reviewadapter = new ReviewListAdapter(this);
        recyclerView.setAdapter(reviewadapter);


        tickets_detail_address = (RelativeLayout) findViewById(R.id.tickets_detail_address);
        scenic_spot = (RelativeLayout) findViewById(R.id.scenic_spot);
        video = (RelativeLayout) findViewById(R.id.video);

        addData();
    }


    @Override
    protected void initView() {
        if (ecs_brand != null) {
            titleTextView.setText(ecs_brand.brand_name);
            addTextView.setText(ecs_brand.address);
        }
        scenic_spot.setOnClickListener(scenic_spotOnClickListener);

        backImageView.setOnClickListener(backImageViewOnClickListener);

        backImageView.setOnTouchListener(backImageViewOnTouchListener);


        addrelativeLayout.setOnClickListener(addrelativeLayoutOnClickListener);


        spot_introduction_id.setOnClickListener(ticketsIntroduceOnClickListener);
        tickets_detail_introduce_text_id.setOnClickListener(ticketsIntroduceOnClickListener);

        tickets_detail_address.setOnClickListener(ticketsdetailOnClickListener);
        hotel_spot.setOnClickListener(hotelOnClickListener);

        video.setOnClickListener(videoOnClickListener);
    }

    /**
     * 景区实况*
     */
    private View.OnClickListener scenic_spotOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = getIntent();
            intent.putExtra("spotTitle", ecs_brand.brand_name  + "出行分析");
            intent.setClass(Tickets_Detail_Activity.this, IntelligentAnalyticsActivity.class);
            startActivityForResult(intent, 0);
        }
    };

    /**
     * 景区视频
     */
    private View.OnClickListener videoOnClickListener  = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = getIntent();
            intent.putExtra("brandid", String.valueOf(ecs_brand.brand_id));
            intent.setClass(Tickets_Detail_Activity.this, VideoListActivity.class);
            startActivityForResult(intent, 0);
        }
    };

    /**
     * 景区实况*
     */
    private View.OnClickListener hotelOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = getIntent();
            intent.putExtra("spotTitle", "周边酒店");
            intent.setClass(Tickets_Detail_Activity.this, AroundPlayActivity.class);
            startActivity(intent);
        }
    };




    private View.OnTouchListener backImageViewOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.setBackgroundColor(getResources().getColor(R.color.list_item_read));
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                v.setBackgroundColor(getResources().getColor(R.color.clarity));
            }
            return false;
        }
    };

    /**
     * 初始化expandableView*
     */
    private void initexpandableView() {
        /**标题**/
        group = new ArrayList<String>();
        /**内容**/
        child = new ArrayList<List<GoodsList>>();
        for (int i = 0; i < arrayList.size(); i++) {
            group.add(arrayList.get(i).getCatName());
        }
        HashSet hashSet = new HashSet(group);
        group.clear();
        group.addAll(hashSet);
        if (group.size() != 0) {
            for (int j = 0; j < group.size(); j++) {
                ArrayList<GoodsList> l = new ArrayList<GoodsList>();
                for (int m = 0; m < arrayList.size(); m++) {

                    String catName = arrayList.get(m).getCatName();
                    if (catName.equals(group.get(j))) {
                        l.add(arrayList.get(m));
                    }
                }
                child.add(j, l);
            }
        }


        tickets_expandableListAdapter = new Tickets_ExpandableListAdapter(Tickets_Detail_Activity.this, group, child);
        expandableListView.setAdapter(tickets_expandableListAdapter);
        expandableListView.expandGroup(0);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (parent.isGroupExpanded(groupPosition)) {
                    parent.collapseGroup(groupPosition);
                } else {
                    //第二个参数false表示展开时是否触发默认滚动动画
                    parent.expandGroup(groupPosition, false);
                }
                //telling the listView we have handled the group click, and don't want the default actions.
                return true;
            }
        });


        tickets_detail_introduce_text_id.setOnClickListener(ticketsIntroduceOnClickListener);

        setListViewHeight(expandableListView);
    }

    /**
     * 重新计算ListView的高度，解决ScrollView和ListView两个View都有滚动的效果，在嵌套使用时起冲突的问题
     * @param listView
     */
    public void setListViewHeight(RecyclerView listView) {

        // 获取ListView对应的Adapter

        ReviewListAdapter listAdapter = (ReviewListAdapter) listView.getAdapter();

        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        ArrayList<View> v = listAdapter.getView();
        for (int i = 0; i < v.size(); i++) { // listAdapter.getCount()返回数据项的数目

            v.get(i).measure(0, 0); // 计算子项View 的宽高
            totalHeight += v.get(i).getMeasuredHeight(); // 统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getHeight() * (listAdapter.getItemCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * 景区地址*
     */
    private View.OnClickListener addrelativeLayoutOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = getIntent();

            intent.setClass(Tickets_Detail_Activity.this, Brand_Address_Activity.class);
            startActivityForResult(intent, 0);
        }
    };


    private void setListViewHeight(ExpandableListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        int count = listAdapter.getCount();
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private View.OnClickListener ticketsIntroduceOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("spotId", ecs_brand.getBrand_name());
            bundle.putString("spotLogo", ecs_brand.getBrand_logo());
            bundle.putString("spotDesc", ecs_brand.getBrand_desc());
            intent.putExtras(bundle);
            intent.setClass(Tickets_Detail_Activity.this, IntroductionActivity.class);
            startActivity(intent);
        }
    };


    private View.OnClickListener ticketsdetailOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("content", ecs_brand.po_notice);
            intent.putExtras(bundle);
            intent.setClass(Tickets_Detail_Activity.this, Book_Informtion_Activity.class);
            startActivity(intent);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tickets__detail_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * 添加点评数据*
     */
    private void addData() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            list.add("点评");
        }
        reviewadapter.update(list, true);
    }

}
