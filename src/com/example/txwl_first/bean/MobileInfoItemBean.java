package com.example.txwl_first.bean;

import com.google.gson.annotations.SerializedName;

public class MobileInfoItemBean {

    @SerializedName("id")
    private int id;
    @SerializedName("userid")
    private String userid;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("validatenum")
    private String validatenum;
    @SerializedName("sendtime")
    private String sendtime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getValidatenum() {
        return validatenum;
    }

    public void setValidatenum(String validatenum) {
        this.validatenum = validatenum;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }
}
