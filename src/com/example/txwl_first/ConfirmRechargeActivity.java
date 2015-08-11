package com.example.txwl_first;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
    private ImageButton ibtn_title_back;

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
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        ibtn_title_back = (ImageButton) findViewById(R.id.ibtn_title_back);
        ibtn_title_back.setVisibility(View.VISIBLE);
        TextView tv_right = (TextView) findViewById(R.id.tv_right);
        tv_right.setVisibility(View.GONE);
        tv_title.setText("确认支付");

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
        ibtn_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                TXWLApplication.getInstance().popStack(ConfirmRechargeActivity.this);
            }
        });

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
            fastpayBean.setBank_card_no(bankid);

            fastpayBean.setCardHolderName(name);

            fastpayBean.setIdType("01");
            fastpayBean.setCardHolderId(idcard);

            fastpayBean.setPhone(phone);
            fastpayBean.setOut_trade_no(params[0]);
            fastpayBean.setTotal_fee(params[3]);               //(8,2) 位数至少2位
            fastpayBean.setSign_type("MD5");
            fastpayBean.setNotify_url("www.hao123.com");
            fastpayBean.setToken(params[1]);
            fastpayBean.setValidCode(params[2]);
            fastpayBean.setCustomerId(PreferenceUtils.getUserId() + "");
            fastpayBean.setDefault_bank(bank_code);

            fastpayBean.setExter_invoke_ip("11.11.11.11");
            fastpayBean.setAnti_phishing_key(params[4]);

            fastpayBean.setSubject("txwl");
            fastpayBean.setBody("spms");
            fastpayBean.setShow_url("spzs");
            fastpayBean.setExtend_param("ggcs");
            fastpayBean.setExtra_common_param("hccs");
            fastpayBean.setPay_method("");
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
                Intent intent = new Intent(ConfirmRechargeActivity.this, RechargeSuccessActivity.class);
                intent.putExtra("registid", getIntent().getStringExtra("registid"));
                intent.putExtra("cz_money",cz_money);
                intent.putExtra("billno", billno);
                startActivity(intent);

            }else{
                TXWLProgressDialog.Dismiss();
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

            String urlPath = "https://www.ebatong.com/gateway.htm";
            urlPath += "?service=query_timestamp";
            urlPath = urlPath + "&partner=" + Url.Partner + "&input_charset=UTF-8&sign_type=MD5&sign=" + fastpayBean.getNeedSignCode();

            Log.i("lyjtest", "urlPath:" + urlPath);

            String[] result = new String[2];
            InputStream is = null;
            try {
                is = NetTool.sendGetData(urlPath);
                result = NetTool.readXML(is);
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

}
