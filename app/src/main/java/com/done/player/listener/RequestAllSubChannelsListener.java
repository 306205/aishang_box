package com.done.player.listener;



import com.done.player.entry.SubChannelInfo;

import java.util.List;

/**
 * Created by XDONE
 */
public interface RequestAllSubChannelsListener {
    void onSuccess(List<SubChannelInfo> subChannelInfos);

    void onError();
}
