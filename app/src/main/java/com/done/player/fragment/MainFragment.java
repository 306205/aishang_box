package com.done.player.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;

import com.done.player.R;
import com.done.player.WebViewActivity;
import com.done.player.fragment.me.LoginFragment;
import com.done.player.util.BuildUrl;
import com.done.player.util.GlideImageLoader;
import com.done.player.util.NetWorkUtils;
import com.done.player.util.Utils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.utils.LogUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by XDONE on 2017/11/27.
 */

public class MainFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    private Banner banner;
    private SwipeRefreshLayout mSwipeLayout;
    private static final int REFRESH_COMPLETE = 0X110;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    mSwipeLayout.setRefreshing(false);
                    break;
            }
        }
    };


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_main1;
    }

    @Override
    public void initView() {
        super.initView();
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);
        banner = (Banner) findViewById(R.id.banner);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.id_swipe_ly);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        getData();
        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
        img4.setOnClickListener(this);
        mSwipeLayout.setOnRefreshListener(this);
    }

    private void getData() {

        HashMap<String, String> map = new HashMap<>();

        NetWorkUtils.postUrl(getActivity(), "http://dl.yxumall.com/admin.php/api/Picture/pic", map, new NetWorkUtils.IListener() {
            private List<String> data;

            @Override
            public void onSuccess(String result, JSONObject resObj) {
                JSONArray pic_urls = resObj.optJSONArray("pic_url");
                data = new ArrayList<>();
                for (int i = 0; i < pic_urls.length(); i++) {
                    data.add(pic_urls.opt(i) + "");
                }
                //设置图片加载器
                banner.setImageLoader(new GlideImageLoader());
                //设置图片集合
                banner.setImages(data);
                //设置banner动画效果
                banner.setBannerAnimation(Transformer.CubeIn);
                //设置自动轮播，默认为true
                banner.isAutoPlay(true);
                //设置轮播时间
                banner.setDelayTime(1500);
                //设置指示器位置（当banner模式中有指示器时）
                banner.setIndicatorGravity(BannerConfig.CENTER);
                //banner设置方法全部调用完毕时最后调用
                banner.start();
                // 加载完数据设置为不刷新状态，将下拉进度收起来
                mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 0);
            }

            @Override
            public void onError(String result, String code, String msg) {
                // 加载完数据设置为不刷新状态，将下拉进度收起来
                mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 0);
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.img1:
                if (Utils.isLogin()) {
                    String url = BuildUrl.getDouyuLiveChannel();
                    LogUtils.i("=========================1", url);
                    intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, ClassifyFragment.class.getName());
                    intent.putExtra("url", url);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, LoginFragment.class.getName());
                    startActivity(intent);
                }

                break;
            case R.id.img2:
                //// TODO: 2017/12/25
                if (Utils.isLogin()) {
//                    intent = new Intent(getActivity(), ZFrameActivity.class);
//                    intent.putExtra(ZFrameActivity.CLASS_NAME, CCTVFragment.class.getName());
//                    intent.putExtra("name", "央视");
//                    startActivity(intent);

                    if (Utils.isLogin()) {
                        intent = new Intent(getActivity(), WebViewActivity.class);
                        intent.putExtra("url", "http://103.243.26.25/dianshi/");
                        intent.putExtra("name", "央视直播");
                        startActivity(intent);
                    } else {
                        intent = new Intent(getActivity(), ZFrameActivity.class);
                        intent.putExtra(ZFrameActivity.CLASS_NAME, LoginFragment.class.getName());
                        startActivity(intent);
                    }
                } else {
                    intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, LoginFragment.class.getName());
                    startActivity(intent);
                }

                break;
            case R.id.img3:
                //// TODO: 2017/12/25
//                    Bundle bundle = new Bundle();
//                    bundle.putString(ARGUMENT, argument);
//                    LiveFragment liveFragment = new LiveFragment();
//                    liveFragment.setArguments(bundle);
                if (Utils.isLogin()) {
                    intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra("url", "http://www.2w.cm/");
                    intent.putExtra("name", "热播");
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, LoginFragment.class.getName());
                    startActivity(intent);
                }
                break;
            case R.id.img4:
                //// TODO: 2017/12/25
                if (Utils.isLogin()) {
                    intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra("url", "https://www.qidian.com");
                    intent.putExtra("name", "小说");
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, LoginFragment.class.getName());
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void onRefresh() {
        getData();
    }
}
