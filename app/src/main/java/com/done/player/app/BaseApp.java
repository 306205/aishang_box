package com.done.player.app;

import android.app.Application;
import android.content.Context;

import com.done.player.R;
import com.done.player.util.MyMD5;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.zss.library.https.OkHttpUtils;
import com.zss.library.utils.LogUtils;

import java.util.ArrayList;

/**
 * Created by XDONE on 2017/11/29.
 */

public class BaseApp extends Application {

    public static BaseApp instance;
    public static MyMD5 md5;


    public static BaseApp getInstance() {
        return instance;
    }

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorYellow, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpUtils.setCertificates(new ArrayList<String>());
        OkHttpUtils.setPostMode(OkHttpUtils.PostMode.FORM);
        //显示是否打印log
        LogUtils.setDebug(true);
        instance = this;
        md5 = new MyMD5();


    }
}
