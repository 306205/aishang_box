package com.done.player.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.done.player.R;
import com.done.player.util.NetWorkUtils;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by XDONE on 2017/12/19.
 */

public class ReadFragment extends BaseFragment {

    private TextView tvNotice;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_read;
    }

    @Override
    public void initView() {
        super.initView();
        tvNotice = (TextView) findViewById(R.id.tvNotice);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        HashMap<String, String> map = new HashMap<>();
        NetWorkUtils.postUrl(getActivity(), "http://dl.yxumall.com/admin.php/api/Notice/text", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                String notice = resObj.optString("notice");
                tvNotice.setText(notice);
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorYellow);
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitleColor(getColor(R.color.white));
        toolbar.setTitle("用户必读");
        toolbar.setBgColor(getColor(R.color.colorYellow));
    }
}
