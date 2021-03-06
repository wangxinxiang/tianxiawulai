package com.example.txwl_first;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.txwl_first.Util.*;
import com.example.txwl_first.bean.GetPersonalInfoBean;
import com.example.txwl_first.bean.PersonalInfoBean;
import com.example.txwl_first.business.LoaderBusiness;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;
import org.apache.http.Header;

import java.io.File;

/**
 * Created by licheng on 18/7/15.
 */
public class PersonalInfoActivity extends Activity implements View.OnFocusChangeListener {
    private static final String TAG = "PersonalInfoActivity";
    private TextView tv_title, tv_right, detail_name, detail_phone, detail_company, detail_company_phone, detail_city, detail_address;
    private ImageButton ibtn_title_back;
    private ImageView detail_head;
    private PersonalInfoBean personalInfoBean;
    private String headImage;
    private LinearLayout ll_quit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_info);
        initView();
        setOnClickListener();
        getInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        Log.d(TAG, "onPause");

    }

    private void setOnClickListener() {
        ibtn_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PersonalInfoActivity.this);
                builder.setMessage("确认修改个人信息吗？");
                builder.setTitle("提示");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        modifyInfo();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
        detail_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalInfoActivity.this, PhotoActivity.class);
                intent.putExtra("from", -1);
                startActivityForResult(intent, Constant.GETPHOTO);
            }
        });

        ll_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PersonalInfoActivity.this);
                builder.setMessage("确认退出吗？");
                builder.setTitle("提示");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PreferenceUtils.getInstance().clear();
                        dialog.dismiss();
                        setResult(Constant.LOGIN_CHANGE);
                        finish();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
    }

    private void initView() {
        TXWLProgressDialog.createDialog(PersonalInfoActivity.this);
        TXWLProgressDialog.setMessage("获取信息中");
        tv_title = (TextView) findViewById(R.id.tv_title);
        ibtn_title_back = (ImageButton) findViewById(R.id.ibtn_title_back);
        ibtn_title_back.setVisibility(View.VISIBLE);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("修改");
        tv_title.setText("个人信息");

        detail_head = (ImageView) findViewById(R.id.detail_head);
        detail_name = (TextView) findViewById(R.id.detail_name);
        detail_phone = (TextView) findViewById(R.id.detail_phone);
        detail_company = (TextView) findViewById(R.id.detail_company);
        detail_company_phone = (TextView) findViewById(R.id.detail_company_phone);
        detail_city = (TextView) findViewById(R.id.detail_city);
        detail_address = (TextView) findViewById(R.id.detail_address);
        ll_quit = (LinearLayout) findViewById(R.id.ll_quit);
    }

    /**
     * 从服务器获取信息
     */
    private void getInfo() {
        String url = Url.PERSONAL_URL;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("userid", PreferenceUtils.getUserId());
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("PersonalInfoActivity-------->", new String(bytes));
                try {
                    GetPersonalInfoBean getPersonalInfoBean = new GsonBuilder().create().fromJson(new String(bytes), GetPersonalInfoBean.class);
                    personalInfoBean = getPersonalInfoBean.getPersonalInfoBean();
                    setData();
                    PreferenceUtils.getInstance().setUserName(personalInfoBean.getRealname());
                    PreferenceUtils.getInstance().setUserHeadImage(personalInfoBean.getHeadimage());
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
    }

    private void setData() {
        LoaderBusiness.loadImage(personalInfoBean.getHeadimage(), detail_head);
        detail_name.setText(personalInfoBean.getRealname());
        detail_phone.setText(personalInfoBean.getMobile());
        detail_company.setText(personalInfoBean.getCompanyname());
        detail_company_phone.setText(personalInfoBean.getPhone());
        detail_city.setText(personalInfoBean.getProvince());
        detail_address.setText(personalInfoBean.getAddress());
        headImage = "";
    }

    private boolean checkData() {

        return true;
    }

    private void modifyInfo() {
        String url = Url.PERSONAL_MODIFY_URL;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("userid", PreferenceUtils.getUserId());
        params.put("address", detail_address.getText());
        params.put("province", detail_city.getText());
        params.put("companyname", detail_company.getText());
        params.put("headimage", headImage);

        if (DataVeri.isMobileNum(detail_phone.getText().toString())) {
            params.put("mobile", detail_phone.getText());
        } else {
            return;
        }

        params.put("realname", detail_name.getText());

        if (DataVeri.isPhone(detail_company_phone.getText().toString())) {
            params.put("phone", detail_company_phone.getText());
        } else {
            TXWLApplication.getInstance().showTextToast("电话号码格式不正确");
            return;
        }

        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("modifyInfo-------->", new String(bytes));
//                if (new String(bytes).contains("success")) {
//                    TXWLApplication.getInstance().showTextToast("修改成功");
//                    PreferenceUtils.getInstance().setUserName(personalInfoBean.getRealname());
//                    PreferenceUtils.getInstance().setUserHeadImage(personalInfoBean.getHeadimage());
//                    setResult(Constant.LOGIN_CHANGE);
//                    finish();
//                } else {
//                    TXWLApplication.getInstance().showTextToast("修改失败");
//                }

                try {
                    GetPersonalInfoBean getPersonalInfoBean = new GsonBuilder().create().fromJson(new String(bytes), GetPersonalInfoBean.class);
                    personalInfoBean = getPersonalInfoBean.getPersonalInfoBean();
                    PreferenceUtils.getInstance().setUserName(personalInfoBean.getRealname());
                    PreferenceUtils.getInstance().setUserHeadImage(personalInfoBean.getHeadimage());
                    setResult(Constant.LOGIN_CHANGE);
                    finish();
                } catch (Exception e) {
                    TXWLApplication.getInstance().showErrorConnected(e);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                TXWLApplication.getInstance().showTextToast("网络错误");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constant.GETPHOTO:
                if (data != null) {
                    headImage = data.getStringExtra("imgUrl");
                    LoaderBusiness.loadImage(headImage, detail_head);
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v instanceof EditText) {
            if (hasFocus) {
                int length = ((EditText) v).getText().length();
                ((EditText) v).setSelection(length);
            }
        }
    }
}
