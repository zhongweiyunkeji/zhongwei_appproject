package com.cdhxqh.travel_ticket_app.ui.activity;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.api.HttpManager;
import com.cdhxqh.travel_ticket_app.api.HttpRequestHandler;
import com.cdhxqh.travel_ticket_app.model.Ecs_brand;
import com.cdhxqh.travel_ticket_app.ui.adapter.SearchScenicAdapter;
import com.cdhxqh.travel_ticket_app.ui.widget.ItemDivider;
import com.cdhxqh.travel_ticket_app.utils.MessageUtils;
import com.cdhxqh.travel_ticket_app.utils.NetWorkHelper;

import java.util.ArrayList;

/**
 * 景区门票列表*
 */
public class Scenic_Tickets_Activity extends BaseActivity {

    private static final String TAG="Scenic_Tickets_Activity";

    /**景区详情的标识**/
    private static final int Goods_TAG=1000;

    /**
     * 返回按钮*
     */
    ImageView backImage;

    /**
     * 标题*
     */
    TextView titleText;

    /**
     * 搜索*
     */
    ImageView searchImageView;


    /****/
    RecyclerView mRecyclerView;


    SearchScenicAdapter brandListAdapter;

    /**显示条数**/
    private static final int showCount=10;
    /**当前页数**/
    private static int currentPage=1;

    private ProgressDialog progressDialog;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenic__tickets_);
        findViewById();
        initView();

        currentPage = 1;

        requestEcsBrands(true);
    }

    @Override
    protected void findViewById() {
        backImage = (ImageView) findViewById(R.id.back_imageview_id);
        titleText = (TextView) findViewById(R.id.title_text_id);
        searchImageView = (ImageView)findViewById(R.id.title_search_id);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_container);

        mRecyclerView = (RecyclerView) findViewById(R.id.list_tickets);
    }

    @Override
    protected void initView() {
        titleText.setText(getResources().getString(R.string.scenic_spot_text));
        backImage.setOnClickListener(backImageOnClickListener);
        backImage.setVisibility(View.VISIBLE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        mRecyclerView.addItemDecoration(new ItemDivider(this, ItemDivider.VERTICAL_LIST));

        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(SearchSecnicActivity.class);
            }
        });

        brandListAdapter = new SearchScenicAdapter(this,Goods_TAG);

        mRecyclerView.setAdapter(brandListAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestEcsBrands(true);
            }
        });
    }



    /**
     * 返回事件*
     */
    private View.OnClickListener backImageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scenic__tickets_, menu);
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
        progressDialog = ProgressDialog.show(Scenic_Tickets_Activity.this, null, getString(R.string.please_loading_hint), true, true);
    }

    private void requestEcsBrands(boolean refresh) {
        if (NetWorkHelper.isNetAvailable(this)) {
            swipeRefreshLayout.setRefreshing(false);
            createProgressDialog();
            HttpManager.getEcs_Brands_list(Scenic_Tickets_Activity.this, "", showCount + "", currentPage + "", true, handler);
        } else {
            MessageUtils.showErrorMessage(this, getResources().getString(R.string.error_network_exception));
        }
    }



    private HttpRequestHandler<ArrayList<Ecs_brand>> handler = new HttpRequestHandler<ArrayList<Ecs_brand>>() {

        @Override
        public void onSuccess(ArrayList<Ecs_brand> data) {
            Log.i(TAG, "data=" + data);
            currentPage++;
            brandListAdapter.update(data);
            progressDialog.dismiss();
        }

        @Override
        public void onSuccess(ArrayList<Ecs_brand> data, int totalPages, int currentPage) {
            Scenic_Tickets_Activity.this.currentPage++;
            progressDialog.dismiss();
            Log.i(TAG,"222222");

        }

        @Override
        public void onFailure(String error) {
            Log.i(TAG,"333333");
            MessageUtils.showErrorMessage(Scenic_Tickets_Activity.this, error);
            progressDialog.dismiss();
        }
    };
}
