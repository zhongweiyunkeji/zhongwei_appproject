package com.cdhxqh.travel_ticket_app.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.cdhxqh.travel_ticket_app.app.SwitchButton.OnChangeListener;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.api.HttpManager;
import com.cdhxqh.travel_ticket_app.api.HttpRequestHandler;
import com.cdhxqh.travel_ticket_app.app.SwitchButton;
import com.cdhxqh.travel_ticket_app.config.Constants;
import com.cdhxqh.travel_ticket_app.utils.HttpUtil;
import com.cdhxqh.travel_ticket_app.utils.MessageUtils;

/**登录界面**/
import org.json.JSONObject;

import java.util.HashMap;


public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity3";
    SharedPreferences.Editor editor;


    /**
     * 用户名*
     */
    private EditText userEditText;


    /**
     * 密码*
     */
    private EditText pwdEditText;


    /**
     * 登录*
     */
    private Button loginBtn;

    /**
     * 忘记密码*
     */
    private TextView forgetPwd;

    /**
     * 注册新用户*
     */
    private LinearLayout linearLayout_id;


    private ProgressDialog progressDialog;

    private SwitchButton sb;
    /**
     * 管理员或用户
     */
    private boolean type = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById();
        initView();

        SwitchButton sb = (SwitchButton) findViewById(R.id.wiperSwitch1);
        sb.setOnChangeListener(new OnChangeListener() {

            @Override
            public void onChange(SwitchButton sb, boolean state) {
                // TODO Auto-generated method stub
                Log.d("switchButton", state ? "验票员" : "用户");
                type = state;
            }
        });
        editor = myshared.edit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 绑定控件id
     */
    protected void findViewById() {


        userEditText = (EditText) findViewById(R.id.user_edittext_id);
        pwdEditText = (EditText) findViewById(R.id.passworld_edittext_id);
        loginBtn = (Button) findViewById(R.id.login_btn_id);
        forgetPwd = (TextView) findViewById(R.id.TextViewPassWord);
        linearLayout_id = (LinearLayout) findViewById(R.id.linearLayout_id);
    }

    ;

    /**
     * 初始化控件
     */
    protected void initView() {
        loginBtn.setOnClickListener(loginBtnOnClickListener);


        forgetPwd.setOnClickListener(forgetPwdOnClickListener);
        linearLayout_id.setOnClickListener(linearLayout_idOnClickListener);
        if(mIsLogin){
            userEditText.setText(ec_user.userName);
        }

    }

    private View.OnClickListener loginBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (userEditText.getText().length() == 0) {
                userEditText.setError(getString(R.string.login_error_empty_user));
                userEditText.requestFocus();
            } else if (pwdEditText.getText().length() == 0) {
                pwdEditText.setError(getString(R.string.login_error_empty_passwd));
                pwdEditText.requestFocus();
            } else {
                login();
            }
        }
    };


    private View.OnClickListener forgetPwdOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, PhoneActivity.class);
            startActivityForResult(intent, 0);
        }
    };


    private View.OnClickListener linearLayout_idOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, RegisterActivity.class);
            startActivityForResult(intent, 0);
        }
    };


    /**
     * 登录方法*
     */
    private void login() {
        progressDialog = ProgressDialog.show(LoginActivity.this, null,
                getString(R.string.login_loging), true, true);

        if (!type) {
            HttpManager.loginWithUsername(this,
                    userEditText.getText().toString(),
                    pwdEditText.getText().toString(),
                    new HttpRequestHandler<String>() {
                        @Override
                        public void onSuccess(String data) {
                            userType = true;
                            editor.putString(Constants.SESSIONIDTRUE,  data);
                            editor.commit();

                            MessageUtils.showMiddleToast(LoginActivity.this, "登陆成功");
                            progressDialog.dismiss();
                            setResult(Constants.STATUS_CODE_1000);
                            finish();
                        }

                        @Override
                        public void onSuccess(String data, int totalPages, int currentPage) {
                            Log.i(TAG, "22222");
                        }

                        @Override
                        public void onFailure(String error) {
                            MessageUtils.showMiddleToast(LoginActivity.this, error);
                            progressDialog.dismiss();
                        }
                    });
        } else {
            HttpManager.loginWithTicketCollector(this,
                    userEditText.getText().toString(),
                    pwdEditText.getText().toString(),
                    new HttpRequestHandler<Integer>() {
                        @Override
                        public void onSuccess(Integer data) {

                            userType = false;

                            MessageUtils.showMiddleToast(LoginActivity.this, "登陆成功");
                            progressDialog.dismiss();
                            setResult(Constants.STATUS_CODE_1000);
                            finish();
                        }

                        @Override
                        public void onSuccess(Integer data, int totalPages, int currentPage) {
                            Log.i(TAG, "22222");
                        }

                        @Override
                        public void onFailure(String error) {
                            MessageUtils.showMiddleToast(LoginActivity.this, error);
                            progressDialog.dismiss();
                        }
                    });
        }
    }


}
