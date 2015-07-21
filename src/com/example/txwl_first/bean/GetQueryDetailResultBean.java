package com.example.txwl_first.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/7/21.
 */
public class GetQueryDetailResultBean {

    @SerializedName("list")
    private QueryDetailResultBean queryDetailResultBean;
    @SerializedName("date")
    private String data;
    @SerializedName("repayday")
    private String repayday;
    @SerializedName("loanday")
    private String loanday;
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

    public QueryDetailResultBean getQueryDetailResultBean() {
        return queryDetailResultBean;
    }

    public void setQueryDetailResultBean(QueryDetailResultBean queryDetailResultBean) {
        this.queryDetailResultBean = queryDetailResultBean;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getRepayday() {
        return repayday;
    }

    public void setRepayday(String repayday) {
        this.repayday = repayday;
    }

    public String getLoanday() {
        return loanday;
    }

    public void setLoanday(String loanday) {
        this.loanday = loanday;
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
