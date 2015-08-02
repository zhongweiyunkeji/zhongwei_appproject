package com.cdhxqh.travel_ticket_app.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.api.HttpManager;
import com.cdhxqh.travel_ticket_app.api.HttpRequestHandler;
import com.cdhxqh.travel_ticket_app.model.Ecs_brand;
import com.cdhxqh.travel_ticket_app.model.SpotBookModel;
import com.cdhxqh.travel_ticket_app.ui.adapter.BrandListAdapter;
import com.cdhxqh.travel_ticket_app.utils.MessageUtils;
import com.cdhxqh.travel_ticket_app.utils.TimeCountUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/28.
 */
public class IntroductionActivity extends BaseActivity{
    /**
     * 景区简介标题
     */
    private TextView text_introduction_tittle;

    /**
     * 景区简介图片
     */
    private ImageView image_introduction_id;

    /**
     * 景区简介详情
     */
    private TextView text_introduction_desc;

    private ProgressDialog progressDialog;

    ArrayList<SpotBookModel> datas = new ArrayList<SpotBookModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        findViewById();
        initView();
        text();
    }

    /**
     * 绑定控件id
     */
    @Override
    protected void findViewById() {
        text_introduction_tittle = (TextView) findViewById(R.id.text_introduction_tittle);
        image_introduction_id = (ImageView) findViewById(R.id.image_introduction_id);
        text_introduction_desc= (TextView) findViewById(R.id.text_introduction_desc);
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initView() {

    }

    BrandListAdapter brandListAdapter;




    private HttpRequestHandler<ArrayList<SpotBookModel>> handler = new HttpRequestHandler<ArrayList<SpotBookModel>>() {


        @Override
        public void onSuccess(ArrayList<SpotBookModel> data) {

            Log.i(TAG, "data=" + data);
            datas = data;
            String spotImage = datas.get(0).getSpotImage();
            ImageLoader.getInstance().displayImage(spotImage, image_introduction_id);


            String spotTittle = datas.get(0).getSpotTittle();
            text_introduction_tittle.setText(spotTittle);

            String spotDesc = datas.get(0).getSpotDesc();
            text_introduction_desc.setText(spotDesc);
        }

        @Override
        public void onSuccess(ArrayList<SpotBookModel> data, int totalPages, int currentPage) {
            progressDialog.dismiss();
            Log.i(TAG,"222222");

        }

        @Override
        public void onFailure(String error) {
            Log.i(TAG,"333333");
            MessageUtils.showErrorMessage(IntroductionActivity.this,error);
            progressDialog.dismiss();
        }
    };



    public void text() {
        HttpManager.getSpotBooking(this, "http://182.92.158.158:8080/qdm/ecsbrand/brandlist", null, "3", "1", handler);
    }


}
