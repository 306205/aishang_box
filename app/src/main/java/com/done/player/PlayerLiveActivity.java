package com.done.player;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.done.ijkplayer.listener.OnPlayerBackListener;
import com.done.ijkplayer.listener.OnShowThumbnailListener;
import com.done.ijkplayer.widget.PlayerView;
import com.done.player.bean.LiveBean;
import com.done.player.util.MediaUtils;
import com.zss.library.utils.LogUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * ========================================
 * <p/>
 * 作 者：东东
 * <p/>
 * 版 本：1.0
 * <p/>
 * 创建日期：2017/12/04
 * <p/>
 * 描 述：电视直播
 * <p/>
 * ========================================
 */
public class PlayerLiveActivity extends Activity {

    private PlayerView player;
    private Context mContext;
    private View rootView;
    private List<LiveBean> list;
    private PowerManager.WakeLock wakeLock;
    private TextView nameTv;
    private CircleImageView photo;
    private TextView close;
    private LinearLayout content;
    private boolean flag = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent getIntent = getIntent();
        String url = getIntent.getStringExtra("url");
        String name = getIntent.getStringExtra("name");
        String logUrl = getIntent.getStringExtra("logUrl");
        int view_time = getIntent.getIntExtra("view_time", 0);
        int state = getIntent.getIntExtra("state", 0);

        LogUtils.i("done=============", url);

        this.mContext = this;
        rootView = getLayoutInflater().from(this).inflate(com.done.player.R.layout.simple_player_view_player, null);
        setContentView(rootView);
        nameTv = (TextView) rootView.findViewById(com.done.player.R.id.name);
        photo = (CircleImageView) rootView.findViewById(com.done.player.R.id.photo);
        close = (TextView) rootView.findViewById(com.done.player.R.id.close);
        content = (LinearLayout) rootView.findViewById(com.done.player.R.id.content);


        nameTv.setText(name);
        Glide.with(getApplicationContext()).load(logUrl).error(com.done.player.R.mipmap.error_img).into(photo);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag) {
                    nameTv.setVisibility(View.GONE);
                    photo.setVisibility(View.GONE);
                    content.setVisibility(View.GONE);
                    close.setText("开启字幕");
                } else {
                    close.setText("关闭字幕");
                    nameTv.setVisibility(View.VISIBLE);
                    photo.setVisibility(View.VISIBLE);
                    content.setVisibility(View.VISIBLE);
                }
                flag = !flag;
            }
        });

        /**常亮*/
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "liveTAG");
        wakeLock.acquire();
        player = new PlayerView(this, rootView);
        if (state == 0) {
            player.setChargeTie(true, view_time);
        } else {
            player.setChargeTie(false, 0);
        }
        player.setTitle(name)
                .hideMenu(false)
                .hideSteam(true)
                .setForbidDoulbeUp(false)
                .hideCenterPlayer(true)
                .hideControlPanl(false)
                .showThumbnail(new OnShowThumbnailListener() {
                    @Override
                    public void onShowThumbnail(ImageView ivThumbnail) {
                        Glide.with(mContext)
                                .load(com.done.player.R.color.black)
                                .placeholder(com.done.player.R.color.black)
                                .error(com.done.player.R.color.black)
                                .into(ivThumbnail);
                    }
                }).setPlaySource(url)
                .setPlayerBackListener(new OnPlayerBackListener() {
                    @Override
                    public void onPlayerBack() {
                        //这里可以简单播放器点击返回键
                        finish();
                    }
                })
                .startPlay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
        MediaUtils.muteAudioFocus(mContext, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
        MediaUtils.muteAudioFocus(mContext, false);
        if (wakeLock != null) {
            wakeLock.acquire();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onBackPressed() {
        if (player != null && player.onBackPressed()) {
            return;
        }
        super.onBackPressed();
        if (wakeLock != null) {
            wakeLock.release();
        }
    }

}
