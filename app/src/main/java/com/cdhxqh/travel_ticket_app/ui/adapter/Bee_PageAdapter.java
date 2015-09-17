package com.cdhxqh.travel_ticket_app.ui.adapter;


import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class Bee_PageAdapter extends PagerAdapter
{
    public List<View> mListViews;

    public Bee_PageAdapter(List<View> mListViews)
    {
        this.mListViews = mListViews;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        int index = position%mListViews.size();
        container.removeView(mListViews.get(index));//删除页卡
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {	//这个方法用来实例化页卡
        int index = position%mListViews.size();
        View view = mListViews.get(index);
        removeParent(view);
        container.addView(view);//添加页卡
        return view;
    }

    @Override
    public int getCount() {
        return  Integer.MAX_VALUE;//返回页卡的数量
        // return  mListViews.size();//返回页卡的数量
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0==arg1;//官方提示这样写
    }

    public int getRealCount(){
        return  mListViews.size();//返回页卡的数量
    }

    private void removeParent(View view){
        ViewGroup parent = (ViewGroup)view.getParent();
        if(parent!=null){
            parent.removeView(view);
        }
    }

}
