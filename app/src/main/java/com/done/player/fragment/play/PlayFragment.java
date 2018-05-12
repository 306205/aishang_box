package com.done.player.fragment.play;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import com.done.player.R;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.utils.LogUtils;

/**
 * Created by XDONE on 2017/12/11.
 */

public class PlayFragment extends BaseFragment {

    private VideoView video_view;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_live_play;
    }

    @Override
    public void initView() {
        super.initView();
        video_view = (VideoView) findViewById(R.id.video_view);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        Intent getIntent = getActivity().getIntent();
        String path = getIntent.getStringExtra("url");
        LogUtils.i("done=============", path);
        playVideo(path);

    }

    private void playVideo(String path) {
        Uri uri = Uri.parse(path);
        LogUtils.i("uri============", uri.toString());
        video_view.setVideoURI(uri);
        video_view.start();
    }
}
