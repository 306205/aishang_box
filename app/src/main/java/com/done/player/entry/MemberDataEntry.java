package com.done.player.entry;

import java.io.Serializable;

/**
 * Created by XDONE on 2017/12/20.
 */

public class MemberDataEntry  implements  Serializable {

    /**
     * member_code : 1
     * msg : vip未到期，继续使用
     */

    private int member_code;
    private String msg;
    private int state;
    private String vip_expire;
    private int vip_type;

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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getMember_code() {
        return member_code;
    }

    public void setMember_code(int member_code) {
        this.member_code = member_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
