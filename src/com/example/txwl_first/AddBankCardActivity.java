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
import com.example.txwl_first.bean.AddRechargeBean;
import com.example.txwl_first.bean.AddRechargeItemBean;
import com.example.txwl_first.bean.BackInfoItemBean;
import com.example.txwl_first.beifu.BeiFuHttpPost;
import com.example.txwl_first.beifu.FastPay2Return;
import com.example.txwl_first.beifu.FastPayReturn;
import com.example.txwl_first.beifu.FastpayBean;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

import java.io.InputStream;

/**
 * 添加银行卡
 */
public class AddBankCardActivity extends Activity{
    //友盟统计
    private Context mContext;
    private final String mPageName = "";

    private boolean isActive = false;

    private EditText et_user_name;
    private EditText et_user_card;
    private EditText et_user_ban;
    private EditText et_user_phone;
    private RelativeLayout rl_bank_name;
    private EditText et_vericode;
    private TextView tv_bank_name, bankcardnum;

    private Button ibtn_submit_next, btn_vericode;

    private String cz_money, token, billno, registid;
    private String name, idcard, bankid, phone, bankname, bank_code;
    private String encrypt_key;                                     //时间戳

    BeiFuHttpPost beiFuHttpPost;
    private ImageButton ibtn_title_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_backcard_layout);
        TXWLApplication.getInstance().pushStack(this);
//        mContext = this;


        initView();


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
            tv_bank_name.setText(bankname);
            bank_code = data.getStringExtra("bank_code");
            Log.d("bank_code:", bank_code);
            TXWLProgressDialog.createDialog(AddBankCardActivity.this);
            TXWLProgressDialog.setMessage("获取订单号中.....");
            getOrderNum();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initListener() {
        ibtn_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                TXWLApplication.getInstance().popStack(AddBankCardActivity.this);
            }
        });

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
                TXWLProgressDialog.setMessage("支付中...");
                try{
                    token = beiFuHttpPost.getToken();
                }catch (Exception e){
                }
                //放在这里做测试
//                boundBankCard();

                //完成1元支付，跳转到支付成功界面
                String vericode = et_vericode.getText().toString();
                if (!"".equals(vericode)) {
                    if(token!=null){
                            new BeiFuPayPhishingKey().execute();

                    }
                } else {
                    TXWLProgressDialog.Dismiss();
                    TXWLApplication.getInstance().showTextToast("验证码还未获取，请稍等");
                }
            }

        });
        btn_vericode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!verification()) {

                    Log.d("billno -->", billno);
                    //获取验证码
                    beiFuHttpPost = new BeiFuHttpPost();
                    beiFuHttpPost.execute(billno, name, idcard, bankid, phone, cz_money, PreferenceUtils.getUserId()+"", bank_code);

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



    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        ibtn_title_back = (ImageButton) findViewById(R.id.ibtn_title_back);
        ibtn_title_back.setVisibility(View.VISIBLE);
        TextView tv_right = (TextView) findViewById(R.id.tv_right);
        tv_right.setVisibility(View.GONE);
        tv_title.setText("银行卡支付");

        cz_money = getIntent().getStringExtra("tip");

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


    /**
     * 验证码设置
     */
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


    /**
     * 验证信息是否为空
     * @return
     */
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
        return !DataVeri.isMobileNum(phone);

    }

    //绑定银行卡交至服务器
    private void boundBankCard() {
        Log.d("boundBankCard", bankid+" "+idcard+" "+name+" "+phone+" "+bankname);
        String url = Url.AddBankInfo_URL;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("userid", PreferenceUtils.getUserId());
        params.put("accountname", name);
        params.put("ID", idcard);
        params.put("mobile", phone);
        params.put("bankname", bankname);
        params.put("banknumber", bankid);

        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("boundBankCard ------>", new String(bytes));
                TXWLProgressDialog.Dismiss();
                if (new String(bytes).contains("success")) {
                    BoundSuccessFragmentDialog dialog = new BoundSuccessFragmentDialog();
                    Bundle data = new Bundle();
                    data.putString("billon", billno);
                    dialog.setArguments(data);//通过Bundle向Activity中传递值
                    dialog.show(getFragmentManager(), "boundsuccess");
                } else {
                    TXWLApplication.getInstance().showTextToast("支付成功，绑定银行卡失败");
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                TXWLProgressDialog.Dismiss();
                TXWLApplication.getInstance().showTextToast("支付成功，绑定银行卡网络错误");
            }
        });

    }

    /**
     * 提交交易
     */
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
            if("T".equals(returnBean.getResult())){
                boundBankCard();

            }else{
                TXWLProgressDialog.Dismiss();
                TXWLApplication.getInstance().showTextToast(returnBean.getError_message());
                Log.d("mobileFast_error---->", returnBean.getError_message());
            }
        }
    }

    /**
     * 获取时间戳
     * 再提交支付申请
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
                encrypt_key = result[1];
                Log.d("encrypt_key---->", "时间戳:" + encrypt_key);
                new BeiFuPayHttpPost().execute(billno,token,et_vericode.getText().toString(),cz_money, encrypt_key);
            }else{
                TXWLProgressDialog.Dismiss();
                TXWLApplication.getInstance().showTextToast(result[1]);
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
            String bankId = "";
            for (int i = 0; i < s.length(); i++) {
                if ((i + 1) % 4 == 0) {
                    bankId += " ";
                }
                bankId += s.charAt(i);
            }
            bankcardnum.setText(bankId);
            Log.v("TextWatcher-onTextChanged-->", " text=" + s.toString() + " length=" + s.length() + " start=" + start + " before-" + before + " count=" + count);
        }

        @Override
        public void afterTextChanged(Editable s) {
            Log.v("TextWatcher-afterTextChanged-->", s.toString() + "length" + s.length());
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

    /**
     * 获取订单号
     */
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
                    TXWLProgressDialog.Dismiss();

                    if (getIntent().getBooleanExtra("isDetele", false)) {
                        TXWLProgressDialog.createDialog(AddBankCardActivity.this);
                        TXWLProgressDialog.setMessage("只能绑定一张银行卡，解绑中");
                        new UnBindBeiFu().execute();
                    }

                } catch (Exception e) {
                    TXWLApplication.getInstance().showErrorConnected(e);
                    TXWLProgressDialog.Dismiss();
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                TXWLProgressDialog.Dismiss();
                TXWLApplication.getInstance().showTextToast("网络错误, 获取订单号失败,请重新选择银行");
            }
        });
//
    }

    /**
     * 解除银行卡绑定
     */
    class UnBindBeiFu extends AsyncTask<String, Integer, String> {

        public UnBindBeiFu() {
            super();
        }

        @Override
        protected String  doInBackground(String... params) {
            //bankNumber：银行卡号 accountNumber：证件号 mobile：手机号码 accountname：用户姓名

            FastpayBean fastpayBean=new FastpayBean();
            fastpayBean.setService("ebatong_mp_unbind");
            fastpayBean.setPartner(Url.Partner);
            fastpayBean.setSign_type("MD5");
            fastpayBean.setInput_charset("UTF-8");
            fastpayBean.setBank_card_no(getIntent().getStringExtra("bankNumber"));
            fastpayBean.setOut_trade_no(billno);
            fastpayBean.setPhone(getIntent().getStringExtra("phone"));
            fastpayBean.setNotify_url("www.hao123.com");
            fastpayBean.setCustomerId(PreferenceUtils.getUserId() + "");
            fastpayBean.setSubject("描述");

            String urlPath = "https://www.ebatong.com/mobileFast/unbind.htm";
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
            if("T".equals(returnBean.getResult())){
                TXWLProgressDialog.Dismiss();
                TXWLApplication.getInstance().showTextToast("解绑成功");

            }else{
                TXWLProgressDialog.Dismiss();
                TXWLApplication.getInstance().showTextToast("解绑失败，重新进入");
                TXWLApplication.getInstance().showTextToast(returnBean.getError_message());
                Log.d("mobileFast_error---->", returnBean.getError_message());
            }
        }
    }
}
