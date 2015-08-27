package com.cdhxqh.travel_ticket_app.utils;

import android.graphics.drawable.GradientDrawable;
import android.widget.TextView;

/**
 * Created by hexian on 2015/8/17.
 */
public class UIUtils {


    /**
     * 画出两端圆角的TextView
     * @param view  传入的TextView
     * @param color 背景颜色
     * @return  传入的TexiView
     *
     * 注意： 使用的时候有一个限制： 要求在EditText中的background和代码设置的color相同，否则会出现颜色不同的差异
     */
    public static TextView drawableRadiusTextView(TextView view, int color){
        int height = view.getHeight();
        if(height == 0){
            view.measure(0, 0);  // 测量宽高
            height = view.getMeasuredWidth();  // 当前获得的宽高不包括padding
        }
        int paddingTop = view.getPaddingTop();
        int paddingBottom = view.getPaddingBottom();
        int strokeWidth = 1;
        int roundRadius = (height + paddingTop + paddingBottom)/2; // 圆角的半径
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(roundRadius);
        int strokeColor = color; // Color.parseColor(color);
        int fillColor   = color; // Color.parseColor(color);
        gd.setColor(fillColor);
        gd.setStroke(strokeWidth, strokeColor);
        view.setBackgroundDrawable(gd);
        // view.setBackground(gd);

        return view;
    }


}
