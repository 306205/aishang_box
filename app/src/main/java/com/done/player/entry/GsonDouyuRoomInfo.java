package com.done.player.entry;

import java.util.List;

/**
 *Created by XDONE
 *
 */
public class GsonDouyuRoomInfo {
    private int error;

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public static class Data {
        private List<GsonSubChannel.Room> room;

        public List<GsonSubChannel.Room> getRoom() {
            return room;
        }
    }
}
