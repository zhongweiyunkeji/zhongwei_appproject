package com.cdhxqh.travel_ticket_app.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.api.HttpManager;
import com.cdhxqh.travel_ticket_app.api.HttpRequestHandler;
import com.cdhxqh.travel_ticket_app.config.Constants;
import com.cdhxqh.travel_ticket_app.model.Attractions;
import com.cdhxqh.travel_ticket_app.model.Ecs_brand;
import com.cdhxqh.travel_ticket_app.ui.adapter.AttractionsListAdapter;
import com.cdhxqh.travel_ticket_app.ui.widget.ItemDivider;
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
 * 景点列表**
 */

public class Attractions_List_Activty extends BaseActivity {

    private static final String TAG = "Attractions_List_Activty";
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

    /**
     * attractionsListAdapter*
     */
    AttractionsListAdapter attractionsListAdapter;

    ArrayList<Attractions> attractionses;
    /**
     * 景区名称*
     */
    String brandName;

    // 景区ID
    int brandId;


    /**
     * 定位相关*
     */
    public LocationClient mLocationClient;

    public MyLocationListener mMyLocationListener;

    SwipeRefreshLayout swipeRefreshLayout;  // 刷新控件

    Map<String, String> params = new HashMap(0);  // 保存http请求的参数

    private static final int showCount = 10;  // 显示条数
    private static int currentPage = 1;   // 当前页

    ProgressDialog progressDialog;  // 进度条

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_distance);
        /**定位相关**/
        locationData();
        getData();
        findViewById();
        initView();

        currentPage = 1;

        refreshData();

    }


    /**
     * 获取数据*
     */
    private void getData() {
        brandId = getIntent().getExtras().getInt("brandId");
        brandName = getIntent().getExtras().getString("brandName");
        attractionses = getIntent().getParcelableArrayListExtra("attractionses");
        // Log.i(TAG, "brandName=" + brandName + ",attractionses=" + attractionses);
    }

    @Override
    protected void findViewById() {
        backImage = (ImageView) findViewById(R.id.back_imageview_id);
        titleText = (TextView) findViewById(R.id.title_text_id);
        searchImageView = (ImageView) findViewById(R.id.title_search_id);


        mRecyclerView = (RecyclerView) findViewById(R.id.list_distance);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_container);

        attractionsListAdapter = new AttractionsListAdapter(this);
    }

    @Override
    protected void initView() {
        titleText.setText(brandName);
        backImage.setOnClickListener(backImageOnClickListener);
        backImage.setVisibility(View.VISIBLE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        mRecyclerView.addItemDecoration(new ItemDivider(this,
                ItemDivider.VERTICAL_LIST));

        mRecyclerView.setAdapter(attractionsListAdapter);
        // attractionsListAdapter.update(attractionses, true);

        searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Attractions_List_Activty.this, Attractions_Search_List_Activty.class);
                Bundle bundle = new Bundle();
                bundle.putInt("brandId", brandId);
                bundle.putString("brandName", brandName);
                bundle.putParcelableArrayList("attractionses", attractionses);
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

    }

    /**
     * 定位相关*
     */
    private void locationData() {
        // Log.i(TAG, "定位");
        mLocationClient = new LocationClient(Attractions_List_Activty.this);
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
//        option.setScanSpan(5000);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }


    /**
     * 添加商品测试数据*
     */
    private ArrayList<Ecs_brand> addGoods() {
        ArrayList<Ecs_brand> brand_List = new ArrayList<Ecs_brand>();

        for (int i = 0; i < 10; i++) {
            Ecs_brand ecs_brands = new Ecs_brand();
            ecs_brands.setBrand_name("沙坡头");
            ecs_brands.setBrand_desc("这里是一望无际.烟波浩瀚的大沙漠，而且脚下则是我们的母亲河黄河...");
            brand_List.add(ecs_brands);
        }

        return brand_List;
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
     * 实现实时位置回调监听
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            Log.i(TAG, "定位开始＝" + location.getLocType());
//            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果

            String latitude = location.getLatitude() + "";

            String longitude = location.getLongitude() + "";

            String speed = location.getSpeed() + "";

            String altitude = location.getAltitude() + "";

            attractionsListAdapter.updateDis(latitude, longitude);
            Log.i(TAG, "latitude=" + latitude + ",Longitude=" + longitude + ",speed=" + speed + ",altitude=" + altitude);


//            }
        }
    }


    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocationClient.stop();
        super.onDestroy();
    }


    public void refreshData() {
        swipeRefreshLayout.setRefreshing(false);     // 完成刷新,隐藏搜索刷新旋转按钮
        params.clear();
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

    private void createProgressDialog() {
        progressDialog = ProgressDialog.show(this, null, getString(R.string.please_loading_hint), true, true);
    }

    HttpRequestHandler requestHandler = new HttpRequestHandler<String> (){ // 分页回调接口
        @Override
        public void onFailure(String error) {
            progressDialog.dismiss();
            MessageUtils.showErrorMessage(Attractions_List_Activty.this, error);
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
                        MessageUtils.showMiddleToast(Attractions_List_Activty.this, "已没有数据可显示");
                    } else {
                        ArrayList<Attractions> array = JsonUtils.parsingAttractions(jsonObject.getString("result"));
                        if(array.size() > 0) { // 可搜索到内容
                            if(currentPage == 1) {
                                attractionsListAdapter.getList().clear();
                            }
                            attractionsListAdapter.update(array, true);
                            currentPage++;
                        } else { // 搜索内容为空
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
            currentPage++;
            progressDialog.dismiss();
        }
    };

}
