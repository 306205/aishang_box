package com.done.player.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.done.player.R;
import com.done.player.entry.PlayDataEntry;
import com.done.player.util.Utils;
import com.done.player.widget.HeaderGridView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.LogUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by XDONE on 2017/12/19.
 */

public class BoxFragment extends BaseFragment {

    private HeaderGridView gridView;
    private CommonAdapter<PlayDataEntry> adapter;
    private PlayDataEntry playData;
    private ArrayList<PlayDataEntry> listObj;
    private String play_url;
    private RefreshLayout refreshLayout;
    private String name;
    private String img;
    private String fjs;

    private static boolean isFirstEnter = true;
    private TextView tvName;
    private TextView tvNum;
    private ImageView icon;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_box;
    }

    @Override
    public void initView() {
        super.initView();

        gridView = (HeaderGridView) findViewById(R.id.gridView);
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        listObj = (ArrayList<PlayDataEntry>) getActivity().getIntent().getSerializableExtra("list");
        name = getActivity().getIntent().getStringExtra("name");
        img = getActivity().getIntent().getStringExtra("img");
        fjs = getActivity().getIntent().getStringExtra("fjs");

        LogUtils.i("listObj================", listObj.toString());
        LogUtils.i("name========================", name);

        adapter = new CommonAdapter<PlayDataEntry>(getActivity(), R.layout.gridview_layout_box) {
            @Override
            protected void convert(ViewHolder viewHolder, PlayDataEntry entry, int i) {

                ImageView iv_item = viewHolder.findViewById(R.id.iv_item);
                TextView tv_num = viewHolder.findViewById(R.id.tv_num);
                TextView tv_name = viewHolder.findViewById(R.id.tv_name);
                TextView random_num = viewHolder.findViewById(R.id.random_num);
                int min = 5;
                int max = 999;
                Random random = new Random();
                int num = random.nextInt(max) % (max - min + 1) + min;
                random_num.setText(num + "");

//                play_url = entry.getPlay_url();
//                String logourl = entry.getLogourl();
//                LogUtils.i("logourl=====================", logourl);
//                String nickname = entry.getNickname();
//                String userid = entry.getUserid();
//                Glide.with(getActivity()).load(logourl).error(R.mipmap.error_img).into(iv_item);
//                tv_name.setText(nickname);
            }
        };
        View headerView = getLayoutInflater(R.layout.layout_top_box);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tvName = (TextView) headerView.findViewById(R.id.name);
        tvNum = (TextView) headerView.findViewById(R.id.tv_num);
        icon = (ImageView) headerView.findViewById(R.id.icon);
        tvName.setText(name);
        tvNum.setText(fjs);
        LogUtils.i("img=============", img);
        Glide.with(getActivity()).load(img).error(R.mipmap.error_icon).into(icon);
        gridView.addHeaderView(headerView);
        gridView.setAdapter(adapter);
        adapter.addAll(listObj);
        setListener();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Utils.startVipPlay(getActivity(), adapter.getItem(position - 3).getPlay_url(), adapter.getItem(position - 3).getNickname(), adapter.getItem(position - 3).getLogourl());
//                Intent intent = new Intent(getActivity(), PlayerActivity.class);
//                intent.putExtra("url", adapter.getItem(position).getPlay_url());
//                LogUtils.i("===============url", adapter.getItem(position).getPlay_url());
//                startActivity(intent);
            }
        });
        if (isFirstEnter) {
            isFirstEnter = false;
            //触发自动刷新
            refreshLayout.autoRefresh();
        }
    }

    private void setListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                adapter.clear();
                adapter.addAll(listObj);
                adapter.notifyDataSetChanged();
                refreshlayout.finishRefresh(500);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(500);
                adapter.clear();
                adapter.addAll(listObj);
                adapter.notifyDataSetChanged();


            }
        });
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorYellow);
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle(name);
        toolbar.setRightImage(R.mipmap.refresh_icon);
        toolbar.setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLayout.autoRefresh();
            }
        });
        toolbar.setTitleColor(getColor(R.color.white));
        toolbar.setBgColor(getColor(R.color.colorYellow));
    }

}
