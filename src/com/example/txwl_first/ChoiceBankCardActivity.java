package com.example.txwl_first;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.example.txwl_first.Util.TXWLApplication;

/**
 * Created by Administrator on 2015/5/30.
 */
public class ChoiceBankCardActivity extends Activity{

    private RelativeLayout rl_about_add_card;
    private LinearLayout rl_about_card;
    private String recharge_money,aim,billno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice_bank_card);
        TXWLApplication.getInstance().pushStack(this);
        initView();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TXWLApplication.getInstance().popStack(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        rl_about_card.removeAllViews();
        getUserBankCard();
    }

    private void initView() {
        rl_about_add_card = (RelativeLayout) findViewById(R.id.rl_about_add_card);
        rl_about_card = (LinearLayout) findViewById(R.id.rl_about_card);
        recharge_money = getIntent().getStringExtra("recharge_money");
        billno = getIntent().getStringExtra("billno");
    }

    private void initListener() {
        rl_about_add_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到添加银行卡 实现生命周期管理
                Intent intent = new Intent(ChoiceBankCardActivity.this, AddBankCardActivity.class);
//                intent.putExtra("recharge_money", getIntent().getStringExtra("recharge_money"));
                intent.putExtra("recharge_money", "1");
                intent.putExtra("aim", getIntent().getStringExtra("aim"));
                startActivity(intent);
//                SLHApplication.getInstance().addActivity(ChoiceBankCardActivity.this);
            }
        });
    }

    private void getUserBankCard() {
        //todo直接跳转到验证码界面
    }


}
