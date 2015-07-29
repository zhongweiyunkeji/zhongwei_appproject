package com.cdhxqh.travel_ticket_app.ui.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;

/**
 * Created by Administrator on 2015/7/28.
 */
public class IntroductionActivity extends BaseActivity{
    /**
     * 加载显示网页
     */
    private WebView spot_webView_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        findViewById();
        initView();
    }

    /**
     * 绑定控件id
     */
    @Override
    protected void findViewById() {
        spot_webView_id = (WebView) findViewById(R.id.spot_webView_id);
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initView() {
        //实例化WebView对象
        spot_webView_id = new WebView(this);
        //设置WebView属性，能够执行Javascript脚本
        spot_webView_id.getSettings().setJavaScriptEnabled( true);
        //加载需要显示的网页
        spot_webView_id.loadUrl("file:///android_asset/android_asset.html");
        //设置Web视图
        setContentView(spot_webView_id);
    }

    @Override
    //设置回退
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && spot_webView_id.canGoBack()) {
            spot_webView_id.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        return false;
    }
}
