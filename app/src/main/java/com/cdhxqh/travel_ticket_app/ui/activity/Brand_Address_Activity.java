package com.cdhxqh.travel_ticket_app.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
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
import com.cdhxqh.travel_ticket_app.model.Ecs_brand;
import com.cdhxqh.travel_ticket_app.utils.MessageUtils;

import java.util.ArrayList;

public class Brand_Address_Activity extends BaseActivity {

    private static final String TAG = "Brand_Address_Activity";
    /**
     * 标题*
     */
    TextView titleText;
    /**
     * 返回按钮*
     */
    ImageView backImageView;
    /**
     * Ecs_brand*
     */
    Ecs_brand ecs_brand;


    /**
     * 地图*
     */
    MapView mMapView = null;


    /**
     * 参照物的经度/纬度*
     */
    String longitude;

    String latitude;


    private ProgressDialog progressDialog;

    /**
     * 定位相关*
     */


    BaiduMap mBaiduMap;

    private Marker mMarkerA;

    private InfoWindow mInfoWindow;


    // 初始化全局 bitmap 信息，不用时及时 recycle
    BitmapDescriptor bdA = BitmapDescriptorFactory
            .fromResource(R.drawable.ic_cus_map);


    Context mContext = Brand_Address_Activity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_brand__address_);
        initData();

        findViewById();
        initView();
    }

    private void initData() {
        ecs_brand = getIntent().getExtras().getParcelable("ecs_brand");

        longitude = ecs_brand.longitude;
        latitude = ecs_brand.latitude;

        Log.i(TAG, "longitude=" + longitude + "latitude=" + latitude);
    }

    @Override
    protected void findViewById() {
        titleText = (TextView) findViewById(R.id.title_text_id);
        backImageView = (ImageView) findViewById(R.id.back_imageview_id);

        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);

        backImageView.setOnTouchListener(backImageViewOnTouchListener);
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

    @Override
    protected void initView() {
        titleText.setText(getString(R.string.brand_address_text));
        backImageView.setOnClickListener(backImageViewOnClickListener);
        initBaiDuMap();
    }


    /**
     * 百度地图*
     */
    private void initBaiDuMap() {

        mBaiduMap = mMapView.getMap();

        initLocation();


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


            initOverlay(cenpt);

        }else{
            MessageUtils.showMiddleToast(Brand_Address_Activity.this,"无法定位该地址");
        }

    }

    /**
     * 绘制地图*
     */
    private void initOverlay(LatLng cenpt) {

        OverlayOptions ooA = new MarkerOptions().position(cenpt).icon(bdA)
                .zIndex(9).draggable(true);

        mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                View view = customView();

                if (marker == mMarkerA) {
                    InfoWindow.OnInfoWindowClickListener listener = null;
                    TextView t = (TextView) view.findViewById(R.id.arrtactions_title_id);
                    t.setText(ecs_brand.brand_name);





                    LatLng ll = marker.getPosition();
                    mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(view), ll, -60,listener);
                    mBaiduMap.showInfoWindow(mInfoWindow);
                }
                return false;

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






    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };


    @Override
    protected void onPause() {
        // MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        // MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
        mMapView.onDestroy();
        super.onDestroy();
        // 回收 bitmap 资源
    }

}
