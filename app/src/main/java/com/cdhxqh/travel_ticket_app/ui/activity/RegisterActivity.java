package com.cdhxqh.travel_ticket_app.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import java.util.regex.Matcher;
import android.widget.TextView;
import java.util.regex.Pattern;
import android.widget.ImageView;
import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.utils.HttpUtil;
import com.cdhxqh.travel_ticket_app.utils.TimeCountUtil;
import com.cdhxqh.travel_ticket_app.utils.EditTextActionUtils;

import org.json.JSONObject;

/**
 * Created by hx on 2015/7/24.
 */
public class RegisterActivity extends BaseActivity {

    ImageView reg_top_left_icon;

    TextView write_phone;
    TextView write_pwd;
    TextView write_sendmsg;

    //  --------------   填写手机号  ---------------------------
    EditText reg_phone_text;     // 手机号
    Button reg_phone_next_btn;  // 手机号 - 下一步按钮
    ViewGroup reg_layou_1;       // 填写手机号

    //  --------------   设置密码  ---------------------------
    EditText reg_pwd_input;     // 密码
    EditText reg_repwd_input;   // 重复密码
    Button reg_pwd_btn;         // 密码  - 下一步按钮
    ViewGroup reg_layou_2;       // 设置密码

    //  --------------   短信验证  ---------------------------
    EditText reg_msg_input;    // 验证码
    Button   reg_senmsg_btn;   // 验证码
    Button reg_msg_btn;        // 下一步按钮
    ViewGroup reg_layou_3;     // 短信验证

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){ // 接收消息
            progressDialog.dismiss();   // 关闭进度条
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
        super.onCreate(bundle);
        setContentView(R.layout.activity_register);
        initView();

        // 注册光标变化事件
        //EditTextActionUtils.regFocusChange(reg_phone_text, this);
        //EditTextActionUtils.regFocusChange(reg_pwd_input, this);
        //EditTextActionUtils.regFocusChange(reg_repwd_input, this);
        //EditTextActionUtils.regFocusChange(reg_msg_input, this);

        // 填写手机号按钮事件
        reg_phone_next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = RegisterActivity.this.reg_phone_text.getText().toString();
                boolean flag = RegisterActivity.this.isMobileNO(phone);
                if(flag){
                    if(phone.length() ==11){
                        // 隐藏控件
                        RegisterActivity.this.reg_layou_1.setVisibility(View.GONE);
                        RegisterActivity.this.reg_layou_2.setVisibility(View.VISIBLE);
                        RegisterActivity.this.reg_layou_3.setVisibility(View.GONE);

                        write_phone.setTextColor(0xFF000000);
                        write_pwd.setTextColor(0xFFEEBA63);
                        write_sendmsg.setTextColor(0xFF000000);
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(),  "请输入11位的手机号", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                } else {
                    String msg = "手机号格式不正确";
                    if(phone.length() == 0){
                        msg = "请输入手机号";
                    }
                    Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

        // 设置密码按钮事件
        reg_pwd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd =   RegisterActivity.this.reg_pwd_input.getText().toString();
                String repwd = RegisterActivity.this.reg_repwd_input.getText().toString();
                boolean flag = RegisterActivity.this.vilidPwd(pwd, repwd);  // 教研2次密码是否一致
                if(flag){
                    // 隐藏控件   View常亮介绍 ---> GONE：隐藏且释放空间, INVISIBLE: 隐藏但不释放空间， VISIBLE显示控件
                    RegisterActivity.this.reg_layou_1.setVisibility(View.GONE);
                    RegisterActivity.this.reg_layou_2.setVisibility(View.GONE);
                    RegisterActivity.this.reg_layou_3.setVisibility(View.VISIBLE);

                    write_phone.setTextColor(0xFF000000);
                    write_pwd.setTextColor(0xFF000000);
                    write_sendmsg.setTextColor(0xFFEEBA63);
                }
            }
        });

        // 短信验证按钮事件
        reg_senmsg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("正在加载...");
                progressDialog.show();
                Button button = (Button) v;

                TimeCountUtil timeCountUtil = new TimeCountUtil(RegisterActivity.this, 60000, 1000, button,  R.drawable.phone_test_on); // 更新按钮状态
                timeCountUtil.start(); // 启动线程

                HttpUtil util = new HttpUtil();
                String url = "http://10.0.2.21:8080/qdm/ecsusers/check";  // 请求服务端返回验证码
                String phone = reg_phone_text.getText().toString();  // 获取手机
                String pwd   = reg_pwd_input.getText().toString();   // 获取密码
                Map param=new HashMap();
                param.put("mobilePhone", phone); // 手机验证方式
                param.put("password", pwd);
                util.post(url, param, handler);
            }
        });

        // 下一步按钮
        reg_msg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("正在加载...");
                progressDialog.show();   // 显示进度条
                String verifyCode = reg_msg_input.getText().toString();

                HttpUtil util = new HttpUtil();
                String url = "http://10.0.2.21:8080/qdm/ecsusers/doCheck";  // 请求服务端创建用户
                String msg   = reg_msg_input.getText().toString();                  // 获取验证码

                Map<String, String> param = new HashMap<String, String>(0);
                param.put("authstring", msg);

                util.post(url, param, handler);
            }
        });

        // 退回按钮事件
        reg_top_left_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visible_layout1 = reg_layou_1.getVisibility();
                int visible_layout2 = reg_layou_2.getVisibility();
                int visible_layout3 = reg_layou_3.getVisibility();
                if(RegisterActivity.this!=null){
                    // 隐藏软键盘
                    InputMethodManager inputmanger = (InputMethodManager)RegisterActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputmanger.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                if(View.VISIBLE == visible_layout1){
                    openActivity(LoginActivity.class);  // 跳转到登录Activity
                } else
                if(View.VISIBLE == visible_layout2){
                    RegisterActivity.this.reg_layou_1.setVisibility(View.VISIBLE);
                    RegisterActivity.this.reg_layou_2.setVisibility(View.GONE);
                    RegisterActivity.this.reg_layou_3.setVisibility(View.GONE);
                    write_phone.setTextColor(0xFFEEBA63);
                    write_pwd.setTextColor(0xFF000000);
                    write_sendmsg.setTextColor(0xFF000000);
                } else
                if(View.VISIBLE == visible_layout3){
                    write_phone.setTextColor(0xFF000000);
                    write_pwd.setTextColor(0xFFEEBA63);
                    write_sendmsg.setTextColor(0xFF000000);
                    RegisterActivity.this.reg_layou_1.setVisibility(View.GONE);
                    RegisterActivity.this.reg_layou_2.setVisibility(View.VISIBLE);
                    RegisterActivity.this.reg_layou_3.setVisibility(View.GONE);
                }

            }
        });
    }

    @Override
    protected void findViewById() {

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

        if(this.progressDialog == null){
            progressDialog = new ProgressDialog(RegisterActivity.this);
        }

        this.reg_top_left_icon =  (ImageView)findViewById(R.id.reg_top_back_icon);

        this.write_phone =           (TextView)findViewById(R.id.write_phone);
        this.reg_phone_text =      (EditText)findViewById(R.id.reg_phone_text);
        this.reg_phone_next_btn = (Button)findViewById(R.id.reg_phone_next_btn);
        this.reg_layou_1 =         (ViewGroup)findViewById(R.id.reg_layou_1);

        this.write_pwd =       (TextView)findViewById(R.id.write_pwd);
        this.reg_pwd_input =    (EditText)findViewById(R.id.reg_pwd_input);
        this.reg_repwd_input = (EditText)findViewById(R.id.reg_repwd_input);
        this.reg_pwd_btn =     (Button)findViewById(R.id.reg_pwd_btn);
        this.reg_layou_2 =     (ViewGroup)findViewById(R.id.reg_layou_2);

        this.write_sendmsg =     (TextView)findViewById(R.id.write_sendmsg);
        this.reg_msg_input =  (EditText)findViewById(R.id.reg_msg_input);
        this.reg_senmsg_btn = (Button)findViewById(R.id.reg_senmsg_btn);
        this.reg_msg_btn     = (Button)findViewById(R.id.reg_msg_btn);
        this.reg_layou_3 =    (ViewGroup)findViewById(R.id.reg_layou_3);

    }
}
