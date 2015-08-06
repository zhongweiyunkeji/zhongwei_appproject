package com.cdhxqh.travel_ticket_app.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.api.ErrorType;
import com.cdhxqh.travel_ticket_app.api.HttpRequestHandler;
import com.cdhxqh.travel_ticket_app.api.ZhiFuManage;
import com.cdhxqh.travel_ticket_app.app.PayResult;
import com.cdhxqh.travel_ticket_app.app.SignUtils;
import com.cdhxqh.travel_ticket_app.config.Constants;
import com.cdhxqh.travel_ticket_app.model.OrderModel;
import com.cdhxqh.travel_ticket_app.model.SpotBookModel;
import com.cdhxqh.travel_ticket_app.utils.JsonUtils;
import com.cdhxqh.travel_ticket_app.utils.MessageUtils;
import com.cdhxqh.travel_ticket_app.utils.SafeHandler;
import com.cdhxqh.travel_ticket_app.utils.TimeCountUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import com.alipay.sdk.app.PayTask;

/**
 * Created by Administrator on 2015/8/5.
 */
public class Layoutonline_Payment_Activity extends BaseActivity{
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
    /**
     * 支付宝
     */
    private ImageView zhifubao_id;

    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_CHECK_FLAG = 2;

    /**
     * 总金额
     */
    private String goodsAmount;

    /**
     * 封装的json
     */
    private String goodsIds;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layoutonline_payment);
        findViewById();
        getdate();
        initView();
    }

    @Override
    protected void findViewById() {
        /**
         * 标题标签相关id
         */
        backImageView = (ImageView) findViewById(R.id.back_imageview_id);
        titleTextView = (TextView) findViewById(R.id.title_text_id);
        seachImageView = (ImageView) findViewById(R.id.title_search_id);

        zhifubao_id = (ImageView) findViewById(R.id.zhifubao_id);
    }

    @Override
    protected void initView() {
        //设置标签页显示方式
        backImageView.setVisibility(View.VISIBLE);
        seachImageView.setVisibility(View.GONE);
        titleTextView.setText("预订信息填写的");

        zhifubao_id.setOnClickListener(zhifubaoListener);
    }

    /**
     * 返回事件的监听*
     */
    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(Layoutonline_Payment_Activity.this, ReservationActivity.class);
            startActivityForResult(intent, 0);
        }
    };

    /**
     * 支付宝监听
     */
    private View.OnClickListener zhifubaoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ZhiFuManage.pay(v, Layoutonline_Payment_Activity.this, mHandler);
        }
    };

    /**
     * 获取数据
     */
    private void getdate() {
        Bundle bundle = new Bundle();
        bundle = this.getIntent().getExtras();
        goodsAmount = bundle.getString("goodsAmount");
        goodsIds = bundle.getString("goodsIds");
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(Layoutonline_Payment_Activity.this, "支付成功",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(Layoutonline_Payment_Activity.this, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(Layoutonline_Payment_Activity.this, "支付失败",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(Layoutonline_Payment_Activity.this, "检查结果为：" + msg.obj,
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                default:
                    break;
            }
        };
    };



}
