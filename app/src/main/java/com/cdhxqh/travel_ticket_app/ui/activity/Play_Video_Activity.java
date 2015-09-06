package com.cdhxqh.travel_ticket_app.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cdhxqh.travel_ticket_app.R;
import com.cdhxqh.travel_ticket_app.config.Constants;
import com.videogo.util.Utils;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;


public class Play_Video_Activity extends BaseActivity implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnVideoSizeChangedListener, SurfaceHolder.Callback {

    private static final String TAG = "Play_Video_Activity";

    /**
     * 返回按钮*
     */
    ImageView backImageView;

    /**
     * 标题*
     */
    TextView titleText;

    /**
     * 标题*
     */
    String title;

    private int mVideoWidth;

    private int mVideoHeight;

    private MediaPlayer mMediaPlayer;

    private SurfaceView mPreview;

    private SurfaceHolder holder;

    private String path;

    private Bundle extras;


    private boolean mIsVideoSizeKnown = false;

    private boolean mIsVideoReadyToBePlayed = false;

    private Intent intent;

    /**
     * 加载进度条布局*
     */
    private LinearLayout waitLinearLayout;
    /**
     * 进度条显示框*
     */
    private TextView progressBarTextView;

    /**是否显示**/
    private boolean isShow=false;
    /**是否播放**/
    private boolean isPlay=true;
    /**是否静音**/
    private boolean isAudio=false;

    /**播放进度条**/
    private RelativeLayout relativeLayout;

    /**屏幕正中播放**/
    private  ImageView playImageView;
    /**播放按钮**/
    private ImageButton playButton;
    /**声音按钮**/
    private ImageButton audioButton;

    /** 屏幕当前方向 */
    private int mOrientation = Configuration.ORIENTATION_PORTRAIT;
    /**标题布局**/
    private RelativeLayout r_Relativelayout;
    /**视频控制布局**/
    private RelativeLayout s_RelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!LibsChecker.checkVitamioLibs(this))
            return;

        setContentView(R.layout.activity_play_video_);

        intent = getIntent();

        getData();
        findViewById();

        initView();
    }

    private void getData() {
        title = getIntent().getExtras().getString("brand_name");
        // 开启旋转传感器
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

    }

    @Override
    protected void findViewById() {
        backImageView = (ImageView) findViewById(R.id.back_imageview_id);
        titleText = (TextView) findViewById(R.id.title_text_id);

        r_Relativelayout=(RelativeLayout)findViewById(R.id.title_relativelayout_id);
        s_RelativeLayout=(RelativeLayout)findViewById(R.id.realplay_play_rl);

        mPreview = (SurfaceView) findViewById(R.id.realplay_sv);
        holder = mPreview.getHolder();
        holder.addCallback(this);
        holder.setFormat(PixelFormat.RGBA_8888);
        extras = getIntent().getExtras();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        waitLinearLayout = (LinearLayout) findViewById(R.id.realplay_loading_ly);
        progressBarTextView = (TextView) findViewById(R.id.realplay_loading_tv);

        relativeLayout=(RelativeLayout)findViewById(R.id.realplay_control_rl);

        playImageView=(ImageView)findViewById(R.id.realplay_play_iv);
        playButton=(ImageButton)findViewById(R.id.realplay_play_btn);
        audioButton=(ImageButton)findViewById(R.id.realplay_sound_btn);

    }

    @Override
    protected void initView() {
        titleText.setText(title);
        backImageView.setOnClickListener(backImageViewOnClickListener);
//        fullScreen(true);
        mPreview.setOnTouchListener(mPreviewOnTouchListener);

        playButton.setOnClickListener(playButtonOnClickListener);
        playImageView.setOnClickListener(playButtonOnClickListener);
        audioButton.setOnClickListener(audioButtonOnClickListener);


    }


    private View.OnClickListener audioButtonOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(isAudio){
                isAudio=false;
                audioButton.setBackgroundResource(R.drawable.remote_list_soundoff_btn_selector);
            }else{
                isAudio=true;
                audioButton.setBackgroundResource(R.drawable.remote_list_soundon_btn_selector);
            }
        }
    };


     private View.OnClickListener playButtonOnClickListener=new View.OnClickListener() {
         @Override
         public void onClick(View v) {
              if(isPlay){
                  setAudio(isPlay);
                  isPlay=false;
                  playButton.setBackgroundResource(R.drawable.play_stop_selector);
                  playImageView.setVisibility(View.VISIBLE);
                  stopVideoPlayback();
              }else{
                  setAudio(isPlay);
                  isPlay=true;
                  playButton.setBackgroundResource(R.drawable.play_play_selector);
                  playImageView.setVisibility(View.GONE);
                  startVideoPlayback();
              }
         }
     };




    private View.OnTouchListener mPreviewOnTouchListener=new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            if(isShow){
                isShow=false;
                relativeLayout.setVisibility(View.VISIBLE);

            }else{
                isShow=true;
                relativeLayout.setVisibility(View.GONE);
            }
            return false;
        }
    };




    /**
     * 显示继续播放按钮
     */
    private void setPlayStartUI() {
        waitLinearLayout.setVisibility(View.GONE); // 隐藏进度条
    }

    /**
     * 显示初次加载时的图片
     */
    private void setPlayLoadingUI() {
        waitLinearLayout.setVisibility(View.VISIBLE);
    }


    /**设置静音**/
    private void setAudio(boolean b){
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        if(b){
            audioManager.setStreamMute(AudioManager.STREAM_MUSIC , true);
        }else{
            audioManager.setStreamMute(AudioManager.STREAM_MUSIC , false);
        }
    }

    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        Log.d(TAG, "surfaceCreated called");
        playVideo(intent.getIntExtra("stream", 5));

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i(TAG, "surfaceChanged called");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i(TAG, "surfaceDestroyed called");

    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

    }


    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.i(TAG, "onCompletion called");
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.i(TAG, "onPrepared called");
        mIsVideoReadyToBePlayed = true;
        if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
            setPlayStartUI();
            startVideoPlayback();
        }
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int width, int height) {

        Log.v(TAG, "onVideoSizeChanged called");
        if (width == 0 || height == 0) {
            Log.e(TAG, "invalid video width(" + width + ") or height(" + height + ")");
            return;
        }
        mIsVideoSizeKnown = true;
        mVideoWidth = width;
        mVideoHeight = height;
        if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
            startVideoPlayback();
        }

    }


    private void playVideo(Integer Media) {
        doCleanUp();
        try {

            path = Constants.video_play_url;
            if (path == "") {
                // Tell the user to provide a media file URL.
                Toast.makeText(Play_Video_Activity.this, "Please edit MediaPlayerDemo_Video Activity," + " and set the path variable to your media file URL.", Toast.LENGTH_LONG).show();
                return;
            }


            // Create a new media player and set the listeners
            mMediaPlayer = new MediaPlayer(this);
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.setDisplay(holder);
            mMediaPlayer.prepare();
            mMediaPlayer.setOnBufferingUpdateListener(this);
            mMediaPlayer.setOnCompletionListener(this);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnVideoSizeChangedListener(this);
            //  mMediaPlayer.getMetadata();
            setVolumeControlStream(AudioManager.STREAM_MUSIC);

        } catch (Exception e) {
            Log.e(TAG, "error: " + e.getMessage(), e);
        }
    }


    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }


    private void doCleanUp() {
        mVideoWidth = 0;
        mVideoHeight = 0;
        mIsVideoReadyToBePlayed = false;
        mIsVideoSizeKnown = false;
    }

    /**开始播放**/
    private void startVideoPlayback() {
        Log.v(TAG, "startVideoPlayback");
        holder.setFixedSize(mVideoWidth, mVideoHeight);
        mMediaPlayer.start();
    }

    /**停止播放**/

    private void stopVideoPlayback() {
        Log.v(TAG, "startVideoPlayback");
        holder.setFixedSize(mVideoWidth, mVideoHeight);
        mMediaPlayer.pause();
    }


    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaPlayer();
        doCleanUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
        doCleanUp();
    }





    /**
     * 全屏显示*
     */
    private void fullScreen(boolean enable) {
        if (enable) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attr);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.i(TAG, "this is ConfigurationChanged");
        mOrientation = newConfig.orientation;
        setRealPlaySvLayout();
        super.onConfigurationChanged(newConfig);
    }

    /**设置屏幕大小**/
    private void setRealPlaySvLayout() {


        if(mOrientation!=Configuration.ORIENTATION_PORTRAIT){
            fullScreen(true);
            RelativeLayout.LayoutParams svLp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            svLp.addRule(RelativeLayout.CENTER_IN_PARENT);
            mPreview.setLayoutParams(svLp);

            r_Relativelayout.setVisibility(View.GONE);
        }else{
            fullScreen(false);
            RelativeLayout.LayoutParams svLp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    600);
            svLp.addRule(RelativeLayout.CENTER_IN_PARENT);
            mPreview.setLayoutParams(svLp);
            r_Relativelayout.setVisibility(View.VISIBLE);
        }

    }
}
