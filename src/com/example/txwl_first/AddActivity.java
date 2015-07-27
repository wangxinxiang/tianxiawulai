package com.example.txwl_first;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.txwl_first.Util.*;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

public class AddActivity extends Activity{
    private TextView tv_title,tv_right;
    private ImageButton ibtn_title_back;
    private Button btn_add;
    private EditText et_username,et_mobile_number,et_password;
    private String[] data = new String[3];
    private boolean isSubmit = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);
        initView();
        setOnClickListener();
    }

    private void setOnClickListener() {
        ibtn_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
                if (isSubmit) {
                    addGroupMember();
                }
            }
        });

    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        ibtn_title_back = (ImageButton) findViewById(R.id.ibtn_title_back);
        btn_add = (Button) findViewById(R.id.btn_add);
        ibtn_title_back.setVisibility(View.VISIBLE);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_right.setVisibility(View.GONE);
        tv_title.setText("添加");

        et_username = (EditText) findViewById(R.id.et_username);
        et_mobile_number = (EditText) findViewById(R.id.et_mobile_number);
        et_password = (EditText) findViewById(R.id.et_password);
    }

    private void getData() {
        isSubmit = true;
        if ("".equals(et_username.getText().toString().trim())) {
            TXWLApplication.getInstance().showTextToast("姓名不能为空");
            isSubmit = false;
        } else {
            data[0] = et_username.getText().toString().trim();
        }

        if (DataVeri.isMobileNum(et_mobile_number.getText().toString().trim())) {
            data[1] = et_mobile_number.getText().toString().trim();
        } else {
            isSubmit = false;
        }

        if (!DataVeri.stringIsNull(et_password.getText().toString().trim(), "密码")) {
            data[2] = MD5.getMD5Lower(et_password.getText().toString().trim());
        } else {
            isSubmit = false;
        }
    }

    private void addGroupMember() {
        String url = Url.ADD_GROUP_URL;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("realname", data[0]);
        params.put("mobile", data[1]);
        params.put("pwd", data[2]);
        params.put("userid", PreferenceUtils.getUserId());

        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("addGroupMember---------->", new String(bytes));
                if (new String(bytes).contains("添加成功")) {
                    TXWLApplication.getInstance().showTextToast("添加成功");
                    finish();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }
}
