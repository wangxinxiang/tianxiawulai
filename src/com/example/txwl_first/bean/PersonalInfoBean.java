package com.example.txwl_first.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/7/22.
 */
public class PersonalInfoBean {
    @SerializedName("userid")
    private String userid;
    @SerializedName("username")
    private String username;
    @SerializedName("userpwd")
    private String userpwd;
    @SerializedName("salt")
    private String salt;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("IDcard")
    private String IDcard;
    @SerializedName("realname")
    private String realname;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("accoutmoney")
    private String accoutmoney;
    @SerializedName("freezemoney")
    private String freezemoney;
    @SerializedName("stampcontent")
    private String stampcontent;
    @SerializedName("createtime")
    private String createtime;
    @SerializedName("userstatus")
    private String userstatus;
    @SerializedName("regip")
    private String regip;
    @SerializedName("headimage")
    private String headimage;
    @SerializedName("province")
    private String province;
    @SerializedName("city")
    private String city;
    @SerializedName("phone")
    private String phone;
    @SerializedName("address")
    private String address;
    @SerializedName("companyname")
    private String companyname;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpwd() {
        return userpwd;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIDcard() {
        return IDcard;
    }

    public void setIDcard(String IDcard) {
        this.IDcard = IDcard;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAccoutmoney() {
        return accoutmoney;
    }

    public void setAccoutmoney(String accoutmoney) {
        this.accoutmoney = accoutmoney;
    }

    public String getFreezemoney() {
        return freezemoney;
    }

    public void setFreezemoney(String freezemoney) {
        this.freezemoney = freezemoney;
    }

    public String getStampcontent() {
        return stampcontent;
    }

    public void setStampcontent(String stampcontent) {
        this.stampcontent = stampcontent;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUserstatus() {
        return userstatus;
    }

    public void setUserstatus(String userstatus) {
        this.userstatus = userstatus;
    }

    public String getRegip() {
        return regip;
    }

    public void setRegip(String regip) {
        this.regip = regip;
    }

    public String getHeadimage() {
        return headimage;
    }

    public void setHeadimage(String headimage) {
        this.headimage = headimage;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }
}
