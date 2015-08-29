package com.cdhxqh.travel_ticket_app.ui.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.app.AppManager;
import com.cdhxqh.travel_ticket_app.ui.activity.BaseActivity;
import com.umeng.update.UmengUpdateAgent;


public class MainActivity extends TabActivity {
    private static final String TAG="MainActivity";

    private RadioGroup mTabButtonGroup;
    private TabHost mTabHost;

    /**
     * 首页*
     */
    public static final String TAB_HOME = "HOME_ACTIVITY";
    /**
     * 分类*
     */
    public static final String TAB_CLASS = "CLASS_ACTIVITY";
    /**
     * 订单*ORDER_ACTIVITY
     */
    public static final String TAB_ORDER = "ORDER_ACTIVITY";
    /**
     * 我的*
     */
    public static final String TAB_USER = "USER_ACTIVITY";

    RadioButton home_tab_main, home_tab_order;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addActivity(this);
        setContentView(R.layout.activity_main);

        findViewByIds();
        initView();
    }


    /**
     * 找到id*
     */
    private void findViewByIds() {

        mTabButtonGroup = (RadioGroup) findViewById(R.id.home_radio_button_group);

        home_tab_main = (RadioButton) findViewById(R.id.home_tab_main);

        home_tab_order = (RadioButton) findViewById(R.id.home_tab_order);
    }

    /**
     * 初始化界面组件*
     */
    private void initView() {

        mTabHost = getTabHost();

        Intent i_home = new Intent(this, HomeActivity.class);
        Intent i_class = new Intent(this, ClassActivity.class);
        Intent i_order = new Intent(this, OrderActivity.class);
        Intent i_user = new Intent(this, UserActivity.class);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_HOME).setIndicator(TAB_HOME)
                .setContent(i_home));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_CLASS)
                .setIndicator(TAB_CLASS).setContent(i_class));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_ORDER)
                .setIndicator(TAB_ORDER).setContent(i_order));
        mTabHost.addTab(mTabHost.newTabSpec(TAB_USER).setIndicator(TAB_USER)
                .setContent(i_user));

        Bundle bundle = new Bundle();

            mTabHost.setCurrentTabByTag(TAB_HOME);

        if(this.getIntent().getExtras() != null) {
            bundle = this.getIntent().getExtras();
            String activity =   bundle.getString("activity");
            home_tab_main.setChecked(false);
            home_tab_order.setChecked(true);
            mTabHost.setCurrentTabByTag(activity);
        }

        mTabButtonGroup
                .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.home_tab_main:
                                mTabHost.setCurrentTabByTag(TAB_HOME);
                                break;

                            case R.id.home_tab_class:
                                mTabHost.setCurrentTabByTag(TAB_CLASS);
                                break;

                            case R.id.home_tab_order:
                                mTabHost.setCurrentTabByTag(TAB_ORDER);
                                break;

                            case R.id.home_tab_user:
                                mTabHost.setCurrentTabByTag(TAB_USER);
                                break;


                            default:
                                break;
                        }
                    }
                });
    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        exit();

    }



    /**
     * 退出登陆*
     */
    private void exit() {
        Log.i(TAG,"123");
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_LONG).show();
            exitTime = System.currentTimeMillis();
        } else {
            AppManager.AppExit(MainActivity.this);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            //do something...
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
