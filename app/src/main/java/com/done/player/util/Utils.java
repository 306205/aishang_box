package com.done.player.util;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.done.player.PlayerLiveActivity;
import com.done.player.R;
import com.done.player.app.BaseApp;
import com.done.player.entry.UserEntry;
import com.done.player.entry.VipEntry;
import com.done.player.fragment.me.LoginFragment;
import com.done.player.fragment.me.OpenVipFragment;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.ptr.PtrClassicFrameLayout;
import com.zss.library.utils.CommonToastUtils;
import com.zss.library.utils.LogUtils;
import com.zss.library.utils.SharedPrefUtils;
import com.zss.library.widget.CommonWhiteDialog;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by XDONE
 */

public class Utils {

    public static void commonError(Activity activity, String result, String code, String msg) {
        if (TextUtils.isEmpty(code) && TextUtils.isEmpty(msg)) {
            LogUtils.i("xdone", "2222222");
            CommonToastUtils.showInCenterToast(activity, activity.getResources().getString(R.string.tips_connect_server_timeout));
        } else {
            CommonToastUtils.showInCenterToast(activity, msg + "(" + code + ")");
        }
    }

    public static SharedPrefUtils getSharedPrefCommonFile() {
        return new SharedPrefUtils(BaseApp.getInstance(), "common_file");
    }

    public static SharedPrefUtils getSharedPreDataFile() {
        return new SharedPrefUtils(BaseApp.getInstance(), "data_file");
    }

    public static void saveUserEntry(UserEntry user) {
        SharedPrefUtils prefUtils = getSharedPrefCommonFile();
        prefUtils.put("user", user);
    }

    public static void saveVipEntry(VipEntry vipEntry) {
        SharedPrefUtils prefUtils = getSharedPrefCommonFile();
        prefUtils.put("vip", vipEntry);
    }


    public static VipEntry getVipEntry() {
        SharedPrefUtils prefUtils = getSharedPrefCommonFile();
        Object obj = prefUtils.get("vip");
        if (obj != null) {
            return (VipEntry) obj;
        } else {
            return null;
        }
    }

    public static UserEntry getUserEntry() {
        SharedPrefUtils prefUtils = getSharedPrefCommonFile();
        Object obj = prefUtils.get("user");
        if (obj != null) {
            return (UserEntry) obj;
        } else {
            return null;
        }
    }


    public static void startLogin(Activity activity, String className) {
        if (isLogin()) {
            if (!TextUtils.isEmpty(className)) {
                Intent intent = new Intent(activity, ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, className);
                activity.startActivity(intent);
            }
        } else {
            Intent intent = new Intent(activity, ZFrameActivity.class);
            intent.putExtra(ZFrameActivity.CLASS_NAME, LoginFragment.class.getName());
            activity.startActivity(intent);
        }
    }


    public static boolean isLogin() {
        return getUserEntry() != null;
    }


    public static void setEmptyView(GridView gridView) {
        setEmptyView(gridView, 0, null, null);
    }

    public static void setEmptyView(GridView gridView, int resId, String text, View.OnClickListener listener) {
        View emptyView = LayoutInflater.from(BaseApp.getInstance()).inflate(R.layout.listview_empty_page, null);
        ViewGroup parentView = (ViewGroup) gridView.getParent();
        if (parentView instanceof PtrClassicFrameLayout) {
            parentView = (ViewGroup) parentView.getParent();
        }
        parentView.addView(emptyView);
        if (parentView instanceof LinearLayout) {
            emptyView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
        } else if (parentView instanceof FrameLayout) {
            emptyView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT));
        } else if (parentView instanceof RelativeLayout) {
            emptyView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT));
        }
        ImageView tips_img = (ImageView) emptyView.findViewById(R.id.tips_img);
        TextView tips_text = (TextView) emptyView.findViewById(R.id.tips_text);
        if (resId > 0) {
            tips_img.setImageResource(resId);
        }
        if (!TextUtils.isEmpty(text)) {
            tips_text.setText(text);
        }
        emptyView.setOnClickListener(listener);
        gridView.setEmptyView(emptyView);
    }

    public static String getTime(String s) {
        Long aLong = Long.valueOf(s);
        Date date = new Date(aLong * 1000);
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }


    public static void startVipPlay(final Activity activity, final String url, final String name, final String logUrl) {
        if (isLogin()) {
            UserEntry entry = Utils.getUserEntry();
            String id = entry.getId();
            String token = entry.getToken();
            Map<String, String> map = new HashMap<>();
            map.put("id", id);
            map.put("token", token);
            NetWorkUtils.postUrl(activity, "http://dl.yxumall.com/admin.php/api/Member/vipState", map, new NetWorkUtils.IListener() {
                @Override
                public void onSuccess(String result, final JSONObject resObj) {
                    final int state = resObj.optInt("state");
                    if (state == 1) {
                        Intent intent = new Intent(activity, PlayerLiveActivity.class);
                        intent.putExtra("url", url);
                        intent.putExtra("name", name);
                        intent.putExtra("logUrl", logUrl);
                        intent.putExtra("state", state);
                        activity.startActivity(intent);
                    } else if (state == 0) {
                        if (!TextUtils.isEmpty(String.valueOf(resObj.optString("view_time"))) &&
                                resObj.optInt("view_time") > 0) {
                            final CommonWhiteDialog dialog = new CommonWhiteDialog(activity);
                            dialog.setTitle("提示");
                            dialog.setContentText("您可以试看一段时间");
                            dialog.setOnClickConfirmListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(activity, PlayerLiveActivity.class);
                                    intent.putExtra("url", url);
                                    intent.putExtra("name", name);
                                    intent.putExtra("logUrl", logUrl);
                                    intent.putExtra("state", state);
                                    intent.putExtra("view_time", resObj.optInt("view_time"));
                                    activity.startActivity(intent);
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
                            final CommonWhiteDialog dialog = new CommonWhiteDialog(activity);
                            dialog.setTitle("提示");
                            dialog.setContentText("请开通VIP");
                            dialog.setOnClickConfirmListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(activity, ZFrameActivity.class);
                                    intent.putExtra(ZFrameActivity.CLASS_NAME, OpenVipFragment.class.getName());
                                    intent.putExtra("state", state);
                                    activity.startActivity(intent);
                                }
                            });
                            dialog.setOnClickCancelListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                    }
                }

                @Override
                public void onError(String result, String code, String msg) {

                }
            });
        } else {
            Intent intent = new Intent(activity, ZFrameActivity.class);
            intent.putExtra(ZFrameActivity.CLASS_NAME, LoginFragment.class.getName());
            activity.startActivity(intent);
        }
    }
}
