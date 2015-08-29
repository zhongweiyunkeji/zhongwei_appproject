package com.cdhxqh.travel_ticket_app.ui.activity;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.app.BaseApplication;
import com.cdhxqh.travel_ticket_app.weather.BrokenLineChart;
import com.cdhxqh.travel_ticket_app.weather.ChartPoint;
import com.cdhxqh.travel_ticket_app.weather.Constants;
import com.cdhxqh.travel_ticket_app.weather.MyProgress;
import com.cdhxqh.travel_ticket_app.weather.NextDaysFirstWeatherFragment;
import com.cdhxqh.travel_ticket_app.weather.NextDaysWeatherPagerAdapter;
import com.cdhxqh.travel_ticket_app.weather.Preferences;
import com.cdhxqh.travel_ticket_app.weather.WeatherEngine;
import com.cdhxqh.travel_ticket_app.weather.WeatherInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/8/28.
 */
public class WeatherActivity extends FragmentActivity {
    /**
     * 返回按钮*
     */
    private ImageView backImageView;
    /**
     * 标题*
     */
    private TextView titleTextView;
    /**
     * 搜索*
     */
    private ImageView seachImageView;
    LinearLayout lin;
    MyProgress progressHorizontal;
    private ViewPager mViewPager;
    private NextDaysWeatherPagerAdapter mWeatherPagerAdapter;
    private List<Fragment> fragments;
    private Context mContext;
    private WeatherEngine mWeatherEngine;
    private static final boolean DBG = Constants.DEBUG;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        mContext = getApplicationContext();
        final Application application =  this.getApplication();
        BaseApplication mApplication = (BaseApplication) application;
        mWeatherEngine = mApplication.getWeatherEngine();
        findViewById();
        initWeatherData();
        initView();
    }

    public void  findViewById() {
        /**
         * 标题标签相关id
         */
        backImageView = (ImageView) findViewById(R.id.back_imageview_id);
        titleTextView = (TextView) findViewById(R.id.title_text_id);
        seachImageView = (ImageView) findViewById(R.id.title_search_id);
        backImageView.setOnTouchListener(backImageViewOnTouchListener);

        lin = (LinearLayout) findViewById(R.id.l1);
        progressHorizontal = (MyProgress) findViewById(R.id.progressBar);
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

    /**
     * 返回事件的监听*
     */
    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private void initWeatherData() {
        if (DBG)
            Log.d(TAG, "init weather data");

        WeatherInfo info = mWeatherEngine.getCache(); //get weather info from cache
        if (info != null) { //open a dialog to let user select their city
            if (DBG)
                Log.d(TAG, "get cache fail, start the city input dialog");
            new WeatherUpdateTask(Preferences.isMetric(WeatherActivity.this.mContext)).execute();
        }
//		updateWeatherView(info,false);
    }

    public void initView() {
        //设置标签页显示方式
        backImageView.setVisibility(View.VISIBLE);
        seachImageView.setVisibility(View.GONE);
        titleTextView.setText("景区实况");
        chartPoint();
        progressBar();

        fragments = new ArrayList<Fragment>();
        fragments.add(new NextDaysFirstWeatherFragment(mContext, mWeatherEngine.getCache(),
                mWeatherEngine.getWeatherProvider().getWeatherResProvider()));

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mWeatherPagerAdapter = new NextDaysWeatherPagerAdapter(getSupportFragmentManager(),
                fragments);
        mViewPager.setAdapter(mWeatherPagerAdapter);

        //返回至登录界面事件
        backImageView.setOnClickListener(backImageViewOnClickListener);
    }

    private class WeatherUpdateTask extends AsyncTask<Void, Void, WeatherInfo> {
        private Location mLocation;
        private boolean mIsMeric;

        public WeatherUpdateTask( boolean isMeric) {
            this.mIsMeric = isMeric;

        }

        public WeatherUpdateTask(Location location, boolean isMeric) {
            this.mLocation = location;
            this.mIsMeric = mIsMeric;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//			mUpdateProgressBar.startAnim();
        }

        @Override
        protected WeatherInfo doInBackground(Void... params) {
            WeatherInfo info = null;
//			if (mLocationResult != null) {
            info = mWeatherEngine.getWeatherProvider().getWeatherInfo("2154493",
                    "中卫", mIsMeric);
//			} else if (mLocation != null) {
//				info = mWeatherEngine.getWeatherProvider().getWeatherInfo(mLocation, mIsMeric);
//			}
            if (info != null) {
                mWeatherEngine.setToCache(info);
                Preferences.setCityID(WeatherActivity.this.mContext, "2154493");
                Preferences.setCountryName(WeatherActivity.this.mContext, "中国");
                Preferences.setCityName(WeatherActivity.this.mContext, "中卫");
            }
            return info;
        }

        @Override
        protected void onPostExecute(WeatherInfo info) {
            super.onPostExecute(info);
//			mUpdateProgressBar.stopAnim();
//			updateWeatherView(info,true);
//			sendBroadcast(new Intent(WeatherUpdateService.ACTION_FORCE_UPDATE));
        }
    }

    public void chartPoint() {
        String title = new String();
        ChartPoint[] datas = new ChartPoint[13];
        datas[0] = new ChartPoint(1,1200);
        datas[1] = new ChartPoint(2,200);
        datas[2] = new ChartPoint(3,500);
        datas[3] = new ChartPoint(5,2500);
        datas[4] = new ChartPoint(7,1200);
        datas[5] = new ChartPoint(10,400);

        datas[6] = new ChartPoint(13,2400);
        datas[7] = new ChartPoint(15,2200);
        datas[8] = new ChartPoint(17,1800);
        datas[9] = new ChartPoint(20,1000);
        datas[10] = new ChartPoint(23,100);
        datas[11] = new ChartPoint(27,1100);
        datas[12] = new ChartPoint(30,2100);

        BrokenLineChart chart = new BrokenLineChart(title, Color.GREEN,datas,WeatherActivity.this);
        lin.addView(chart.GetView(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
    }

    public void progressBar() {
        setProgressBarVisibility(true);
        setProgress(progressHorizontal.getProgress() * 100);
        setSecondaryProgress(progressHorizontal.getSecondaryProgress() * 100);
//		setText(progressHorizontal.getProgress());
    }

//	//设置文字内容
//	private void setText(int progress){
//		int i = (progress * 100)/progressHorizontal.getMax();
//		progressHorizontal.text = String.valueOf(i) + "%";
//	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
