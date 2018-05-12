package com.done.player.module;

import android.content.Context;
import android.util.Log;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.done.player.db.RoomIdDatabaseHelper;
import com.done.player.entry.GsonAllSubChannels;
import com.done.player.entry.GsonDouyuRoom;
import com.done.player.entry.GsonDouyuRoomInfo;
import com.done.player.entry.GsonSubChannel;
import com.done.player.entry.RoomInfo;
import com.done.player.entry.SubChannelInfo;
import com.done.player.listener.NetworkRequest;
import com.done.player.listener.RequestAllSubChannelsListener;
import com.done.player.listener.RequestHeartRoomsListener;
import com.done.player.listener.RequestStreamUrlListener;
import com.done.player.listener.RequestSubChannelListener;
import com.done.player.util.BuildUrl;
import com.google.gson.Gson;
import com.zss.library.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDONE
 */
public class NetworkRequestImpl implements NetworkRequest {
    private static final String TAG = "NetworkRequestImpl";
    private Context mContext;
    private RequestQueue mRequestQueue;
    private RoomIdDatabaseHelper mRoomIdDB;

    public NetworkRequestImpl(Context context) {
        this.mContext = context;
        mRequestQueue = Volley.newRequestQueue(context);
        mRoomIdDB = new RoomIdDatabaseHelper(context, RoomIdDatabaseHelper.HEART_DB_NAME, null, 1);
    }

    private Boolean isSearchUrl(String url) {
        return url.contains("mobileSearch");
    }

    @Override
    public void getSubChannel(String url, final RequestSubChannelListener listener) {
        final String urlTemp = url;
        StringRequest request = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<RoomInfo> roomInfos;
                        if (isSearchUrl(urlTemp)) {
                            roomInfos = handleSearchResponse(response);
                        } else {
                            roomInfos = handlerSubChannelResponse(response);
                        }
                        listener.onSuccess(roomInfos);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError();
            }
        });
        mRequestQueue.add(request);
    }

    private List<RoomInfo> handleSearchResponse(String response) {
        Gson gson = new Gson();
        List<RoomInfo> roomInfos = new ArrayList<>();
        try {
            GsonDouyuRoomInfo subChannel = gson.fromJson(response, GsonDouyuRoomInfo.class);
            for (GsonSubChannel.Room room : subChannel.getData().getRoom()) {
                RoomInfo roomInfo = new RoomInfo();
                roomInfo.setRoomId(room.getRoom_id());
                roomInfo.setRoomSrc(room.getRoomSrc());
                roomInfo.setRoomName(room.getRoom_name());
                roomInfo.setNickname(room.getNickname());
                roomInfo.setOnline(room.getOnline());
                roomInfos.add(roomInfo);
            }
        } catch (Exception e) {
            Log.e(TAG, "handlerSunChannelResponse: subChannel is null", e);
        }
        return roomInfos;
    }

    private List<RoomInfo> handlerSubChannelResponse(String response) {
        Gson gson = new Gson();
        List<RoomInfo> roomInfos = new ArrayList<>();
        try {
            GsonSubChannel subChannel = gson.fromJson(response, GsonSubChannel.class);
            for (GsonSubChannel.Room room : subChannel.getData()) {
                RoomInfo roomInfo = new RoomInfo();
                roomInfo.setRoomId(room.getRoom_id());
                roomInfo.setRoomSrc(room.getRoom_src());
                roomInfo.setRoomName(room.getRoom_name());
                roomInfo.setNickname(room.getNickname());
                roomInfo.setOnline(room.getOnline());
                roomInfos.add(roomInfo);
            }
        } catch (Exception e) {
            Log.e(TAG, "handlerSunChannelResponse: subChannel is null", e);
        }
        return roomInfos;
    }

    @Override
    public void getStreamUrl(final int roomId, final RequestStreamUrlListener listener) {
        String url = BuildUrl.getDouyuRoomUrl(roomId);
        LogUtils.i("url=============roomId", url);
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response);
                        Gson gson = new Gson();
                        try {
                            GsonDouyuRoom roomInfo = gson.fromJson(response, GsonDouyuRoom.class);
                            LogUtils.i("roomInfo===============url", roomInfo.toString());
                            LogUtils.i("roomInfo===============url", roomInfo.getData().toString());

                            String url = roomInfo.getData().getHls_url();
                            listener.onSuccess(roomId, url);
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse: roomInfo is null", e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError();
            }
        });

        mRequestQueue.add(request);
    }

    @Override
    public void getAllSubChannels(final RequestAllSubChannelsListener listener) {
        String url = BuildUrl.getDouyuAllSubChannels();
        StringRequest request = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<SubChannelInfo> subChannelInfos = handlerAllSubChannelsResponse(response);
                        listener.onSuccess(subChannelInfos);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError();
            }
        });
        mRequestQueue.add(request);
    }

    private List<SubChannelInfo> handlerAllSubChannelsResponse(String response) {
        List<SubChannelInfo> subChannelInfos = new ArrayList<>();
        Gson gson = new Gson();
        try {
            GsonAllSubChannels allSubChannel = gson.fromJson(response, GsonAllSubChannels.class);
            for (GsonAllSubChannels.Data gsonData : allSubChannel.getData()) {
                SubChannelInfo subChannelInfo = new SubChannelInfo();
                subChannelInfo.setTagId(gsonData.getTag_id());
                subChannelInfo.setTagName(gsonData.getTag_name());
                subChannelInfo.setIconUrl(gsonData.getIcon_url());

                subChannelInfos.add(subChannelInfo);
            }
        } catch (Exception e) {
            Log.e(TAG, "handlerAllSubChannelsResponse: allSubChannel is null", e);
        }
        return subChannelInfos;
    }

    @Override
    public void getHeartRooms(final RequestHeartRoomsListener listener) {
        mRequestQueue.cancelAll(null);
        List<Integer> roomIds = mRoomIdDB.getRoomIds();
        for (int roomId : roomIds) {
            String url = BuildUrl.getDouyuRoom(roomId);
            StringRequest request = new StringRequest(url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            RoomInfo roomInfo = handlerHeartRoomsResponse(response);
                            if (roomInfo != null) {
                                listener.onSuccess(roomInfo);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.onError();
                }
            });
            mRequestQueue.add(request);
        }
    }

    private RoomInfo handlerHeartRoomsResponse(String response) {
        Gson gson = new Gson();
        try {
            GsonDouyuRoomInfo subChannel = gson.fromJson(response, GsonDouyuRoomInfo.class);
            GsonSubChannel.Room room = subChannel.getData().getRoom().get(0);
            RoomInfo roomInfo = new RoomInfo();
            roomInfo.setRoomId(room.getRoom_id());
            roomInfo.setRoomSrc(room.getRoom_src());
            roomInfo.setRoomName(room.getRoom_name());
            roomInfo.setNickname(room.getNickname());
            roomInfo.setOnline(room.getOnline());

            return roomInfo;
        } catch (Exception e) {
            Log.e(TAG, "handlerHeartRoomsResponse: gsonRoomInfo is error", e);
        }
        return null;
    }
}
