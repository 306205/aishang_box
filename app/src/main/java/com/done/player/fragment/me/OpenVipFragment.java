package com.done.player.fragment.me;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.done.player.R;
import com.done.player.entry.MemberDataEntry;
import com.done.player.entry.UserEntry;
import com.done.player.util.GsonUtils;
import com.done.player.util.NetWorkUtils;
import com.done.player.util.Utils;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.CommonToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by XDONE on 2017/12/15.
 */

public class OpenVipFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    private ImageView img5;
    private EditText inputPwd;
    private TextView renewal;
    private JSONObject cardUrl;

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
    private TextView phone_num;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_open_vip;
    }


    @Override
    public void initView() {
        super.initView();
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);
        img5 = (ImageView) findViewById(R.id.img5);
        inputPwd = (EditText) findViewById(R.id.input_pwd);
        renewal = (TextView) findViewById(R.id.renewal);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.id_swipe_ly);
        phone_num = (TextView) findViewById(R.id.phone_num);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        getVipData();
        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
        img4.setOnClickListener(this);
        img5.setOnClickListener(this);
        renewal.setOnClickListener(this);
        mSwipeLayout.setOnRefreshListener(this);
        //LogUtils.i("cardUrl", cardUrl.toString());
        //
    }

    private void getVipData() {
        HashMap<String, String> map = new HashMap<>();
        final UserEntry entry = Utils.getUserEntry();
        String token = entry.getToken();
        String id = entry.getId();
        map.put("token", token);
        map.put("id", id);
        NetWorkUtils.postUrl(getActivity(), " http://dl.yxumall.com/admin.php/api/member/cardUrl", map,
                new NetWorkUtils.IListener() {
                    @Override
                    public void onSuccess(String result, JSONObject resObj) {
                        cardUrl = resObj.optJSONObject("card_url");
                        phone_num.setText("QQ/微信:" + cardUrl.optString("mobile"));
                        mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 0);
                    }

                    @Override
                    public void onError(String result, String code, String msg) {
                        mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 0);

                    }
                });
    }

    @Override
    public void onClick(final View v) {
        Intent intent;
        Uri content_url;
        switch (v.getId()) {
            case R.id.img1:
                intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                if (!TextUtils.isEmpty(cardUrl.optString("monthCard"))) {
                    content_url = Uri.parse(cardUrl.optString("monthCard"));
                    intent.setData(content_url);
                    startActivity(intent);
                } else {
                    CommonToastUtils.showInCenterToast(getActivity(), "代理没有添加发卡链接");
                }

                break;
            case R.id.img2:
                intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                if (!TextUtils.isEmpty(cardUrl.optString("quarterCard"))) {
                    content_url = Uri.parse(cardUrl.optString("quarterCard"));
                    intent.setData(content_url);
                    startActivity(intent);
                } else {
                    CommonToastUtils.showInCenterToast(getActivity(), "代理没有添加发卡链接");
                }
                break;
            case R.id.img3:
                intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                if (!TextUtils.isEmpty(cardUrl.optString("half_yearCard"))) {
                    content_url = Uri.parse(cardUrl.optString("half_yearCard"));
                    intent.setData(content_url);
                    startActivity(intent);
                } else {
                    CommonToastUtils.showInCenterToast(getActivity(), "代理没有添加发卡链接");
                }

                break;
            case R.id.img4:
                intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                if (!TextUtils.isEmpty(cardUrl.optString("yearCard"))) {
                    content_url = Uri.parse(cardUrl.optString("yearCard"));
                    intent.setData(content_url);
                    startActivity(intent);
                } else {
                    CommonToastUtils.showInCenterToast(getActivity(), "代理没有添加发卡链接");
                }

                break;
            case R.id.img5:
                intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                if (!TextUtils.isEmpty(cardUrl.optString("longCard"))) {
                    content_url = Uri.parse(cardUrl.optString("longCard"));
                    intent.setData(content_url);
                    startActivity(intent);
                } else {
                    CommonToastUtils.showInCenterToast(getActivity(), "代理没有添加发卡链接");
                }
                break;
            case R.id.renewal:
                HashMap<String, String> map = new HashMap<>();
                final UserEntry entry = Utils.getUserEntry();
                String token = entry.getToken();
                String id = entry.getId();
                map.put("token", token);
                map.put("id", id);
                map.put("number", inputPwd.getText().toString());
                NetWorkUtils.postUrl(getActivity(), "http://dl.yxumall.com/admin.php/api/Member/cardCode", map, new NetWorkUtils.IListener() {
                    @Override
                    public void onSuccess(String result, JSONObject resObj) {
                        String data = resObj.optString("data");
                        MemberDataEntry dataEntry = GsonUtils.getObjFromJSON(data, MemberDataEntry.class);
                        String vipExpire = dataEntry.getVip_expire();
                        int vip_type = dataEntry.getVip_type();
                        String msg = resObj.optString("msg");
                        Long aLong = Long.valueOf(vipExpire);
                        CommonToastUtils.showInCenterToast(getActivity(), msg + "充值成功");
                        Date date = new Date(aLong * 1000);
                        String time = new SimpleDateFormat("yyyy-MM-dd").format(date);
                        EventBus.getDefault().post(dataEntry);
                        getActivity().finish();
                    }

                    @Override
                    public void onError(String result, String code, String msg) {
                        if ("token错误".equals(msg) || "token已过期".equals(msg)) {
                            getActivity().finish();
                            Utils.saveUserEntry(null);
                            Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                            intent.putExtra(ZFrameActivity.CLASS_NAME, LoginFragment.class.getName());
                            startActivity(intent);
                        }
                    }
                });
                break;
            default:
                break;
        }
    }


    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorYellow);
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("会员续费");
        toolbar.setTitleColor(getColor(R.color.white));
        toolbar.setBgColor(getColor(R.color.colorYellow));
    }

    @Override
    public void onRefresh() {
        getVipData();
    }
}
