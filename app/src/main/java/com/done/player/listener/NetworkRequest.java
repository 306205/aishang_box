package com.done.player.listener;

/**
 * Created by XDONE
 *
 */
public interface NetworkRequest {
    void getSubChannel(String url, RequestSubChannelListener listener);

    void getStreamUrl(int roomId, RequestStreamUrlListener listener);

    void getAllSubChannels(RequestAllSubChannelsListener listener);

    void getHeartRooms(RequestHeartRoomsListener listener);


}
