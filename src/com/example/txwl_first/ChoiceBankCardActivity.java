package com.example.txwl_first;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.txwl_first.Util.PreferenceUtils;
import com.example.txwl_first.Util.TXWLApplication;
import com.example.txwl_first.Util.Url;
import com.example.txwl_first.bean.BackInfoItemBean;
import com.example.txwl_first.bean.GetBankInfoBean;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/5/30.
 */
public class ChoiceBankCardActivity extends Activity{

    private static final String TAG ="ChoiceBankCardActivity" ;
    private RelativeLayout rl_about_add_card;
    private LinearLayout rl_about_card;
    private String registid,billno,tip;
    private GetBankInfoBean bean;
    private ArrayList<BackInfoItemBean> list;

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
        registid = getIntent().getStringExtra("registid");
        billno = getIntent().getStringExtra("billno");
        tip = getIntent().getStringExtra("tip");
    }

    private void initListener() {
        rl_about_add_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到添加银行卡 实现生命周期管理
                Intent intent = new Intent(ChoiceBankCardActivity.this, AddBankCardActivity.class);
//                intent.putExtra("recharge_money", getIntent().getStringExtra("recharge_money"));
                intent.putExtra("registid", registid);
                intent.putExtra("billno", billno);
                intent.putExtra("tip", tip);
                startActivity(intent);
//                SLHApplication.getInstance().addActivity(ChoiceBankCardActivity.this);
            }
        });
    }

    private void getUserBankCard() {
        //得到用户的银行卡信息

        AsyncHttpClient client=new AsyncHttpClient();
        client.setTimeout(10000);
        //查询所有黑名单接口 目前没有参数上传
        final RequestParams params = new RequestParams();
        params.put("id", PreferenceUtils.getUserId());//只传入用户id

        client.post(Url.GetBankInfo_URL, params, new AsyncHttpResponseHandler() {

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
                    bean = new GsonBuilder().create().fromJson(new String(responseBody), GetBankInfoBean.class);
                    if ((bean != null) && ("success".equals(bean.getStatus()))) {
                        int size = bean.getList().length;
                        if (size > 0) {
                            //返回有数据 开始动态加载信息
                            initItem(bean, size);
                        } else {
                            //返回没有数据 只显示 按钮
                        }
                    } else {
//                        Toast.makeText(getActivity(), "网络错误，请检查网络", Toast.LENGTH_LONG).show();
                        TXWLApplication.getInstance().showTextToast(bean.getMsg());
                    }
                } catch (Exception e) {
                    TXWLApplication.getInstance().showErrorConnected(e);
                }

            }

            private void initItem(GetBankInfoBean bean, int size) {
                //动态加载信息
                for (int i = 0; i < size; i++) {
                    final BackInfoItemBean item = bean.getList()[i];
                    String card_no = item.getBanknumber();
                    LinearLayout ll_card_item = (LinearLayout) LayoutInflater.from(ChoiceBankCardActivity.this).inflate(R.layout.card_item, null);
                    TextView tv_card_no = (TextView) ll_card_item.findViewById(R.id.tv_card_no);
                    tv_card_no.setText(card_no.substring(card_no.length() - 4, card_no.length()));
                    TextView tv_bank_name = (TextView) ll_card_item.findViewById(R.id.tv_bank_name);
                    tv_bank_name.setText(item.getBankname());
                    ll_card_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //点击下一界面
                            Intent intent = new Intent(ChoiceBankCardActivity.this, VerificationActivity.class);
                            intent.putExtra("registid", registid);
                            intent.putExtra("billno", billno);
                            intent.putExtra("recharge_money", tip);
                            intent.putExtra("bankNumber", item.getBankid());
                            intent.putExtra("accountNumber", item.getBanknumber());
                            intent.putExtra("mobile", item.getMobile());
                            intent.putExtra("accountname", item.getAccountname());
                            intent.putExtra("recharge_money", tip);
                            intent.putExtra("billno",billno);
                            intent.putExtra("bank_code", item.getBank_code());
                            startActivity(intent);
                        }
                    });
                    rl_about_card.addView(ll_card_item);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.d(TAG, "联网失败");
            }
        });

    }


}
