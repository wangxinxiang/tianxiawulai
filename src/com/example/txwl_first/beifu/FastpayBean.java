package com.example.txwl_first.beifu;


import com.example.txwl_first.Util.MD5;

import java.text.Collator;
import java.util.*;

/**
 *
 *    params.put("service", "ebatong_getdyn_fastpay");//接口名称
 params.put("partner", "201502101818121388");//商户ID
 params.put("sign_type", "MD5");
 params.put("input_charset", "utf-8");
 //            params.put("customerId", "001");
 params.put("pan", "6212261208007246059");//银行卡号
 params.put("cardHolderName", "林燕军");//持卡人姓名
 *   params.put("idType", "0");
 params.put("cardHolderId", "330822198108100017");//持卡人证件号码
 params.put("phone", "15669592540");//手机号码
 params.put("out_trade_no", "330822198108100017");//商户订单号
 params.put("amount", "0.50");//商户订单号
 */
public class FastpayBean {
    private String amount;
    private String cardHolderId;
    private String cardHolderName;
    private String idType;
    private String input_charset;
    private String out_trade_no;
    private String card_no;             //银行卡号
    private String partner;
    private String phone;
    private String service;
    private String sign_type;

    //
    private String notify_url;
    private String spFlag;
    private String savePciFlag;
    private String token;
    private String payBatch;
    private String validCode;

    private String customerId;
    private String bankId;

    private String total_fee;
    private String exter_invoke_ip;
    private String anti_phishing_key;
    private String default_bank;
    private String subject;
    private String body;
    private String show_url;
    private String extra_common_param;
    private String extend_param;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getShow_url() {
        return show_url;
    }

    public void setShow_url(String show_url) {
        this.show_url = show_url;
    }

    public String getExtra_common_param() {
        return extra_common_param;
    }

    public void setExtra_common_param(String extra_common_param) {
        this.extra_common_param = extra_common_param;
    }

    public String getExtend_param() {
        return extend_param;
    }

    public void setExtend_param(String extend_param) {
        this.extend_param = extend_param;
    }

    public String getDefault_bank() {
        return default_bank;
    }

    public void setDefault_bank(String default_bank) {
        this.default_bank = default_bank;
    }

    public String getAnti_phishing_key() {
        return anti_phishing_key;
    }

    public void setAnti_phishing_key(String anti_phishing_key) {
        this.anti_phishing_key = anti_phishing_key;
    }

    public String getExter_invoke_ip() {
        return exter_invoke_ip;
    }

    public void setExter_invoke_ip(String exter_invoke_ip) {
        this.exter_invoke_ip = exter_invoke_ip;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCardHolderId() {
        return cardHolderId;
    }

    public void setCardHolderId(String cardHolderId) {
        this.cardHolderId = cardHolderId;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getInput_charset() {
        return input_charset;
    }

    public void setInput_charset(String input_charset) {
        this.input_charset = input_charset;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getPan() {
        return card_no;
    }

    public void setPan(String card_no) {
        this.card_no = card_no;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getSpFlag() {
        return spFlag;
    }

    public void setSpFlag(String spFlag) {
        this.spFlag = spFlag;
    }

    public String getSavePciFlag() {
        return savePciFlag;
    }

    public void setSavePciFlag(String savePciFlag) {
        this.savePciFlag = savePciFlag;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPayBatch() {
        return payBatch;
    }

    public void setPayBatch(String payBatch) {
        this.payBatch = payBatch;
    }

    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    /**
     *  private String amount;
     private String cardHolderId;
     private String cardHolderName;
     private String idType;
     private String input_charset;
     private String out_trade_no;
     private String pan;
     private String partner;
     private String service;
     private String sign_type;
     * @return
     */
    public String getNeedSignCode(){
        Map<String, String> temp = getContentMap();

        List<String> list = new ArrayList();
        for (Iterator<String> iterator = temp.keySet().iterator(); iterator.hasNext(); ) {
            String key = iterator.next();
            list.add(key);
        }
        Collections.sort(list, Collator.getInstance(Locale.ENGLISH));//
        StringBuffer needSign=new StringBuffer();
        int i=0;
        for (Iterator<String> iterator = list.iterator(); iterator.hasNext(); ) {
            String key = iterator.next();
            if (i!=list.size()-1) {
                needSign.append(key).append("=").append(temp.get(key)).append("&");
            }else{
                needSign.append(key).append("=").append(temp.get(key));
            }
            i++;
        }

        String mycode="OCYCB1ZTI4RAIGK48H0G6AIJ9AOC6Etbixyz";
        needSign.append(mycode);
        String string = needSign.toString();
        return MD5.getMD5Lower(string);
    }

    private Map<String, String> getContentMap() {
        Map<String,String> temp=new LinkedHashMap<String,String>();
        if (total_fee != null) {
            temp.put("total_fee", total_fee);
        }
        if (subject != null) {
            temp.put("subject", "");                //商品名称
        }
        if (body != null) {
            temp.put("body", "");               //商品描述
        }
        if (show_url != null) {
            temp.put("show_url", "");               //商品展示网址
        }
        if (extra_common_param != null) {
            temp.put("extra_common_param", "");         //公用回传参数
        }
        if (extend_param != null) {
            temp.put("extend_param", "");           //公用业务扩展参数
        }
        if(amount!=null){
            temp.put("amount",amount);
        }
        if (default_bank != null) {
            temp.put("default_bank", default_bank);
        }
        if (exter_invoke_ip != null) {
            temp.put("exter_invoke_ip", exter_invoke_ip);           //订单IP
        }
        if (anti_phishing_key != null) {
            temp.put("anti_phishing_key", anti_phishing_key);       //防钓鱼时间戳，调用时间戳查询接口获取
        }
        if(cardHolderId!=null){
            temp.put("cert_no",cardHolderId);
        }
        if(cardHolderName!=null){
            temp.put("real_name",cardHolderName);
        }
        if(idType!=null){
            temp.put("cert_type",idType);
        }
        if(input_charset!=null){
            temp.put("input_charset",input_charset);
        }
        if(out_trade_no!=null){
            temp.put("out_trade_no",out_trade_no);
        }
        if(card_no!=null){
            temp.put("card_no",card_no);
        }
        if(partner!=null){
            temp.put("partner",partner);
        }
        if(service!=null){
            temp.put("service",service);
        }
        if(sign_type!=null){
            temp.put("sign_type",sign_type);
        }
        if(phone!=null){
            temp.put("card_bind_mobile_phone_no",phone);
        }
        if(notify_url!=null){
            temp.put("notify_url",notify_url);
        }
        if(spFlag!=null){
            temp.put("spFlag",spFlag);
        }
        if(savePciFlag!=null){
            temp.put("savePciFlag",savePciFlag);
        }
        if(token!=null){
            temp.put("dynamic_code_token",token);
        }
        if(payBatch!=null){
            temp.put("payBatch",payBatch);
        }
        if(validCode!=null){
            temp.put("dynamic_code",validCode);
        }
        if(customerId!=null){
            temp.put("customer_id",customerId);
        }
        if(bankId!=null){
            temp.put("bank_code",bankId);
        }
        return temp;
    }

    public String getPostJson(){
        Map<String, String> temp = getContentMap();
        StringBuffer result=new StringBuffer();
        result.append("{");
        int i=0;
        for (Iterator<String> iterator = temp.keySet().iterator(); iterator.hasNext(); ) {
            String key = iterator.next();
                result.append("\"").append(key).append("\":\"").append(temp.get(key)).append("\",");

        }
        result.append("\"sign\":\"").append(getNeedSignCode()).append("\"}");
        return result.toString();
    }


//    public static void main(String[] args) throws NoSuchAlgorithmException {
//        String test="bankId=BOC&cardType=0001&customerId=119999&input_charset=UTF-8&partner=201402271330347295&service=ebatong_fp_card_bind_query&sign_type=MD5&storablePan=4380880007PDF9WKZCJ4E65UDO408207JNM27GCKqjmnjb";
//        String result="ade83fdc2970f4d5e9cc079133fec3ef";
//        System.out.println(MD5.getMD52(test));
//        System.out.println(MD5.getMD5(test));
//        System.out.println(MD5.stringToMD5(test));
//        System.out.println("MD5.getMD5Lower(test) = " + MD5.stringToMD5(test).equals(result));
//    }
}
