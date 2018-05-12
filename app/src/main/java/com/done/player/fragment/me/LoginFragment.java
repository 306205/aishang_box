package com.done.player.fragment.me;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.done.player.MainActivity;
import com.done.player.R;
import com.done.player.entry.MemberDataEntry;
import com.done.player.entry.UserEntry;
import com.done.player.util.EditTextUtils;
import com.done.player.util.GsonUtils;
import com.done.player.util.NetWorkUtils;
import com.done.player.util.Utils;
import com.google.gson.reflect.TypeToken;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.CommonToastUtils;
import com.zss.library.utils.DPUtils;
import com.zss.library.utils.LogUtils;
import com.zss.library.utils.MD5Utils;
import com.zss.library.utils.SharedPrefUtils;
import com.zss.library.widget.CommonEditWidget;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by XDONE on 2017/11/27.
 */

public class LoginFragment extends BaseFragment implements View.OnClickListener {

    private CommonEditWidget eWidget1;
    private CommonEditWidget eWidget2;
    private TextView missPassword;
    private TextView login;
    private TextView register;
    private String imei;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_login;
    }

    @Override
    public void initView() {
        super.initView();
        eWidget1 = (CommonEditWidget) findViewById(R.id.eWidget1);
        eWidget2 = (CommonEditWidget) findViewById(R.id.eWidget2);
        missPassword = (TextView) findViewById(R.id.miss_password);
        login = (TextView) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        EventBus.getDefault().register(this);

        int dp = DPUtils.dip2px(getContext(), 10);
        eWidget1.setLeftImageResource(R.mipmap.phone_icon);
        eWidget1.setHint("请输入手机号码");
        eWidget1.setCenterPadding(dp, 0, 0, 0);
        EditTextUtils.setPhoneAcceptedChars(eWidget1.getEditText());

        eWidget2.setLeftImageResource(R.mipmap.password_icon);
        eWidget2.setHint("请输入密码");
        eWidget2.setCenterPadding(dp, 0, 0, 0);
        EditTextUtils.setPasswordAcceptedChars(eWidget2.getEditText());
//        verifyPermissions(getActivity(), Manifest.permission.READ_PHONE_STATE,
//                new String[]{Manifest.permission.READ_PHONE_STATE}
//                , 0x01, new PermissionCallBack() {
//                    @Override
//                    public void onGranted() {
//                        try {
//                            TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(getActivity().TELEPHONY_SERVICE);
//                            imei = telephonyManager.getDeviceId();
//                            LogUtils.i("cId=============123", imei);
//                            SharedPrefUtils prefUtils1 = Utils.getSharedPreDataFile();
//                            prefUtils1.put("imei", imei);
//                            //在次做个验证，也不是什么时候都能获取到的啊
//                            if (imei == null) {
//                                //CommonToastUtils.showInCenterToast(getActivity(), "请打开权限");
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onDenied() {
//
//                    }
//                });

        missPassword.setOnClickListener(this);
        login.setOnClickListener(this);
        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int vId = v.getId();
        Intent intent;
        switch (vId) {
            case R.id.miss_password:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, MissPasswordFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.login:
                if (TextUtils.isEmpty(eWidget1.getText())) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_phone);
                    return;
                }
                if (eWidget1.getText().startsWith("1") && eWidget1.getText().length() != 11) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_ok_phone);
                    return;
                }
                if (eWidget1.getText().startsWith("+") && eWidget1.getText().length() != 14) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_ok_phone);
                    return;
                }
                if (TextUtils.isEmpty(eWidget2.getText())) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_pwd);
                    return;
                }
                if (eWidget2.getText().length() < 6) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_pwd_length);
                    return;
                }

                goLogin();
                break;
            case R.id.register:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, RegisterFragment.class.getName());
                startActivity(intent);
                break;
        }
    }

    private void goLogin() {
        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", eWidget1.getText().toString());
        String md5Str = MD5Utils.getMD5String(eWidget2.getText().toString());
//        try {
//            md5Str = MyMD5.bytesToHex(BaseApp.md5.encrypt(eWidget2.getText().toString()));
//            LogUtils.i("md5Str================", md5Str);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        LogUtils.i("md5Str================", md5Str);
        map.put("passwd", md5Str);
        NetWorkUtils.postUrl(getActivity(), "http://dl.yxumall.com/admin.php/api/Member/login",
                map, new NetWorkUtils.IListener() {
                    @Override
                    public void onSuccess(String result, JSONObject resObj) {
                        SharedPrefUtils prefUtils = Utils.getSharedPrefCommonFile();
                        SharedPrefUtils prefUtils1 = Utils.getSharedPreDataFile();
                        prefUtils.put("isLogin", true);
                        String data = resObj.optString("data");
                        UserEntry entry = GsonUtils.getObjFromJSON(data, UserEntry.class);
                        String member_data = resObj.optString("member_data");
                        List<MemberDataEntry> list = GsonUtils.getListFromJSON(member_data, new TypeToken<ArrayList<MemberDataEntry>>() {
                        }.getType());
                        LogUtils.i("list================", list.toString());
                        MemberDataEntry dataEntry = list.get(0);
                        LogUtils.i("dataEntry================", dataEntry.getMsg() + dataEntry.getMember_code());
                        prefUtils1.put("dataEntry", dataEntry);
                        String msg = resObj.optString("msg");
                        LogUtils.i("msg================", msg);
                        prefUtils.put("user", entry);
                        EventBus.getDefault().post(entry);
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }

                    @Override
                    public void onError(String result, String code, String msg) {
                        if ("token错误".equals(msg) || "token已过期".equals(msg)) {
                            getActivity().finish();
                            Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                            intent.putExtra(ZFrameActivity.CLASS_NAME, LoginFragment.class.getName());
                            startActivity(intent);
                        }
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRegisterSuccess(String phone) {
        CommonToastUtils.showInCenterToast(getActivity(), "注册成功");
        eWidget1.setText(phone);
        eWidget2.setText("");
    }


    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorYellow);
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("登 录");
        toolbar.setBgColor(getColor(R.color.colorYellow));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
