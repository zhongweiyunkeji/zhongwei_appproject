package com.cdhxqh.travel_ticket_app.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.WriterException;
import com.cdhxqh.travel_ticket_app.zxing.encoding.EncodingHandler;
import com.cdhxqh.travel_ticket_app.zxing.activity.CaptureActivity;
import com.cdhxqh.travel_ticket_app.R;
import java.io.File;

/**
 * Created by hexian on 2015/8/6.
 */
public class TicketCheckActivity extends BaseActivity {

    private final static int SCANNIN_GREQUEST_CODE = 1;

    ImageView backImg;
    ImageView searchImg;
    TextView checkCode; // 电子票输入框
    Button validBtn;  // 验证按钮
    LinearLayout elecLayout;  // 电子验票显示区域
    TextView title;     // 抬头
    TextView elecTextSelcect; // 电子票选项卡
    TextView qrcodeSelect;    // 二维码选项卡


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

        elecLayout = (LinearLayout)findViewById(R.id.electronic_layout);

        elecTextSelcect = (TextView)findViewById(R.id.electronic_text);
        qrcodeSelect = (TextView)findViewById(R.id.electronic_qrcode);
    }

    @Override
    protected void initView() {
        title.setText("验票");
        backImg.setVisibility(View.GONE);
        searchImg.setVisibility(View.GONE); // 隐藏搜索按钮

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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String result = data.getExtras().getString("result");
            Log.e("----------------------->", result);
        }
    }


}
