package com.example.txwl_first;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.txwl_first.Util.DataVeri;
import com.example.txwl_first.Util.PreferenceUtils;
import com.example.txwl_first.Util.TXWLApplication;
import com.example.txwl_first.Util.Url;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;
import org.apache.http.Header;

/**
 * Created by licheng on 19/7/15.
 */
public class ReportActivity extends Activity {
    private static final String TAG = "ReportActivity";
    private TextView tv_title;
    private ImageButton ibtn_title_back;
    private EditText et_report;
    private Button btn_pay_money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_layout);
        initView();
        setOnClickListener();
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

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        ibtn_title_back = (ImageButton) findViewById(R.id.ibtn_title_back);
        ibtn_title_back.setVisibility(View.VISIBLE);
        tv_title.setText("举报");

        et_report = (EditText) findViewById(R.id.et_report);
        btn_pay_money = (Button) findViewById(R.id.btn_pay_money);
    }

    private void setOnClickListener() {
        ibtn_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_pay_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commitReport();
            }
        });
    }

    private void commitReport() {
        String url = Url.REPORT_URL;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("registid", getIntent().getStringExtra("registid"));
        if (DataVeri.stringIsNull(et_report.getText().toString(), "举报内容")) {
            return;
        }
        params.put("reportcontent", et_report.getText());
        params.put("userid", PreferenceUtils.getUserId());

        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("commitReport ----->", new String(bytes));
                if (new String(bytes).contains("success")) {
                    TXWLApplication.getInstance().showTextToast("添加成功");
                    finish();
                } else {
                    TXWLApplication.getInstance().showTextToast("添加失败");
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                TXWLApplication.getInstance().showTextToast("网络错误");
            }
        });

    }
}
