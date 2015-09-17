package com.cdhxqh.travel_ticket_app.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.ui.adapter.Bee_PageAdapter;
import com.cdhxqh.travel_ticket_app.viewpagerindicator.PageIndicator;
import com.umeng.update.UmengUpdateAgent;
import java.util.ArrayList;

public class HomeActivity extends BaseActivity {

    private static final String TAG = "HomeActivity";
    private ViewPager bannerViewPager;
    private PageIndicator mIndicator;

    private ArrayList<View> bannerListView;
    private Bee_PageAdapter bannerPageAdapter;

    private View mTouchTarget;

    private int[] images = {R.drawable.image_1, R.drawable.image_2, R.drawable.image_3, R.drawable.image_4};

    /**
     * 景区门票*
     */
    private LinearLayout scenic_spot_layout;
    /**
     * 听中卫*
     */
    private LinearLayout listen_zhongwei_layout;
    /**看中卫**/
    private LinearLayout  look_zhongwei_layout;

    /**
     * 今日特价
     */
    private LinearLayout todaySpecial;

    /**
     * 智慧旅游
     */
    LinearLayout tourism_spot_id;

    /**
     * 周边游
     */
    LinearLayout around_play;
    boolean flag = true;   // 图片轮播停止标识
    private static final long timeSpace = 3500L;  // 图片轮播器时间间隔
    Handler handler = new Handler(){ // 图片轮播器
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            if(flag){
                int currentItem = bannerViewPager.getCurrentItem();
                int nextIndex =  repairIndex(currentItem) + 1;
                bannerViewPager.setCurrentItem(nextIndex);
                handler.sendEmptyMessageDelayed(1, timeSpace);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        flag = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.update(this);
        findViewById();
        initView();

    }

    @Override
    protected void findViewById() {
        tourism_spot_id = (LinearLayout) findViewById(R.id.tourism_spot_id);
        bannerViewPager = (ViewPager) findViewById(R.id.banner_viewpager);
        mIndicator = (PageIndicator) findViewById(R.id.indicator);
        scenic_spot_layout = (LinearLayout) findViewById(R.id.scenic_spot_id);
        listen_zhongwei_layout = (LinearLayout) findViewById(R.id.listen_zhongwei_layout_id);
        look_zhongwei_layout = (LinearLayout) findViewById(R.id.look_zhongwei_linearlayout);
        todaySpecial = (LinearLayout) findViewById(R.id.todaySpecial);
        around_play = (LinearLayout) findViewById(R.id.around_play);
    }

    @Override
    protected void initView() {
        LayoutParams params1 = bannerViewPager.getLayoutParams();
        params1.width = getDisplayMetricsWidth();
        params1.height = (int) (params1.width * 1.0 / 484 * 250);
        flag = true;

        bannerViewPager.setLayoutParams(params1);

        bannerListView = new ArrayList<View>();
        bannerPageAdapter = new Bee_PageAdapter(bannerListView);
        bannerViewPager.setAdapter(bannerPageAdapter);
        addBannerView();


        bannerViewPager.setAdapter(bannerPageAdapter);
        int index = Integer.MAX_VALUE/2 - (Integer.MAX_VALUE/2%images.length);
        bannerViewPager.setCurrentItem(index);

        bannerViewPager.setOnPageChangeListener(bannerViewPagerOnPageChangeListener);

        scenic_spot_layout.setOnClickListener(senic_spot_layoutOnClickListener);
        listen_zhongwei_layout.setOnClickListener(listen_zhongweiOnClickListener);
        look_zhongwei_layout.setOnClickListener(look_zhongweiOnClickListener);

        tourism_spot_id.setOnClickListener(tourism_spotOnClickListener);

        todaySpecial.setOnClickListener(todaySpecialOnClickListener);

        around_play.setOnClickListener(around_playOnClickListener);

        handler.sendEmptyMessageDelayed(1, timeSpace + 1);
    }

    private View.OnClickListener around_playOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.putExtra("spotTitle", "酒店");
            intent.setClass(HomeActivity.this, AroundPlayActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener tourism_spotOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.putExtra("spotTitle", "中卫所有景区游客人数");
            intent.setClass(HomeActivity.this, WeatherActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener todaySpecialOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(HomeActivity.this, TodaySpecialActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener senic_spot_layoutOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(HomeActivity.this, Scenic_Tickets_Activity.class);
            startActivityForResult(intent, 0);
        }
    };
    private View.OnClickListener listen_zhongweiOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.putExtra("zw_title",0);
            intent.setClass(HomeActivity.this, Listen_ZhongWei_Activity.class);
            startActivityForResult(intent, 0);
        }
    };
    private View.OnClickListener look_zhongweiOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.putExtra("zw_title",1);
            intent.setClass(HomeActivity.this, Listen_ZhongWei_Activity.class);
            startActivityForResult(intent, 0);
        }
    };


    private ViewPager.OnPageChangeListener bannerViewPagerOnPageChangeListener = new ViewPager.OnPageChangeListener() {

        private int mPreviousState = ViewPager.SCROLL_STATE_IDLE;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Log.i(TAG, "**position=" + position);
            int nextIndex =  repairIndex(position);
            mIndicator.setCurrentItem(nextIndex);

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            // All of this is to inhibit any scrollable container from consuming our touch events as the user is changing

            if (mPreviousState == ViewPager.SCROLL_STATE_IDLE) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    mTouchTarget = bannerViewPager;
                }
            } else {
                if (state == ViewPager.SCROLL_STATE_IDLE || state == ViewPager.SCROLL_STATE_SETTLING) {
                    mTouchTarget = null;
                }
            }

            mPreviousState = state;

            Log.i(TAG, "mPreviousState=" + mPreviousState);
        }
    };


    //获取屏幕宽度
    public int getDisplayMetricsWidth() {
        int i = getWindowManager().getDefaultDisplay().getWidth();
        int j = getWindowManager().getDefaultDisplay().getHeight();
        return Math.min(i, j);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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


    public void addBannerView() {
        bannerListView.clear();
        for (int i = 0; i < images.length; i++) {
            Log.i(TAG, "ssss");
            ImageView viewOne = (ImageView) LayoutInflater.from(this).inflate(R.layout.index_banner_cell, null);
            viewOne.setImageResource(images[i]);
            bannerListView.add(viewOne);
        }
        mIndicator.setViewPager(bannerViewPager);
        mIndicator.notifyDataSetChanged();
        int index = Integer.MAX_VALUE/2 - (Integer.MAX_VALUE/2%images.length);
        mIndicator.setCurrentItem(0);
        bannerPageAdapter.mListViews = bannerListView;
    }


//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        exit(HomeActivity.this);
//    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            //do something...
            exit(HomeActivity.this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /*@Override
    protected void onResume() {
        flag = true;
        if(bannerViewPager!=null && bannerViewPager.getAdapter()!=null){
            handler.sendEmptyMessageDelayed(1, timeSpace + 1);
        }
        super.onResume();
    }

    @Override
    protected void onStop() {
        flag = false;
        super.onStop();
    }*/

    @Override
    protected void onDestroy() {
        flag = false;
        super.onDestroy();
    }

    /**
     * 如果所以到达2个端点则默认将其所以修正为Integer.MAX_VALUE的中间位置处
     */
    private int repairIndex(int index){
        if(index == 0){
            index = Integer.MAX_VALUE/2 - Integer.MAX_VALUE/2%images.length;
        } else
        if(index == Integer.MAX_VALUE){
            int centerIndex = Integer.MAX_VALUE/2 - Integer.MAX_VALUE/2%images.length;
            int tempIndex = Integer.MAX_VALUE%images.length;
            index = centerIndex + tempIndex;
        }

        return index;
    }
}
