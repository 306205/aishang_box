package com.done.player.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.done.player.R;
import com.done.player.entry.MemberDataEntry;
import com.done.player.entry.UserEntry;
import com.done.player.fragment.me.LoginFragment;
import com.done.player.fragment.me.MissPasswordFragment;
import com.done.player.fragment.me.OpenVipFragment;
import com.done.player.util.NetWorkUtils;
import com.done.player.util.Utils;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.utils.LogUtils;
import com.zss.library.widget.CommonTextWidget;
import com.zss.library.widget.CommonWhiteDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by XDONE on 2017/11/27.
 */

public class MeFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout info;
    private CommonTextWidget tWidget1;
    private CommonTextWidget tWidget2;
    private CommonTextWidget tWidget3;
    private CommonTextWidget tWidget4;

    //TODO 设置电话地址
    private String telString = "10086";
    private TextView login_out;
    private LinearLayout ll_bottom;
    private TextView phone;
    private CircleImageView photo;
    private TextView tvTime;
    private String time;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_me;
    }

    @Override
    public void initView() {
        super.initView();
        info = (LinearLayout) findViewById(R.id.info);
        tWidget1 = (CommonTextWidget) findViewById(R.id.tWidget1);
        tWidget2 = (CommonTextWidget) findViewById(R.id.tWidget2);
        tWidget3 = (CommonTextWidget) findViewById(R.id.tWidget3);
        tWidget4 = (CommonTextWidget) findViewById(R.id.tWidget4);
        login_out = (TextView) findViewById(R.id.login_out);
        ll_bottom = (LinearLayout) findViewById(R.id.ll_bottom);
        phone = (TextView) findViewById(R.id.phone);
        photo = (CircleImageView) findViewById(R.id.photo);
        tvTime = (TextView) findViewById(R.id.time);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        EventBus.getDefault().register(this);
        refreshUI();
        //Glide.with(getActivity()).load(R.mipmap.icon1).into(photo);
        tWidget1.setOnClickListener(this);
        tWidget2.setOnClickListener(this);
        tWidget3.setOnClickListener(this);
        tWidget4.setOnClickListener(this);
        ll_bottom.setOnClickListener(this);
    }

    private void refreshUI() {
        UserEntry entry = Utils.getUserEntry();
        if (Utils.isLogin()) {
            login_out.setText("退出登录");
            phone.setText(entry.getMobile());
            String id = entry.getId();
            String token = entry.getToken();
            QueryVip(id, token);
        } else {
            login_out.setText("登 录");
            phone.setText("访客模式");
            tvTime.setText("");
        }
    }

    private void QueryVip(String id, String token) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("token", token);
        NetWorkUtils.postUrl(getActivity(), "http://dl.yxumall.com/admin.php/api/Member/vipState", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                int state = resObj.optInt("state");
                if (state == 1) {
                    if (resObj.optInt("vip_type") == 5) {
                        tvTime.setText("终生卡，永久使用");
                    } else {
                        String vip_expire = resObj.optString("vip_expire");
                        String time = Utils.getTime(vip_expire);
                        tvTime.setText(time);
                    }
                } else {
                    tvTime.setText("您还不是会员！");
                }
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
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tWidget1:
                if (Utils.isLogin()) {
                    intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, OpenVipFragment.class.getName());
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, LoginFragment.class.getName());
                    startActivity(intent);
                }
//                OpenVipFragment fragment = new OpenVipFragment();
//                addFragment(fragment);
                break;
            case R.id.tWidget2:
                break;
            case R.id.tWidget3:
                if (ContextCompat.checkSelfPermission(getBaseActivity(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // 没有获得授权，申请授权
                    //给用户一个申请权限的解释，该方法只有在用户在上一次已经拒绝过你的这个权限申请
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getBaseActivity(),
                            Manifest.permission.CALL_PHONE)) {
                        // 返回值：
//                          如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
//                          如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
//                          如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
                        // 弹窗需要解释为何需要该权限，再次请求授权
                        Toast.makeText(getBaseActivity(), "请授权！", Toast.LENGTH_LONG).show();

                        // 帮跳转到该应用的设置界面，让用户手动授权
                        Intent intent1 = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                        intent1.setData(uri);
                        startActivity(intent1);
                    } else {
                        // 不需要解释为何需要该权限，直接请求授权
                        ActivityCompat.requestPermissions(getBaseActivity(),
                                new String[]{Manifest.permission.CALL_PHONE},
                                1);
                    }
                } else {
                    // 已经获得授权，可以打电话
                    CallPhone();
                }
                break;
            case R.id.tWidget4:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, MissPasswordFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.ll_bottom:
                if (Utils.isLogin()) {
                    final CommonWhiteDialog dialog = new CommonWhiteDialog(getActivity());
//                    TextView textView = new TextView(getActivity());
//                    textView.setPadding(10, 10, 10, 10);
//                    textView.setText("");
//                    dialog.setMiddleView(textView);
                    dialog.setTitle("账号管理");
                    dialog.setContentText("你确定要退出此账号吗？");
                    dialog.setOnClickConfirmListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HashMap<String, String> map = new HashMap<>();
                            UserEntry entry = Utils.getUserEntry();
                            String token = entry.getToken();
                            String id = entry.getId();
                            map.put("token", token);
                            map.put("id", id);
                            NetWorkUtils.postUrl(getActivity(), "http://dl.yxumall.com/admin.php/api/Member/loginOut", map, new NetWorkUtils.IListener() {
                                @Override
                                public void onSuccess(String result, JSONObject resObj) {
                                    Utils.saveUserEntry(null);
                                    Utils.saveVipEntry(null);
                                    refreshUI();
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
                                    LogUtils.i("done==========", "方法运行了");
                                    refreshUI();
                                }
                            });
                        }
                    });
                    dialog.setOnClickCancelListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();

                        }
                    });
                    dialog.show();
                } else {
                    intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, LoginFragment.class.getName());
                    startActivity(intent);
                }

        }
    }

    private void CallPhone() {
        Intent intent = new Intent(); // 意图对象：动作 + 数据
        intent.setAction(Intent.ACTION_CALL); // 设置动作
        Uri data = Uri.parse("tel:" + telString); // 设置数据
        intent.setData(data);
        startActivity(intent); // 激活Activity组件
    }

    // 处理权限申请的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 授权成功，继续打电话
                    CallPhone();
                } else {
                    // 授权失败！
                    Toast.makeText(getBaseActivity(), "授权失败！", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetTime(MemberDataEntry memberDataEntry) {
        UserEntry userEntry = Utils.getUserEntry();
        String id = userEntry.getId();
        String token = userEntry.getToken();
        QueryVip(id, token);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
