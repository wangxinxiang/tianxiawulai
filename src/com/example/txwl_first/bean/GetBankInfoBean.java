package com.example.txwl_first.bean;

import com.google.gson.annotations.SerializedName;
//取银行卡信息
public class GetBankInfoBean   {

    @SerializedName("accountMoney")
    private String accountMoney;
    @SerializedName("freezeMoney")
    private String freezeMoney;
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
    @SerializedName("list")
    private BackInfoItemBean[] list;

    public String getAccountMoney() {
        return accountMoney;
    }

    public void setAccountMoney(String accountMoney) {
        this.accountMoney = accountMoney;
    }

    public String getFreezeMoney() {
        return freezeMoney;
    }

    public void setFreezeMoney(String freezeMoney) {
        this.freezeMoney = freezeMoney;
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

    public BackInfoItemBean[] getList() {
        return list;
    }

    public void setList(BackInfoItemBean[] list) {
        this.list = list;
    }
}
