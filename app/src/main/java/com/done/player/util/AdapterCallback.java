package com.done.player.util;

import android.view.View;

/**
 * Created by XDONE
 *
 */
public interface AdapterCallback {
    void onItemClick(View view, int position);
    void onItemLongClick(View view, int position);
    void onPositionChanged(int position);
}
