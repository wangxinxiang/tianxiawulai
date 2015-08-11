package com.example.txwl_first;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.txwl_first.Util.*;
import com.example.txwl_first.bean.AddRechargeBean;
import com.example.txwl_first.bean.AddRechargeItemBean;
import com.example.txwl_first.bean.BackInfoItemBean;
import com.example.txwl_first.beifu.BeiFuHttpPost;
import com.example.txwl_first.beifu.FastPayReturn;
import com.example.txwl_first.beifu.FastpayBean;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

import java.io.InputStream;

/**
 * Created by licheng on 31/5/15.
 */
public class VerificationActivity extends Activity {
    TextView tv_paymoney,tv_phone;
    EditText et_chekcode;
    Button btn_submit_reget,btn_next;
    private String token,billno;
    private String cz_money,name,idcard,bankid,phone, bank_code, bankname;
    BeiFuHttpPost beiFuHttpPost;
    private ImageButton ibtn_title_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verification_activity);
        TXWLApplication.getInstance().pushStack(this);

        initView();

        setOnClickListener();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TXWLApplication.getInstance().popStack(this);
    }

    private void setOnClickListener(){
        ibtn_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                TXWLApplication.getInstance().popStack(VerificationActivity.this);
            }
        });

        //重新获取验证码
        btn_submit_reget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getChecCode();
            }
        });

        //提交
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    token = beiFuHttpPost.getToken();
                }catch (Exception e){
                    TXWLApplication.getInstance().showTextToast("请重新输入验证码");
                }
                if(!"".equals(et_chekcode.getText().toString())){
                    Intent intent = new Intent(VerificationActivity.this, ConfirmRechargeActivity.class);
                    intent.putExtra("billno", billno);
                    intent.putExtra("token", token);
                    intent.putExtra("et_vericode", et_chekcode.getText().toString());
                    intent.putExtra("cz_money", cz_money);
                    intent.putExtra("name", name);
                    intent.putExtra("idcard", idcard);
                    intent.putExtra("bankid", bankid);
                    intent.putExtra("phone", phone);
                    intent.putExtra("bank_code", bank_code);
                    intent.putExtra("registid", getIntent().getStringExtra("registid"));
                    startActivity(intent);
//                    TXWLApplication.getInstance().addActivity(VerificationActivity.this);
                }else{
                    TXWLApplication.getInstance().showTextToast("验证码不能为空");
                }
            }
        });
    }

    private void initView(){
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        ibtn_title_back = (ImageButton) findViewById(R.id.ibtn_title_back);
        ibtn_title_back.setVisibility(View.VISIBLE);
        TextView tv_right = (TextView) findViewById(R.id.tv_right);
        tv_right.setVisibility(View.GONE);
        tv_title.setText("验证码");

        TXWLProgressDialog.createDialog(VerificationActivity.this);
        TXWLProgressDialog.setMessage("获取订单号中.....");
//        setNormalTitle("填写验证码");
        cz_money = getIntent().getStringExtra("recharge_money");
        name=getIntent().getStringExtra("accountname");
        idcard=getIntent().getStringExtra("accountNumber");
        bankid=getIntent().getStringExtra("bankNumber");
        phone=getIntent().getStringExtra("mobile");
        bankname = getIntent().getStringExtra("bankname");
        tv_paymoney = (TextView) findViewById(R.id.tv_paymoney);
        tv_phone = (TextView) findViewById(R.id.phone_tv);
        et_chekcode = (EditText) findViewById(R.id.et_user_checkcode);
        btn_next = (Button) findViewById(R.id.ibtn_submit_register);
        btn_submit_reget = (Button) findViewById(R.id.ibtn_submit_reget);
        tv_paymoney.setText("￥" + cz_money);
        tv_phone.setText(phone);
        btn_next.setEnabled(false);
        et_chekcode.addTextChangedListener(textwatcher);

        getOrderNum();          //获取订单号
    }

    class MyCountDownTimer extends CountDownTimer {
        /**
         * @param millisInFuture    表示以毫秒为单位 倒计时的总数
         *                          <p/>
         *                          例如 millisInFuture=1000 表示1秒
         * @param countDownInterval 表示 间隔 多少微秒 调用一次 onTick 方法
         *                          <p/>
         *                          例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }


        @Override
        public void onFinish() {
            btn_submit_reget.setText("重新获取验证码");
            btn_submit_reget.setEnabled(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btn_submit_reget.setText("重新获取验证码(" + millisUntilFinished / 1000 + "秒)");
        }
    }

    private void getChecCode(){
        beiFuHttpPost = new BeiFuHttpPost();
        Log.d("billno -->", billno);
        beiFuHttpPost.execute(billno, name, idcard, bankid, phone, cz_money, PreferenceUtils.getUserId() + "", bank_code);
        MyCountDownTimer mc = new MyCountDownTimer(60000, 1000);
        mc.start();
        btn_submit_reget.setEnabled(false);
//        TXWLApplication.getInstance().showTextToast("获取验证码后请不要再修改信息");
    }


    private TextWatcher textwatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            btn_next.setEnabled(true);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void getOrderNum() {
        String url = Url.AddRecharge_URL;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("rechargeaccount", cz_money);
        params.put("userid", PreferenceUtils.getUserId());
        params.put("bankname", bankname);

        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("getOrderNum ---->", new String(bytes));

                try {
                    AddRechargeBean addRechargeBean = new GsonBuilder().create().fromJson(new String(bytes), AddRechargeBean.class);
                    AddRechargeItemBean addRechargeItemBean = addRechargeBean.getRecharge();
                    billno = addRechargeItemBean.getBillno();
                    bank_code = addRechargeBean.getBankcode();

                    getChecCode();              //获取验证码

                    TXWLProgressDialog.Dismiss();
                } catch (Exception e) {
                    TXWLApplication.getInstance().showErrorConnected(e);
                    TXWLProgressDialog.Dismiss();
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                TXWLProgressDialog.Dismiss();
                TXWLApplication.getInstance().showTextToast("网络错误, 获取订单号失败,请重新进入此页面");
            }
        });
//
    }

}
