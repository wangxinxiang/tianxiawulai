package com.example.txwl_first.bean;

import com.google.gson.annotations.SerializedName;

//保存充值记录
public class AddRechargeBean {

    @SerializedName("bankcode")
    private String bankcode;
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
    @SerializedName("recharge")
    private AddRechargeItemBean recharge;

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
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

    public AddRechargeItemBean getRecharge() {
        return recharge;
    }

    public void setRecharge(AddRechargeItemBean recharge) {
        this.recharge = recharge;
    }
}
