package com.cdhxqh.travel_ticket_app.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
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

/**
 * Created by hexian on 2015/8/4.
 */
public class Listen_ZhongWei_Search_Activity extends BaseActivity {

    LinearLayout hintLaout;          // 提示框的值

    ProgressDialog progressDialog;  // 进度条

    XEditText searchText;            // 搜索框
    String curText = "";             // 当前搜索框内容的值
    String preText = "";             // 上次搜索框内容的值
    ImageView backupImg;             // 退回按钮图片
    ImageView clearImg;              // 清除搜索内容图片

    SearchScenicAdapter adapter;     // RecyclerView适配器

    int currentPage = 1;            // 当前页
    int showCount = 10;              // 每页显示

    RecyclerView recyclerView;      // ListView替代控件
    SwipeRefreshLayout swipeRefreshLayout;

    Map<String, String> params = new HashMap(0);  // 保存http请求的参数

    HttpRequestHandler requestHandler = new HttpRequestHandler<String> (){ // 分页回调接口
        @Override
        public void onFailure(String error) {
            progressDialog.dismiss();
            MessageUtils.showErrorMessage(Listen_ZhongWei_Search_Activity.this, error);
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
                        MessageUtils.showMiddleToast(Listen_ZhongWei_Search_Activity.this, "已没有数据可显示");
                    } else {
                        ArrayList<Ecs_brand> array = JsonUtils.parsingBrandsInfo(jsonObject.getString("result"));
                        if(array.size() > 0) { // 可搜索到内容
                            recyclerView.setVisibility(View.VISIBLE);
                            hintLaout.setVisibility(View.GONE);
                            if(currentPage == 1) {
                                adapter.getList().clear();
                            }
                            adapter.update(array);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        hintLaout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        clearImg.setVisibility(View.GONE);

        backupImg.setOnTouchListener(backImageViewOnTouchListener);

        searchText.setHint("请输入搜索内容");
        searchText.setHintTextColor(getResources().getColor(R.color.white));  // 设置提示颜色为白色
        searchText.setTextSize(15);

        // 注册清除按钮内容变更事件
        this.searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if (0 != length) {
                    clearImg.setVisibility(View.VISIBLE);
                } else {
                    clearImg.setVisibility(View.GONE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        // RecyclerView注册适配器
        if (adapter == null) {
            adapter = new SearchScenicAdapter(this, 1001);
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
        if("".equals(searchText)){
            return;
        }
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
