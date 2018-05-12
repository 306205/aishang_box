package com.done.player.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;


import com.done.player.R;
import com.done.player.adapter.SubChannelAdapter;
import com.done.player.entry.SubChannelInfo;
import com.done.player.listener.NetworkRequest;
import com.done.player.listener.RequestAllSubChannelsListener;
import com.done.player.module.NetworkRequestImpl;
import com.done.player.util.BuildUrl;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by uspai.taobao.com on 2016/6/22.
 */
public class ClassifyFragment extends BaseFragment {
    private static final String TAG = "CLASSIFY_FRAGMENT";
    private View mView;
    private PtrClassicFrameLayout mptrClassicFrameLayout;
    private RecyclerView mRecyclerView;
    private List<SubChannelInfo> mSubChannelInfos;
    private SubChannelAdapter mAdapter;

    private NetworkRequest mNetworkRequest;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_live;
    }

    @Override
    public void initView() {
        super.initView();
        mptrClassicFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.store_house_ptr_frame);
        mRecyclerView = (RecyclerView) findViewById(R.id.store_house_ptr_rv);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mNetworkRequest = new NetworkRequestImpl(getContext());

        mSubChannelInfos = new ArrayList<>();
        mAdapter = new SubChannelAdapter(getContext(), mSubChannelInfos);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mAdapter.setOnItemClickListener(new SubChannelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, LiveFragment.class.getName());
                String url = BuildUrl.getDouyuSubChannelBaseTag(mSubChannelInfos.get(position).getTagId());
                intent.putExtra("url", url);
                String tagName = mSubChannelInfos.get(position).getTagName();
                intent.putExtra("tagName", tagName);
                startActivity(intent);
            }
        });
        setAdapter();
        setPtrHandler();
        mptrClassicFrameLayout.autoRefresh(true);
    }




    private void setAdapter() {
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setPtrHandler() {
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
            mNetworkRequest.getAllSubChannels(mAllSubChannelsListener);
        }
    };

    private RequestAllSubChannelsListener mAllSubChannelsListener = new RequestAllSubChannelsListener() {
        @Override
        public void onSuccess(List<SubChannelInfo> subChannelInfos) {
            mSubChannelInfos.clear();
            mSubChannelInfos.addAll(subChannelInfos);
            mAdapter.notifyDataSetChanged();
            mptrClassicFrameLayout.refreshComplete();
        }

        @Override
        public void onError() {
            Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
            mptrClassicFrameLayout.refreshComplete();
        }
    };

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorYellow);
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.colorYellow));
        toolbar.setTitleColor(getColor(R.color.white));
        toolbar.setTitle("斗鱼直播");
    }
}
