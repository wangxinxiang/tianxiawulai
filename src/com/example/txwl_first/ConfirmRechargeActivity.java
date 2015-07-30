package com.example.txwl_first;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.txwl_first.Util.*;
import com.example.txwl_first.beifu.FastPayReturn;
import com.example.txwl_first.beifu.FastpayBean;
import com.google.gson.GsonBuilder;

import java.io.InputStream;

/**
 * Created by Administrator on 2015/5/31.
 */
public class ConfirmRechargeActivity extends Activity{

    private Button confirm_recharge_commit;
    private String name,idcard,bankid,phone,et_vericode,billno,token,cz_money,bank_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_recharge);
        TXWLApplication.getInstance().pushStack(this);
        initView();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TXWLApplication.getInstance().popStack(this);
    }

    private void initView() {

        bank_code = getIntent().getStringExtra("bank_code");
        name = getIntent().getStringExtra("name");
        idcard = getIntent().getStringExtra("idcard");
        bankid = getIntent().getStringExtra("bankid");
        et_vericode = getIntent().getStringExtra("et_vericode");
        billno = getIntent().getStringExtra("billno");
        phone = getIntent().getStringExtra("phone");
        token = getIntent().getStringExtra("token");
        cz_money = getIntent().getStringExtra("cz_money");

        confirm_recharge_commit = (Button) findViewById(R.id.confirm_recharge_commit);
        TextView confirm_recharge_money = (TextView) findViewById(R.id.confirm_recharge_money);

//        if("donation".equals(aim)){
//            setNormalTitle("确认捐款");
//            confirm_recharge_commit.setText("确认捐款");
//        }else{
//            setNormalTitle("确认充值");
//        }

//        setNormalTitle("确认充值");
        confirm_recharge_money.setText("￥ " + cz_money);

    }

    private void initListener() {
        confirm_recharge_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TXWLProgressDialog.createDialog(ConfirmRechargeActivity.this);
                TXWLProgressDialog.setMessage("支付中...");
                    new BeiFuPayPhishingKey().execute();
            }
        });
    }


    class BeiFuPayHttpPost extends AsyncTask<String, Integer, String> {

        public BeiFuPayHttpPost() {
            super();
        }

        @Override
        protected String  doInBackground(String... params) {
            //bankNumber：银行卡号 accountNumber：证件号 mobile：手机号码 accountname：用户姓名


            FastpayBean fastpayBean=new FastpayBean();
            fastpayBean.setService("create_direct_pay_by_mp");
            fastpayBean.setPartner(Url.Partner);
            fastpayBean.setSign_type("MD5");
            fastpayBean.setInput_charset("UTF-8");
            fastpayBean.setPan(bankid);

            fastpayBean.setCardHolderName(name);

            fastpayBean.setIdType("0");
            fastpayBean.setCardHolderId(idcard);

            fastpayBean.setPhone(phone);
            fastpayBean.setOut_trade_no(params[0]);
            fastpayBean.setTotal_fee(Double.valueOf(params[3]) + "");               //(8,2) 位数至少2位
            fastpayBean.setSign_type("MD5");
            fastpayBean.setSpFlag("QuickPay");
            fastpayBean.setNotify_url("www.hao123.com");
            fastpayBean.setSavePciFlag("0");
            fastpayBean.setPayBatch("1");
            fastpayBean.setToken(params[1]);
            fastpayBean.setValidCode(params[2]);
            fastpayBean.setCustomerId(PreferenceUtils.getUserId() + "");
            fastpayBean.setDefault_bank(bank_code);

            fastpayBean.setExter_invoke_ip("");
            fastpayBean.setAnti_phishing_key(params[4]);

            fastpayBean.setSubject("");
            fastpayBean.setBody("");
            fastpayBean.setShow_url("");
            fastpayBean.setExtend_param("");
            fastpayBean.setExtra_common_param("");
//            fastpayBean.setBankId("CCB");


            String urlPath = "https://www.ebatong.com/mobileFast/pay.htm";
            String postJson = fastpayBean.getPostJson();
            Log.i("lyjtest", "getPostJson:" + postJson);
            byte[] data = postJson.getBytes();
            InputStream is = null;
            try {
                is = NetTool.sendXMLData(urlPath, data, "UTF-8");
                data = NetTool.readStream(is);
                Log.i("lyjtest", new String(data));
            } catch (Exception e) {
                e.printStackTrace();
            }

            return new String(data);
        }


        @Override
        protected void onPostExecute(String result) {
            FastPayReturn returnBean = new GsonBuilder().create().fromJson( result, FastPayReturn.class);
            if("T".equals(returnBean.getResult())) {
                TXWLProgressDialog.Dismiss();
                getPaymentOrderOk();
                Intent intent = new Intent(ConfirmRechargeActivity.this, RechargeSuccessActivity.class);
                intent.putExtra("registid", getIntent().getStringExtra("registid"));
                intent.putExtra("cz_money",cz_money);
                intent.putExtra("billno", billno);
                startActivity(intent);

            }else{
                TXWLApplication.getInstance().showTextToast(returnBean.getError_message());
            }
        }
    }

    /**
     * 获取时间戳
     */
    class BeiFuPayPhishingKey extends AsyncTask<String, Integer, String[]> {

        public BeiFuPayPhishingKey() {
            super();
        }

        @Override
        protected String[]  doInBackground(String... params) {
            //bankNumber：银行卡号 accountNumber：证件号 mobile：手机号码 accountname：用户姓名


            FastpayBean fastpayBean=new FastpayBean();
            fastpayBean.setService("query_timestamp");
            fastpayBean.setPartner(Url.Partner);
            fastpayBean.setSign_type("MD5");
            fastpayBean.setInput_charset("UTF-8");

            String urlPath = "https://www.ebatong.com/mobileFast/pay.htm";
            String postJson = fastpayBean.getPostJson();
            Log.i("lyjtest", "getPostJson:" + postJson);
            byte[] data = postJson.getBytes();
            String[] result = new String[2];
            InputStream is = null;
            try {
                is = NetTool.sendXMLData(urlPath, data, "UTF-8");
                result = NetTool.readXML(is);
                Log.i("lyjtest", new String(data));
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }


        @Override
        protected void onPostExecute(String[] result) {
            if("T".equals(result[0])){
                new BeiFuPayHttpPost().execute(billno, token, et_vericode, cz_money, result[1]);
            }else{
                TXWLApplication.getInstance().showTextToast(result[1]);
            }
        }
    }

    //通知服务端充值金额，同时获取账户余额
    public void getPaymentOrderOk() {
//        String url = URL.getPaymentOrderOk(billno);
//        Log.d("getPaymentOrderOk-->",url);
//        DataWebService userService = DataWebService.getInstance();
//        userService.getData(url, new DataWebCallBack() {
//            @Override
//            public void onSuccess(String responseBody, String successMsg) {
//                Log.d("getPaymentOrderOk--> ", responseBody);
//                String strBody = responseBody.substring(1, responseBody.length() - 1);
//                Log.d("getPaymentOrderOk--> ", strBody);
//                try {
//                    ResultBean resultBean = new GsonBuilder().create().fromJson(strBody, ResultBean.class);
//                    if ("充值成功".equals(resultBean.getResult())) {
//                        TXWLApplication.getInstance().showTextToast("获取余额成功");
//                        PreferenceUtils.getInstance().setAccountMoney(cz_money);
//                    } else {
//                        TXWLApplication.getInstance().showTextToast(resultBean.getResult());
//                    }
//                } catch (Exception ex) {
//                    TXWLApplication.getInstance().showErrorConnected(ex);
//                }
//            }
//
//            @Override
//            public void onFailure(String responseBody, String failureMsg) {
//                TXWLApplication.getInstance().showTextToast("充值失败");
//            }
//        });
    }


//    public void donationMoneyTopic( ) {
//        String url = getUserDonateUrl(recharge_money);
//
//        Log.d("donationURL-->",url);
//
//        DataWebService userService = DataWebService.getInstance();
//        userService.getData(url, new DataWebCallBack() {
//
//            @Override
//            public void onSuccess(String responseBody, String successMsg) {
//                Log.d("donationMoneyTopic--> ", responseBody);
//
//                String strBody = responseBody.substring(1,responseBody.length()-1);
//
//                Log.d("donationMoneyTopic--> ", strBody);
//                try {
//                    ResultBean resultBean = new GsonBuilder().create().fromJson(strBody, ResultBean.class);
//                    if ("成功".equals(resultBean.getResult())) {
//                        //TODO:传入的参数recharge_money需要处理成负数
//                        PreferenceUtils.getInstance().setAccountMoney("-" + recharge_money);
//                        TXWLApplication.getInstance().showTextToast("捐助成功");
//                    }else {
//                        TXWLApplication.getInstance().showTextToast(resultBean.getResult());
//                    }
//                }catch (Exception ex) {
//                    TXWLApplication.getInstance().showErrorConnected(ex);
//                }
//            }
//
//            @Override
//            public void onFailure(String responseBody, String failureMsg) {
//                TXWLApplication.getInstance().showTextToast("捐助失败");
//            }
//        });
//    }

//    private String getUserDonateUrl(String moneycount){
//
//        String auction="AssistEndow";
//        String tempId= SharedMethod.getInstance().getRandomNumber();
//        String userId= PreferenceUtils.getUserID();
//        String sign= MD5.getMD5("Auction:" + auction + "Key:1q2w3e1q2w3e,./<>?!@#!@#QAZWSXEDC{}UserID:"
//                + PreferenceUtils.getUserID());
//        //这里要小写加密
//        String enodwSign=MD5.getMD5Lower("AssistIDS:"+topicID+"Moneys:"+moneycount+URL.plaintext);
//
//
//        String randSign=MD5.getMD5("RandInfo:" + PreferenceUtils.getRandInfo() + URL.plaintext);
//
//        String url="http://appnew.shilehui.com/WebService/endow.aspx?"+"&Auction="+auction+
//                "&sign="+sign+"&TempID="+tempId+"&AssistIDS="+topicID+"&Moneys="+moneycount+
//                "&userid="+userId+"&RandSign="+randSign+"&EnodwSign="+enodwSign;
//
//        return url;
//
//    }
}
