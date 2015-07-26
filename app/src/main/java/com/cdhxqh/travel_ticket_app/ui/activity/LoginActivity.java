package com.cdhxqh.travel_ticket_app.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
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

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.utils.HttpUtil;

import org.json.JSONObject;

import java.util.HashMap;


public class LoginActivity extends BaseActivity {
    private static final String TAG="LoginActivity1";

    private TextView TextViewPassWord;      //忘记密码
    private LinearLayout LinearLayout_id;   //注册用户
    private EditText editText2;              //用户名框
    private EditText editText3;              //密码框
    private Button button_id1;               //登录按钮
    private ProgressDialog progressDialog;
    private HashMap<String, String> map ;    //传入访问参数及值
    HttpUtil httpUtil;

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
//                        String phone = reg_phone_text.getText().toString();
//                        bundle.putCharSequence("RegisterActivity",  phone);
                        openActivity(LoginActivity.class, bundle);
                    }else{
                        Toast toast = Toast.makeText(getApplicationContext(), (String)jsonObject.get("errmsg"), Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        if("ZWTICKET-USER-S-101".equals(errcode)) {
                            Intent intent = new Intent();
                            intent.setClass(LoginActivity.this, UserActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById();
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 绑定控件id
     */
    protected  void findViewById(){
        TextViewPassWord = (TextView) findViewById(R.id.TextViewPassWord);
        LinearLayout_id = (LinearLayout) findViewById(R.id.LinearLayout_id);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        button_id1 = (Button)findViewById(R.id.button_id1);
    };

    /**
     * 初始化控件
     */
    protected  void initView(){
        if(this.progressDialog == null){
            progressDialog = new ProgressDialog(LoginActivity.this);
        }

        Bundle bundle = this.getIntent().getExtras();
        if(bundle!=null){
            CharSequence str = bundle.getCharSequence("RegisterActivity");
            if(str!=null){
                ((EditText)findViewById(R.id.editText2)).setText(str);
            }
        }

        /*
        忘记密码
         */
        TextViewPassWord.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, PhoneActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout_id.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        /*
        登录系统
         */
        button_id1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String url = "http://172.25.124.1:8080/qdm/ecsusers/login";
                String username = editText2.getText().toString(), styleName = "loginName";
                String passwoord = editText3.getText().toString(), stylePass = "password";
                if(username == null || "".equals(username)) {
                    String msg = getResources().getString(R.string.username_get_err);

                    Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else if(passwoord == null || "".equals(passwoord)) {
                    String msg = getResources().getString(R.string.password_get_err);

                    Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else {
                    progressDialog.setMessage(getResources().getString(R.string.phone_get_message));
                    progressDialog.show();   // 显示进度条
                    map = new HashMap<String, String>();
                    map.put(styleName, username);
                    map.put(stylePass, passwoord);
                    httpUtil.post(url, map, handler);
                }
            }
        });
    };
}
