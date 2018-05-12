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

public class ShowFragment2 extends BaseFragment {

    private HeaderGridView gridView;
    private CommonAdapter<BoxDataEntry.ListBean> adapter;
    private PtrClassicFrameLayout mptrClassicFrameLayout;
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
        adapter = new CommonAdapter<BoxDataEntry.ListBean>(getActivity(), R.layout.gridview_layout_show1) {

            @Override
            protected void convert(ViewHolder viewHolder, BoxDataEntry.ListBean listBean, int i) {
                CircleImageView iv_item = viewHolder.findViewById(R.id.iv_item);
                TextView tv_num = viewHolder.findViewById(R.id.tv_num);
                TextView tv_name = viewHolder.findViewById(R.id.tv_name);
                String img = listBean.getIcon();
                String name = listBean.getName();
                int live_number = listBean.getLive_number();
                Glide.with(getActivity()).load(img).error(R.mipmap.error_icon).into(iv_item);
                tv_num.setText(live_number + "");
                tv_name.setText(name);
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
                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, BoxFragment1.class.getName());
                intent.putExtra("id", adapter.getItem(position - 3).getId() + "");
                intent.putExtra("name", adapter.getItem(position - 3).getName());
                intent.putExtra("icon", adapter.getItem(position - 3).getIcon());
                intent.putExtra("number", adapter.getItem(position - 3).getLive_number());
                LogUtils.i("number-------", adapter.getItem(position - 3).getLive_number() + "");
                // intent.putExtra("name", adapter.getItem(position - 1).getName());
                startActivity(intent);
//                intent.putExtra("url", adapter.getItem(position - 3).getUrl());
//                intent.putExtra("name", adapter.getItem(position - 3).getName());
//                intent.putExtra("img", adapter.getItem(position - 3).getImg());
//                intent.putExtra("fjs", adapter.getItem(position - 3).getFjs());
//                HashMap<String, String> map = new HashMap<>();
//                map.put("url", adapter.getItem(position).getUrl());
//                LogUtils.i("url==========", adapter.getItem(position).getUrl());
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
                data = new ArrayList<>();
                for (int i = 0; i < pic_urls.length(); i++) {
//                    data.add(pic_urls.opt(0) + "");
//                    data.add(pic_urls.opt(1) + "");
//                    data.add(pic_urls.opt(2) + "");
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
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

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
        NetWorkUtils.postUrl1(getActivity(), "http://xl.yxumall.com/public/api/api/requestGetClassList", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                String data = resObj.optString("list");
                List<BoxDataEntry.ListBean> list = GsonUtils.getListFromJSON(data, new TypeToken<ArrayList<BoxDataEntry.ListBean>>() {
                }.getType());
                adapter.replaceAll(list);
                video_size.setText("持续更新：" + list.size());
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }
}
