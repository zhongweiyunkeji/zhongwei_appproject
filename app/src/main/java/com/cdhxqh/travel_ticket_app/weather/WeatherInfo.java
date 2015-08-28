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

import java.util.ArrayList;

/**
 * 具体的天气数据明细
 * 
 * @author SunRain 2014年1月12日
 */
public class WeatherInfo {
    public static final String DATA_NULL = "N/A";

    private String yujing;// 是否有预警，如果无，则为“暂无预警”
    private String alarmtext;

    private ArrayList<DayForecast> forecasts;

    public WeatherInfo(ArrayList<DayForecast> forecasts) {
        this.forecasts = forecasts;
    }

    /**
     * 2014年1月19日
     * 
     * the ArrayList should be ordered by weather date
     * 
     * @return
     */
    public ArrayList<DayForecast> getDayForecast() {
        return forecasts;
    }

    @Override
    public String toString() {
        int i = 0;
        StringBuilder builder = new StringBuilder();
        for (DayForecast forecast : forecasts) {
            builder.append(String.format("DayForecast[%d] => {%s}\n", i, forecast.toString()));
            i++;
        }
        return builder.toString();
    }

}
