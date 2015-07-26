package com.cdhxqh.travel_ticket_app.app;

import android.app.Application;
import android.content.res.Configuration;

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
        // TODO Auto-generated method stub
        super.onCreate();
//        ImageLoaderConfig.initImageLoader(this, Constants.BASE_IMAGE_CACHE);
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
