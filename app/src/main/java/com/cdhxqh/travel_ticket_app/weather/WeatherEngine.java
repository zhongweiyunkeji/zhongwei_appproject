/*************************************************************************

Copyright 2014 MagicMod Project

This file is part of MagicMod Weather.

MagicMod Weather is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

MagicMod Weather is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with MagicMod Weather. If not, see <http://www.gnu.org/licenses/>.

*************************************************************************/

package com.cdhxqh.travel_ticket_app.weather;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * 提供一个操作天气数据的主要引擎类
 * 建议使用此类来得到原始的WeatherInfo和WeatherProvider
 * 
 * @author SunRain
 * 
 * 2014年1月14日
 *
 */
public class WeatherEngine {

    private static final String TAG = "WeatherEngine";

    private static final String CACHE_NAME = "weather_cache.dat";
    private String mCacheDir;
    
    private Context mContext;
    
    private static WeatherEngine mWeatherEngine;
    
    private WeatherProvider mWeatherProvider;

    private String Key_city = "key_city";
    private String Key_date = "Key_date"; // 天气数据的日期
    private String Key_temp = "Key_temp"; // 当前温度
    private String key_temphight = "key_temphight"; // 最高温度
    private String key_templow = "key_templow"; // 最低温度
    private String key_condition = "key_condition"; // 天气情况=>晴/阴之类
    private String key_conditionCode = "key_conditionCode"; //天气对应的图标
    private String key_windSpeed = "key_windSpeed"; // 风力
    private String key_windDirection = "key_windDirection"; // 风向
    //private String windSpeedUnit; // 风速单位,如 km/h
    private String key_humidity = "key_humidity"; // 湿度
    private String key_synctimestamp = "key_synctimestamp"; // 同步时间

    private String key_PM2Dot5Data = "key_PM2Dot5Data"; // PM2.5
    private String key_AQIData = "key_AQIData"; // AQI(空气质量指数)
    private String key_sunrise = "key_sunrise";
    private String key_sunset = "key_sunset";
    
    private String key_tempUnit = "key_tempUnit";; // 温度格式==>摄氏或者华氏
    private String key_windSpeedUnit = "key_windSpeedUnit"; // 风速单位,如 km/h
    
    private WeatherEngine(Context context) {
        mContext = context;
        
        mCacheDir = mContext.getApplicationInfo().dataDir+"/weather_cache";
        File dir = new File(mCacheDir);
        if (!dir.exists())
            dir.mkdirs();
    }
    
    
    public static WeatherEngine getinstance(Context context) {
        if (mWeatherEngine == null) {
            mWeatherEngine = new WeatherEngine(context);
        }
        return mWeatherEngine;
    }
    
    /**
     * get the specified WeatherProvider by setWeatherProvider(WeatherProvider provider)
     * 
     * 2014年1月13日
     * @return
     */
    public WeatherProvider getWeatherProvider() {
        if (mWeatherProvider == null) {
            mWeatherProvider = new YahooWeatherProvider(mContext);
        }
        return mWeatherProvider;
    }
    
    public void setWeatherProvider(WeatherProvider provider) {
        mWeatherProvider = provider;
    }
    
    /**
     * set weatherInfo to cache, cache will be writte to apk's cache folder
     * 
     * 2014年1月13日
     * @param weatherInfo
     * @return
     */
    public synchronized boolean setToCache(WeatherInfo weatherInfo) {
        cleanCache();
        return flushCache(encodeWeatherInfo(weatherInfo));
    }
    
    public synchronized boolean cleanCache() {
        File file = new File(mCacheDir, CACHE_NAME);
        return file.delete();
    }
    /**
     * Get cached weather info
     * 
     * 2014年1月14日
     * @return
     */
    public synchronized WeatherInfo getCache() {
        File file = new File(mCacheDir, CACHE_NAME);
        if (!file.exists() || !file.isFile()) {
            return null;
        }

        InputStream ins = null;
        BufferedReader reader = null;
        StringBuffer buffer = new StringBuffer();
        String line;
        
        try {
            ins = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(ins));
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\r\n");
            }
            
            if (reader != null) {
                reader.close();
            }
            if (ins != null) {
                ins.close();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // Use try instead of throw IOException as we need to make sure we got the weather info
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        
        return decodeWeatherInfo(buffer.toString());
    }
    
    private synchronized boolean flushCache(String string) {
        DataOutputStream out = null;
        File file = null;
        try {
            file = new File(mCacheDir, CACHE_NAME);
            out = new DataOutputStream(new FileOutputStream(file));
            out.write(string.getBytes());
            if (out != null) {
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            // TODO: handle exception
            Log.e(TAG, "Write to Cache fail");
            e.printStackTrace();
            
            if(file != null) {
                file.delete();
            }
            return false;
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (Exception e2) {
                    // TODO: handle exception
                    if (file != null) {
                        file.delete();
                    }
                }
            }
        }
        return true;
    }
    
    /**
     * encodeWeatherInfo to json array
     * 2014年1月13日
     * @param weatherInfo
     * @return
     */
     private String encodeWeatherInfo(WeatherInfo weatherInfo) {
        ArrayList<DayForecast> days = weatherInfo.getDayForecast();
        JSONArray array = new JSONArray();
        for(DayForecast day : days) {
            JSONObject object = new JSONObject();

            try {
                object.put(key_AQIData, day.getAQIData());
                object.put(key_condition, day.getCondition());
                object.put(key_conditionCode, day.getConditionCode());
                object.put(key_humidity, day.getHumidity());
                object.put(key_PM2Dot5Data, day.getPM2Dot5Data());
                object.put(key_sunrise, day.getSunRise());
                object.put(key_sunset, day.getSunSet());
                object.put(key_synctimestamp, day.getSynctimestamp());
                object.put(key_temphight, day.getTempHigh());
                object.put(key_templow, day.getTempLow());
                object.put(key_windDirection, day.getWindDirection());
                object.put(key_windSpeed, day.getWindSpeed());
                object.put(Key_city, day.getCity());
                object.put(Key_date, day.getDate());
                object.put(Key_temp, day.getTemperature());
                object.put(key_tempUnit, day.getTempUnit());
                object.put(key_windSpeedUnit, day.getWindSpeedUnit());
            } catch (Exception e) {
                // TODO: handle exception
                Log.e(TAG, "encode weather info to json error");
                e.printStackTrace();
                return null;
            }
            array.put(object);
        }
        
        //Log.d(TAG, String.format("json array is [ %s ]", array.toString()));
        
        return array.toString();
    }
    
    /**
     * decode the weather json array
     * 2014年1月13日

     * @return
     */
    private WeatherInfo decodeWeatherInfo(String encodedWeatherInfoJsonArray) {
        JSONArray array;
        try {
            array = new JSONArray(encodedWeatherInfoJsonArray);
        } catch (Exception e) {
            // TODO: handle exception
            Log.d(TAG, String.format("Cant' create jaon array from String [%s]", encodedWeatherInfoJsonArray));
            e.printStackTrace();
            return null;
        }
        ArrayList<DayForecast> days = null;
        try {
            days = new ArrayList<DayForecast>();
            
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                DayForecast day = new DayForecast();
                day.setCondition(object.getString(key_condition));
                day.setConditionCode(object.getString(key_conditionCode));
                day.setHumidity(object.getString(key_humidity));
                day.setPM2Dot5Data(object.getString(key_PM2Dot5Data));
                day.setSunRaise(object.getString(key_sunrise));
                day.setSunSet(object.getString(key_sunset));
                day.setSynctimestamp(object.getString(key_synctimestamp));
                day.setTempHigh(object.getString(key_temphight));
                day.setTempLow(object.getString(key_templow));
                day.setWindDirection(object.getString(key_windDirection));
                day.setWindSpeed(object.getString(key_windSpeed));
                day.setCity(object.getString(Key_city));
                day.setDate(object.getString(Key_date));
                day.setTemperature(object.getString(Key_temp));
                day.setTempUnit(object.getString(key_tempUnit));
                day.setWindSpeedUnit(object.getString(key_windSpeedUnit));
                days.add(day);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
        return new WeatherInfo(days);
    }
    
}
