package com.cdhxqh.travel_ticket_app.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by hexian on 2015/8/4.
 */
public class CustomLinearLayout extends LinearLayout {

    public CustomLinearLayout(Context paramContext) {
        super(paramContext);
    }

    public CustomLinearLayout(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    public CustomLinearLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
    }

    public boolean onTouchEvent(MotionEvent paramMotionEvent) {
        View localView = getChildAt(2);
        if (localView != null) { // 阻止事件继续分发
            localView.dispatchTouchEvent(paramMotionEvent);
        }

        return true;
    }
}
