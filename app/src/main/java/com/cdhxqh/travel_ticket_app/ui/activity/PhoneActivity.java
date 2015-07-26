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

import com.cdhxqh.travel_ticket_app.utils.HttpUtil;
import com.cdhxqh.travel_ticket_app.utils.TimeCountUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.cdhxqh.travel_ticket_app.utils.EditTextActionUtils;

import org.apache.http.Header;
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
    HttpUtil httpUtil;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) { // 接收消息
            progressDialog.dismiss();   // 关闭进度条
            String str = (String) msg.obj;
            try {
                JSONObject jsonObject = new JSONObject(str);
                String errcode = (String) jsonObject.get("errcode");
                if (null != errcode && errcode.length() > 0) {
                    if ("ZWTICKET-GLOBAE-S-14".equals(errcode)) {
                        Bundle bundle = new Bundle();
//                        String phone = reg_phone_text.getText().toString();
//                        bundle.putCharSequence("RegisterActivity",  phone);
                        openActivity(LoginActivity.class, bundle);
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), (String) jsonObject.get("errmsg"), Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        findViewById();
        initView();

        //跳转至邮箱找回界面事件
        TextViewMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneActivity.this.reg_layou_1.setVisibility(View.GONE);
                PhoneActivity.this.reg_layou_2.setVisibility(View.VISIBLE);
            }
        });


        //手机获取密码
        restart_passworld_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage(getResources().getString(R.string.phone_get_message));
                progressDialog.show();   // 显示进度条
                /*
                发送请求获取密码
                 */
                Log.i(TAG, "****4");
                url = "http://192.168.2.119:8080/qdm/ecsusers/doReset";
                String str = button_id.getText().toString(), style = "authstring";
                if (isMobileNO(str)) {
                    map = new HashMap<String, String>();
                    map.put(style, str);
                    httpUtil.post(url, map, handler);
                } else {
                    String msg = "手机号格式不正确";
                    if (str == null || "".equals(str)) {
                        msg = "请输入手机号";
                    }
                    progressDialog.dismiss();   // 关闭进度条
                    Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                /*
                返回值上一层
                 */
                Intent intent = new Intent();
                intent.setClass(PhoneActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //设置按下时按钮效果事件
        restart_passworld_id.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundResource(R.drawable.phone_button_get);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundResource(R.drawable.phone_button_login);
                }
                return false;
            }
        });

        //验证码按钮按下效果
        info_button_id.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundResource(R.drawable.phone_a_down);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundResource(R.drawable.phone_a_on);
                }
                return false;
            }
        });

        //获取验证码
        info_button_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("正在加载...");
                progressDialog.show();   // 显示进度条
                /*
                发送请求获取验证码
                 */
                TimeCountUtil timeCountUtil = new TimeCountUtil(PhoneActivity.this, 60000, 1000, info_button_id, R.drawable.phone_test_on);
                timeCountUtil.start();
                Log.i(TAG, "****4");
                url = "http://192.168.2.119:8080/qdm/ecsusers/reset";
                String style = "mobilePhone";
                String str = EditText1.getText().toString();
                if (isMobileNO(str)) {
                    map = new HashMap<String, String>();
                    map.put(style, str);
                    httpUtil.post(url, map, handler);
                } else {
                    String msg = "手机号格式不正确";
                    if (str == null || "".equals(str)) {
                        msg = "请输入手机号";
                    }
                    progressDialog.dismiss();   // 关闭进度条
                    Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

        //手机找回标签页
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneActivity.this.reg_layou_1.setVisibility(View.VISIBLE);
                PhoneActivity.this.reg_layou_2.setVisibility(View.GONE);
            }
        });

        //邮箱获取密码按钮效果
        restart_passworld_mail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundResource(R.drawable.button_on_mail);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundResource(R.drawable.button_login_mail);
                }
                return false;
            }
        });

        //根据邮箱号获取新密码
        restart_passworld_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("正在加载...");
                progressDialog.show();   // 显示进度条
                String url = "http://192.168.2.119:8080/qdm/ecsusers/reset";
                String str = EditTextMail.getText().toString(), style = "email";
                if (isMailNo(str)) {
                    map = new HashMap<String, String>();
                    map.put(style, str);
                    httpUtil.post(url, map, handler);
                } else {
                    String msg = "邮箱号格式不正确";
                    if (str == null || "".equals(str)) {
                        msg = "请输入邮箱号";
                    }
                    progressDialog.dismiss();   // 关闭进度条
                    Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
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

        backImageView = (ImageView) findViewById(R.id.back_imageview_id);
        titleTextView = (TextView) findViewById(R.id.title_text_id);
        seachImageView = (ImageView) findViewById(R.id.title_search_id);


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

    ;

    /**
     * 初始化控件
     */
    protected void initView() {

        backImageView.setVisibility(View.VISIBLE);
        seachImageView.setVisibility(View.GONE);
        titleTextView.setText(getResources().getString(R.string.find_pwd_text));

        if (this.progressDialog == null) {
            progressDialog = new ProgressDialog(PhoneActivity.this);
        }


        //返回至登录界面事件
        backImageView.setOnClickListener(backImageViewOnClickListener);


        backImageView.setOnTouchListener(backImageViewOnTouchListener);
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


}
