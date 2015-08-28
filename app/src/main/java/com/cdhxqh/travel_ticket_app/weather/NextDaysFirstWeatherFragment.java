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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;

import java.util.ArrayList;

public class NextDaysFirstWeatherFragment extends Fragment {
    
    private static final String TAG = "NextDaysFirstWeatherFragment";
	
    private TextView weekTv1, weekTv2, weekTv3,weekTv4, weekTv5, weekTv6, weekTv7;
    private ImageView weather_imgIv1, weather_imgIv2, weather_imgIv3, weather_imgIv4, weather_imgIv5, weather_imgIv6, weather_imgIv7;
    private TextView temperatureTv1, temperatureTv2, temperatureTv3, temperatureTv4, temperatureTv5, temperatureTv6, temperatureTv7;
    private TextView temperatureTv1_a, temperatureTv2_a, temperatureTv3_a, temperatureTv4_a, temperatureTv5_a, temperatureTv6_a, temperatureTv7_a;
    private TextView climateTv1, climateTv2, climateTv3, climateTv4, climateTv5, climateTv6, climateTv7;
    private TextView windTv1, windTv2, windTv3, windTv4, windTv5, windTv6, windTv7;

    private Context mContext;
    private WeatherInfo mWeatherInfo;
    private WeatherResProvider mResProvider;
    public NextDaysFirstWeatherFragment(Context context, WeatherInfo info, WeatherResProvider res) {
        this.mContext = context;
        this.mWeatherInfo = info;
        this.mResProvider = res;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_view_weather_nextdays_item,
                container, false);
        View view1 = view.findViewById(R.id.subitem1);
        View view2 = view.findViewById(R.id.subitem2);
        View view3 = view.findViewById(R.id.subitem3);
        View view4 = view.findViewById(R.id.subitem4);
        View view5 = view.findViewById(R.id.subitem5);

        weekTv1 = (TextView) view1.findViewById(R.id.week);
        weekTv2 = (TextView) view2.findViewById(R.id.week);
        weekTv3 = (TextView) view3.findViewById(R.id.week);
        weekTv4 = (TextView) view4.findViewById(R.id.week);
        weekTv5 = (TextView) view5.findViewById(R.id.week);

        weather_imgIv1 = (ImageView) view1.findViewById(R.id.weather_img);
        weather_imgIv2 = (ImageView) view2.findViewById(R.id.weather_img);
        weather_imgIv3 = (ImageView) view3.findViewById(R.id.weather_img);
        weather_imgIv4 = (ImageView) view4.findViewById(R.id.weather_img);
        weather_imgIv5 = (ImageView) view5.findViewById(R.id.weather_img);

        temperatureTv1 = (TextView) view1.findViewById(R.id.temperature);
        temperatureTv2 = (TextView) view2.findViewById(R.id.temperature);
        temperatureTv3 = (TextView) view3.findViewById(R.id.temperature);
        temperatureTv4 = (TextView) view4.findViewById(R.id.temperature);
        temperatureTv5 = (TextView) view5.findViewById(R.id.temperature);

        temperatureTv1_a = (TextView) view1.findViewById(R.id.temperature_a);
        temperatureTv2_a = (TextView) view2.findViewById(R.id.temperature_a);
        temperatureTv3_a = (TextView) view3.findViewById(R.id.temperature_a);
        temperatureTv4_a = (TextView) view4.findViewById(R.id.temperature_a);
        temperatureTv5_a = (TextView) view5.findViewById(R.id.temperature_a);

        climateTv1 = (TextView) view1.findViewById(R.id.climate);
        climateTv2 = (TextView) view2.findViewById(R.id.climate);
        climateTv3 = (TextView) view3.findViewById(R.id.climate);
        climateTv4 = (TextView) view4.findViewById(R.id.climate);
        climateTv5 = (TextView) view5.findViewById(R.id.climate);

        windTv1 = (TextView) view1.findViewById(R.id.wind);
        windTv2 = (TextView) view2.findViewById(R.id.wind);
        windTv3 = (TextView) view3.findViewById(R.id.wind);
        windTv4 = (TextView) view4.findViewById(R.id.wind);
        windTv5 = (TextView) view5.findViewById(R.id.wind);

        updateWeather(mWeatherInfo, mResProvider, mContext);
        return view;
    }

    //Ugly code,  :(
    public void updateWeather(WeatherInfo info, WeatherResProvider res, Context context) {
        if (info == null || res == null) {
            return;
        }
        ArrayList<DayForecast> days = info.getDayForecast();

        DayForecast day1 = res.getPreFixedWeatherInfo(context, getDayForecast(days, 0));
        DayForecast day2 = res.getPreFixedWeatherInfo(context, getDayForecast(days, 1));
        DayForecast day3 = res.getPreFixedWeatherInfo(context, getDayForecast(days, 2));
        DayForecast day4 = res.getPreFixedWeatherInfo(context, getDayForecast(days, 3));
        DayForecast day5 = res.getPreFixedWeatherInfo(context, getDayForecast(days, 4));
        
        if (day1 != null) {
            String week = res.getWeek(day1, context);
            weekTv1.setText(changeWeek(week));
            weather_imgIv1.setImageResource(res.getWeatherIconResId(context, day1.getConditionCode(), null));
//            climateTv1.setText(day1.getCondition());
//            String s = String.format("%s ~ %s", day1.getTempLow(), day1.getTempHigh());
            temperatureTv1.setText(day1.getTempLow());
            temperatureTv1_a.setText(day1.getTempHigh());
//            windTv1.setText(day1.getWindDirection());
        } else {
            weekTv1.setVisibility(View.INVISIBLE);
            weather_imgIv1.setImageResource(R.drawable.weather_na);
            climateTv1.setVisibility(View.INVISIBLE);
            temperatureTv1.setVisibility(View.INVISIBLE);
            windTv1.setVisibility(View.INVISIBLE);
        }
        
        if (day2 != null) {
            weekTv2.setText(changeWeek(res.getWeek(day2, context)));
            weather_imgIv2.setImageResource(res.getWeatherIconResId(context, day2.getConditionCode(), null));
//            climateTv2.setText(day2.getCondition());
//            String s = String.format("%s ~ %s", day2.getTempLow(), day2.getTempHigh());
            temperatureTv2.setText(day2.getTempLow());
            temperatureTv2_a.setText(day2.getTempHigh());
//            windTv2.setText(day2.getWindDirection());
        } else {
            weekTv2.setVisibility(View.INVISIBLE);
            weather_imgIv2.setImageResource(R.drawable.weather_na);
            climateTv2.setVisibility(View.INVISIBLE);
            temperatureTv2.setVisibility(View.INVISIBLE);
            windTv2.setVisibility(View.INVISIBLE);
        }
        
        if (day3 != null) {
            weekTv3.setText(changeWeek(res.getWeek(day3, context)));
            weather_imgIv3.setImageResource(res.getWeatherIconResId(context, day3.getConditionCode(), null));
//            climateTv3.setText(day3.getCondition());
//            String s = String.format("%s ~ %s", day3.getTempLow(), day3.getTempHigh());
            temperatureTv3.setText(day3.getTempLow());
            temperatureTv3_a.setText(day3.getTempHigh());
//            windTv3.setText(day3.getWindDirection());
        } else {
            weekTv3.setVisibility(View.INVISIBLE);
            weather_imgIv3.setImageResource(R.drawable.weather_na);
            climateTv3.setVisibility(View.INVISIBLE);
            temperatureTv3.setVisibility(View.INVISIBLE);
            windTv3.setVisibility(View.INVISIBLE);
        }

        if (day4 != null) {
            weekTv4.setText(changeWeek(res.getWeek(day4, context)));
            weather_imgIv4.setImageResource(res.getWeatherIconResId(context, day4.getConditionCode(), null));
//            climateTv4.setText(day4.getCondition());
//            String s = String.format("%s ~ %s", day4.getTempLow(), day4.getTempHigh());
            temperatureTv4.setText(day4.getTempLow());
            temperatureTv4_a.setText(day4.getTempHigh());
//            windTv4.setText(day4.getWindDirection());
        } else {
            weekTv4.setVisibility(View.INVISIBLE);
            weather_imgIv4.setImageResource(R.drawable.weather_na);
            climateTv4.setVisibility(View.INVISIBLE);
            temperatureTv4.setVisibility(View.INVISIBLE);
            windTv4.setVisibility(View.INVISIBLE);
        }

        if (day5 != null) {
            weekTv5.setText(changeWeek(res.getWeek(day5, context)));
            weather_imgIv5.setImageResource(res.getWeatherIconResId(context, day5.getConditionCode(), null));
//            climateTv5.setText(day5.getCondition());
//            String s = String.format("%s ~ %s", day5.getTempLow(), day5.getTempHigh());
            temperatureTv5.setText(day5.getTempLow());
            temperatureTv5_a.setText(day5.getTempHigh());
//            temperatureTv5.setText(s);
//            windTv5.setText(day5.getWindDirection());
        } else {
            weekTv5.setVisibility(View.INVISIBLE);
            weather_imgIv5.setImageResource(R.drawable.weather_na);
            climateTv5.setVisibility(View.INVISIBLE);
            temperatureTv5.setVisibility(View.INVISIBLE);
            windTv5.setVisibility(View.INVISIBLE);
        }
    }

    private String changeWeek(String week) {
        switch (week){
            case ("Monday") :
                return "周一";
            case ("Tuesday") :
                return "周二";
            case ("Wednesday") :
                return "周三";
            case ("Thursday") :
                return "周四";
            case ("Friday") :
                return "周五";
            case ("Saturday") :
                return "周六";
            case ("Sunday") :
                return "周日";
        }
        return null;
    }
    
    private DayForecast getDayForecast(ArrayList<DayForecast>list, int index) {
        int size = list.size();
        if (size == 0 || list.isEmpty()) {
            return null;
        }
        if (index > size-1) {
            return null;
        }
        return list.get(index);
    }
}
