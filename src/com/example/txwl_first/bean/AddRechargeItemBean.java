package com.example.txwl_first.bean;

import com.google.gson.annotations.SerializedName;

//保存充值记录
public class AddRechargeItemBean {

    @SerializedName("rechargeid")
    private String rechargeid;
    @SerializedName("billno")
    private String billno;
    @SerializedName("userid")
    private String userid;
    @SerializedName("rechargeaccount")
    private String rechargeaccount;
    @SerializedName("paytype")
    private String paytype;
    @SerializedName("createdate")
    private String createdate;
    @SerializedName("donedate")
    private String donedate;
    @SerializedName("STATUS")
    private String STATUS;
    @SerializedName("registid")
    private String registid;

    public String getRechargeid() {
        return rechargeid;
    }

    public void setRechargeid(String rechargeid) {
        this.rechargeid = rechargeid;
    }

    public String getBillno() {
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRechargeaccount() {
        return rechargeaccount;
    }

    public void setRechargeaccount(String rechargeaccount) {
        this.rechargeaccount = rechargeaccount;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getDonedate() {
        return donedate;
    }

    public void setDonedate(String donedate) {
        this.donedate = donedate;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getRegistid() {
        return registid;
    }

    public void setRegistid(String registid) {
        this.registid = registid;
    }
}
