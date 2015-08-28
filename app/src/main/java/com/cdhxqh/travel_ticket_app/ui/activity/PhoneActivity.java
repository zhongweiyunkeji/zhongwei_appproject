package com.cdhxqh.travel_ticket_app.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cdhxqh.travel_ticket_app.api.HttpManager;
import com.cdhxqh.travel_ticket_app.api.HttpRequestHandler;
import com.cdhxqh.travel_ticket_app.utils.HttpUtil;
import com.cdhxqh.travel_ticket_app.utils.MessageUtils;
import com.cdhxqh.travel_ticket_app.utils.TimeCountUtil;
import org.json.JSONObject;

import com.cdhxqh.travel_ticket_app.R;

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * 手机找回主界面
 */
public class PhoneActivity extends BaseActivity {

    /**
     * 返回按钮*
     */
    private ImageView backImageView;

    /**
     * 标题*
     */
    private TextView titleTextView;


    /**
     * 搜索*
     */
    private ImageView seachImageView;



    //邮箱验证标签页
    private TextView TextViewMail;


    /**
     * 手机组件
     */
    private TextView button_id;             //短信验证码框




    private Button restart_passworld_id;  //获取密码按钮（手机找回）
    private Button info_button_id;         //获取验证码按钮
    private LinearLayout reg_layou_1;      //手机找回
    private LinearLayout reg_layou_2;      //邮箱找回
    private EditText EditText1;            //手机注册号
    /**
     * 邮箱组件
     */
    private TextView imageButton2;         //手机验证标签页
    private EditText EditTextMail;            //邮箱验证框
    private Button restart_passworld_mail;  //密码框
    /**
     * 通用组件
     */
    private static final String TAG = "ImageButton";  //提示框
    private static String url = null;                  //访问地址
    private HashMap<String, String> map;    //传入访问参数及值
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        findViewById();
        initView();
    }

    /*
    手机验证
     */
    public boolean isMobileNO(String mobiles) {
        mobiles = (mobiles == null ? "" : mobiles);
        return Pattern.compile("^[1][3,4,5,8][0-9]{9}$").matcher(mobiles).matches();
    }

    /*
    邮箱验证
     */
    public boolean isMailNo(String mail) {
        mail = (mail == null ? "" : mail);
        return Pattern.compile("^[a-z0-9]+([._\\\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$").matcher(mail).matches();
    }

    /**
     * 绑定控件id
     */
    protected void findViewById() {
        /**
         * 标题标签相关id
         */
        backImageView = (ImageView) findViewById(R.id.back_imageview_id);
        titleTextView = (TextView) findViewById(R.id.title_text_id);
        seachImageView = (ImageView) findViewById(R.id.title_search_id);

        /**
         * 找回方式标签idk222
         */
        reg_layou_1 = (LinearLayout) findViewById(R.id.reg_layou_1);
        reg_layou_2 = (LinearLayout) findViewById(R.id.reg_layou_2);




        TextViewMail = (TextView) findViewById(R.id.TextViewMail);
        restart_passworld_id = (Button) findViewById(R.id.restart_passworld_id);
        info_button_id = (Button) findViewById(R.id.info_button_id);
        EditText1 = (EditText) findViewById(R.id.EditText1);
        imageButton2 = (TextView) findViewById(R.id.ImageButton2);
        restart_passworld_mail = (Button) findViewById(R.id.restart_passworld_mail);
        EditTextMail = (EditText) findViewById(R.id.EditTextMail);
        button_id = (EditText) findViewById(R.id.button_id);
    }



    /**
     * 初始化控件
     */
    protected void initView() {
        //设置标签页显示方式
        backImageView.setVisibility(View.VISIBLE);
        seachImageView.setVisibility(View.GONE);
        titleTextView.setText(getResources().getString(R.string.find_pwd_text));
        //返回至登录界面事件
        backImageView.setOnClickListener(backImageViewOnClickListener);

        //
        backImageView.setOnTouchListener(backImageViewOnTouchListener);

        //跳转至邮箱找回界面事件
        TextViewMail.setOnClickListener(backImageMailOnTouchListener);

        //设置密码按钮（手机找回）按下时按钮效果事件
        restart_passworld_id.setOnTouchListener(passwordMailOnTouchListener);

        //验证码按钮按下效果
        info_button_id.setOnTouchListener(inspectCodeOnTouchListener);

        //获取验证码
        info_button_id.setOnClickListener(inspectCodeOnClickListener);

        //跳转至手机找回界面事件
        imageButton2.setOnClickListener(backImagephoneClickListener);

        //跳转至手机找回界面事件
        restart_passworld_mail.setOnTouchListener(getMailPassOnTouchListener);

        //根据邮箱号获取新密码
        restart_passworld_mail.setOnClickListener(getMailPassClickListener);

        //根据手机号获取新密码
        restart_passworld_id.setOnClickListener(getPhonePassClickListener);
    }

    /**
     * 返回事件的监听*
     */
    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private View.OnTouchListener backImageViewOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.setBackgroundColor(getResources().getColor(R.color.write));
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                v.setBackgroundColor(getResources().getColor(R.color.clarity));
            }
            return false;
        }
    };

    //跳转至邮箱找回界面事件
    private View.OnClickListener backImageMailOnTouchListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PhoneActivity.this.reg_layou_1.setVisibility(View.GONE);
            PhoneActivity.this.reg_layou_2.setVisibility(View.VISIBLE);
        }
    };

    //设置密码按钮（手机找回）按下时按钮效果事件
    private View.OnTouchListener passwordMailOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.setBackgroundResource(R.drawable.phone_button_get);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                v.setBackgroundResource(R.drawable.phone_button_login);
            }
            return false;
        }
    };

    //验证码按钮按下效果
    private View.OnTouchListener inspectCodeOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.setBackgroundResource(R.drawable.phone_a_down);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                v.setBackgroundResource(R.drawable.phone_a_on);
            }
            return false;
        }
    };

    //获取验证码
    private View.OnClickListener inspectCodeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                /*
                发送请求获取验证码
                 */
            String str = EditText1.getText().toString();
            if (str == null || "".equals(str)) {
                EditText1.setError(getString(R.string.please_in_phone_number_text));
                EditText1.requestFocus();
            }else  if (!isMobileNO(str)) {
                EditText1.setError(getString(R.string.phone_get_err));
                EditText1.requestFocus();
            }else {
                getPhoneCode ();
            }
        }
    };

    //手机找回标签页
    private View.OnClickListener backImagephoneClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PhoneActivity.this.reg_layou_1.setVisibility(View.VISIBLE);
            PhoneActivity.this.reg_layou_2.setVisibility(View.GONE);
        }
    };

    //邮箱获取密码按钮效果
    private View.OnTouchListener getMailPassOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.setBackgroundResource(R.drawable.button_on_mail);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                v.setBackgroundResource(R.drawable.button_login_mail);
            }
            return false;
        }
    };

    //根据邮箱号获取新密码
    private View.OnClickListener getMailPassClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String str = EditTextMail.getText().toString();
            if (str == null || "".equals(str)) {
                EditTextMail.setError(getString(R.string.email_code_null));
                EditTextMail.requestFocus();
            } else if(!isMailNo(str)) {
                EditTextMail.setError(getString(R.string.email_style_err));
                EditTextMail.requestFocus();
            } else {
                getMailPassWord();
            }
        }
    };


    /**
     * 根据邮箱号获取密码
     */
    private void getMailPassWord () {
        /**
         * 加载中
         */
        progressDialog = ProgressDialog.show(PhoneActivity.this, null,
                getString(R.string.loading), true, true);

        HttpManager.getMailPassword(this,
                EditTextMail.getText().toString(),
                new HttpRequestHandler<Integer>() {
                    @Override
                    public void onSuccess(Integer data) {
                        MessageUtils.showMiddleToast(PhoneActivity.this, "邮箱发送成功");
                        progressDialog.dismiss();
                        finish();
                    }

                    @Override
                    public void onSuccess(Integer data, int totalPages, int currentPage) {
                        Log.i(TAG, "22222");
                    }

                    @Override
                    public void onFailure(String error) {
                        MessageUtils.showErrorMessage(PhoneActivity.this, error);
                        progressDialog.dismiss();
                    }
                });
    }

    /**
     * 根据手机号获取验证码
     */
    private void getPhoneCode () {
        /**
         * 加载中
         */
        progressDialog = ProgressDialog.show(PhoneActivity.this, null,
                getString(R.string.loading), true, true);

        HttpManager.getPhoneCode(this,
                EditText1.getText().toString(),
                new HttpRequestHandler<Integer>() {
                    @Override
                    public void onSuccess(Integer data) {
                        MessageUtils.showMiddleToast(PhoneActivity.this, "验证码发送成功");
                        progressDialog.dismiss();
                        TimeCountUtil timeCountUtil = new TimeCountUtil(PhoneActivity.this, 60000, 1000, info_button_id, R.drawable.phone_test_on);
                        timeCountUtil.start();
                    }

                    @Override
                    public void onSuccess(Integer data, int totalPages, int currentPage) {
                        Log.i(TAG, "22222");
                    }

                    @Override
                    public void onFailure(String error) {
                        MessageUtils.showErrorMessage(PhoneActivity.this, error);
                        progressDialog.dismiss();
                    }
                });
    }

    //手机获取密码
    private View.OnClickListener getPhonePassClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                /*
                发送请求获取密码
                 */
            getPhonePass ();
        }
    };

    /**
     * 根据手机号重置密码
     */
    private void getPhonePass () {
        /**
         * 加载中
         */
        progressDialog = ProgressDialog.show(PhoneActivity.this, null,
                getString(R.string.loading), true, true);

        HttpManager.getPhonePass(this,
                button_id.getText().toString(),
                new HttpRequestHandler<Integer>() {
                    @Override
                    public void onSuccess(Integer data) {
                        MessageUtils.showMiddleToast(PhoneActivity.this, "密码重置成功");
                        progressDialog.dismiss();
                        finish();
                    }

                    @Override
                    public void onSuccess(Integer data, int totalPages, int currentPage) {
                        Log.i(TAG, "22222");
                    }

                    @Override
                    public void onFailure(String error) {
                        MessageUtils.showErrorMessage(PhoneActivity.this, error);
                        progressDialog.dismiss();
                    }
                });
    }
}
