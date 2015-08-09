package com.cdhxqh.travel_ticket_app.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cdhxqh.travel_ticket_app.api.HttpManager;
import com.cdhxqh.travel_ticket_app.api.HttpRequestHandler;
import com.cdhxqh.travel_ticket_app.config.Constants;
import com.cdhxqh.travel_ticket_app.utils.MessageUtils;
import com.google.zxing.WriterException;
import com.cdhxqh.travel_ticket_app.zxing.encoding.EncodingHandler;
import com.cdhxqh.travel_ticket_app.zxing.activity.CaptureActivity;
import com.cdhxqh.travel_ticket_app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hexian on 2015/8/6.
 */
public class TicketCheckActivity extends BaseActivity {

    private final static int SCANNIN_GREQUEST_CODE = 1;

    ImageView backImg;
    ImageView searchImg;
    TextView checkCode; // 电子票输入框
    Button validBtn;  // 验证按钮
    Button checkBtn;  // 验证电子码按钮
    Button rtnButton; // ;返回按钮
    LinearLayout elecLayout;  // 电子验票显示区域
    TextView title;     // 抬头
    TextView elecTextSelcect; // 电子票选项卡
    TextView qrcodeSelect;    // 二维码选项卡
    LinearLayout chkTktSuccess;
    LinearLayout chkTktFail;
    TextView failMsgTextView;   // 验码失败提示消息

    private ProgressDialog progressDialog;

    // boolean isEleClick;  // 记录是否是验票按钮返回点击的事件


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_check);

        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        title = (TextView)findViewById(R.id.title_text_id);

        backImg =   (ImageView)findViewById(R.id.back_imageview_id);
        searchImg = (ImageView)findViewById(R.id.title_search_id);
        checkCode = (TextView)findViewById(R.id.electronic_ticket_text);
        validBtn =  (Button)findViewById(R.id.ticket_check_btn);
        checkBtn =  (Button)findViewById(R.id.check_ticket_valid_btn);
        rtnButton = (Button)findViewById(R.id.check_ticket_fail_rtn_btn);

        elecLayout = (LinearLayout)findViewById(R.id.electronic_layout);

        elecTextSelcect = (TextView)findViewById(R.id.electronic_text);
        qrcodeSelect = (TextView)findViewById(R.id.electronic_qrcode);

        chkTktSuccess = (LinearLayout)findViewById(R.id.check_ticket_success_ele);
        chkTktFail = (LinearLayout)findViewById(R.id.check_ticket_fail_ele);
        failMsgTextView = (TextView)findViewById(R.id.check_ticket_fail_msg);

    }

    @Override
    protected void initView() {
        title.setText("验票");
        backImg.setVisibility(View.VISIBLE);
        searchImg.setVisibility(View.GONE); // 隐藏搜索按钮
        chkTktSuccess.setVisibility(View.GONE);
        chkTktFail.setVisibility(View.GONE);

        backImg.setOnTouchListener(backImageViewOnTouchListener);

        elecTextSelcect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elecLayout.setVisibility(View.VISIBLE);
            }
        });

        qrcodeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开扫描界面扫描条形码或二维码
                Intent openCameraIntent = new Intent(TicketCheckActivity.this,CaptureActivity.class);
                startActivityForResult(openCameraIntent, 0);
            }
        });

        // 注册验票按钮事件
        validBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = checkCode.getText().toString();
                if(code==null || "".equals(code.trim())){
                    MessageUtils.showErrorMessage(TicketCheckActivity.this, "请输入6位电子票码");
                    return;
                }

                // 调用接口验票
                Map<String, String> mapparams = new HashMap<String, String>(0);
                mapparams.put("ticket_sn", "LLL2015827234245");   // 电子票号
                mapparams.put("brand_id", "3");    // 验票终端所属景区ID
                mapparams.put("check_admin", "admin"); // 验票终端登录用户名称

                checkCode(mapparams);
            }
        });

        // 验票错误退回按钮
        rtnButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                elecLayout.setVisibility(View.VISIBLE);
                chkTktSuccess.setVisibility(View.GONE);
                chkTktFail.setVisibility(View.GONE);
            }
        });

        // 验票成功按钮事件
        checkBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                elecLayout.setVisibility(View.VISIBLE);
                chkTktSuccess.setVisibility(View.GONE);
                chkTktFail.setVisibility(View.GONE);
            }
        });

        // 注册退回按钮事件
        backImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private View.OnTouchListener backImageViewOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.setBackgroundColor(getResources().getColor(R.color.list_item_read));
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                v.setBackgroundColor(getResources().getColor(R.color.clarity));
            }
            return false;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String result = data.getExtras().getString("result");
            Log.e("----------------------->", result);
            // 调用接口验票
            Map<String, String> mapparams = new HashMap<String, String>(0);
            mapparams.put("ticket_sn", result);   // 电子票号
            mapparams.put("brand_id", HttpManager.brand_id);    // 验票终端所属景区ID
            mapparams.put("check_admin", HttpManager.check_admin); // 验票终端登录用户名称

            checkCode(mapparams);
        }
    }

    private void createProgressDialog() {
        progressDialog = ProgressDialog.show(this, null, getString(R.string.please_loading_hint), true, true);
    }

    private void checkCode(Map<String,String> mapparams){
        createProgressDialog();

        HttpManager.requestOnceWithURLString(TicketCheckActivity.this, Constants.CHECK_TICKET_URL, mapparams, handler );
    }

    private HttpRequestHandler handler = new HttpRequestHandler<String>() {
        @Override
        public void onSuccess(String data) {
            progressDialog.dismiss();

            //  {"errcode":"ZWTICKET-CHECKTICKET-S-200","errmsg":"校验成功","result":null}
            JSONObject jsonObject =null;
            try {
                jsonObject = new JSONObject(data);
                String msg = jsonObject.getString("errmsg");
                if("校验成功".equals(msg)){
                    // 如果验票成功
                    elecLayout.setVisibility(View.GONE);
                    chkTktSuccess.setVisibility(View.VISIBLE);
                } else {
                    // 如果验票失败
                    elecLayout.setVisibility(View.GONE);
                    chkTktFail.setVisibility(View.VISIBLE);
                    failMsgTextView.setText(msg);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSuccess(String data, int totalPages, int currentPage) {
            progressDialog.dismiss();
        }

        @Override
        public void onFailure(String error) {
            progressDialog.dismiss();
            MessageUtils.showErrorMessage(TicketCheckActivity.this, error);
        }
    };
}
