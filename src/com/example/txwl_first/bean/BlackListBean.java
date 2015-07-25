package com.example.txwl_first.bean;

import com.google.gson.annotations.SerializedName;
public class BlackListBean {

    @SerializedName("code")
    private String code;
    @SerializedName("recordcount")
    private String recordcount;
    @SerializedName("msg")
    private String msg;
    @SerializedName("opcode")
    private String opcode;
    @SerializedName("apprid")
    private String apprid;
    @SerializedName("status")
    private String status;
    @SerializedName("list")
    private BlackListItemBean[] blackListItemBeans;

    public String getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(String recordcount) {
        this.recordcount = recordcount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getOpcode() {
        return opcode;
    }

    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }

    public String getApprid() {
        return apprid;
    }

    public void setApprid(String apprid) {
        this.apprid = apprid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BlackListItemBean[] getBlackListItemBeans() {
        return blackListItemBeans;
    }

    public void setBlackListItemBeans(BlackListItemBean[] blackListItemBeans) {
        this.blackListItemBeans = blackListItemBeans;
    }
}
