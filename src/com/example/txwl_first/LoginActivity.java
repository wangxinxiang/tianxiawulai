package com.example.txwl_first;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class LoginActivity extends Activity implements View.OnClickListener{
    private TextView tv_title;
    private ImageButton ibtn_title_back;
    private EditText et_regist_mobile,et_password;
    private Button btn_login_regist,btn_regist;

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
        tv_title.setText("登录");
        ibtn_title_back.setVisibility(View.VISIBLE);
        ibtn_title_back.setOnClickListener(this);
    }


    private void initView() {
        et_regist_mobile= (EditText) findViewById(R.id.et_regist_mobile);
        et_password= (EditText) findViewById(R.id.et_password);
        btn_login_regist= (Button) findViewById(R.id.btn_login_regist);
        btn_regist= (Button) findViewById(R.id.btn_regist);
        btn_regist.setVisibility(View.VISIBLE);

        et_regist_mobile.setOnClickListener(this);
        et_password.setOnClickListener(this);
        btn_login_regist.setOnClickListener(this);
        btn_regist.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ibtn_title_back:
                finish();//点击返回 退出当前activity
                break;
            case R.id.btn_regist:
                Intent intent=new Intent(LoginActivity.this,RegistActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login_regist:
                getLogin();//开始联网登录
                break;

        }
    }

    private void getLogin() {

    }
}
