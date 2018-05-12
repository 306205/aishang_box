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

import com.bumptech.glide.Glide;
import com.done.ijkplayer.listener.OnPlayerBackListener;
import com.done.ijkplayer.listener.OnShowThumbnailListener;
import com.done.ijkplayer.widget.PlayStateParams;
import com.done.ijkplayer.widget.PlayerView;
import com.done.player.util.StatusBarUtil;
import com.zss.library.utils.LogUtils;


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
public class PlayerActivity extends Activity {

    private PlayerView player;
    private Context mContext;
    private View rootView;
    private PowerManager.WakeLock wakeLock;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        rootView = getLayoutInflater().from(this).inflate(com.done.player.R.layout.simple_player_view_player, null);
        setContentView(rootView);

        /**常亮*/
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "liveTAG");
        wakeLock.acquire();

        StatusBarUtil.darkMode(this);

        Intent getIntent = getIntent();
        String url = getIntent.getStringExtra("url");
        LogUtils.i("done=============", url);
        player = new PlayerView(this, rootView)
                .setTitle("返回")
                .setScaleType(PlayStateParams.fitparent)
                .forbidTouch(false)
                .hideMenu(true)
                .hideCenterPlayer(false)
                .showThumbnail(new OnShowThumbnailListener() {
                    @Override
                    public void onShowThumbnail(ImageView ivThumbnail) {
                        Glide.with(mContext)
                                .load(com.done.player.R.color.cl_default)
                                .placeholder(com.done.player.R.color.cl_default)
                                .error(com.done.player.R.color.cl_default)
                                .into(ivThumbnail);
                    }
                })
                .setPlaySource(url)
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
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
    }

}
