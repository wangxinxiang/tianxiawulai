package com.example.txwl_first.bean;

import com.google.gson.annotations.SerializedName;

public class BackInfoItemBean {

    @SerializedName("bankid")
    private String bankid;
    @SerializedName("userid")
    private String userid;
    @SerializedName("accountname")
    private String accountname;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("bankname")
    private String bankname;
    @SerializedName("banknumber")
    private String banknumber;
    @SerializedName("cardtype")
    private String cardtype;
    @SerializedName("cardstatus")
    private String cardstatus;
    @SerializedName("ID")
    private String ID;
    @SerializedName("createdate")
    private String createdate;
    @SerializedName("bank_code")
    private String bank_code;

    public String getBank_code() {
        return bank_code;
    }

    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }

    public String getBankid() {
        return bankid;
    }

    public void setBankid(String bankid) {
        this.bankid = bankid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBanknumber() {
        return banknumber;
    }

    public void setBanknumber(String banknumber) {
        this.banknumber = banknumber;
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    public String getCardstatus() {
        return cardstatus;
    }

    public void setCardstatus(String cardstatus) {
        this.cardstatus = cardstatus;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }
}
