package com.done.player.listener;



import com.done.player.entry.RoomInfo;

import java.util.List;

/**
 * Created by XDONE
 */
public interface RequestSubChannelListener {
    void onSuccess(List<RoomInfo> roomInfos);

    void onError();
}
