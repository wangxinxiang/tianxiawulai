package com.example.txwl_first;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.txwl_first.Util.PreferenceUtils;
import com.example.txwl_first.Util.TXWLApplication;
import com.example.txwl_first.beifu.BeiFuHttpPost;

/**
 * Created by licheng on 31/5/15.
 */
public class VerificationActivity extends Activity {
    TextView tv_paymoney,tv_phone;
    EditText et_chekcode;
    Button btn_submit_reget,btn_next;
    private String token,billno;
    private String cz_money,name,idcard,bankid,phone;
    BeiFuHttpPost beiFuHttpPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verification_activity);
        TXWLApplication.getInstance().pushStack(this);

        initView();

        setOnClickListener();

        getChecCode();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TXWLApplication.getInstance().popStack(this);
    }

    private void setOnClickListener(){
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
                if(et_chekcode.getText().toString()!=null){
                    Intent intent = new Intent(VerificationActivity.this, ConfirmRechargeActivity.class);
                    intent.putExtra("billno", billno);
                    intent.putExtra("token", token);
                    intent.putExtra("et_vericode", et_chekcode.getText().toString());
                    intent.putExtra("cz_money", cz_money);
                    intent.putExtra("name", name);
                    intent.putExtra("idcard", idcard);
                    intent.putExtra("bankid", bankid);
                    intent.putExtra("phone", phone);
                    startActivity(intent);
//                    TXWLApplication.getInstance().addActivity(VerificationActivity.this);
                }else{
                    TXWLApplication.getInstance().showTextToast("验证码不能为空");
                }
            }
        });
    }

    private void initView(){
//        setNormalTitle("填写验证码");
        cz_money = getIntent().getStringExtra("recharge_money");
        name=getIntent().getStringExtra("accountname");
        idcard=getIntent().getStringExtra("accountNumber");
        bankid=getIntent().getStringExtra("bankNumber");
        phone=getIntent().getStringExtra("mobile");
        billno = getIntent().getStringExtra("billno");
        tv_paymoney = (TextView) findViewById(R.id.tv_paymoney);
        tv_phone = (TextView) findViewById(R.id.phone_tv);
        et_chekcode = (EditText) findViewById(R.id.et_user_checkcode);
        btn_next = (Button) findViewById(R.id.ibtn_submit_register);
        btn_submit_reget = (Button) findViewById(R.id.ibtn_submit_reget);
        tv_paymoney.setText("￥" + cz_money);
        tv_phone.setText(phone);
        btn_next.setEnabled(false);
        et_chekcode.addTextChangedListener(textwatcher);
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
        beiFuHttpPost.execute(billno, name, idcard, bankid, phone, cz_money, PreferenceUtils.getUserId() + "");
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
}
