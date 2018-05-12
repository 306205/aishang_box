package com.done.player.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.done.player.R;
import com.done.player.entry.BoxDataEntry;
import com.done.player.entry.PlayDataEntry;
import com.done.player.util.GlideImageLoader;
import com.done.player.util.GsonUtils;
import com.done.player.util.NetWorkUtils;
import com.done.player.widget.HeaderGridView;
import com.google.gson.reflect.TypeToken;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.utils.LogUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by XDONE on 2017/11/27.
 */

public class ShowFragment1 extends BaseFragment {

    private HeaderGridView gridView;
    private CommonAdapter<BoxDataEntry> adapter;
    private PtrClassicFrameLayout mptrClassicFrameLayout;
    private String commUlr = "http://103.243.26.25/meiying/";
    private TextView video_size;
    private Toolbar toolbar;
    private ImageView image;
    private Banner banner;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_show1;
    }

    @Override
    public void initView() {
        super.initView();
        gridView = (HeaderGridView) findViewById(R.id.gridView);
        mptrClassicFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.store_house_ptr_frame);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        image = (ImageView) findViewById(R.id.image);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        getData();
        adapter = new CommonAdapter<BoxDataEntry>(getActivity(), R.layout.gridview_layout_show1) {

            @Override
            protected void convert(ViewHolder viewHolder, BoxDataEntry boxDataEntry, int i) {
                CircleImageView iv_item = viewHolder.findViewById(R.id.iv_item);
                TextView tv_num = viewHolder.findViewById(R.id.tv_num);
                TextView tv_name = viewHolder.findViewById(R.id.tv_name);
//                String img = boxDataEntry.getImg();
//                String fjs = boxDataEntry.getFjs();
//                String name = boxDataEntry.getName();
////                String url1 = boxDataEntry.getUrl();
//                Glide.with(getActivity()).load(img).error(R.mipmap.error_icon).into(iv_item);
//                tv_num.setText(fjs);
//                tv_name.setText(name);
            }
        };
        final View headerView = getLayoutInflater(R.layout.layout_top);
        banner = (Banner) headerView.findViewById(R.id.banner);
        video_size = (TextView) headerView.findViewById(R.id.video_size);
        getPictureData();
        gridView.addHeaderView(headerView);
        gridView.setAdapter(adapter);
        if (adapter.getCount() < 0) {
            View emptyView = getLayoutInflater(R.layout.listview_empty_page);
            gridView.setEmptyView(emptyView);
        }
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                HashMap<String, String> map = new HashMap<>();
//                map.put("url", adapter.getItem(position - 3).getUrl());
//                LogUtils.d("url==========", adapter.getItem(position - 3).getUrl());
                NetWorkUtils.postUrl1(getActivity(), "http://dl.yxumall.com/admin.php/api/caiji/caiji_pingdao", map, new NetWorkUtils.IListener() {
                    @Override
                    public void onSuccess(String result, JSONObject resObj) {
//                        String data = resObj.optString("data");
//                        LogUtils.i("data===++", data);
//                        List<PlayDataEntry> list = GsonUtils.getListFromJSON(data, new TypeToken<ArrayList<PlayDataEntry>>() {
//                        }.getType());
//                        LogUtils.i("list==================", list.toString());
//                        Intent intent = new Intent(getActivity(), ZFrameActivity.class);
//                        intent.putExtra(ZFrameActivity.CLASS_NAME, BoxFragment.class.getName());
//                        intent.putExtra("list", (Serializable) list);
//                        intent.putExtra("name", adapter.getItem(position - 3).getName());
//                        intent.putExtra("img", adapter.getItem(position - 3).getImg());
//                        intent.putExtra("fjs", adapter.getItem(position - 3).getFjs());
//                        startActivity(intent);
                    }

                    @Override
                    public void onError(String result, String code, String msg) {

                    }
                });

//                HashMap<String, String> map = new HashMap<>();
//                map.put("url", adapter.getItem(position).getUrl());
//                LogUtils.i("url==========", adapter.getItem(position).getUrl());
//                HttpManager.postAsync("http://api.ydchain.cc/api.php/base/login_post_json.html", map,
//                        new HttpManager.ResultCallback<String>() {
//                    @Override
//                    public void onError(Request request, Exception e) {
//
//                    }
//
//                    @Override
//                    public void onResponse(String response) {
//                        LogUtils.i("url==========", response.toString());
//                    }
//
//                    @Override
//                    public void onBefore(Request request) {
//                        super.onBefore(request);
//
//                    }
//                });
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                getData();
                adapter.notifyDataSetChanged();
                mptrClassicFrameLayout.refreshComplete();
            }
        });
        setPtrHandler();

        mptrClassicFrameLayout.autoRefresh(true);
    }

    private void getPictureData() {
        HashMap<String, String> map = new HashMap<>();

        NetWorkUtils.postUrl(getActivity(), "http://dl.yxumall.com/admin.php/api/Picture/pic", map, new NetWorkUtils.IListener() {
            private List<String> data;

            @Override
            public void onSuccess(String result, JSONObject resObj) {
                JSONArray pic_urls = resObj.optJSONArray("pic_url");
                for (int i = 0; i < pic_urls.length(); i++) {
                    data = new ArrayList<>();
                    data.add(pic_urls.opt(0) + "");
                    data.add(pic_urls.opt(1) + "");
                    data.add(pic_urls.opt(2) + "");
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
            }

            @Override
            public void onError(String result, String code, String msg) {
            }
        });
    }

    public void setPtrHandler() {
        mptrClassicFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                LogUtils.i("11111111111111111111", "11111111111111111111");
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                LogUtils.i("2222222222222222", "2222222222222222");

                mptrClassicFrameLayout.post(runnable);
            }
        });
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            adapter.clear();
            getData();
            adapter.notifyDataSetChanged();
            mptrClassicFrameLayout.refreshComplete();
        }
    };

    public void getData() {
        HashMap<String, String> map = new HashMap<>();
        NetWorkUtils.postUrl(getActivity(), "http://dl.yxumall.com/admin.php/api/caiji/caiji", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                String data = resObj.optString("data");
                List<BoxDataEntry> list = GsonUtils.getListFromJSON(data, new TypeToken<ArrayList<BoxDataEntry>>() {
                }.getType());
                adapter.addAll(list);
                video_size.setText("持续更新：" + list.size());
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }
}
