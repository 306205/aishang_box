package com.done.player.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.done.player.R;
import com.done.player.WebViewActivity;
import com.done.player.fragment.me.LoginFragment;
import com.done.player.util.Utils;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

/**
 * Created by XDONE on 2017/11/27.
 */

public class TelevisionFragment1 extends BaseFragment implements View.OnClickListener {


    private ImageView iv_tv;
    private ImageView iv_movice;
    private ImageView iv_bg;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_television1;
    }


    @Override
    public void initView() {
        super.initView();
        iv_tv = (ImageView) findViewById(R.id.iv_tv);
        iv_movice = (ImageView) findViewById(R.id.iv_movice);
        iv_bg = (ImageView) findViewById(R.id.iv_bg);

    }


    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        iv_tv.setOnClickListener(this);
        iv_movice.setOnClickListener(this);
        iv_bg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.iv_bg:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, ReadFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.iv_movice:
                if (Utils.isLogin()) {
                    intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra("url", "http://vip.1905.com/?fr=homepc_menu_vip");
                    intent.putExtra("name", "VIP影院");
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, LoginFragment.class.getName());
                    startActivity(intent);
                }
                break;
            case R.id.iv_tv:
                if (Utils.isLogin()) {
                    intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra("url", "http://103.243.26.25/dianshi/");
                    intent.putExtra("name", "卫视直播");
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, LoginFragment.class.getName());
                    startActivity(intent);
                }


//                intent = new Intent();
//                intent.setAction("android.intent.action.VIEW");
//                Uri content_url = Uri.parse("http://103.243.26.25/dianshi/");
//                intent.setData(content_url);
//                startActivity(intent);

                break;

        }

    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorYellow);
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.colorYellow));
        toolbar.setTitleColor(getColor(R.color.white));
    }
}
