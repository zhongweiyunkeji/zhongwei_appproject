package com.cdhxqh.travel_ticket_app.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.cdhxqh.travel_ticket_app.R;
/**地图显示的Activity**/
public class ScenicMapActivity extends BaseActivity {
    private static final String TAG="ScenicMapActivity";
    /**返回按钮**/
    ImageView backImageView;
    /**标题**/
    TextView titleTextView;
    /**菜单**/
    ImageView menuTextView;

    /**地图**/
    MapView mMapView = null;

    /**标题**/
    String brandName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_scenic_map);
        getData();
        findViewById();
        initView();
    }
    /**获取上个页面的数据**/
    private void getData() {
        brandName=getIntent().getExtras().getString("brand_name");
        Log.i(TAG,"brandName="+brandName);
    }

    @Override
    protected void findViewById() {
        backImageView=(ImageView)findViewById(R.id.back_imageview_id);
        titleTextView=(TextView)findViewById(R.id.title_text_id);
        menuTextView=(ImageView)findViewById(R.id.title_search_id);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
    }

    @Override
    protected void initView() {
        backImageView.setVisibility(View.VISIBLE);
        titleTextView.setText(brandName);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scenic_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }


    
}
