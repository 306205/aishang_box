package com.done.player.entry;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XDONE on 2017/12/14.
 */

public class UserEntry implements Serializable {

    /**
     * mobile : 15111237359
     * passwd : E10ADC3949BA59ABBE56E057F20F883E
     * code : aadd
     * member_id : 99
     * member_id : 102
     * token : d1c8987b3c23f3418d9374d875e6a76a2449282f
     * token_out : 1513845642
     */

    private String mobile;
    private String passwd;
    private String code;
    private String id;
    private int member_id;
    private String token;
    private int token_out;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_idX) {
        this.member_id = member_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getToken_out() {
        return token_out;
    }

    public void setToken_out(int token_out) {
        this.token_out = token_out;
    }
}
