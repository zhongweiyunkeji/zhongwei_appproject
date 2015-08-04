package com.cdhxqh.travel_ticket_app.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.api.HttpManager;
import com.cdhxqh.travel_ticket_app.api.HttpRequestHandler;
import com.cdhxqh.travel_ticket_app.config.Constants;
import com.cdhxqh.travel_ticket_app.model.Ecs_brand;
import com.cdhxqh.travel_ticket_app.ui.adapter.SearchScenicAdapter;
import com.cdhxqh.travel_ticket_app.ui.widget.ItemDivider;
import com.cdhxqh.travel_ticket_app.ui.widget.XEditText;
import com.cdhxqh.travel_ticket_app.utils.JsonUtils;
import com.cdhxqh.travel_ticket_app.utils.MessageUtils;
import com.cdhxqh.travel_ticket_app.utils.NetWorkHelper;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchSecnicActivity extends BaseActivity  {

    LinearLayout hintLaout;          // 提示框的值

    ProgressDialog progressDialog;  // 进度条

    XEditText searchText;            // 搜索框
    String curText = "";             // 当前搜索框内容的值
    String preText = "";             // 上次搜索框内容的值
    ImageView backupImg;             // 退回按钮图片
    ImageView clearImg;              // 清除搜索内容图片

    SearchScenicAdapter adapter;     // RecyclerView适配器

    int currentPage = 1;            // 当前页
    int showCount = 1;              // 每页显示

    RecyclerView recyclerView;      // ListView替代控件
    SwipeRefreshLayout swipeRefreshLayout;

    Map<String, String> params = new HashMap(0);  // 保存http请求的参数

    HttpRequestHandler requestHandler = new HttpRequestHandler<String> (){ // 分页回调接口
        @Override
        public void onFailure(String error) {
            progressDialog.dismiss();
            MessageUtils.showErrorMessage(SearchSecnicActivity.this, error);
        }

        @Override
        public void onSuccess(String data) {
            progressDialog.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(data);
                String str = jsonObject.getString("errcode");
                int totalPage = ((JSONObject) jsonObject.get("result")).getInt("totalPage");
                if("ZWTICKET-GLOBAL-S-0".equals(str)) {
                    if((currentPage > totalPage) && (totalPage > 0)) {// 已经没有课搜索的数据
                        MessageUtils.showMiddleToast(SearchSecnicActivity.this, "已没有数据可显示");
                    } else {
                        ArrayList<Ecs_brand> array = JsonUtils.parsingBrandsInfo(jsonObject.getString("result"));
                        if(array.size() > 0) { // 可搜索到内容
                            recyclerView.setVisibility(View.VISIBLE);
                            hintLaout.setVisibility(View.GONE);
                            if(currentPage == 1) {
                                adapter.getList().clear();
                            }
                            adapter.update(array);
                            recyclerView.getLayoutManager().scrollToPosition(0);
                            currentPage++;
                        } else { // 搜索内容为空
                            hintLaout.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
            }
        }

        @Override
        public void onSuccess(String data, int totalPages, int currentPage) {
            progressDialog.dismiss();
        }
    };

    @Override
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_secnic_search);

        findViewById();
        initView();

        currentPage = 1;
    }

    private void createProgressDialog() {// 显示进度条
        this.progressDialog = ProgressDialog.show(this, null, getString(R.string.please_loading_hint), true, true);
    }

    protected void findViewById() {// 初始化各控件
        this.recyclerView = ((RecyclerView) findViewById(R.id.scenic_ticket_list));
        this.swipeRefreshLayout = ((SwipeRefreshLayout) findViewById(R.id.scenic_refresh_container));
        this.searchText = (XEditText) findViewById(R.id.title_text_id);
        this.hintLaout = ((LinearLayout) findViewById(R.id.scenic_search_result_empty));
        this.backupImg = ((ImageView) findViewById(R.id.back_imageview_id));
        this.clearImg = ((ImageView) findViewById(R.id.title_search_id));
    }

    protected void initView() {
        LinearLayoutManager localLinearLayoutManager = new LinearLayoutManager(this);
        localLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        localLinearLayoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(localLinearLayoutManager);   // 设置管理器
        recyclerView.setItemAnimator(new DefaultItemAnimator());  // 添加动画
        recyclerView.addItemDecoration(new ItemDivider(this, 1));// 添加分隔线
        hintLaout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        // RecyclerView注册适配器
        if (adapter == null) {
            adapter = new SearchScenicAdapter(this, 1000);
            recyclerView.setAdapter(this.adapter);
        }

        // 注册搜索框左边图片点击事件
        this.searchText.setDrawableLeftListener(new XEditText.DrawableLeftListener() {
            @Override
            public void onDrawableLeftClick(EditText paramEditText) {
                refreshData();
            }
        });

        // SwipeRefreshLayout注册刷新事件
        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        // 注册退回按钮事件
        this.backupImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 注册清除按钮事件
        this.clearImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText.setText("");
            }
        });
    }

    public void loadData(Map<String, String> params) {
        if (NetWorkHelper.isNetAvailable(this)) {
            createProgressDialog();
            HttpManager.requestOnceWithURLString(this, Constants.SCENICE_SEARCH_URL, params, requestHandler);
        } else {
            MessageUtils.showErrorMessage(this, getResources().getString(R.string.error_network_exception));
        }
    }


    public void refreshData() {
        swipeRefreshLayout.setRefreshing(false);     // 完成刷新,隐藏搜索刷新旋转按钮
        String searchText = this.searchText.getText().toString();
        preText = curText;         // 保存上一次搜索的内容
        curText = searchText;       // 保存当前搜索框的内容
        if (!curText.equals(preText)) { // 更新搜索的当前页
            currentPage = 1;
        }
        params.clear();
        if ((searchText != null) && (!"".equals(searchText.trim()))) {// 搜索框内容
            params.put("brandName", searchText);
        }
        this.params.put("currentPage", this.currentPage + "");
        this.params.put("showCount", this.showCount + "");
        loadData(params);
    }

}
