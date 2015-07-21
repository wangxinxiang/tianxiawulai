package com.example.txwl_first;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.example.txwl_first.Adapter.QueryResultNewAdapter;
import com.example.txwl_first.Util.TXWLApplication;
import com.example.txwl_first.Util.Url;
import com.example.txwl_first.bean.QueryResultBean;
import com.example.txwl_first.bean.QueryResultItemBean;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

import java.util.ArrayList;

public class QueryResultActivity extends Activity {
    private WebView webView;

    private String way;
    private TextView tv_title,tv_right;
    private ImageButton ibtn_title_back;
    private ListView lv_query;
    private QueryResultNewAdapter adapter;
    private ArrayList<QueryResultItemBean> loanBeans;
    private final static  String QUERY_LISTVIEW_CAR_ITEM="query_listview_car_item";
    private final static  String QUERY_LISTVIEW_HOUSE_ITEM="query_listview_house_item";
    private final static  String QUERY_LISTVIEW_CREDIT_ITEM="query_listview_credit_item";
    private final static  String QUERY_LISTVIEW_OTHER_ITEM="query_listview_other_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.querylist_layout);
        loanBeans = new ArrayList<QueryResultItemBean>();
        way = getIntent().getStringExtra("key");
        //TODO:从网络接口得到数据
        getSearchData();
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

        lv_query.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getApplicationContext(),LoanDetailActivity.class);
                if("1".equals(loanBeans.get(position).getRegisttype())){
                    intent.putExtra("fromButton", QUERY_LISTVIEW_CAR_ITEM);
                }
                if ("2".equals(loanBeans.get(position).getRegisttype())){
                    intent.putExtra("fromButton",QUERY_LISTVIEW_HOUSE_ITEM);
                }
                if ("3".equals(loanBeans.get(position).getRegisttype())){
                    intent.putExtra("fromButton",QUERY_LISTVIEW_CREDIT_ITEM);
                }
                if ("4".equals(loanBeans.get(position).getRegisttype())){
                    intent.putExtra("fromButton",QUERY_LISTVIEW_OTHER_ITEM);
                }
                intent.putExtra("registid", loanBeans.get(position).getRegistid());
                startActivity(intent);
            }
        });
    }

    private void initView() {
        webView = (WebView) findViewById(R.id.web_query);
        WebSettings settings = webView.getSettings();
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);

        tv_title = (TextView) findViewById(R.id.tv_title);
        ibtn_title_back = (ImageButton) findViewById(R.id.ibtn_title_back);
        lv_query = (ListView) findViewById(R.id.lv_query);
        ibtn_title_back.setVisibility(View.VISIBLE);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_right.setVisibility(View.GONE);
        tv_title.setText("查询结果");

        adapter = new QueryResultNewAdapter(QueryResultActivity.this,loanBeans);
        lv_query.setAdapter(adapter);
    }

    private void getSearchData() {
        String url = Url.QUERY_URL;
        Log.d("QueryResult_url ------>", url);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("way", way);
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("QueryResult_url ------>", new String(bytes));
                try {
                    QueryResultBean queryResultBean = new GsonBuilder().create().fromJson(new String(bytes), QueryResultBean.class);
                    if (queryResultBean.getUrl() == null) {
                        for (int j = 0; j < queryResultBean.getGetQueryResultListBean().length; j++) {
                            loanBeans.add(queryResultBean.getGetQueryResultListBean()[j]);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        lv_query.setVisibility(View.GONE);
                        webView.setVisibility(View.VISIBLE);
                        webView.loadUrl(queryResultBean.getUrl());
                    }
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
}
