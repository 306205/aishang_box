package com.done.player.listener;


import com.done.player.entry.RoomInfo;

/**
 * Created by XDONE
 *
 */
public interface RequestHeartRoomsListener {
    void onSuccess(RoomInfo roomInfo);

    void onError();
}
