package com.cdhxqh.travel_ticket_app.ui.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.model.Attractions;
import com.cdhxqh.travel_ticket_app.utils.MessageUtils;
import com.cdhxqh.travel_ticket_app.utils.Player_Music;
import com.nostra13.universalimageloader.core.ImageLoader;

public class Attractions_details_Activity extends BaseActivity {

    private static final String TAG = "Attractions_details_Activity";

    /**
     * 返回按钮*
     */
    ImageView backImage;

    /**
     * 标题*
     */
    TextView titleText;

    /**
     * 搜索*
     */
    ImageView searchImageView;


    /**
     * 获取对象*
     */
    Attractions attractions;


    /**
     * 图片*
     */
    ImageView imageView;
    /**
     * 播放按钮*
     */
    ImageView playImage;
    /**
     * 标题*
     */
    TextView atitleText;
    /**
     * 内容*
     */
    TextView contentText;

    private Player_Music player;

    /**
     * 播放状态*
     */
    int playstaus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions_details_);

        getData();
        initAudio();
        findViewById();
        initView();

    }

    /**
     * 初始化播放音频*
     */
    private void initAudio() {
        player = new Player_Music();

    }


    /**
     * 获取数据*
     */
    private void getData() {
        attractions = getIntent().getParcelableExtra("attractions");

        Log.i(TAG, "attractions=" + attractions.file_url);
    }

    @Override
    protected void findViewById() {

        backImage = (ImageView) findViewById(R.id.back_imageview_id);
        titleText = (TextView) findViewById(R.id.title_text_id);
        searchImageView = (ImageView) findViewById(R.id.title_search_id);

        imageView = (ImageView) findViewById(R.id.att_image_id);
        playImage = (ImageView) findViewById(R.id.music_play_id);
        atitleText = (TextView) findViewById(R.id.attr_title_id);
        contentText = (TextView) findViewById(R.id.attr_content_id);


    }

    @Override
    protected void initView() {
        titleText.setText(attractions.title);
        backImage.setOnClickListener(backImageOnClickListener);
        backImage.setVisibility(View.VISIBLE);

        ImageLoader.getInstance().displayImage(attractions.image, imageView);
        playImage.setOnClickListener(playImageOnClickListener);

        if (attractions != null) {
            atitleText.setText(attractions.title);
            contentText.setText(Html.fromHtml(attractions.content));
        }


    }


    private View.OnClickListener playImageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i(TAG, "play....");
            String file_url = attractions.file_url;
            if (file_url == null) {
                Log.i(TAG, "mei_you");
                MessageUtils.showMiddleToast(Attractions_details_Activity.this, getString(R.string.not_music_file_text));
            } else {

                if (playstaus == 0) { //未播放
                    Log.i(TAG, "开始播放");
                    playstaus = 1;
                    player.playUrl(file_url);
                    player.play();
                    playImage.setImageResource(R.drawable.music_playing);
                } else if (playstaus == 1) { //暂停
                    Log.i(TAG, "暂停");
                    playstaus = 2;
                    player.pause();
                    playImage.setImageResource(R.drawable.music_play);
                } else if (playstaus == 2) { //暂停后再播放
                    player.play();
                    playstaus = 1;
                    playImage.setImageResource(R.drawable.music_playing);
                } else {
                    playstaus = 0;
                }
            }
        }
    };


    /**
     * 返回事件*
     */
    private View.OnClickListener backImageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_attractions_details_, menu);
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


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (player != null) {
            player.stop();
            player = null;
        }
    }
}
