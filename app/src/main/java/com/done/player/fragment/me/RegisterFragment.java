package com.done.player.fragment.me;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.done.player.R;
import com.done.player.entry.UserEntry;
import com.done.player.util.EditTextUtils;
import com.done.player.util.GsonUtils;
import com.done.player.util.NetWorkUtils;
import com.done.player.util.SmsSendHelper;
import com.done.player.util.Utils;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.CommonToastUtils;
import com.zss.library.utils.DPUtils;
import com.zss.library.utils.LogUtils;
import com.zss.library.utils.MD5Utils;
import com.zss.library.utils.SharedPrefUtils;
import com.zss.library.widget.CommonEditWidget;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by XDONE on 2017/11/28.
 */

public class RegisterFragment extends BaseFragment {

    private CommonEditWidget eWidget1;
    private CommonEditWidget eWidget2;
    private CommonEditWidget eWidget3;
    private CommonEditWidget eWidget4;
    private SmsSendHelper smsHelper;
    private TextView register;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_register;
    }

    @Override
    public void initView() {
        super.initView();

        eWidget1 = (CommonEditWidget) findViewById(R.id.eWidget1);
        eWidget2 = (CommonEditWidget) findViewById(R.id.eWidget2);
        eWidget3 = (CommonEditWidget) findViewById(R.id.eWidget3);
        eWidget4 = (CommonEditWidget) findViewById(R.id.eWidget4);
        register = (TextView) findViewById(R.id.register);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        int dp = DPUtils.dip2px(getContext(), 10);
        eWidget1.setLeftImageResource(R.mipmap.phone_icon);
        eWidget1.setHint("请输入手机号码");
        eWidget1.setCenterPadding(dp, 0, 0, 0);
        EditTextUtils.setPhoneAcceptedChars(eWidget1.getEditText());

        eWidget2.setLeftImageResource(R.mipmap.code_icon);
        eWidget2.setHint("请输入验证码");
        eWidget2.setCenterPadding(dp, 0, 0, 0);
        TextView rightView = (TextView) getLayoutInflater(R.layout.layout_send_sms);
        eWidget2.setRightView(rightView);
        eWidget2.setInputType(EditTextUtils.getNumberSignedType());
        EditTextUtils.setMaxLength(eWidget2.getEditText(), 6);

        eWidget3.setLeftImageResource(R.mipmap.password_icon);
        eWidget3.setHint("请输入密码");
        eWidget3.setCenterPadding(dp, 0, 0, 0);
        EditTextUtils.setPasswordAcceptedChars(eWidget3.getEditText());

        eWidget4.setLeftImageResource(R.mipmap.invite_code_icon);
        eWidget4.setHint("请输入邀请码");
        eWidget4.setCenterPadding(dp, 0, 0, 0);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(eWidget1.getText())) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_phone);
                    return;
                }
//                if (eWidget1.getText().length() != 11) {
//                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_ok_phone);
//                    return;
//                }
                if (TextUtils.isEmpty(eWidget2.getText())) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_vcode);
                    return;
                }
                if (TextUtils.isEmpty(eWidget3.getText())) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_pwd);
                    return;
                }
                if (eWidget3.getText().length() < 6) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_pwd_length);
                    return;
                }
                if (eWidget1.getText().startsWith("1") && eWidget1.getText().length() != 11) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_ok_phone);
                    return;
                }

                if (TextUtils.isEmpty(eWidget4.getText())) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_invite);
                    return;
                }

                goRegister();
            }
        });


        smsHelper = new SmsSendHelper(rightView, 60000, 1000, new SmsSendHelper.ICallback() {
            @Override
            public boolean onStartTimer() {
                return sendSms(eWidget1.getText());
            }

            @Override
            public void getCode() {
                HashMap<String, String> map = new HashMap<>();
                map.put("mobile", eWidget1.getText().toString());
                map.put("flag", 1 + "");
                NetWorkUtils.postUrl(getActivity(), "http://dl.yxumall.com/admin.php/api/Message/verify", map, new NetWorkUtils.IListener() {
                    @Override
                    public void onSuccess(String result, JSONObject resObj) {
                        String verCode = resObj.optString("ver_code");
                        LogUtils.i("verCode============", verCode);
                    }

                    @Override
                    public void onError(String result, String code, String msg) {
                        CommonToastUtils.showInCenterToast(getActivity(), msg);
                    }
                });

            }
        });
    }

    private void goRegister() {
        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", eWidget1.getText().toString());
        String md5Str = MD5Utils.getMD5String(eWidget3.getText().toString());
        LogUtils.i("md5Str================", md5Str);
        map.put("passwd", md5Str);
        map.put("ver_code", eWidget2.getText().toString());
        map.put("code", eWidget4.getText().toString());
        NetWorkUtils.postUrl(getActivity(), "http://dl.yxumall.com/admin.php/api/Member/register",
                map, new NetWorkUtils.IListener() {
                    @Override
                    public void onSuccess(String result, JSONObject resObj) {
                        String data = resObj.optString("data");
                        UserEntry entry = GsonUtils.getObjFromJSON(data, UserEntry.class);
                        SharedPrefUtils prefUtils = Utils.getSharedPrefCommonFile();
                        prefUtils.put("user", entry);
                        LogUtils.i("data=============", data.toString());
                        LogUtils.i("entry=============", entry.getMobile());
                        String msg = resObj.optString("msg");
                        LogUtils.i("msg================", msg);
                        EventBus.getDefault().post(entry.getMobile());
                        getActivity().finish();
                    }

                    @Override
                    public void onError(String result, String code, String msg) {


                    }
                });
    }

    public boolean sendSms(String phone) {
        if (TextUtils.isEmpty(phone)) {
            CommonToastUtils.showInCenterToast(getActivity(), "请输入手机号码");
            return false;
        }
        return true;
    }


    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorYellow);
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.colorYellow));
        toolbar.setTitleColor(getColor(R.color.white));
        toolbar.setTitle("注 册");
    }
}
