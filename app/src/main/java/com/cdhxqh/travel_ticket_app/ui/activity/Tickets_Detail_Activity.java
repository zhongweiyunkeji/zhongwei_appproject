package com.cdhxqh.travel_ticket_app.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.model.Ecs_brand;

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
    }

    @Override
    protected void initView() {
        if (ecs_brand!=null){
            titleTextView.setText(ecs_brand.brand_name);
        }

        backImageView.setOnClickListener(backImageViewOnClickListener);
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


}
