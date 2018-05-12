package com.done.player.entry;

import java.io.Serializable;

/**
 * Created by XDONE on 2017/12/20.
 */

public class VipEntry implements Serializable{

    /**
     * vip_type : 2
     * vip_expire : 1521532594
     */

    private int vip_type;
    private String vip_expire;
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getVip_type() {
        return vip_type;
    }

    public void setVip_type(int vip_type) {
        this.vip_type = vip_type;
    }

    public String getVip_expire() {
        return vip_expire;
    }

    public void setVip_expire(String vip_expire) {
        this.vip_expire = vip_expire;
    }
}
