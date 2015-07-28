package com.example.txwl_first.bean;

import com.google.gson.annotations.SerializedName;

//保存充值记录
public class AddRechargeBean {

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
    private AddRechargeItemBean[] recharge;


}
