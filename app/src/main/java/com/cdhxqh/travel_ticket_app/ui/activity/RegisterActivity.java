package com.cdhxqh.travel_ticket_app.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import android.view.Gravity;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import android.widget.TextView;
import java.util.regex.Pattern;
import android.widget.ImageView;
import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.utils.HttpUtil;
import com.cdhxqh.travel_ticket_app.utils.TimeCountUtil;

import org.json.JSONObject;

/**
 * Created by hx on 2015/7/24.
 */
public class RegisterActivity extends BaseActivity {

    private static final String TAG="RegisterActivity";

    ImageView backImageviewId;

    TextView writePhone;
    TextView writeWwd;
    TextView writeSendmsg;

    //  --------------   填写手机号  ---------------------------
    EditText reg_phone_text;  // 手机号
    Button regPhoneNextBtn;  // 手机号 - 下一步按钮
    ViewGroup regLayou1;      // 填写手机号

    //  --------------   设置密码  ---------------------------
    EditText reg_pwd_input;   // 密码
    EditText reg_repwd_input; // 重复密码
    Button regPwdBtn;          // 密码  - 下一步按钮
    ViewGroup regLayou2;       // 设置密码

    //  --------------   短信验证  ---------------------------
    EditText reg_msg_input; // 验证码
    Button regSenmsgBtn;    // 验证码
    Button regMsgBtn;       // 下一步按钮
    ViewGroup regLayou3;    // 短信验证

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){   // 接收消息
            progressDialog.dismiss(); // 关闭进度条
            String str = (String)msg.obj;
            try {
                JSONObject jsonObject = new JSONObject(str);
                String errcode = (String)jsonObject.get("errcode");
                if(null != errcode && errcode.length() > 0){
                    if("ZWTICKET-GLOBAE-S-14".equals(errcode)){
                        Bundle bundle = new Bundle();
                        String phone = reg_phone_text.getText().toString();
                        bundle.putCharSequence("RegisterActivity",  phone);
                        openActivity(LoginActivity.class, bundle);
                    }else{
                        Toast toast = Toast.makeText(getApplicationContext(), (String)jsonObject.get("errmsg"), Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private ProgressDialog  progressDialog;




    @Override
    protected void onCreate(Bundle bundle) {
        setContentView(R.layout.activity_register);
        super.onCreate(bundle);
        findViewById();
        initView();
    }

    @Override 
    protected void findViewById() {

        this.backImageviewId =  (ImageView)findViewById(R.id.back_imageview_id);

        this.writePhone =        (TextView)findViewById(R.id.write_phone);
        this.reg_phone_text =     (EditText)findViewById(R.id.reg_phone_text);
        this.regPhoneNextBtn = (Button)findViewById(R.id.reg_phone_next_btn);
        this.regLayou1 =        (ViewGroup)findViewById(R.id.reg_layou_1);

        this.writeWwd =       (TextView)findViewById(R.id.write_pwd);
        this.reg_pwd_input =   (EditText)findViewById(R.id.reg_pwd_input);
        this.reg_repwd_input = (EditText)findViewById(R.id.reg_repwd_input);
        this.regPwdBtn =     (Button)findViewById(R.id.reg_pwd_btn);
        this.regLayou2 =     (ViewGroup)findViewById(R.id.reg_layou_2);

        this.writeSendmsg =  (TextView)findViewById(R.id.write_sendmsg);
        this.reg_msg_input =  (EditText)findViewById(R.id.reg_msg_input);
        this.regSenmsgBtn = (Button)findViewById(R.id.reg_senmsg_btn);
        this.regMsgBtn = (Button)findViewById(R.id.reg_msg_btn);
        this.regLayou3 =    (ViewGroup)findViewById(R.id.reg_layou_3);
    }

    public boolean vilidPwd(String pwd, String repwd){
        boolean flag = false;
        if(!"".equals(pwd) && pwd.equals(repwd)){
            flag = true;
        } else
        if("".equals(pwd) || "".equals(repwd)){
            Toast toast = Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "两次密码不一致", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

        return flag;
    }
    /**
     * 验证手机号格式
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles){
        mobiles = (mobiles==null? "" : mobiles);
        return Pattern.compile("^[1][3,4,5,8][0-9]{9}$").matcher(mobiles).matches();
    }


    @Override
    protected void initView() {
        // 注册EditText点击事件
        regOnClick(reg_phone_text);
        regOnClick(reg_pwd_input);
        regOnClick(reg_repwd_input);
        regOnClick(reg_msg_input);

        // 填写手机号按钮事件
        regPhoneNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = RegisterActivity.this.reg_phone_text.getText().toString();
                boolean flag = RegisterActivity.this.isMobileNO(phone);
                if (flag) {
                    if (phone.length() == 11) {
                        // 隐藏控件
                        invisibleAllLayout(); // 隐藏全部Layout, 下面根据实际情况显示控件
                        RegisterActivity.this.regLayou2.setVisibility(View.VISIBLE);

                        writePhone.setTextColor(0xFF000000);
                        writeWwd.setTextColor(0xFFEEBA63);
                        writeSendmsg.setTextColor(0xFF000000);
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "请输入11位的手机号", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                } else {
                    String msg = "手机号格式不正确";
                    if (phone.length() == 0) {
                        msg = "请输入手机号";
                    }
                    Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

        // 设置密码按钮事件
        regPwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = RegisterActivity.this.reg_pwd_input.getText().toString();
                String repwd = RegisterActivity.this.reg_repwd_input.getText().toString();
                boolean flag = RegisterActivity.this.vilidPwd(pwd, repwd);  // 教研2次密码是否一致
                if (flag) {
                    // 隐藏控件   View常亮介绍 ---> GONE：隐藏且释放空间, INVISIBLE: 隐藏但不释放空间， VISIBLE显示控件
                    RegisterActivity.this.regLayou1.setVisibility(View.GONE);
                    RegisterActivity.this.regLayou2.setVisibility(View.GONE);
                    RegisterActivity.this.regLayou3.setVisibility(View.VISIBLE);

                    writePhone.setTextColor(0xFF000000);
                    writeWwd.setTextColor(0xFF000000);
                    writeSendmsg.setTextColor(0xFFEEBA63);
                }
            }
        });

        // 短信验证按钮事件
        regSenmsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = ProgressDialog.show(RegisterActivity.this, null, getString(R.string.reg_loaing), true, true); // 显示进度条
                Button button = (Button) v;

                TimeCountUtil timeCountUtil = new TimeCountUtil(RegisterActivity.this, 60000, 1000, button, R.drawable.phone_test_on); // 更新按钮状态
                timeCountUtil.start(); // 启动线程

                HttpUtil util = new HttpUtil();
                String url = "http://10.0.2.21:8080/qdm/ecsusers/check";  // 请求服务端返回验证码
                String phone = reg_phone_text.getText().toString();  // 获取手机
                String pwd = reg_pwd_input.getText().toString();   // 获取密码
                Map param = new HashMap();
                param.put("mobilePhone", phone); // 手机验证方式
                param.put("password", pwd);
                util.post(url, param, handler);
            }
        });

        // 下一步按钮
        regMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = ProgressDialog.show(RegisterActivity.this, null, getString(R.string.reg_loaing), true, true); // 显示进度条
                String verifyCode = reg_msg_input.getText().toString();

                HttpUtil util = new HttpUtil();
                String url = "http://10.0.2.21:8080/qdm/ecsusers/doCheck";  // 请求服务端创建用户
                String msg = reg_msg_input.getText().toString();                  // 获取验证码

                Map<String, String> param = new HashMap<String, String>(0);
                param.put("authstring", msg);

                util.post(url, param, handler);
            }
        });

        // 退回按钮事件
        backImageviewId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visible_layout1 = regLayou1.getVisibility();
                int visible_layout2 = regLayou2.getVisibility();
                int visible_layout3 = regLayou3.getVisibility();
                if (RegisterActivity.this != null) {
                    // 隐藏软键盘
                    InputMethodManager inputmanger = (InputMethodManager) RegisterActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputmanger.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                if (View.VISIBLE == visible_layout1) {
                    finish();
                } else if (View.VISIBLE == visible_layout2) {
                    RegisterActivity.this.regLayou1.setVisibility(View.VISIBLE);
                    RegisterActivity.this.regLayou2.setVisibility(View.GONE);
                    RegisterActivity.this.regLayou3.setVisibility(View.GONE);
                    writePhone.setTextColor(0xFFEEBA63);
                    writeWwd.setTextColor(0xFF000000);
                    writeSendmsg.setTextColor(0xFF000000);
                } else if (View.VISIBLE == visible_layout3) {
                    writePhone.setTextColor(0xFF000000);
                    writeWwd.setTextColor(0xFFEEBA63);
                    writeSendmsg.setTextColor(0xFF000000);
                    RegisterActivity.this.regLayou1.setVisibility(View.GONE);
                    RegisterActivity.this.regLayou2.setVisibility(View.VISIBLE);
                    RegisterActivity.this.regLayou3.setVisibility(View.GONE);
                }

            }
        });
    }

    /**
     * 隐藏：填写手机号、设置密码和短信验证
     */
    public void invisibleAllLayout(){
        // 隐藏控件
        RegisterActivity.this.regLayou1.setVisibility(View.GONE);
        RegisterActivity.this.regLayou2.setVisibility(View.GONE);
        RegisterActivity.this.regLayou3.setVisibility(View.GONE);


    }

    public void regOnClick(EditText text){
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text = (EditText)v;
                text.setFocusable(true);
                text.setFocusableInTouchMode(true);
                text.requestFocus();
                text.setHint("");
            }
        });

    }
}
