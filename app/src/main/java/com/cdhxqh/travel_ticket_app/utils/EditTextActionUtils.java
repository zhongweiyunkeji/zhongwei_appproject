package com.cdhxqh.travel_ticket_app.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.cdhxqh.travel_ticket_app.ui.activity.BaseActivity;

/**
 * Created by hexian on 2015/7/25.
 */
public class EditTextActionUtils {

    /**
     * 注册获取和失去光标事件
     * @param textEdit
     */
    public static void regFocusChange(EditText textEdit, Activity activity){
        final Activity aty = activity;
        textEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            // 保存提示信息
            private String hintText = null;
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText textEdit = (EditText)v; // 获取出发事件的控件
                String text = textEdit.getText().toString();// 获取输入框内容
                if(hintText == null){
                    hintText = textEdit.getHint().toString();  // 获取原先提示框的值
                    if(hintText == null){
                        hintText = "";
                    }
                }

                if(hasFocus){ // 如果是获得光标事件
                    //if(text == null || "".equals(text)){
                    //    textEdit.setHint(hintText);   // 还原提示信息
                    //} else {
                        textEdit.setHint("");         // 提示信息置空
                    //}
                } else { // 失去光标事件
                    if(text==null || "".equals(text)){
                        textEdit.setHint(hintText);
                    }
                    /*
                    if(aty!=null && v != null){
                        // 隐藏软键盘
                        InputMethodManager inputmanger = (InputMethodManager)aty.getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputmanger.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }*/
                }
            }
        });
    }
}
