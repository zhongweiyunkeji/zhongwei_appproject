package com.cdhxqh.travel_ticket_app.app;

import android.app.Application;
import android.content.res.Configuration;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by apple on 15/7/24.
 */
public class BaseApplication extends Application {

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method


        super.onCreate();
//        ImageLoaderConfig.initImageLoader(this, Constants.BASE_IMAGE_CACHE);
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
    }

    @Override
    public void onLowMemory() {
        // TODO Auto-generated method stub
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        // TODO Auto-generated method stub
        super.onTerminate();
    }
}
