package com.cdhxqh.travel_ticket_app.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.api.HttpManager;
import com.cdhxqh.travel_ticket_app.api.HttpRequestHandler;
import com.cdhxqh.travel_ticket_app.config.Constants;
import com.cdhxqh.travel_ticket_app.utils.MessageUtils;
import com.cdhxqh.travel_ticket_app.utils.TimeCountUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by hx on 2015/7/24.
 */
public class RegisterActivity extends BaseActivity {

    private static final String TAG="RegisterActivity";

    ImageView backImageviewId;
    ImageView titleSearchId;

    TextView titleText;
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
    TextView reg_hint_text;
    Button regMsgBtn;       // 下一步按钮
    ViewGroup regLayou3;    // 短信验证

    private ProgressDialog  progressDialog;




    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_register);
        findViewById();
        initView();
    }

    @Override 
    protected void findViewById() {

        this.backImageviewId =  (ImageView)findViewById(R.id.back_imageview_id);
        this.titleSearchId =    (ImageView)findViewById(R.id.title_search_id);

        this.titleText =        (TextView)findViewById(R.id.title_text_id);
        this.writePhone =       (TextView)findViewById(R.id.write_phone);
        this.reg_phone_text =  (EditText)findViewById(R.id.reg_phone_text);
        this.regPhoneNextBtn = (Button)findViewById(R.id.reg_phone_next_btn);
        this.regLayou1 =        (ViewGroup)findViewById(R.id.reg_layou_1);

        this.writeWwd =         (TextView)findViewById(R.id.write_pwd);
        this.reg_pwd_input =   (EditText)findViewById(R.id.reg_pwd_input);
        this.reg_repwd_input = (EditText)findViewById(R.id.reg_repwd_input);
        this.regPwdBtn =        (Button)findViewById(R.id.reg_pwd_btn);
        this.regLayou2 =        (ViewGroup)findViewById(R.id.reg_layou_2);

        this.writeSendmsg =  (TextView)findViewById(R.id.write_sendmsg);
        this.reg_msg_input = (EditText)findViewById(R.id.reg_msg_input);
        this.reg_hint_text = (TextView)findViewById(R.id.reg_hint_text);
        this.regSenmsgBtn =  (Button)findViewById(R.id.reg_senmsg_btn);
        this.regMsgBtn =      (Button)findViewById(R.id.reg_msg_btn);
        this.regLayou3 =      (ViewGroup)findViewById(R.id.reg_layou_3);
    }

    @Override
    protected void initView() {
        // 注册EditText点击事件
        regOnClick(reg_phone_text);
        regOnClick(reg_pwd_input);
        regOnClick(reg_repwd_input);
        regOnClick(reg_msg_input);

        titleText.setText("手机号注册");
        backImageviewId.setVisibility(View.VISIBLE);
        titleSearchId.setVisibility(View.GONE);

        // 填写手机号按钮事件
        regPhoneNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = RegisterActivity.this.reg_phone_text.getText().toString();
                boolean flag = RegisterActivity.this.isMobileNO(phone);
                if (flag) {
                    if (phone.length() == 11) {
                        showLayout(RegisterActivity.this.regLayou2); // 显示regLayou2
                        setTextViewBackground(writeWwd);              // 设置背景颜色
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
                    MessageUtils.showMiddleToast(RegisterActivity.this, msg);
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
                    showLayout(RegisterActivity.this.regLayou3); // 显示regLayou2
                    setTextViewBackground(writeSendmsg);         // 设置背景颜色
                }
            }
        });

        // 短信验证按钮事件
        regSenmsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;

                loadTokenCode(button);   // 获取验证码
            }
        });

        // 注册按钮
        regMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = ProgressDialog.show(RegisterActivity.this, null, getString(R.string.reg_loaing), true, true); // 显示进度条
                newUserRegister();     // 新注册用户
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
                } else
                if (View.VISIBLE == visible_layout2) {
                    showLayout(RegisterActivity.this.regLayou1); // 显示regLayou2
                    setTextViewBackground(writePhone);           // 设置背景颜色
                } else
                if (View.VISIBLE == visible_layout3) {
                    showLayout(RegisterActivity.this.regLayou2); // 显示regLayou2
                    setTextViewBackground(writeWwd);              // 设置背景颜色
                }

            }
        });
    }

    /**
    * 验证2次密码是否一致
     */
    private boolean vilidPwd(String pwd, String repwd){
        boolean flag = false;
        if(!"".equals(pwd) && pwd.equals(repwd)){
            flag = true;
        } else
        if("".equals(pwd) || "".equals(repwd)){
            MessageUtils.showMiddleToast(RegisterActivity.this, "密码不能为空");
        } else {
            MessageUtils.showMiddleToast(RegisterActivity.this, "两次密码不一致");
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

    /**
     * 隐藏：填写手机号、设置密码和短信验证Layout
     */
    public void showLayout(ViewGroup viewGroup){
        // 隐藏控件
        regLayou1.setVisibility(View.GONE);
        regLayou2.setVisibility(View.GONE);
        regLayou3.setVisibility(View.GONE);

        viewGroup.setVisibility(View.VISIBLE);
    }

    /**
     * 传入参数为需要更改Label
     * 更改填写手机号、设置密码和短信验证的TextView背景颜色
     */
    public void setTextViewBackground(TextView textView){
        writePhone.setTextColor(0xFF000000);
        writeWwd.setTextColor(0xFF000000);
        writeSendmsg.setTextColor(0xFF000000);
        textView.setTextColor(0xFFEEBA63);
    }

    public void regOnClick(EditText text){
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text = (EditText) v;
                text.setFocusable(true);
                text.setFocusableInTouchMode(true);
                text.requestFocus();
                text.setHint("");
            }
        });

    }

    /**
     * 注册新用户
     */
    private void newUserRegister(){
        String phone = reg_phone_text.getText().toString();  // 获取手机
        String pwd = reg_pwd_input.getText().toString();     // 获取密码
        Map<String, String> mapparams=new HashMap<String,String>(0);
        mapparams.put("mobilePhone",phone);                  // 手机验证方式
        mapparams.put("password", pwd);
        HttpManager.requestOnceWithURLString(Constants.REG_URL, this, mapparams, new HttpRequestHandler<String>() {
            @Override
            public void onSuccess(String data) {
                Log.i(TAG, "data=" + data);
                progressDialog.dismiss(); // 关闭进度条
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String errcode = (String) jsonObject.get("errcode");
                    if (null != errcode && errcode.length() > 0) {
                        if ("ZWTICKET-GLOBAE-S-14".equals(errcode)) {
                            Bundle bundle = new Bundle();
                            String phone = reg_phone_text.getText().toString();
                            bundle.putCharSequence("RegisterActivity", phone);
                            openActivity(LoginActivity.class, bundle);
                            finish();
                        } else {
                            MessageUtils.showMiddleToast(RegisterActivity.this, (String) jsonObject.get("errmsg"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(String data, int totalPages, int currentPage) {
                Log.i(TAG, "data=" + data);
                progressDialog.dismiss(); // 关闭进度条
            }

            @Override
            public void onFailure(String error) {
                progressDialog.dismiss();  // 关闭进度条
                if (null != error && error.length() > 0) {
                    MessageUtils.showErrorMessage(RegisterActivity.this, error);
                } else {
                    MessageUtils.showErrorMessage(RegisterActivity.this, "服务器未响应");
                }
            }
        });
    }

    /**
     * 验证用户输入的验证码是否正确
     */
    private void loadTokenCode(final Button button) {
        String verifyCode = reg_msg_input.getText().toString();   // 获取输入的验证码
        Map<String, String> mapparams = new HashMap<String, String>(0);
        mapparams.put("authstring", verifyCode);
        HttpManager.requestOnceWithURLString(Constants.REG_CODE_URL, this, mapparams, new HttpRequestHandler<String>() {
            @Override
            public void onSuccess(String data) {
                TimeCountUtil timeCountUtil = new TimeCountUtil(RegisterActivity.this, 60000, 1000, button, R.drawable.phone_test_on); // 更新按钮状态
                timeCountUtil.start(); // 启动线程
                Log.i(TAG, "data=" + data);
                String msg = "已发送短信验证码到";
                String phone = reg_phone_text.getText().toString();
                phone = phone.substring(0, 3) + "****" + phone.substring(7);
                msg = msg + phone;
                reg_hint_text.setText(msg);
            }

            @Override
            public void onSuccess(String data, int totalPages, int currentPage) {
                Log.i(TAG, "data=" + data);
            }

            @Override
            public void onFailure(String error) {
                if (null != error && error.length() > 0) {
                    MessageUtils.showErrorMessage(RegisterActivity.this, error);
                } else {
                    MessageUtils.showErrorMessage(RegisterActivity.this, "服务器未响应");
                }
            }
        });
    }

}
