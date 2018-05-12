package com.done.player.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.done.player.R;
import com.done.player.entry.HotEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDONE on 2017/12/1
 */

public class RecyclerInfoAdapter extends RecyclerView.Adapter<RecyclerInfoAdapter.MyViewHolder> {

    private List<HotEntry> datas = new ArrayList<>();
    private Context context;
    private OnItemClickLitener mOnItemClickLitener;
    private int currentPos = 0;

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public void replaceAll(List<HotEntry> datas) {
        this.currentPos = 0;
        this.datas.clear();
        this.datas.addAll(datas);
        this.notifyDataSetChanged();
    }

    public RecyclerInfoAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recycleritem_info_image, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        HotEntry entry = datas.get(position);
        int icon = entry.getIcon();
        holder.image.setImageResource(icon);
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(holder.itemView, position);
                    currentPos = position;
                    notifyDataSetChanged();

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (datas == null) {
            return 0;
        }
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.image);
        }
    }

    public HotEntry getItem(int position) {
        return datas.get(position);
    }


    public int getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
        this.notifyDataSetChanged();
    }
}


