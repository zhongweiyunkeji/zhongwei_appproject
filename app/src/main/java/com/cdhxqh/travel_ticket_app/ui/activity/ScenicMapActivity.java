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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.api.HttpManager;
import com.cdhxqh.travel_ticket_app.api.HttpRequestHandler;
import com.cdhxqh.travel_ticket_app.model.Attractions;
import com.cdhxqh.travel_ticket_app.model.Ecs_brand;
import com.cdhxqh.travel_ticket_app.ui.adapter.PopWinAdapter;
import com.cdhxqh.travel_ticket_app.utils.MessageUtils;

import java.util.ArrayList;

/**
 * 地图显示的Activity*
 */
public class ScenicMapActivity extends BaseActivity {
    private static final String TAG = "ScenicMapActivity";
    /**
     * 返回按钮*
     */
    ImageView backImageView;
    /**
     * 标题*
     */
    TextView titleTextView;
    /**
     * 菜单*
     */
    ImageView menuTextView;

    /**
     * 地图*
     */
    MapView mMapView = null;

    /**
     * 标题*
     */
    String brandName;

    /**
     * 参照物的经度/纬度*
     */
    String longitude;

    String latitude;

    /**
     * 景区门票id*
     */
    int brand_id;


    /**
     * 定位相关*
     */


    BaiduMap mBaiduMap;


    private Marker mMarkerA;
    // 初始化全局 bitmap 信息，不用时及时 recycle
    BitmapDescriptor bdA = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_marka);

    private InfoWindow mInfoWindow;


    private ProgressDialog progressDialog;


    /**
     * 显示条数*
     */
    private static final int showCount = 10;
    /**
     * 当前页数*
     */
    private static final int currentPage = 1;


    /****/
    private ArrayList<Attractions> attractionses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_scenic_map);
        getData();
        findViewById();
        initView();
        createProgressDialog();
        requestAttractions(true);


    }

    /**
     * 获取上个页面的数据*
     */
    private void getData() {
        brand_id = getIntent().getExtras().getInt("brand_id");
        brandName = getIntent().getExtras().getString("brand_name");
        longitude = getIntent().getExtras().getString("longitude");
        latitude = getIntent().getExtras().getString("latitude");
        Log.i(TAG, "brand_id=" + brand_id + "brandName=" + brandName + ",longitude=" + longitude + ",latitude=" + latitude);
    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.back_imageview_id);
        titleTextView = (TextView) findViewById(R.id.title_text_id);
        menuTextView = (ImageView) findViewById(R.id.title_search_id);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
    }

    @Override
    protected void initView() {
        backImageView.setVisibility(View.VISIBLE);
        titleTextView.setText(brandName);
        backImageView.setOnClickListener(backImageOnClickListener);
        menuTextView.setOnClickListener(menuTextViewOnClickListener);

        initBaiDuMap();

    }


    /**
     * 百度地图*
     */
    private void initBaiDuMap() {

        mBaiduMap = mMapView.getMap();
        initLocation();
        initOverlay();


        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            public boolean onMarkerClick(final Marker marker) {
                Button button = new Button(getApplicationContext());
                button.setBackgroundResource(R.drawable.popup);
                InfoWindow.OnInfoWindowClickListener listener = null;
                if (marker == mMarkerA) {
                    button.setText("沙漠博物馆");
                    LatLng ll = marker.getPosition();
                    mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(button), ll, -47, listener);
                    mBaiduMap.showInfoWindow(mInfoWindow);
                }
                return true;
            }
        });
    }


    /**
     * 根据参照物经纬度定位中心*
     */
    public void initLocation() {
        if (!latitude.equals("") && !longitude.equals("")) {
            LatLng cenpt = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

            MapStatus mMapStatus = new MapStatus.Builder()
                    .target(cenpt)
                    .zoom(15)
                    .build();
            //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化


            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            //改变地图状态
            mBaiduMap.setMapStatus(mMapStatusUpdate);
        }

    }


    /**
     * 覆盖物*
     */
    public void initOverlay() {
        LatLng llA = new LatLng(37.46976, 105.004958);

        OverlayOptions ooA = new MarkerOptions().position(llA).icon(bdA)
                .zIndex(9).draggable(true);
        mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
        ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
        giflist.add(bdA);

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


    /**
     * 弹出popwindow*
     */
    private View.OnClickListener menuTextViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(ScenicMapActivity.this, Attractions_List_Activty.class);
            Bundle bundle = new Bundle();
            bundle.putString("brandName", brandName);
            bundle.putParcelableArrayList("attractionses", attractionses);
            intent.putExtras(bundle);
            startActivityForResult(intent, 0);
        }
    };


    /**
     * 计算ListView的高度 *
     */
    private int setPullLvHeight(ListView pull) {
        int totalHeight = 0;
        ListAdapter adapter = pull.getAdapter();
        for (int i = 0, len = adapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = adapter.getView(i, null, pull);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }
        return totalHeight;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scenic_map, menu);
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        mMapView = null;
        bdA.recycle();
    }

    @Override
    protected void onResume() {
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
        super.onResume();

    }

    @Override
    protected void onPause() {
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
        super.onPause();

    }

    /**
     * 创建progressDialog*
     */
    private void createProgressDialog() {
        progressDialog = ProgressDialog.show(ScenicMapActivity.this, null, getString(R.string.please_loading_hint), true, true);
    }


    /**
     * 获取请求数据*
     */
    private void requestAttractions(boolean refresh) {
        HttpManager.getAttractions_list(ScenicMapActivity.this, "", brand_id + "", showCount + "", currentPage + "", true, handler);
    }


    private HttpRequestHandler<ArrayList<Attractions>> handler = new HttpRequestHandler<ArrayList<Attractions>>() {


        @Override
        public void onSuccess(ArrayList<Attractions> data) {

            Log.i(TAG, "data=" + data);
            if (data != null && data.size() != 0) {
                attractionses = data;

            }
            progressDialog.dismiss();

        }

        @Override
        public void onSuccess(ArrayList<Attractions> data, int totalPages, int currentPage) {
            progressDialog.dismiss();
            Log.i(TAG, "222222");

        }

        @Override
        public void onFailure(String error) {
            Log.i(TAG, "333333");
            MessageUtils.showErrorMessage(ScenicMapActivity.this, error);
            progressDialog.dismiss();
        }
    };

}
