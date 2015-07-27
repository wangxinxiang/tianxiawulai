package com.example.txwl_first;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.txwl_first.Util.*;
import com.example.txwl_first.beifu.BeiFuHttpPost;
import com.example.txwl_first.beifu.FastPay2Return;
import com.example.txwl_first.beifu.FastpayBean;
import com.example.txwl_first.service.GetOrderNumService;
import com.google.gson.GsonBuilder;

import java.io.InputStream;

/**
 * 添加银行卡
 */
public class AddBankCardActivity extends Activity{
    //友盟统计
    private Context mContext;
    private final String mPageName = "";

    private PreferenceUtils preferenceUtils = PreferenceUtils.getInstance();
    private boolean isActive = false;

    private EditText et_user_name;
    private EditText et_user_card;
    private EditText et_user_ban;
    private EditText et_user_phone;
    private RelativeLayout rl_bank_name;
    private EditText et_vericode;
    private TextView tv_bank_name, bankcardnum;

    private Button ibtn_submit_next, btn_vericode;

    private String cz_money, token, billno;
    private String name, idcard, bankid, phone, bankname,id;
    private GetOrderNumService getOrderNumService;

    BeiFuHttpPost beiFuHttpPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_backcard_layout);
        TXWLApplication.getInstance().pushStack(this);
//        mContext = this;


        initView();

        getOrderNumber();

        initListener();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TXWLApplication.getInstance().popStack(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Constant.ResultCode) {
            String bankname = data.getStringExtra("BankName");
            id = data.getStringExtra("id");
            Log.d("BankName",bankname);
            tv_bank_name.setText(bankname);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initListener() {
        rl_bank_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到银行卡列表 用startActivityForResult取回选择的银行
                Intent intent = new Intent(AddBankCardActivity.this, BankSelectionActivity.class);
                startActivityForResult(intent, Constant.RequestCode);
            }
        });
        ibtn_submit_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TXWLProgressDialog.createDialog(AddBankCardActivity.this);
                TXWLProgressDialog.setMessage("绑定中...");
                try{
                    token = beiFuHttpPost.getToken();
                }catch (Exception e){
                }
                //放在这里做测试
//                boundBankCard();

                //完成1元支付，跳转到绑定成功界面
                if (et_vericode.getText().toString()!=null) {
                    if(token!=null){
                        new BeiFuPayHttpPost().execute(billno,token,et_vericode.getText().toString(),cz_money);
                    }
                } else {
                    TXWLApplication.getInstance().showTextToast("验证码不能为空");
                }
            }

        });
        btn_vericode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verification()) {
                    try{
                        billno = getOrderNumService.getOrder();       //订单号
                    }catch (Exception e){

                    }
                    Log.d("billno -->", billno);
                    //获取验证码
                    beiFuHttpPost = new BeiFuHttpPost();
                    beiFuHttpPost.execute(billno, name, idcard, bankid, phone, cz_money, PreferenceUtils.getUserId()+"");

                    MyCountDownTimer mc = new MyCountDownTimer(60000, 1000);
                    mc.start();
                    btn_vericode.setEnabled(false);
//                    TXWLApplication.getInstance().showTextToast("获取验证码后请不要再修改信息");
                }
            }
        });

        et_user_ban.addTextChangedListener(textwatcher);
        et_vericode.addTextChangedListener(vericodewatcher);

    }

    //获取订单号接口
    private void getOrderNumber() {
        getOrderNumService = GetOrderNumService.getInstance();
        getOrderNumService.getOrderNum(cz_money, AddBankCardActivity.this);
//        billno=getOrderNumService.getOrder();
    }


    private void initView() {
        cz_money = getIntent().getStringExtra("recharge_money");
        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_user_card = (EditText) findViewById(R.id.et_user_card);
        et_user_ban = (EditText) findViewById(R.id.et_user_ban);
        et_user_phone = (EditText) findViewById(R.id.et_user_phone);
        ibtn_submit_next = (Button) findViewById(R.id.ibtn_submit_next);
        rl_bank_name = (RelativeLayout) findViewById(R.id.rl_bank_name);
        tv_bank_name = (TextView) findViewById(R.id.tv_bank_name_name);
        btn_vericode = (Button) findViewById(R.id.btn_vericode);
        bankcardnum = (TextView) findViewById(R.id.bankcardnum);
        et_vericode = (EditText) findViewById(R.id.et_vericode);
        ibtn_submit_next.setEnabled(false);
    }


    @Override
    protected void onResume() {
        super.onResume();


        isActive = true;

//        MobclickAgent.onPageStart(mPageName);
//        MobclickAgent.onResume(mContext);

    }

    @Override
    protected void onPause() {
        super.onPause();
        isActive = false;
//        MobclickAgent.onPageEnd( mPageName );
//        MobclickAgent.onPause(mContext);
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
            btn_vericode.setText("重新获取验证码");
            btn_vericode.setEnabled(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btn_vericode.setText("  重新获取\n验证码" + millisUntilFinished / 1000 + "秒");
//            btn_vericode.setText("重新获取验证码(" + millisUntilFinished / 1000 + "秒)");
        }
    }


    private boolean verification() {
        name = et_user_name.getText().toString();
        idcard = et_user_card.getText().toString();
        bankid = et_user_ban.getText().toString();
        phone = et_user_phone.getText().toString();
        bankname = tv_bank_name.getText().toString();

        if ("".equals(bankname)) {
            TXWLApplication.getInstance().showTextToast("银行不能为空");
            return false;
        }
        if (DataVeri.isBlank(name)) {
            TXWLApplication.getInstance().showTextToast("真实姓名不能为空");
            return false;
        }
        if (DataVeri.isBlank(idcard)) {
            TXWLApplication.getInstance().showTextToast("证件号码不能为空");
            return false;
        }
        if (DataVeri.isBlank(bankid)) {
            TXWLApplication.getInstance().showTextToast("银行卡号不能为空");
            return false;
        }
        if (DataVeri.isMobileNum(phone)) {
            return false;
        }

        return true;
    }

    //绑定银行卡
    private void boundBankCard() {
        Log.d("boundBankCard",id+" "+bankid+" "+idcard+" "+name+" "+phone+" "+bankname);
//        String url = URL.getBoundUserCardURL(id, bankid, idcard, name, phone, bankname);
//        DataWebService dataWebService = DataWebService.getInstance();
//        Log.d("boundBankCard:",url);
//        dataWebService.getData(url, new DataWebCallBack() {
//            @Override
//            public void onSuccess(String responseBody, String successMsg) {
//                Log.d("boundBankCard-->", responseBody);
//                String strBody = responseBody.substring(1,responseBody.length()-1);
//                try {
//                    ResultBean resultBean = new GsonBuilder().create().fromJson(strBody, ResultBean.class);
//                    if("绑定成功".equals(resultBean.getResult().toString())){
////                        TXWLApplication.getInstance().showTextToast("绑定接口传入服务端成功");
//                        getPaymentOrderOk();
//                    }else{
//                        TXWLApplication.getInstance().showTextToast(resultBean.getResult());
//                    }
//                }catch (Exception ex) {
//                    TXWLApplication.getInstance().showErrorConnected(ex);
//                }
//            }
//
//            @Override
//            public void onFailure(String responseBody, String failureMsg) {
//                TXWLApplication.getInstance().showTextToast("网络错误");
//            }
//        });

    }


    class BeiFuPayHttpPost extends AsyncTask<String, Integer, String> {

        public BeiFuPayHttpPost() {
            super();
        }

        @Override
        protected String  doInBackground(String... params) {
            //bankNumber：银行卡号 accountNumber：证件号 mobile：手机号码 accountname：用户姓名


            FastpayBean fastpayBean=new FastpayBean();
            fastpayBean.setService("ebatong_direct_fastpay");
            fastpayBean.setPartner(Url.Partner);
            fastpayBean.setSign_type("MD5");
            fastpayBean.setInput_charset("UTF-8");
            fastpayBean.setPan(bankid);

            fastpayBean.setCardHolderName(name);

            fastpayBean.setIdType("0");
            fastpayBean.setCardHolderId(idcard);

            fastpayBean.setPhone(phone);
            fastpayBean.setOut_trade_no(params[0]);
            fastpayBean.setAmount(params[3]);
            fastpayBean.setSign_type("MD5");
            fastpayBean.setSpFlag("QuickPay");
            fastpayBean.setNotify_url("http://appnew.shilehui.com");
            fastpayBean.setSavePciFlag("0");
            fastpayBean.setPayBatch("1");
            fastpayBean.setToken(params[1]);
            fastpayBean.setValidCode(params[2]);
            fastpayBean.setCustomerId(PreferenceUtils.getUserId() + "");
//            fastpayBean.setBankId("CCB");


            String urlPath = "https://www.ebatong.com/fppay/gateway.htm";
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
            FastPay2Return returnBean = new GsonBuilder().create().fromJson( result, FastPay2Return.class);
            if("00".equals(returnBean.getRespCode())&&"绑定成功".equals(returnBean.getRespMessage())){
                TXWLProgressDialog.Dismiss();
                System.out.println("onPostExecute--------------->notifyServier");
                boundBankCard();
//                PreferenceUtils.getInstance().setAccountMoney(cz_money);

                BoundSuccessFragmentDialog dialog = new BoundSuccessFragmentDialog();
                dialog.show(getFragmentManager(),"boundsuccess");
//                TXWLApplication.getInstance().addActivity(AddPanActivity.this);
            }else{
                TXWLApplication.getInstance().showTextToast(returnBean.getRespMessage());
            }
        }
    }

    private TextWatcher textwatcher = new TextWatcher() {
        private CharSequence charSequence;
        private int editStart;
        private int editEnd;
//        "6212 2612 0800 1211 232"

        @Override
        public void beforeTextChanged(CharSequence ss, int start, int count, int after) {
            charSequence = ss;
            Log.v("TextWatcher-beforeTextChanged-->"," text="+ss.toString()+" length="+ss.length()+" start="+start+" count-"+count+" after="+after);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            bankcardnum.setText(s);
            Log.v("TextWatcher-onTextChanged-->", " text=" + s.toString() + " length=" + s.length() + " start=" + start + " before-" + before + " count=" + count);
        }

        @Override
        public void afterTextChanged(Editable s) {
            Log.v("TextWatcher-afterTextChanged-->",s.toString()+"length"+s.length());
            // 输入的时候，只有一个光标，那么这两个值应该是相等的。。。
            editStart = et_user_ban.getSelectionStart();
            editEnd = et_user_ban.getSelectionEnd();

            // 限定EditText只能输入19个数字，并且达到19个的时候用红色显示
            if (charSequence.length() > 19) {
                Toast.makeText(AddBankCardActivity.this, "你输入的字数已经超过了限制，不能再输入！",
                        Toast.LENGTH_SHORT).show();
                // 默认光标在最前端，所以当输入第19个数字的时候，删掉（光标位置从11-1到11）的数字，这样就无法输入超过19个以后的数字
                s.delete(editStart - 1, editEnd);
                // 当输入超过第19个数字的时候，改变字体颜色为红色
            }
        }
    };

    private TextWatcher vericodewatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            ibtn_submit_next.setEnabled(true);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

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
////                        TXWLApplication.getInstance().showTextToast("获取余额成功");
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

}
