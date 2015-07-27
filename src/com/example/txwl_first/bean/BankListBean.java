package com.example.txwl_first.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/5/29.
 */
public class BankListBean {
    @SerializedName("Id")
    private String Id;
    @SerializedName("BankName")
    private String BankName;
    @SerializedName("Arrange")
    private String Arrange;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    public String getArrange() {
        return Arrange;
    }

    public void setArrange(String arrange) {
        Arrange = arrange;
    }
}
