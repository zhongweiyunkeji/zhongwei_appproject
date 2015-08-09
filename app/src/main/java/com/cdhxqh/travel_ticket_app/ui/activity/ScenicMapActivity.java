package com.cdhxqh.travel_ticket_app.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.api.HttpManager;
import com.cdhxqh.travel_ticket_app.api.HttpRequestHandler;
import com.cdhxqh.travel_ticket_app.model.Attractions;
import com.cdhxqh.travel_ticket_app.utils.MessageUtils;

import java.util.ArrayList;
import java.util.List;

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

    private List<Marker> mMarkerList;
    private Marker mMarkerA;
    // 初始化全局 bitmap 信息，不用时及时 recycle
    ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();

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

    Context mContext = ScenicMapActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // SDKInitializer.initialize(getApplicationContext());   在使用SDK各组件之前初始化context信息，必须在setContentView方法之前调用
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
        longitude = getIntent().getExtras().getString("longitude");  // 接收参照物的经度
        latitude = getIntent().getExtras().getString("latitude");    // 接收参照物的纬度
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


        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {// 注册覆盖物监听事件
            public boolean onMarkerClick(final Marker marker) {
                for (int i = 0; i < mMarkerList.size(); i++) {
                    View view = customView();

                    if (marker == mMarkerList.get(i)) {
                        InfoWindow.OnInfoWindowClickListener listener = null;
                        TextView t = (TextView) view.findViewById(R.id.arrtactions_title_id);
                        t.setText(attractionses.get(i).title);
                        final int finalI = i;


                        listener=new InfoWindow.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick() {
                                Intent intent = new Intent();
                                intent.setClass(ScenicMapActivity.this, Attractions_details_Activity.class);
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("attractions", attractionses.get(finalI));
                                intent.putExtras(bundle);
                                startActivityForResult(intent, 0);
                            }
                        };



                        LatLng ll = marker.getPosition();
                        mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(view), ll, -60,listener);  // 创建一个已知经纬度的信息窗口，已view作为显示窗口， 并增减消息窗口的监听事件
                        mBaiduMap.showInfoWindow(mInfoWindow);  // 显示信息提示窗口
                    }
                }
                return true;
            }
        });

        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mBaiduMap.hideInfoWindow();
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
    }


    /**
     * 自定义布局*
     */
    private View customView() {

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.cover_infowindow, null);


        return view;
    }


    /**
     * 根据参照物经纬度定位中心*
     */
    public void initLocation() {
        if (!latitude.equals("") && !longitude.equals("")) {
            LatLng cenpt = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));  // 定义参照物坐标(中心坐标)
            //定义地图状态
            MapStatus mMapStatus = new MapStatus.Builder()  // 创建百度地图状态构造器
                    .target(cenpt)  // 设置地图中心点
                    .zoom(15)       // 设置地图缩放级别
                    .build();       // 创建地图状态对象
            //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化


            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);  // 创建百度地图状态
            //改变地图状态
            mBaiduMap.setMapStatus(mMapStatusUpdate);
        }

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
            if(attractionses!=null) {
                Intent intent = new Intent();
                intent.setClass(ScenicMapActivity.this, Attractions_List_Activty.class);
                Bundle bundle = new Bundle();
                bundle.putString("brandName", brandName);
                bundle.putInt("brandId", brand_id);
                bundle.putParcelableArrayList("attractionses", attractionses);
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
            }else{
                MessageUtils.showMiddleToast(ScenicMapActivity.this,getString(R.string.not_hava_arrt_info));
            }
        }
    };


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
//        bdA.recycle();
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

            if (data != null && data.size() != 0) {
                attractionses = data;
                //获取经纬度
                getCoordinates(attractionses);
            }
            progressDialog.dismiss();

        }

        @Override
        public void onSuccess(ArrayList<Attractions> data, int totalPages, int currentPage) {
            progressDialog.dismiss();

        }

        @Override
        public void onFailure(String error) {
            MessageUtils.showMiddleToast(ScenicMapActivity.this,getString(R.string.not_hava_arrt_info));
            progressDialog.dismiss();
        }
    };


    /**
     * 获取经纬度*
     */
    private void getCoordinates(ArrayList<Attractions> alist) {
        giflist = new ArrayList<BitmapDescriptor>();
        mMarkerList = new ArrayList<Marker>();
        if (alist != null) {
            for (int i = 0; i < alist.size(); i++) {
                String latitude = alist.get(i).latitude; //纬度
                String longitude = alist.get(i).longitude;//经度
                if (!latitude.equals("") && !longitude.equals("")) {
                    LatLng llA = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                    BitmapDescriptor bd = BitmapDescriptorFactory
                            .fromView(cusBitmapDescripyorView(i + 1 + ""));   // 创建地图标覆盖物图标
                    OverlayOptions oo = new MarkerOptions().position(llA).icon(bd)  // 设置覆盖物选项的经纬度和图标
                            .zIndex(9).draggable(true);  // 设置覆盖物可拖拽
                    Marker mMarker = (Marker) (mBaiduMap.addOverlay(oo));  // 在地图中添加覆盖物
                    mMarkerList.add(mMarker);
                    giflist.add(bd);
                }
            }
        }


    }


    /**
     * 设置Marker显示信息*
     */
    private View cusBitmapDescripyorView(String str) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.cover_marker, null);
        TextView textView = (TextView) view.findViewById(R.id.marker_text_id);
        textView.setText(str);
        return view;
    }


}
