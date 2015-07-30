package com.example.txwl_first.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/7/21.
 */
public class GetLaolaiListBean {

    @SerializedName("list")
    private GetLaolaiListItemBean[] list;
    @SerializedName("code")
    private String code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("opcode")
    private String opcode;
    @SerializedName("apprid")
    private String apprid;
    @SerializedName("status")
    private String status;

    public GetLaolaiListItemBean[] getList() {
        return list;
    }

    public void setList(GetLaolaiListItemBean[] list) {
        this.list = list;
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
}
