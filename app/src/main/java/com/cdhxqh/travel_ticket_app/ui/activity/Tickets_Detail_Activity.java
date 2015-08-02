package com.cdhxqh.travel_ticket_app.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.api.HttpManager;
import com.cdhxqh.travel_ticket_app.api.HttpRequestHandler;
import com.cdhxqh.travel_ticket_app.model.CategoryModel;
import com.cdhxqh.travel_ticket_app.model.Ecs_brand;
import com.cdhxqh.travel_ticket_app.utils.MessageUtils;

import java.util.ArrayList;

/**
 * 景区门票详情
 */
public class Tickets_Detail_Activity extends BaseActivity {
    private static final String TAG="Tickets_Detail_Activity";

    /**返回按钮**/
    ImageView backImageView;
    /**标题**/
    TextView titleTextView;
    /**
     * 预定
     */
    private Button btn_reservation_id;

    private ProgressDialog progressDialog;

    /**Ecs_brand**/
    Ecs_brand ecs_brand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_detail_);

        getData();
        findViewById();
        initView();

        createProgressDialog();
        requestCategoryList();
    }


    /**获取上个界面的数据**/
    private void getData() {
        Log.i(TAG,"rrrrrr");
        ecs_brand=getIntent().getExtras().getParcelable("ecs_brand");
    }

    @Override
    protected void findViewById() {
        backImageView=(ImageView)findViewById(R.id.ticket_detail_back_id);
        titleTextView=(TextView)findViewById(R.id.ticket_detail_title_id);
        btn_reservation_id = (Button)findViewById(R.id.btn_reservation_id);
    }

    @Override
    protected void initView() {
        if (ecs_brand!=null){
            titleTextView.setText(ecs_brand.brand_name);
        }

        backImageView.setOnClickListener(backImageViewOnClickListener);

        btn_reservation_id.setOnClickListener(reservationOnClickListener);
    }


    private View.OnClickListener backImageViewOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                finish();
        }
    };

    private View.OnClickListener reservationOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("sort_order", ecs_brand.sort_order);
            intent.putExtras(bundle);
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
     * 创建progressDialog*
     */
    private void createProgressDialog() {
        progressDialog = ProgressDialog.show(Tickets_Detail_Activity.this, null, getString(R.string.please_loading_hint), true, true);
    }

    private void requestCategoryList() {
        HttpManager.getCategoryList_list(this, "ticket", handler);
    }

    private HttpRequestHandler<ArrayList<CategoryModel>> handler = new HttpRequestHandler<ArrayList<CategoryModel>>() {
        @Override
        public void onSuccess(ArrayList<CategoryModel> data) {

            Log.i(TAG, "data=" + data);
//            brandListAdapter.update(data, true);
            progressDialog.dismiss();
//            MessageUtils.showErrorMessage(Listen_ZhongWei_Activity.this,"加载成功");

        }

        @Override
        public void onSuccess(ArrayList<CategoryModel> data, int totalPages, int currentPage) {
            progressDialog.dismiss();
            Log.i(TAG,"222222");

        }

        @Override
        public void onFailure(String error) {
            Log.i(TAG,"333333");
            MessageUtils.showErrorMessage(Tickets_Detail_Activity.this, error);
            progressDialog.dismiss();
        }
    };
}
