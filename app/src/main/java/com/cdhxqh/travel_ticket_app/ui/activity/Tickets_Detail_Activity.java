package com.cdhxqh.travel_ticket_app.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.model.Ecs_brand;
import com.cdhxqh.travel_ticket_app.ui.adapter.Tickets_ExpandableListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 景区门票详情
 */
public class Tickets_Detail_Activity extends BaseActivity {
    private static final String TAG="Tickets_Detail_Activity";

    /**返回按钮**/
    ImageView backImageView;
    /**标题**/
    TextView titleTextView;

    /**Ecs_brand**/
    Ecs_brand ecs_brand;

    /**可扩展的**/
    ExpandableListView expandableListView;

    Tickets_ExpandableListAdapter tickets_expandableListAdapter;


    private List<String> group;
    private List<List<String>> child;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_detail_);

        getData();
        findViewById();
        initView();
    }


    /**获取上个界面的数据**/
    private void getData() {
        ecs_brand=getIntent().getExtras().getParcelable("ecs_brand");
    }

    @Override
    protected void findViewById() {
        backImageView=(ImageView)findViewById(R.id.ticket_detail_back_id);
        titleTextView=(TextView)findViewById(R.id.ticket_detail_title_id);
        expandableListView=(ExpandableListView)findViewById(R.id.expandableListView);
    }

    @Override
    protected void initView() {
        if (ecs_brand!=null){
            titleTextView.setText(ecs_brand.brand_name);
        }

        backImageView.setOnClickListener(backImageViewOnClickListener);
        initData();
        tickets_expandableListAdapter=new Tickets_ExpandableListAdapter(Tickets_Detail_Activity.this,group,child);
        expandableListView.setAdapter(tickets_expandableListAdapter);
    }


    private View.OnClickListener backImageViewOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                finish();
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
        addInfo("成人票",new String[]{"沙坡头1","沙坡头2","沙坡头3","沙坡头4"});
        addInfo("儿童票", new String[]{"儿童票5","儿童票6","儿童票7̨"});
        addInfo("优待票", new String[]{"儿童票8","儿童票","儿童票10"});
    }


    private void addInfo(String g,String[] c) {
        group.add(g);
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < c.length; i++) {
            list.add(c[i]);
        }
        child.add(list);
    }


}
