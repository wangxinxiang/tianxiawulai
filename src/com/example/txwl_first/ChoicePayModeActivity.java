package com.example.txwl_first;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.txwl_first.Util.PreferenceUtils;
import com.example.txwl_first.Util.TXWLApplication;
import com.example.txwl_first.Util.TXWLProgressDialog;
import com.example.txwl_first.Util.Url;
import com.example.txwl_first.bean.AddRechargeBean;
import com.example.txwl_first.bean.AddRechargeItemBean;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

/**
 * Created by Administrator on 2015/7/28 0028.
 */
public class ChoicePayModeActivity extends Activity{
    private RelativeLayout rl_choice_pay;
    private TextView tv_choice_pay_amount;
    private String tip,registid,billno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice_pay_mode);
        TXWLApplication.getInstance().pushStack(this);
        initView();
    }

    private void initView() {
        TXWLProgressDialog.createDialog(ChoicePayModeActivity.this);
        TXWLProgressDialog.setMessage("获取订单号中.....");
        tip = getIntent().getStringExtra("tip");
        registid = getIntent().getStringExtra("registid");

        rl_choice_pay = (RelativeLayout) findViewById(R.id.rl_choice_pay);

        tv_choice_pay_amount = (TextView) findViewById(R.id.tv_choice_pay_amount);
        tv_choice_pay_amount.setText("¥ " + tip);



        rl_choice_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (billno != null) {
                    Intent intent = new Intent(ChoicePayModeActivity.this, ChoiceBankCardActivity.class);
                    intent.putExtra("registid", registid);
                    intent.putExtra("billno", billno);
                    intent.putExtra("tip", tip);
                    startActivity(intent);
                } else {
                    TXWLApplication.getInstance().showTextToast("订单号为空，退出本页面后再试一次");
                }
            }
        });
    }

    private void getOrderNum() {
        String url = Url.AddRecharge_URL;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("rechargeaccount", tip);
        params.put("userid", PreferenceUtils.getUserId());

        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("getOrderNum ---->", new String(bytes));

                try {
                    AddRechargeBean addRechargeBean = new GsonBuilder().create().fromJson(new String(bytes), AddRechargeBean.class);
                    AddRechargeItemBean addRechargeItemBean = addRechargeBean.getRecharge();
                    billno = addRechargeItemBean.getBillno();
                    TXWLProgressDialog.Dismiss();
                } catch (Exception e) {
                    TXWLApplication.getInstance().showErrorConnected(e);
                    TXWLProgressDialog.Dismiss();
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                TXWLProgressDialog.Dismiss();
                TXWLApplication.getInstance().showTextToast("网络错误");
            }
        });
//
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrderNum();
    }
}
