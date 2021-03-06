package com.cdhxqh.travel_ticket_app.ui.widget.hotelWineShop.utils;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Administrator on 2015/9/5.
 */
public class Gps {
    private static double latitude=0.0;
    private static double longitude =0.0;

    public static double[] getGps(Context activity) {
        double[] gps = {0.0, 0.0};
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {

            // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            // Provider被enable时触发此函数，比如GPS被打开
            @Override
            public void onProviderEnabled(String provider) {

            }

            // Provider被disable时触发此函数，比如GPS被关闭
            @Override
            public void onProviderDisabled(String provider) {

            }

            //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    Log.e("Map", "Location changed : Lat: "
                            + location.getLatitude() + " Lng: "
                            + location.getLongitude());
                }
            }
        };
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if(location == null) {
                while (location == null) {
                    locationManager.requestLocationUpdates("gps", 60000, 1, locationListener);
                }
            }
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                gps[0] = latitude;
                gps[1] = longitude;
                return gps;
            }
        } else {

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude(); //经度
                longitude = location.getLongitude(); //纬度
                gps[0] = latitude;
                gps[1] = longitude;
                return gps;
            }
        }
        return gps;
    }
}
