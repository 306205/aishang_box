package com.done.player.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.done.player.PlayerLiveActivity;
import com.done.player.R;
import com.done.player.adapter.RoomInfoAdapter;
import com.done.player.entry.RoomInfo;
import com.done.player.listener.RequestStreamUrlListener;
import com.done.player.listener.RequestSubChannelListener;
import com.done.player.module.NetworkRequestImpl;
import com.done.player.util.AdapterCallback;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


/**
 * Created by XDONE
 */
public class LiveFragment extends BaseFragment {
    private static final String TAG = "LIVE_FRAGMENT";
    public static final String ARGUMENT = "argument";
    private View mView;
    private PtrClassicFrameLayout mptrClassicFrameLayout;
    private RecyclerView mRecyclerView;
    private List<RoomInfo> mRoomInfos;
    private RoomInfoAdapter mAdapter;
    private String mRequestUrl;
    private int mOffset = 1;

    private NetworkRequestImpl mNetworkRequest;
    private String tagName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mRequestUrl = bundle.getString("url");
            tagName = bundle.getString("tagName");

            LogUtils.i("mRequestUrl================", mRequestUrl);
        }
    }

//    public static LiveFragment newInstance(String argument) {
//        Bundle bundle = new Bundle();
//        bundle.putString(ARGUMENT, argument);
//        LiveFragment liveFragment = new LiveFragment();
//        liveFragment.setArguments(bundle);
//        return liveFragment;
//    }


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

        mRoomInfos = new ArrayList<>();
        mAdapter = new RoomInfoAdapter(getContext(), mRoomInfos);
        LogUtils.i("mRoomInfos==============", mRoomInfos.toString());
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        //第一列单独占一行
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position == 0) ? gridLayoutManager.getSpanCount() : 1;
            }
        });
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mAdapter.setAdapterCallback(new AdapterCallback() {
            @Override
            public void onItemClick(View view, int position) {
                mNetworkRequest.getStreamUrl(mRoomInfos.get(position).getRoomId(), mStreamUrlListener);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }

            @Override
            public void onPositionChanged(int position) {
                if (position == (mRoomInfos.size() - 10)) {
                    String url = mRequestUrl + "&offset=" + mOffset * 20;
                    mOffset++;
                    LogUtils.i("url==============", url);
                    mNetworkRequest.getSubChannel(url, mFootRefreshListener);
                }
            }
        });

        setAdapter();
        setPtrHandler();
        mptrClassicFrameLayout.autoRefresh(true);
    }

    private RequestStreamUrlListener mStreamUrlListener = new RequestStreamUrlListener() {
        @Override
        public void onSuccess(int roomId, String url) {
            Intent intent = new Intent(getActivity(), PlayerLiveActivity.class);
            intent.putExtra("url", url);
            LogUtils.i("mStreamUrlListener===============url", url);
            intent.putExtra("ROOM_ID", roomId);
            startActivity(intent);
        }

        @Override
        public void onError() {
            Log.i(TAG, "onErrorResponse: requestStreamPath fail");
        }
    };


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
            String url = mRequestUrl + "&offset=0";
            mNetworkRequest.getSubChannel(url, mPullToRefreshListener);
        }
    };

    //下拉刷新加载回调
    private RequestSubChannelListener mPullToRefreshListener = new RequestSubChannelListener() {
        @Override
        public void onSuccess(List<RoomInfo> roomInfos) {
            mOffset = 1;
            mRoomInfos.clear();
            mRoomInfos.addAll(roomInfos);
            mAdapter.notifyDataSetChanged();
            mptrClassicFrameLayout.refreshComplete();
        }

        @Override
        public void onError() {
            Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
            mptrClassicFrameLayout.refreshComplete();
        }
    };

    //上拉刷新加载回调
    private RequestSubChannelListener mFootRefreshListener = new RequestSubChannelListener() {
        @Override
        public void onSuccess(List<RoomInfo> roomInfos) {
            if (roomInfos.size() != 0) {
                mRoomInfos.addAll(roomInfos);
                mAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onError() {

        }
    };

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorYellow);
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.colorYellow));
        toolbar.setTitleColor(getColor(R.color.white));
        toolbar.setTitle(tagName);
    }
}
