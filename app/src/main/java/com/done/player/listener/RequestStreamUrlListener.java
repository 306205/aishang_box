package com.done.player.listener;

/**
 * Created by XDONE
 *
 */
public interface RequestStreamUrlListener {
    void onSuccess(int roomId, String url);

    void onError();
}
