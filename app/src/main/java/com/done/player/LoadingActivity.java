package com.done.player;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.done.player.util.NetWorkUtils;
import com.zss.library.activity.BaseActivity;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.LogUtils;
import com.zss.library.utils.StringUtils;
import com.zss.library.widget.CommonWhiteDialog;

import org.json.JSONObject;

import java.util.HashMap;


/**
 * 启动页
 */
public class LoadingActivity extends BaseActivity {

    private Handler mHadler = new Handler();
    private TextView versionId;
    private boolean flag = false;
    private TextView tvSkip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonToolbar toolbar = getToolbar();
        toolbar.setVisibility(View.GONE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @Override
    public int getLayoutResId() {
        return com.done.player.R.layout.activity_loading;
    }

    @Override
    public void initView() {
        super.initView();
        versionId = (TextView) findViewById(com.done.player.R.id.versionId);
        tvSkip = (TextView) findViewById(com.done.player.R.id.tvSkip);
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        initData();
    }

    public void initData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("app_id", "1");
        NetWorkUtils.postUrl(getActivity(), "http://dl.yxumall.com/admin.php/api/Init/upgrade", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                String version_id = resObj.optString("version_id");
                if (!StringUtils.getVersionName(getActivity()).equals(version_id)) {
                    final String apkUrl = resObj.optString("apkUrl");
                    CommonWhiteDialog dialog = new CommonWhiteDialog(getActivity());
                    dialog.setTitle("版本更新");
                    dialog.setContentText("检测到新版本，是否更新？");
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            startPage();
                        }
                    });
                    dialog.setOnClickConfirmListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(apkUrl));
                            startActivity(i);
                            System.exit(0);
                        }
                    });

                    dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            startPage();
                        }
                    });
                    dialog.show();
                } else {
                    startPage();
                }

            }

            @Override
            public void onError(String result, String code, String msg) {
                LogUtils.i("LoadingActivity", "==============");
                startPage();
            }
        });

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });
    }

    public void startPage() {
        mHadler.postDelayed(new Runnable() {
            @Override
            public void run() {
                goHome();
            }
        }, 1000);
    }

    public void goHome() {
        if (!flag) {
            flag = true;
            startActivity(new Intent(LoadingActivity.this, MainActivity.class));
            finish();
        }
    }


    @Override
    public void onBackPressed() {

    }
}

