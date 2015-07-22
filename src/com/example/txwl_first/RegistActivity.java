package com.example.txwl_first;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

/**
 * Created by Administrator on 2015/7/22 0022.
 */
public class RegistActivity extends Activity implements View.OnClickListener{
    private TextView tv_title;
    private ImageButton ibtn_title_back;
    private EditText et_regist_mobile,et_password,et_check_code_number;
    private Button btn_login_regist,btn_get_check_code;
    private RelativeLayout rl_regist_check_code;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_regist_layout);
        initTitle();//初始化标题
        initView();//初始化视图
        intiListener();//初始化绑定事件
    }

    private void intiListener() {

    }

    private void initTitle() {
        tv_title= (TextView) findViewById(R.id.tv_title);
        ibtn_title_back= (ImageButton) findViewById(R.id.ibtn_title_back);
        tv_title.setText("注册");
        ibtn_title_back.setVisibility(View.VISIBLE);
        ibtn_title_back.setOnClickListener(this);
    }


    private void initView() {
        et_regist_mobile= (EditText) findViewById(R.id.et_regist_mobile);
        et_password= (EditText) findViewById(R.id.et_password);
        et_check_code_number= (EditText) findViewById(R.id.et_check_code_number);
        btn_login_regist= (Button) findViewById(R.id.btn_login_regist);
        btn_get_check_code= (Button) findViewById(R.id.btn_get_check_code);
        rl_regist_check_code= (RelativeLayout) findViewById(R.id.rl_regist_check_code);

        rl_regist_check_code.setVisibility(View.VISIBLE);
        btn_login_regist.setText("注册");

        et_regist_mobile.setOnClickListener(this);
        et_password.setOnClickListener(this);
        btn_login_regist.setOnClickListener(this);

        btn_get_check_code.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ibtn_title_back:
                finish();//点击返回 退出当前activity
                break;
            case R.id.btn_login_regist:
                getRegist();//开始联网注册
                break;
            case R.id.btn_get_check_code:
                getRegistCode();//开始联网获取 验证码
                break;


        }
    }

    private void getRegistCode() {

    }

    private void getRegist() {
    }
}
