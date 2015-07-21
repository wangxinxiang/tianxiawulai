package com.example.txwl_first.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/7/20.
 *CallBackType是否有不诚信信息（1表示有，2表示没有，没有则从URL接口中获取数据)"
 * CallBackType":"1",
 "url":null,
 "code":100,
 "msg":"查找成功",
 "opcode":"GetUserListByway",
 "apprid":null,
 "status":"success"
 */
public class QueryResultBean {

    @SerializedName("list")
    private QueryResultItemBean[] getQueryResultListBean;
    @SerializedName("CallBackType")
    private String CallBackType;
    @SerializedName("url")
    private String url;
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

    public QueryResultItemBean[] getGetQueryResultListBean() {
        return getQueryResultListBean;
    }

    public void setGetQueryResultListBean(QueryResultItemBean[] getQueryResultListBean) {
        this.getQueryResultListBean = getQueryResultListBean;
    }

    public String getCallBackType() {
        return CallBackType;
    }

    public void setCallBackType(String callBackType) {
        CallBackType = callBackType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
