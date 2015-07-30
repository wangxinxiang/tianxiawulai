package com.example.txwl_first.bean;

import com.google.gson.annotations.SerializedName;

/*
用户类型 usertype： 1 老板 2 成员
总计数量 totalcount
总计金额 totalmoney
违约数量 brokencount
违约金额 brokenmoney
已还款数量 recallcount
已还款金额 recallmoney
待还款数量  unrecallcount
待还款金额  unrecallmoney
登记列表 registinfolist
 */
public class GetMyInfoBean {

    @SerializedName("registinfolist")
    private GetMyInfoItemBean[] registinfolist;

    @SerializedName("userid")
    private int userid;
    @SerializedName("usertype")
    private int usertype;

    @SerializedName("companyname")
    private String companyname;

    @SerializedName("totalcount")
    private String totalcount;
    @SerializedName("totalmoney")
    private String totalmoney;

    @SerializedName("brokencount")
    private String brokencount;
    @SerializedName("brokenmoney")
    private String brokenmoney;

    @SerializedName("recallcount")
    private String recallcount;
    @SerializedName("recallmoney")
    private String recallmoney;

    @SerializedName("unrecallcount")
    private String unrecallcount;
    @SerializedName("unrecallmoney")
    private String unrecallmoney;

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

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public GetMyInfoItemBean[] getRegistinfolist() {
        return registinfolist;
    }

    public void setRegistinfolist(GetMyInfoItemBean[] registinfolist) {
        this.registinfolist = registinfolist;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }

    public String getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(String totalcount) {
        this.totalcount = totalcount;
    }

    public String getTotalmoney() {
        return totalmoney;
    }

    public void setTotalmoney(String totalmoney) {
        this.totalmoney = totalmoney;
    }

    public String getBrokencount() {
        return brokencount;
    }

    public void setBrokencount(String brokencount) {
        this.brokencount = brokencount;
    }

    public String getBrokenmoney() {
        return brokenmoney;
    }

    public void setBrokenmoney(String brokenmoney) {
        this.brokenmoney = brokenmoney;
    }

    public String getRecallcount() {
        return recallcount;
    }

    public void setRecallcount(String recallcount) {
        this.recallcount = recallcount;
    }

    public String getRecallmoney() {
        return recallmoney;
    }

    public void setRecallmoney(String recallmoney) {
        this.recallmoney = recallmoney;
    }

    public String getUnrecallcount() {
        return unrecallcount;
    }

    public void setUnrecallcount(String unrecallcount) {
        this.unrecallcount = unrecallcount;
    }

    public String getUnrecallmoney() {
        return unrecallmoney;
    }

    public void setUnrecallmoney(String unrecallmoney) {
        this.unrecallmoney = unrecallmoney;
    }

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


}
