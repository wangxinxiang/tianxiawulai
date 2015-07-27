package com.example.txwl_first.beifu;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 104468 on 2015/2/11.
 */
public class FastPay2Return {
    @SerializedName("sign")
    private String sign;
    @SerializedName("respCode")
    private String respCode;
    @SerializedName("customerId")
    private String customerId;
    @SerializedName("token")
    private String token;
    @SerializedName("sign_type")
    private String sign_type;
    @SerializedName("input_charset")
    private String input_charset;
    @SerializedName("service")
    private String service;
    @SerializedName("partner")
    private String partner;
    @SerializedName("out_trade_no")
    private String out_trade_no;
    @SerializedName("storablePan")
    private String storablePan;
    @SerializedName("respMessage")
    private String respMessage;


    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getInput_charset() {
        return input_charset;
    }

    public void setInput_charset(String input_charset) {
        this.input_charset = input_charset;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getStorablePan() {
        return storablePan;
    }

    public void setStorablePan(String storablePan) {
        this.storablePan = storablePan;
    }

    public String getRespMessage() {
        return respMessage;
    }

    public void setRespMessage(String respMessage) {
        this.respMessage = respMessage;
    }
}
