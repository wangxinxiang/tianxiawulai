package com.example.txwl_first.bean;

import com.google.gson.annotations.SerializedName;

public class GetMyInfoItemBean {


    @SerializedName("userid")
    private String userid;
    @SerializedName("name")
    private String name;
    @SerializedName("registid")
    private String registid;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("account")
    private String account;
    @SerializedName("repaydate")
    private String repaydate;
    @SerializedName("ownerheadimg")
    private String ownerheadimg;
    @SerializedName("status2")
    private String status2;
    @SerializedName("date")
    private String date;
    @SerializedName("registtype")
    private String registtype;
    @SerializedName("loandate")
    private String loandate;
    @SerializedName("annualrate")
    private String annualrate;
    @SerializedName("registname")
    private String registname;


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistid() {
        return registid;
    }

    public void setRegistid(String registid) {
        this.registid = registid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRepaydate() {
        return repaydate;
    }

    public void setRepaydate(String repaydate) {
        this.repaydate = repaydate;
    }

    public String getOwneridimg() {
        return ownerheadimg;
    }

    public void setOwneridimg(String owneridimg) {
        this.ownerheadimg = owneridimg;
    }

    public String getStatus2() {
        return status2;
    }

    public void setStatus2(String status2) {
        this.status2 = status2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRegisttype() {
        return registtype;
    }

    public void setRegisttype(String registtype) {
        this.registtype = registtype;
    }

    public String getLoandate() {
        return loandate;
    }

    public void setLoandate(String loandate) {
        this.loandate = loandate;
    }

    public String getAnnualrate() {
        return annualrate;
    }

    public void setAnnualrate(String annualrate) {
        this.annualrate = annualrate;
    }

    public String getRegistname() {
        return registname;
    }

    public void setRegistname(String registname) {
        this.registname = registname;
    }
}
