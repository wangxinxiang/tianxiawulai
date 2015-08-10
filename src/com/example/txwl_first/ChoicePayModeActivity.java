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
    private String tip,registid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice_pay_mode);
        TXWLApplication.getInstance().pushStack(this);
        initView();
    }

    private void initView() {
        tip = getIntent().getStringExtra("tip");
        registid = getIntent().getStringExtra("registid");

        RelativeLayout rl_choice_pay = (RelativeLayout) findViewById(R.id.rl_choice_pay);

        TextView tv_choice_pay_amount = (TextView) findViewById(R.id.tv_choice_pay_amount);
        tv_choice_pay_amount.setText("¥ " + tip);



        rl_choice_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(ChoicePayModeActivity.this, ChoiceBankCardActivity.class);
                    intent.putExtra("registid", registid);
                    intent.putExtra("tip", tip);
                    startActivity(intent);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
