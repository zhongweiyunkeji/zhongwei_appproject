package com.cdhxqh.travel_ticket_app.ui.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;

public class XEditText extends EditText  {

    public static final int DRAWABLE_BOTTOM = 3;
    public static final int DRAWABLE_LEFT = 0;
    public static final int DRAWABLE_RIGHT = 2;
    public static final int DRAWABLE_TOP = 1;
    public static final String TAG = XEditText.class.getSimpleName();
    private XEditText.DrawableBottomListener mBottomListener;
    private XEditText.DrawableLeftListener mLeftListener;
    private XEditText.DrawableRightListener mRightListener;
    private XEditText.DrawableTopListener mTopListener;

    public XEditText(Context paramContext)
    {
        super(paramContext);
    }

    public XEditText(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
    }

    public XEditText(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
        super(paramContext, paramAttributeSet, paramInt);
    }

    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
        switch (paramMotionEvent.getAction())
        {
            case MotionEvent.ACTION_UP : {
                if (this.mRightListener != null) {
                    Drawable localDrawable = getCompoundDrawables()[2];
                    if ((localDrawable != null) && (paramMotionEvent.getRawX() >= getRight() - localDrawable.getBounds().width())) {
                        this.mRightListener.onDrawableRightClick(this);
                        return true;
                    }
                }

                if (this.mLeftListener != null) {
                    Drawable localDrawable = getCompoundDrawables()[0];
                    if ((localDrawable != null) && (paramMotionEvent.getRawX() <= getLeft() + localDrawable.getBounds().width())) {
                        this.mLeftListener.onDrawableLeftClick(this);
                        return true;
                    }
                }

                if (this.mTopListener != null) {
                    Drawable localDrawable = getCompoundDrawables()[1];
                    if ((localDrawable != null) && (paramMotionEvent.getRawY() <= getTop() + localDrawable.getBounds().height())) {
                        this.mTopListener.onDrawableTopClick(this);
                        return true;
                    }
                }

                if(this.mBottomListener != null) {
                    Drawable localDrawable = getCompoundDrawables()[3];
                    if ((localDrawable != null) && (paramMotionEvent.getRawY() < getBottom() - localDrawable.getBounds().height())) {
                        this.mBottomListener.onDrawableBottomClick(this);
                        return true;
                    }
                }
            }

        }

        return super.onTouchEvent(paramMotionEvent);
    }

    public void setDrawableBottomListener(XEditText.DrawableBottomListener paramDrawableBottomListener)
    {
        this.mBottomListener = paramDrawableBottomListener;
    }

    public void setDrawableLeftListener(XEditText.DrawableLeftListener paramDrawableLeftListener)
    {
        this.mLeftListener = paramDrawableLeftListener;
    }

    public void setDrawableRightListener(XEditText.DrawableRightListener paramDrawableRightListener)
    {
        this.mRightListener = paramDrawableRightListener;
    }

    public void setDrawableTopListener(XEditText.DrawableTopListener paramDrawableTopListener)
    {
        this.mTopListener = paramDrawableTopListener;
    }




    public static interface DrawableLeftListener
    {
        public abstract void onDrawableLeftClick(EditText paramEditText);
    }

    public static interface DrawableRightListener
    {
        public abstract void onDrawableRightClick(EditText paramEditText);
    }

    public static interface DrawableTopListener
    {
        public abstract void onDrawableTopClick(EditText paramEditText);
    }

    public static interface DrawableBottomListener {
        public abstract void onDrawableBottomClick(EditText paramEditText);
    }



}
