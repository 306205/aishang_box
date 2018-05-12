package com.done.player.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


import com.done.player.R;
import com.done.player.entry.ShowEntry;
import com.zss.library.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDONE on 2017/12/2.
 */

public class MyAdapter extends BaseAdapter {
    private Context context;
    private GridView mGv;
    private List<ShowEntry> pictures;
    private static int ROW_NUMBER = 3;

//    private int[] arrImages = new int[]{R.mipmap.show_icon1, R.mipmap.show_icon2, R.mipmap.show_icon3,
//            R.mipmap.show_icon4, R.mipmap.show_icon5, R.mipmap.show_icon6,
//            R.mipmap.show_icon7, R.mipmap.show_icon8, R.mipmap.show_icon9};
//    private int[] arrText = new int[]{R.string.show_text1, R.string.show_text2,
//            R.string.show_text3, R.string.show_text4, R.string.show_text5, R.string.show_text6,
//            R.string.show_text7, R.string.show_text8, R.string.show_text9};

    public MyAdapter(Context context, GridView gv, List<ShowEntry> pictures) {
        this.context = context;
        this.mGv = gv;
        this.pictures = pictures;
        if (pictures == null) {
            pictures = new ArrayList<>();
        }
//        for (int i = 0; i < 9; i++) {
//            ShowEntry pt = new ShowEntry(arrText[i] + "", arrImages[i], arrText[i]);
//            pictures.add(pt);
//        }
    }

    @Override
    public int getCount() {
        if (null != pictures) {
            return pictures.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return pictures.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(R.layout.gridview_layout_show, null);
            holder.iv = (ImageView) convertView.findViewById(R.id.iv_item);
            //设置显示图片
            holder.iv.setImageResource(pictures.get(position).getImageId());
            holder.tv = (TextView) convertView.findViewById(R.id.tv_name);
            //设置标题
            holder.tv.setText(pictures.get(position).getTitle());
            holder.tvNum = (TextView) convertView.findViewById(R.id.tv_num);
            holder.tvNum.setText(pictures.get(position).getNum());
            convertView.setTag(holder);
        } else {
            convertView.getTag();
        }

        //高度计算
        AbsListView.LayoutParams param = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (mGv.getHeight() - mGv.getHorizontalSpacing() * 2) / ROW_NUMBER);
        LogUtils.i("done============", mGv.getHorizontalSpacing() + "");

        convertView.setLayoutParams(param);
        return convertView;
    }

    class Holder {
        ImageView iv;
        TextView tv;
        TextView tvNum;
    }
}