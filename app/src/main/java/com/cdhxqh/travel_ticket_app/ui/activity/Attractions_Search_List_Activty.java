package com.cdhxqh.travel_ticket_app.ui.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.api.HttpManager;
import com.cdhxqh.travel_ticket_app.api.HttpRequestHandler;
import com.cdhxqh.travel_ticket_app.config.Constants;
import com.cdhxqh.travel_ticket_app.model.Attractions;
import com.cdhxqh.travel_ticket_app.model.Ecs_brand;
import com.cdhxqh.travel_ticket_app.ui.adapter.AttractionsListAdapter;
import com.cdhxqh.travel_ticket_app.ui.widget.ItemDivider;
import com.cdhxqh.travel_ticket_app.ui.widget.XEditText;
import com.cdhxqh.travel_ticket_app.utils.JsonUtils;
import com.cdhxqh.travel_ticket_app.utils.MessageUtils;
import com.cdhxqh.travel_ticket_app.utils.NetWorkHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hexian on 2015/8/4.
 */
public class Attractions_Search_List_Activty extends BaseActivity {

    private static final String TAG = "Attractions_Search_List_Activty";

    ImageView backImg;           // 退回按钮
    ImageView closeImg;          // 清除内容
    XEditText searchText;       // 搜索框
    LinearLayout hintLayout;    // 提示框
    RecyclerView recyclerView;  // 列表控件
    AttractionsListAdapter attractionsListAdapter;  // 适配器
    SwipeRefreshLayout swipeRefreshLayout;           // 刷新按钮

    ArrayList<Attractions> attractionses;  // 景点名称
    String brandName; // 景区名称
    int brandId;     // 景点ID

    private static final int showCount = 1;  // 显示条数
    private static int currentPage = 1;   // 当前页

    private ProgressDialog progressDialog;   // 进度条

    Map<String, String> params = new HashMap(0);  // 保存http请求的参数

    String curText = "";             // 当前搜索框内容的值
    String preText = "";             // 上次搜索框内容的值

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secnic_search);

        currentPage = 1;

        getData();
        findViewById();
        initView();;
    }

    /**
     * 获取数据*
     */
    private void getData() {
        brandId = getIntent().getExtras().getInt("brandId");
        brandName = getIntent().getExtras().getString("brandName");
        attractionses = getIntent().getParcelableArrayListExtra("attractionses");
    }

    private void createProgressDialog() {
        progressDialog = ProgressDialog.show(this, null, getString(R.string.please_loading_hint), true, true);
    }

    @Override
    protected void findViewById() {
        backImg = (ImageView)findViewById(R.id.back_imageview_id);
        closeImg = (ImageView)findViewById(R.id.title_search_id);
        searchText = (XEditText)findViewById(R.id.title_text_id);
        hintLayout = (LinearLayout)findViewById(R.id.scenic_search_result_empty);
        recyclerView = (RecyclerView)findViewById(R.id.scenic_ticket_list);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.scenic_refresh_container);
    }

    @Override
    protected void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(linearLayoutManager);   // 设置管理器
        recyclerView.setItemAnimator(new DefaultItemAnimator());  // 添加动画
        recyclerView.addItemDecoration(new ItemDivider(this, 1));// 添加分隔线
        hintLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        if(attractionsListAdapter == null){
            attractionsListAdapter = new AttractionsListAdapter(this);
            recyclerView.setAdapter(attractionsListAdapter);
        }

        searchText.setDrawableLeftListener(new XEditText.DrawableLeftListener() {  // 注册左侧图片搜索事件
            @Override
            public void onDrawableLeftClick(EditText paramEditText) {
                refreshData();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // 注册下拉刷新事件
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        closeImg.setOnClickListener(new View.OnClickListener() {  // 注册清除按钮事件
            @Override
            public void onClick(View v) {
                searchText.setText("");
            }
        });

        backImg.setOnClickListener(new View.OnClickListener() {  // 注册退回按钮事件
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
            params.put("title", searchText);
        }
        this.params.put("brandId", brandId + "");              // 景点ID
        this.params.put("currentPage", this.currentPage + ""); // 当前页
        this.params.put("showCount", this.showCount + "");    // 每页显示条数
        loadData(params);
    }

    public void loadData(Map<String, String> params) {
        if (NetWorkHelper.isNetAvailable(this)) {
            createProgressDialog();
            HttpManager.requestOnceWithURLString(this, Constants.ATTRACTIONS_SEARCH_URL, params, requestHandler);
        } else {
            MessageUtils.showErrorMessage(this, getResources().getString(R.string.error_network_exception));
        }
    }

    HttpRequestHandler requestHandler = new HttpRequestHandler<String> (){ // 分页回调接口
        @Override
        public void onFailure(String error) {
            progressDialog.dismiss();
            MessageUtils.showErrorMessage(Attractions_Search_List_Activty.this, error);
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
                        MessageUtils.showMiddleToast(Attractions_Search_List_Activty.this, "已没有数据可显示");
                    } else {
                        ArrayList<Attractions> array = JsonUtils.parsingAttractions(jsonObject.getString("result"));
                        if(array.size() > 0) { // 可搜索到内容
                            recyclerView.setVisibility(View.VISIBLE);
                            hintLayout.setVisibility(View.GONE);
                            if(currentPage == 1) {
                                attractionsListAdapter.getList().clear();
                            }
                            attractionsListAdapter.update(array, true);
                            recyclerView.getLayoutManager().scrollToPosition(0);
                            currentPage++;
                        } else { // 搜索内容为空
                            hintLayout.setVisibility(View.VISIBLE);
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

}
