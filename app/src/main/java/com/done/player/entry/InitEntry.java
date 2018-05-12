package com.done.player.entry;

/**
 * Created by XDONE on 2017/12/21.
 */

public class InitEntry {


    /**
     * code : 1
     * apkUrl : http://www.baidu.com
     * msg : 尊敬的爱尚宝盒客户,发现新版本APP，请您更新APP
     * content : 优化了........
     * version_id : 1.1
     */
    private String code;
    private String apkUrl;
    private String msg;
    private String content;
    private String version_id;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVersion_id() {
        return version_id;
    }

    public void setVersion_id(String version_id) {
        this.version_id = version_id;
    }
}
