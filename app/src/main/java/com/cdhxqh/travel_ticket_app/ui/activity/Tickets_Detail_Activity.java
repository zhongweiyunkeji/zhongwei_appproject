package com.cdhxqh.travel_ticket_app.ui.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.model.Ecs_brand;
import com.cdhxqh.travel_ticket_app.ui.adapter.Tickets_ExpandableListAdapter;
import com.cdhxqh.travel_ticket_app.ui.widget.CustomExpandableListView;

import java.util.ArrayList;
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
     * Ecs_brand*
     */
    private static Ecs_brand ecs_brand;

    /**
     * 可扩展的*
     */
    CustomExpandableListView expandableListView;

    /**
     * 景区介绍*
     */
    private TextView tickets_detail_introduce_text_id;

    /**
     * 预定须知
     */
    private TextView tickets_detail_address_text_id;

//    /**
//     * 预定
//     */
//    private ImageView reserve_id;

    /**
     *测试
     */
    private TextView tickets_detail_address_text_id_a;

    Tickets_ExpandableListAdapter tickets_expandableListAdapter;


    private List<String> group;
    private List<List<String>> child;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_detail_);

        if(ecs_brand == null) {
            getData();
        }
        findViewById();
        initView();
    }


    /**
     * 获取上个界面的数据*
     */
    private void getData() {
        ecs_brand = getIntent().getExtras().getParcelable("ecs_brand");
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.ticket_detail_back_id);
        titleTextView = (TextView) findViewById(R.id.ticket_detail_title_id);
        expandableListView = (CustomExpandableListView) findViewById(R.id.expandableListView);
        tickets_detail_introduce_text_id = (TextView) findViewById(R.id.tickets_detail_introduce_text_id);
        tickets_detail_address_text_id = (TextView) findViewById(R.id.tickets_detail_address_text_id_1);
//        reserve_id = (ImageView) findViewById(R.id.reserve_id);
        tickets_detail_address_text_id_a = (TextView) findViewById(R.id.tickets_detail_address_text_id_a);
    }

    @Override
    protected void initView() {
        if (ecs_brand != null) {
            titleTextView.setText(ecs_brand.brand_name);
        }

        backImageView.setOnClickListener(backImageViewOnClickListener);
        initData();
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
        tickets_detail_address_text_id.setOnClickListener(ticketsdetailOnClickListener);
//        reserve_id.setOnClickListener(reserveOnClickListener);
        tickets_detail_address_text_id_a.setOnClickListener(reserveOnClickListener);

    }


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
            bundle.putString("content", "特色之二是沙山北面是浩瀚无垠的腾格里沙漠。而沙山南面则是一片郁郁葱葱的沙漠绿洲。游人既可以在这里观赏大沙漠的景色，眺望包兰铁路如一条绿龙伸向远方；又可以骑骆驼在沙漠上走走，照张相片，领略一下沙漠行旅的味道。\\n\" +\n" +
                    "//                \"特色之三是乘古老的渡河工具羊皮筏，在滔滔黄河之中，渡向彼岸。 这种羊皮筏俗称“排子”，是将山羊割去头蹄，然后将囫囵脱下的羊皮， 扎口，用时以嘴吹气，使之鼓起，十几个“浑脱”制成的“排子”，一 个人就能扛起，非常轻便。游人坐在“排子”上，筏工用桨划筏前进， 非常有趣。\\n\" +\n" +
                    "//                \"许多人在评价中国旅游区形象的说到，“看的多，玩的少；让人沉思的，让人心情愉快的少”，在宁夏这片被中国旅游界称之为“中国旅游最后的处女地”的土地上，当神秘的面纱被掀起时，一次“看的过瘾，玩的尽兴”现代时尚的沙漠旅游拉开了序幕。因为这里好看，2005年10月被最具权威的《中国地理杂志社》组织国家十几位院士和近百位专家组成的评审团评为“中国最美的五大沙漠”之一；因为这里好玩，在2004年10月被中国电视艺术家协会旅游电视委员会、全国电视旅游节目协作会、中央电视台评为“中国十大最好玩的地方”之一。");
            intent.putExtras(bundle);
            intent.setClass(Tickets_Detail_Activity.this, Book_Informtion_Activity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener reserveOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("tittle_reservation", "沙坡头成人票（下单立减100元）");
            bundle.putString("end_date_id_a", "2015-12-31 17:00:00");
            bundle.putString("bookingNum", "20");
            bundle.putString("unit_fare", "10");
            intent.putExtras(bundle);
            intent.setClass(Tickets_Detail_Activity.this, ReservationActivity.class);
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


    private void initData() {
        group = new ArrayList<String>();
        child = new ArrayList<List<String>>();
        addInfo("成人票", new String[]{"沙坡头1", "沙坡头2", "沙坡头3", "沙坡头4"});
        addInfo("儿童票", new String[]{"儿童票5", "儿童票6", "儿童票7̨"});
        addInfo("优待票", new String[]{"儿童票8", "儿童票", "儿童票10"});
    }


    private void addInfo(String g, String[] c) {
        group.add(g);
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < c.length; i++) {
            list.add(c[i]);
        }
        child.add(list);
    }

}
