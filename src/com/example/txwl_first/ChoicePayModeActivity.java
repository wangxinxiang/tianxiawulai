package com.example.txwl_first;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.txwl_first.Util.TXWLApplication;
import com.example.txwl_first.service.GetOrderNumService;

/**
 * Created by Administrator on 2015/5/29.
 */
public class ChoicePayModeActivity extends Activity{
    private RelativeLayout rl_choice_pay,rl_acount_pay,rl_bankcard_pay;
    private TextView tv_choice_pay_amount,tv_pay_money,tv_acount_money;
    private String recharge_money,aim,FromActivity;
    Button btn_submit;
    CheckBox acount_checbox,bankcard_checkbox;
    private String topicID,recipientsdisease,recipientsname,ShiLeHui,Kid;
    private GetOrderNumService getOrderNumService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice_pay_mode);
        TXWLApplication.getInstance().pushStack(this);
        initView();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        TXWLApplication.getInstance().popStack(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrderNumber();
        GetUserMoney();
    }

    private void initView() {
        recharge_money = getIntent().getStringExtra("recharge_money");
        aim = getIntent().getStringExtra("aim");
        FromActivity = getIntent().getStringExtra("FromActivity");
        topicID = getIntent().getStringExtra("topicID");
        recipientsdisease = getIntent().getStringExtra("recipientsdisease");
        recipientsname = getIntent().getStringExtra("recipientsname");
        ShiLeHui = getIntent().getStringExtra("ShiLeHui");
        Kid = getIntent().getStringExtra("Kid");


        rl_choice_pay = (RelativeLayout) findViewById(R.id.rl_choice_pay);
        rl_acount_pay = (RelativeLayout) findViewById(R.id.rl_acount_pay);
        rl_bankcard_pay = (RelativeLayout) findViewById(R.id.rl_bankcard_pay);
        tv_acount_money = (TextView) findViewById(R.id.tv_account_money);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        acount_checbox = (CheckBox) findViewById(R.id.acount_checkbox);
        bankcard_checkbox = (CheckBox) findViewById(R.id.bankcar_checkbox);

        tv_choice_pay_amount = (TextView) findViewById(R.id.tv_choice_pay_amount);
        tv_pay_money= (TextView) findViewById(R.id.tv_pay_money);
        tv_choice_pay_amount.setText("¥ " + recharge_money);
        tv_pay_money.setText(recharge_money);



        if("DonationMoneyActivity".equals(FromActivity)){
            rl_choice_pay.setVisibility(View.GONE);

            acount_checbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    acount_checbox.setButtonDrawable(R.drawable.single_select_pressed);
                    bankcard_checkbox.setButtonDrawable(R.drawable.single_select_normal);
                    acount_checbox.setChecked(true);
                    bankcard_checkbox.setChecked(false);
                }
            });

            bankcard_checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bankcard_checkbox.setButtonDrawable(R.drawable.single_select_pressed);
                    acount_checbox.setButtonDrawable(R.drawable.single_select_normal);
                    bankcard_checkbox.setChecked(true);
                    acount_checbox.setChecked(false);
                }
            });

            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //余额支付
                    if(acount_checbox.isChecked()){

                    }
                    //银行卡支付
                    if(bankcard_checkbox.isChecked()){
                        if(getOrderNumService.getOrder()!=null){
                            Intent intent = new Intent(ChoicePayModeActivity.this, ChoiceBankCardActivity.class);
                            intent.putExtra("recharge_money", recharge_money);
                            intent.putExtra("aim", aim);
                            intent.putExtra("recipientsname",recipientsname);
                            intent.putExtra("recipientsdisease",recipientsdisease);
                            intent.putExtra("billno",getOrderNumService.getOrder());
                            startActivity(intent);
                        }
//                        SLHApplication.getInstance().addActivity(ChoicePayModeActivity.this);
                    }
                }
            });
        }

        else{
            rl_bankcard_pay.setVisibility(View.GONE);
            rl_acount_pay.setVisibility(View.GONE);
            btn_submit.setVisibility(View.GONE);

            rl_choice_pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ChoicePayModeActivity.this, ChoiceBankCardActivity.class);
                    intent.putExtra("recharge_money", recharge_money);
                    intent.putExtra("aim", aim);
                    intent.putExtra("billno",getOrderNumService.getOrder());
                    startActivity(intent);
//                    SLHApplication.getInstance().addActivity(ChoicePayModeActivity.this);
                }
            });
        }
    }




    //获取订单号接口
    private void getOrderNumber() {
        getOrderNumService = GetOrderNumService.getInstance();
        getOrderNumService.getOrderNum(recharge_money, ChoicePayModeActivity.this);
    }

    //获取用户余额
    public void GetUserMoney() {

    }

    //资助施乐会
    public void UserEndow(final String kid,String money) {
    }

}
