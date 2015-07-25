package com.example.txwl_first;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.txwl_first.Util.*;
import com.example.txwl_first.bean.GetPersonalInfoBean;
import com.example.txwl_first.bean.MobileInfoBean;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

public class LoginActivity extends Activity implements View.OnClickListener{
    private static String TAG="LoginActivity";
    private TextView tv_title;
    private ImageButton ibtn_title_back;
    private EditText et_regist_mobile,et_password;
    private Button btn_login_regist,btn_regist;
    private GetPersonalInfoBean bean;

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
                setResult(Constant.LOGIN_FAILE);
                finish();//点击返回 退出当前activity
                //登录没有完成就退出 需要返回处理
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
        String mobile=null;
        String userpwd=null;

        mobile=et_regist_mobile.getText().toString().trim();
        userpwd=MD5.getMD5Lower(et_password.getText().toString().trim());
        if(DataVeri.isMobileNum(mobile)&&!("".equals(userpwd))){
            AsyncHttpClient client=new AsyncHttpClient();
            client.setTimeout(10000);

            final RequestParams params = new RequestParams();
            params.put("mobile",mobile);//手机号参数
            params.put("userpwd",userpwd);//登录密码

            client.post(Url.UserLogin_URL, params, new AsyncHttpResponseHandler() {

                @Override
                public void onStart() {
                    super.onStart();
                    Log.d(TAG, "开始联网");
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.d(TAG, "联网成功");
                    Log.d(TAG + "  onSuccess-->", new String(responseBody));//打印网络访问结果

                    try {
                        bean = new GsonBuilder().create().fromJson(new String(responseBody), GetPersonalInfoBean.class);
                        if ((bean != null) && ("success".equals(bean.getStatus()))) {
                            //网络请求成功
                            TXWLApplication.getInstance().showTextToast("登录成功");
                            //登录成功 应该实现跳转 到完善用户信息界面

                            PreferenceUtils.getInstance().setUserID(bean.getPersonalInfoBean().getUserid());
                            PreferenceUtils.getInstance().setUserName(bean.getPersonalInfoBean().getRealname());
                            PreferenceUtils.getInstance().setUserHeadImage(bean.getPersonalInfoBean().getHeadimage());
                            PreferenceUtils.getInstance().setIsLogin(true);//设置 已经登录标志位

                            setResult(Constant.LOGIN_CHANGE);
                            //写入 userId 查看个人信息要用
                            finish();
                        } else {
                            TXWLApplication.getInstance().showTextToast(bean.getMsg());
                        }
                    } catch (Exception e) {
                        TXWLApplication.getInstance().showErrorConnected(e);
                    }

                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    Log.d(TAG, "联网失败");
                }
            });
        }
    }
}
