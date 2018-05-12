package com.done.player;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.done.player.fragment.MainFragment;
import com.done.player.fragment.MeFragment;
import com.done.player.fragment.ShowFragment2;
import com.done.player.fragment.TelevisionFragment1;
import com.zss.library.activity.BaseActivity;
import com.zss.library.tabhost.FragmentTabHost;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.CommonToastUtils;
import com.zss.library.utils.SystemBarTintManager;

//                                 _ooOoo_
//                                o8888888o
//                                88" . "88
//                                (| -_- |)
//                                O\  =  /O
//                             ____/`---'\____
//                           .'  \\|     |//  `.
//                          /  \\|||  :  |||//  \
//                         /  _||||| -:- |||||-  \
//                         |   | \\\  -  /// |   |
//                         | \_|  ''\---/''  |   |
//                         \  .-\__  `-`  ___/-. /
//                       ___`. .'  /--.--\  `. . __
//                    ."" '<  `.___\_<|>_/___.'  >'"".
//                   | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//                   \  \ `-.   \_ __\ /__ _/   .-` /  /
//              ======`-.____`-.___\_____/___.-`____.-'======
//                                 `=---='
//                  ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
//                         佛祖保佑        永无BUG
//                佛曰:
//                       写字楼里写字间，写字间里程序员；
//                       程序人员写程序，又拿程序换酒钱。
//                       酒醒只在网上坐，酒醉还来网下眠；
//                       酒醉酒醒日复日，网上网下年复年。
//                       但愿老死电脑间，不愿鞠躬老板前；
//                       奔驰宝马贵者趣，公交自行程序员。
//                       别人笑我忒疯癫，我笑自己命太贱；
//                       不见满街漂亮妹，哪个归得程序员？

public class MainActivity extends BaseActivity implements TabHost.OnTabChangeListener {
    private FragmentTabHost mTabHost;
    private View tab1, tab2, tab3, tab4;
    private CommonToolbar toolbar;
    int currentTab = -1;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//    }


    @Override
    public int getLayoutResId() {
        return com.done.player.R.layout.activity_main;
    }

    @Override
    public void initView() {
        super.initView();
        toolbar = getToolbar();

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getSupportFragmentManager(), com.done.player.R.id.realtabcontent);

        tab1 = getLayoutInflater(com.done.player.R.layout.layout_tab_item);
        TextView textTab = (TextView) tab1.findViewById(com.done.player.R.id.text_tab);
        textTab.setText("首页");
        tab2 = getLayoutInflater(com.done.player.R.layout.layout_tab_item);
        textTab = (TextView) tab2.findViewById(com.done.player.R.id.text_tab);
        textTab.setText("影视");
        tab3 = getLayoutInflater(com.done.player.R.layout.layout_tab_item);
        textTab = (TextView) tab3.findViewById(com.done.player.R.id.text_tab);
        textTab.setText("娱乐");
        tab4 = getLayoutInflater(com.done.player.R.layout.layout_tab_item);
        textTab = (TextView) tab4.findViewById(com.done.player.R.id.text_tab);
        textTab.setText("我的");


        ImageView imgTab1 = (ImageView) tab1.findViewById(com.done.player.R.id.img_tab);
        imgTab1.setImageResource(com.done.player.R.drawable.tab1);
        ImageView imgTab2 = (ImageView) tab2.findViewById(com.done.player.R.id.img_tab);
        imgTab2.setImageResource(com.done.player.R.drawable.tab2);
        ImageView imgTab3 = (ImageView) tab3.findViewById(com.done.player.R.id.img_tab);
        imgTab3.setImageResource(com.done.player.R.drawable.tab3);
        ImageView imgTab4 = (ImageView) tab4.findViewById(com.done.player.R.id.img_tab);
        imgTab4.setImageResource(com.done.player.R.drawable.tab4);

        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator(tab1), ShowFragment2.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator(tab2), TelevisionFragment1.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab3").setIndicator(tab3), MainFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab4").setIndicator(tab4), MeFragment.class, null);

        mTabHost.setOnTabChangedListener(this);
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        setTopTab1();
    }


    @Override
    public void onTabChanged(String tabId) {
        if (tabId.equals("tab1")) {
            setTopTab1();
        } else if (tabId.equals("tab2")) {
            setTopTab2();
        } else if (tabId.equals("tab3")) {
            setTopTab3();
        } else if (tabId.equals("tab4")) {
            setTopTab4();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("currentTab", mTabHost.getCurrentTab());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String tabId = mTabHost.getCurrentTabTag();
        if (tabId.equals("tab1")) {
            setTopTab1();
        } else if (tabId.equals("tab2")) {
            setTopTab2();
        } else if (tabId.equals("tab3")) {
            setTopTab3();
        } else if (tabId.equals("tab4")) {
            setTopTab4();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentTab = savedInstanceState.getInt("currentTab");
        if (mTabHost != null) {
            mTabHost.postDelayed(new Runnable() {
                @Override
                public void run() {
                    switch (currentTab) {
                        case 0:
                            setTopTab1();
                            break;
                        case 1:
                            setTopTab2();
                            break;
                        case 2:
                            setTopTab3();
                            break;
                        case 3:
                            setTopTab4();
                            break;
                    }
                    currentTab = -1;
                }
            }, 200);
        }
    }


    private void setTopTab1() {
        TextView title = (TextView) getLayoutInflater(com.done.player.R.layout.layout_tab_main_title);
        setStatusBarColor(com.done.player.R.color.colorYellow);
        title.setText("首页");
        toolbar.setMiddleView(title);
        toolbar.setBgRes(com.done.player.R.color.colorYellow);
        toolbar.setLeftShow(false);
        toolbar.setRightImage(com.done.player.R.mipmap.refresh_icon);

    }


    private void setTopTab2() {
        TextView title = (TextView) getLayoutInflater(com.done.player.R.layout.layout_tab_main_title);
        title.setText("影视");
        setStatusBarColor(com.done.player.R.color.colorYellow);
        title.setTextColor(Color.WHITE);
        toolbar.setMiddleView(title);
        toolbar.setBgRes(com.done.player.R.color.colorYellow);
        toolbar.setLeftShow(false);
        toolbar.setRightShow(false);
    }

    private void setTopTab3() {
        TextView title = (TextView) getLayoutInflater(com.done.player.R.layout.layout_tab_main_title);
        title.setText("娱乐");
        setStatusBarColor(com.done.player.R.color.colorYellow);
        toolbar.setMiddleView(title);
        toolbar.setBgRes(com.done.player.R.color.colorYellow);
        toolbar.setLeftShow(false);
        toolbar.setRightShow(false);
    }

    private void setTopTab4() {
        TextView title = (TextView) getLayoutInflater(com.done.player.R.layout.layout_tab_main_title);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        Drawable drawable = getResources().getDrawable(R.mipmap.top_bg);
        tintManager.setTintDrawable(drawable);
        toolbar.setBgRes(com.done.player.R.color.trans);
        title.setText("");
        toolbar.setMiddleView(title);
        toolbar.setLeftShow(false);
        toolbar.setRightShow(false);
    }

    @Override
    public void onBackPressed() {
        CommonToastUtils.exitClient(this, true);
    }


}