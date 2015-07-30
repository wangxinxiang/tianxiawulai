package com.example.txwl_first;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.example.txwl_first.Adapter.LaolaiListAdapter;
import com.example.txwl_first.Util.TXWLApplication;
import com.example.txwl_first.Util.TXWLProgressDialog;
import com.example.txwl_first.Util.Url;
import com.example.txwl_first.bean.GetLaoLaiDetailBean;
import com.example.txwl_first.bean.GetLaolaiListBean;
import com.example.txwl_first.bean.GetLaolaiListItemBean;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/30 0030.
 */
public class GetLaolaiDetailActivity extends Activity {
    private String TAG="GetLaolaiDetailActivity";
    private TextView tv_title;
    private ImageButton ibtn_title_back;
    private String laolaiid;
    private GetLaoLaiDetailBean bean;

    private TextView tv_query_name,tv_query_age,tv_query_cardnum,tv_query_area,tv_query_casecount;

    private TextView tv_courtname,tv_areaname,tv_gistid,tv_casecreatetime,tv_casecode,tv_gistunit,
            tv_duty,tv_performance,tv_disrupttypename,tv_publishdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laolai_detail_layout);
        laolaiid=getIntent().getStringExtra("laolaiid");
        Log.d("laolaiid",laolaiid);
        initView();
        getHttp();
        ininListener();
    }

    private void ininListener() {

        ibtn_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void getHttp() {
        String url = Url.GetLaoLaiDetail_URL;
        Log.d("GetUserDetail_URL ------>", url);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("laolaiid", laolaiid);
        client.post(url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                Log.d(TAG, "开始联网");
                TXWLProgressDialog.createDialog(GetLaolaiDetailActivity.this);
                TXWLProgressDialog.setMessage("正在加载数据...");
            }

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d(TAG+"onSuccess ------>", new String(bytes));
                try {
                    bean= new GsonBuilder().create().fromJson(new String(bytes), GetLaoLaiDetailBean.class);
                    if ("success".equals(bean.getStatus())&&bean.getLaolaiinfo()!=null){
                         setData();
                    }else {
                        TXWLApplication.getInstance().showTextToast(bean.getMsg());
                    }
                } catch (Exception e) {
                    TXWLApplication.getInstance().showErrorConnected(e);
                }

            }

            private void setData() {
                tv_query_name.setText(bean.getLaolaiinfo().getName());
                tv_query_age.setText(bean.getLaolaiinfo().getAge()+"岁");
                tv_query_cardnum.setText(bean.getLaolaiinfo().getCardnum());
                tv_query_area.setText(bean.getLaolaiinfo().getArea());

                tv_courtname.setText(bean.getLaolaiinfo().getCourtname());
                tv_areaname.setText(bean.getLaolaiinfo().getAreaname());
                tv_gistid.setText(bean.getLaolaiinfo().getGistid());
                tv_casecreatetime.setText(bean.getLaolaiinfo().getCasecreatetime());
                tv_casecode.setText(bean.getLaolaiinfo().getCasecode());
                tv_gistunit.setText(bean.getLaolaiinfo().getGistunit());
                tv_duty.setText(bean.getLaolaiinfo().getDuty());
                tv_performance.setText(bean.getLaolaiinfo().getPerformance());
                tv_disrupttypename.setText(bean.getLaolaiinfo().getDisrupttypename());
                tv_publishdate.setText(bean.getLaolaiinfo().getPublishdate());

                TXWLProgressDialog.Dismiss();//加载完成数据 消失
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                TXWLApplication.getInstance().showTextToast("网络错误");
                TXWLProgressDialog.Dismiss();
            }
        });
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        ibtn_title_back = (ImageButton) findViewById(R.id.ibtn_title_back);
        ibtn_title_back.setVisibility(View.VISIBLE);
        tv_title.setText("详情");

        tv_query_name= (TextView) findViewById(R.id.tv_query_name);
        tv_query_age= (TextView) findViewById(R.id.tv_query_age);
        tv_query_cardnum= (TextView) findViewById(R.id.tv_query_cardnum);
        tv_query_area= (TextView) findViewById(R.id.tv_query_area);
        tv_query_casecount= (TextView) findViewById(R.id.tv_query_casecount);
        tv_query_casecount.setVisibility(View.INVISIBLE);

        tv_courtname= (TextView) findViewById(R.id.tv_courtname);
        tv_areaname= (TextView) findViewById(R.id.tv_areaname);
        tv_gistid= (TextView) findViewById(R.id.tv_gistid);
        tv_casecreatetime= (TextView) findViewById(R.id.tv_casecreatetime);
        tv_casecode= (TextView) findViewById(R.id.tv_casecode);
        tv_gistunit= (TextView) findViewById(R.id.tv_gistunit);
        tv_duty= (TextView) findViewById(R.id.tv_duty);
        tv_performance= (TextView) findViewById(R.id.tv_performance);
        tv_disrupttypename= (TextView) findViewById(R.id.tv_disrupttypename);
        tv_publishdate= (TextView) findViewById(R.id.tv_publishdate);


    }
}
