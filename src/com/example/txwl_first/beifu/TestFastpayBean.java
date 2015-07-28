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
public class TestFastpayBean {
    private String agent_time;
    private String amount_str;
    private String bank_account_name;
    private String bank_account_no;
    private String bank_name;
    private String bank_site_name;
    private String input_charset;
    private String out_trade_no;
    private String partner;
    private String remark;
    private String return_url;

    //
    private String service;
    private String sign_type;
    private String to_account_mode;

    public String getAgent_time() {
        return agent_time;
    }

    public void setAgent_time(String agent_time) {
        this.agent_time = agent_time;
    }

    public String getAmount_str() {
        return amount_str;
    }

    public void setAmount_str(String amount_str) {
        this.amount_str = amount_str;
    }

    public String getBank_account_name() {
        return bank_account_name;
    }

    public void setBank_account_name(String bank_account_name) {
        this.bank_account_name = bank_account_name;
    }

    public String getBank_account_no() {
        return bank_account_no;
    }

    public void setBank_account_no(String bank_account_no) {
        this.bank_account_no = bank_account_no;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_site_name() {
        return bank_site_name;
    }

    public void setBank_site_name(String bank_site_name) {
        this.bank_site_name = bank_site_name;
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

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
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

    public String getTo_account_mode() {
        return to_account_mode;
    }

    public void setTo_account_mode(String to_account_mode) {
        this.to_account_mode = to_account_mode;
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

//        String mycode="JMTTO16YBJJXOOI5T795K19GWKKXTIiltdme";
        String mycode="6G13P7W8C8NU6J5L5IOIBBZAKNI3ROaqdevz";
        needSign.append(mycode);
        String string = needSign.toString();
       // LogZ.d("lyjtest","签名str:"+ string);
        System.out.println("string = " + string);
        return MD5.getMD5Lower(string);
    }

    //

    /**
     *  private String agent_time;
     private String amount_str;
     private String bank_account_name;
     private String bank_account_no;
     private String bank_name;
     private String bank_site_name;
     private String input_charset;
     private String out_trade_no;
     private String partner;
     private String remark;
     private String return_url;

     //
     private String service;
     private String sign_type;
     private String to_account_mode;
     * @return
     */
    private Map<String, String> getContentMap() {
        Map<String,String> temp=new LinkedHashMap<String,String>();
        if(agent_time!=null){
            temp.put("agent_time",agent_time);
        }
        if(amount_str!=null){
            temp.put("amount_str",amount_str);
        }
        if(bank_account_name!=null){
            temp.put("bank_account_name",bank_account_name);
        }
        if(bank_account_no!=null){
            temp.put("bank_account_no",bank_account_no);
        }
        if(bank_name!=null){
            temp.put("bank_name",bank_name);
        }
        if(bank_site_name!=null){
            temp.put("bank_site_name",bank_site_name);
        }
        if(out_trade_no!=null){
            temp.put("out_trade_no",out_trade_no);
        }
        if(input_charset!=null){
            temp.put("input_charset",input_charset);
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
        if(remark!=null){
            temp.put("remark",remark);
        }
        if(return_url!=null){
            temp.put("return_url",return_url);
        }
        if(to_account_mode!=null){
            temp.put("to_account_mode",to_account_mode);
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
//        TestFastpayBean bean=new TestFastpayBean();
//        bean.setAgent_time("20131213143151");
//        bean.setAmount_str("10");
//        bean.setBank_account_name("周磊");
//        bean.setBank_account_no("6222023202044234580");
//        bean.setBank_name("工商银行");
//        bean.setBank_site_name("天天开网点");
//        bean.setInput_charset("UTF-8");
//        bean.setOut_trade_no("20131213162537-10004491");
//        bean.setPartner("201303101809506780");
//        bean.setRemark("发工资了");
//        bean.setReturn_url("http://localhost:8080/vpersonalweb/gateway/receiveAgentSynNotify.htm");
//        bean.setService("ebatong_agent_distribution");
//        bean.setSign_type("MD5");
//        bean.setTo_account_mode("1000");
//       String signCode= bean.getNeedSignCode();
//        System.out.println("signCode = " + signCode);
//    }
}
