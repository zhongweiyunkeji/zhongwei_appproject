package com.cdhxqh.travel_ticket_app.ui.activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
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
    /**播放动画**/
    ImageView playAnimImage;
    /**
     * 标题*
     */
    TextView atitleText;
    /**
     * 内容*
     */
    TextView contentText;

    private Player_Music player;
    public MediaPlayer mediaPlayer;

    ImageView settingsImg;

    /**
     * 播放状态*
     */
    int playstaus = 0;

    /**
     * 音频动画*
     */
    private AnimationDrawable anim;


    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions_details_);

        getData();

        findViewById();
        initView();
    }

    /**
     * 初始化播放音频*
     */
    private void initAudio() {
        mediaPlayer=new MediaPlayer();
        player = new Player_Music(progressDialog,mediaPlayer);
        mediaPlayer.setOnCompletionListener(mediaPlayerOnCompletionListener);
    }


    /**
     * 获取数据*
     */
    private void getData() {
        attractions = getIntent().getParcelableExtra("attractions");

    }

    @Override
    protected void findViewById() {

        backImage = (ImageView) findViewById(R.id.back_imageview_id);
        titleText = (TextView) findViewById(R.id.title_text_id);
        searchImageView = (ImageView) findViewById(R.id.title_search_id);

        imageView = (ImageView) findViewById(R.id.att_image_id);
        playImage = (ImageView) findViewById(R.id.music_play_id);
        playAnimImage = (ImageView) findViewById(R.id.music_play_anim);


        atitleText = (TextView) findViewById(R.id.attr_title_id);
        contentText = (TextView) findViewById(R.id.attr_content_id);


    }

    private MediaPlayer.OnCompletionListener mediaPlayerOnCompletionListener=new MediaPlayer.OnCompletionListener(){
        @Override
        public void onCompletion(MediaPlayer mp) {
            stopA();
            playstaus = 0;
            playImage.setVisibility(View.VISIBLE);
            playAnimImage.setVisibility(View.GONE);
            playImage.setImageResource(R.drawable.ic_play1);
        }
    };




    @Override
    protected void initView() {
        titleText.setText(attractions.title);
        backImage.setOnClickListener(backImageOnClickListener);
        backImage.setVisibility(View.VISIBLE);
        searchImageView.setVisibility(View.GONE);
        ImageLoader.getInstance().displayImage(attractions.image, imageView);
        playImage.setOnClickListener(playImageOnClickListener);
        playAnimImage.setOnClickListener(playImageOnClickListener);

        if (attractions != null) {
            atitleText.setText(attractions.title);
            contentText.setText(Html.fromHtml(attractions.content));
        }

        backImage.setOnTouchListener(backImageViewOnTouchListener);

        anim = (AnimationDrawable) playAnimImage.getBackground();

    }



    /**
     * 加载音频动画*
     */
    private void loadProgressDialog() {
        progressDialog = ProgressDialog.show(Attractions_details_Activity.this, null,
                "加载中...", true, true);
    }


    /**
     * 开始播放音频动画*
     */
    public void startA() {
        anim.start();
    }

    /**
     * 停止播放音频动画*
     */
    public void stopA() {
        anim.stop();
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

    private View.OnClickListener playImageOnClickListener = new View.OnClickListener() {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {

            String file_url = attractions.file_url;
            if (file_url == null) {
                MessageUtils.showMiddleToast(Attractions_details_Activity.this, getString(R.string.not_music_file_text));
            } else {

                playStaus();
            }
        }
    };




    /**切换播放状态**/
    private void playStaus(){
        if (playstaus == 0) { //未播放
            loadProgressDialog();
            initAudio();
            startA();
            playstaus = 1;
            player.playUrl(attractions.file_url);
            playImage.setVisibility(View.GONE);
            playAnimImage.setVisibility(View.VISIBLE);
        } else if (playstaus == 1) { //暂停
            stopA();
            playstaus = 2;
            player.pause();
            playImage.setVisibility(View.VISIBLE);
            playAnimImage.setVisibility(View.GONE);
            playImage.setImageResource(R.drawable.ic_play_stop);
        } else if (playstaus == 2) { //暂停后再播放
            startA();
            player.play();
            playstaus = 1;
            playImage.setVisibility(View.GONE);
            playAnimImage.setVisibility(View.VISIBLE);
        } else {
            stopA();
            playstaus = 0;
            playImage.setVisibility(View.VISIBLE);
            playAnimImage.setVisibility(View.GONE);
            playImage.setImageResource(R.drawable.ic_play1);
        }
    }





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
