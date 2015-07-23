package com.example.txwl_first;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.txwl_first.Util.DataVeri;
import com.example.txwl_first.Util.TXWLApplication;
import com.example.txwl_first.Util.Url;
import com.example.txwl_first.bean.BlackListBean;
import com.example.txwl_first.bean.MobileInfoBean;
import com.example.txwl_first.bean.MobileInfoItemBean;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

/**
 * Created by Administrator on 2015/7/22 0022.
 */
public class RegistActivity extends Activity implements View.OnClickListener{
    private static String TAG="RegistActivity";
    private TextView tv_title;
    private ImageButton ibtn_title_back;
    private EditText et_regist_mobile,et_password,et_check_code_number;
    private Button btn_login_regist,btn_get_check_code;
    private RelativeLayout rl_regist_check_code;

    private MobileInfoBean bean;
    private MobileInfoItemBean item;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_regist_layout);
        initTitle();//初始化标题
        initView();//初始化视图
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
        String mobile=null;
        mobile=et_regist_mobile.getText().toString().trim();
        if(DataVeri.isMobileNum(mobile)){
            AsyncHttpClient client=new AsyncHttpClient();
            client.setTimeout(10000);

            final RequestParams params = new RequestParams();
            params.put("mobile",mobile);//手机号参数

            client.post(Url.MobileValidateSend_URL, params, new AsyncHttpResponseHandler() {

                @Override
                public void onStart() {
                    super.onStart();
                    Log.d(TAG, "开始联网");
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.d(TAG, "联网成功");
                    Log.d(TAG + "onSuccess-->", new String(responseBody));//打印网络访问结果

                    try {
                        bean = new GsonBuilder().create().fromJson(new String(responseBody), MobileInfoBean.class);
                        if ((bean != null) && ("success".equals(bean.getStatus()))) {
                            //网络请求成功
                            TXWLApplication.getInstance().showTextToast("验证码发送成功请查收");
                        } else {
                            Toast.makeText(RegistActivity.this, "网络错误，请检查网络", Toast.LENGTH_LONG).show();
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

    private void getRegist() {
        String mobile=null;
        String validatenum=null;
        String userpwd=null;

        mobile=et_regist_mobile.getText().toString().trim();
        validatenum=et_check_code_number.getText().toString().trim();
        userpwd=et_password.getText().toString().trim();
        if(DataVeri.isMobileNum(mobile)&&DataVeri.isNaN(validatenum)&&!("".equals(userpwd))){
            AsyncHttpClient client=new AsyncHttpClient();
            client.setTimeout(10000);

            final RequestParams params = new RequestParams();
            params.put("mobile",mobile);//手机号参数
            params.put("validatenum",validatenum);//验证码
            params.put("userpwd",userpwd);//登录密码

            client.post(Url.UserRegist_URL, params, new AsyncHttpResponseHandler() {

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
                        bean = new GsonBuilder().create().fromJson(new String(responseBody), MobileInfoBean.class);
                        if ((bean != null) && ("success".equals(bean.getStatus()))) {
                            //网络请求成功
                            TXWLApplication.getInstance().showTextToast("注册成功");
                            finish();
                        } else {
                            Toast.makeText(RegistActivity.this, "网络错误，请检查网络", Toast.LENGTH_LONG).show();
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
