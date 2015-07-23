package com.example.txwl_first.bean;

import com.google.gson.annotations.SerializedName;

public class MobileInfoBean {

    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("opcode")
    private String opcode;
    @SerializedName("apprid")
    private String apprid;
    @SerializedName("status")
    private String status;
    @SerializedName("mobileinfo")
    private MobileInfoItemBean mobileInfoItemBeans;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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

    public MobileInfoItemBean getMobileInfoItemBeans() {
        return mobileInfoItemBeans;
    }

    public void setMobileInfoItemBeans(MobileInfoItemBean mobileInfoItemBeans) {
        this.mobileInfoItemBeans = mobileInfoItemBeans;
    }
}
