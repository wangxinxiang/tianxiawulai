package com.example.txwl_first;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.txwl_first.Util.Constant;
import com.example.txwl_first.Util.PreferenceUtils;
import com.example.txwl_first.Util.TXWLApplication;
import com.example.txwl_first.Util.Url;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2015/5/31.
 */
public class RechargeSuccessActivity extends Activity{

    private Button recharge_success_commit;
    private ImageButton ibtn_title_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recharge_success);
        TXWLApplication.getInstance().pushStack(this);
        initView();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        ibtn_title_back = (ImageButton) findViewById(R.id.ibtn_title_back);
        ibtn_title_back.setVisibility(View.VISIBLE);
        TextView tv_right = (TextView) findViewById(R.id.tv_right);
        tv_right.setVisibility(View.GONE);
        tv_title.setText("确认支付");

        recharge_success_commit = (Button) findViewById(R.id.recharge_success_commit);

        TextView  recharge_success_order = (TextView) findViewById(R.id.recharge_success_order);
        TextView  recharge_success_time = (TextView) findViewById(R.id.recharge_success_time);
        TextView recharge_success_method = (TextView) findViewById(R.id.recharge_success_method);
        TextView   recharge_success_status = (TextView) findViewById(R.id.recharge_success_status);
        TextView   tv_recipientsdisease = (TextView) findViewById(R.id.tv_recipientsdisease);
        TextView confirm_recharge_money = (TextView) findViewById(R.id.confirm_recharge_money);


            //设置带有finish栈功能的setTitle
//            setTitleAndFinishStack("充值成功");
            tv_recipientsdisease.setVisibility(View.GONE);
            recharge_success_order.setText(getIntent().getStringExtra("billno"));
            recharge_success_method.setText("贝付");
            recharge_success_status.setText("充值成功");
            confirm_recharge_money.setText(getIntent().getStringExtra("cz_money"));
            recharge_success_time.setText(getCurrentTime());

    }

    private void initListener() {
        ibtn_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TXWLApplication.getInstance().showTextToast("交易成功，请按确定将交易数据提交到服务器");
            }
        });

        recharge_success_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPaymentOrderOk();
            }
        });
    }

    //获取当前时间
    private String getCurrentTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());
    }

    @Override
    public void onBackPressed() {
       TXWLApplication.getInstance().showTextToast("交易成功，请按确定将交易数据提交到服务器");
    }

    //将结果交给服务器
    public void getPaymentOrderOk() {
        String url = Url.RechargeOK_URL;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("out_trade_no", getIntent().getStringExtra("billno"));
        params.put("customerId", PreferenceUtils.getUserId());
        params.put("registid", getIntent().getStringExtra("registid"));

        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                if (new String(bytes).contains("success")) {
                    TXWLApplication.getInstance().showTextToast("已将记录交给服务器");
                    setResult(Constant.LOGIN_CHANGE);
                    TXWLApplication.getInstance().finishStack();
                } else {
                    TXWLApplication.getInstance().showTextToast("支付成功，将记录交给服务器失败,再按次确定");
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                TXWLApplication.getInstance().showTextToast("支付成功，网络错误,再按次确定");
            }
        });
    }
}
