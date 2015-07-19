package com.example.txwl_first;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class LoginActivity extends Activity {
    RelativeLayout rl_regist_check_code;
    Button btn_pay_money;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist_layout);

        initView();

    }

    private void initView() {
        rl_regist_check_code = (RelativeLayout) findViewById(R.id.rl_regist_check_code);
        btn_pay_money = (Button) findViewById(R.id.btn_pay_money);

        rl_regist_check_code.setVisibility(View.INVISIBLE);
        btn_pay_money.setText("登录");
    }
}
