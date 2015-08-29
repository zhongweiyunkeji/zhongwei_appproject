package com.cdhxqh.travel_ticket_app.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;

public class Play_Video_Activity extends BaseActivity {

    private static final String TAG = "Play_Video_Activity";

    /**返回按钮**/
    ImageView backImageView;

    /**标题**/
    TextView titleText;

    /**标题**/
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play__video_);
        getData();
        findViewById();
        initView();
    }

    private void getData() {
        title=getIntent().getExtras().getString("brand_name");
        Log.i(TAG,"title="+title);

    }

    @Override
    protected void findViewById() {
        backImageView=(ImageView)findViewById(R.id.back_imageview_id);
        titleText=(TextView)findViewById(R.id.title_text_id);

    }

    @Override
    protected void initView() {
        titleText.setText(title);
        backImageView.setOnClickListener(backImageViewOnClickListener);
    }

    private View.OnClickListener backImageViewOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

}
