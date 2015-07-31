package com.example.txwl_first;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.example.txwl_first.Adapter.QueryResultCommonAdapter;
import com.example.txwl_first.Adapter.QueryResultLaolaiAdapter;
import com.example.txwl_first.Util.Measure_ForListView;
import com.example.txwl_first.Util.TXWLApplication;
import com.example.txwl_first.Util.TXWLProgressDialog;
import com.example.txwl_first.Util.Url;
import com.example.txwl_first.bean.QueryResultBean;
import com.example.txwl_first.bean.QueryResultItemBean;
import com.example.txwl_first.bean.QueryResultLaolaiItemBean;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;
import org.apache.http.Header;

import java.util.ArrayList;

public class QueryResultActivity extends Activity {
    private static final String TAG ="QueryResultActivity" ;
    private View view;
    private ListView lv_query;
    private ListView lv_add;
    private String way;
    private TextView tv_title,tv_right,tv_add_title;
    private ImageButton ibtn_title_back;
    private QueryResultCommonAdapter adapter_common;
    private QueryResultLaolaiAdapter adapter_laolai;
    private ArrayList<QueryResultItemBean> list_common;
    private ArrayList<QueryResultLaolaiItemBean> list_laolai;
    private final static  String QUERY_LISTVIEW_CAR_ITEM="query_listview_car_item";
    private final static  String QUERY_LISTVIEW_HOUSE_ITEM="query_listview_house_item";
    private final static  String QUERY_LISTVIEW_CREDIT_ITEM="query_listview_credit_item";
    private final static  String QUERY_LISTVIEW_OTHER_ITEM="query_listview_other_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.querylist_layout);
        list_common = new ArrayList<QueryResultItemBean>();
        list_laolai = new ArrayList<QueryResultLaolaiItemBean>();
        way = getIntent().getStringExtra("key");
        //TODO:从网络接口得到数据
        initView();
        getHttp();
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
                Intent intent = new Intent(getApplicationContext(), LoanDetailActivity.class);
                if ("1".equals(list_common.get(position).getRegisttype())) {
                    intent.putExtra("fromButton", QUERY_LISTVIEW_CAR_ITEM);
                }
                if ("2".equals(list_common.get(position).getRegisttype())) {
                    intent.putExtra("fromButton", QUERY_LISTVIEW_HOUSE_ITEM);
                }
                if ("3".equals(list_common.get(position).getRegisttype())) {
                    intent.putExtra("fromButton", QUERY_LISTVIEW_CREDIT_ITEM);
                }
                if ("4".equals(list_common.get(position).getRegisttype()) || "5".equals(list_common.get(position).getRegisttype())) {
                    intent.putExtra("fromButton", QUERY_LISTVIEW_OTHER_ITEM);
                }
                intent.putExtra("registid", list_common.get(position).getRegistid());
                intent.putExtra("headImage", list_common.get(position).getOwneridimg());
                intent.putExtra("status2", list_common.get(position).getStatus2());
                startActivity(intent);
            }
        });

        lv_add.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent=new Intent(QueryResultActivity.this,GetLaolaiListActivity.class);
                intent.putExtra("name", list_laolai.get(position).getName());
                intent.putExtra("cardnum", list_laolai.get(position).getCardnum());
                startActivity(intent);
            }
        });
    }

    private void initView() {

        tv_title = (TextView) findViewById(R.id.tv_title);
        ibtn_title_back = (ImageButton) findViewById(R.id.ibtn_title_back);
        lv_query = (ListView) findViewById(R.id.lv_query);
        ibtn_title_back.setVisibility(View.VISIBLE);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_right.setVisibility(View.GONE);
        tv_title.setText("查询结果");

        adapter_common = new QueryResultCommonAdapter(QueryResultActivity.this,list_common, getResources());
        adapter_laolai = new QueryResultLaolaiAdapter(QueryResultActivity.this,list_laolai, getResources());

        view=View.inflate(this,R.layout.querylist_addfootview,null);//实例化view布局文件
        tv_add_title= (TextView) view.findViewById(R.id.tv_add_title);
        lv_add= (ListView) view.findViewById(R.id.lv_query_add);//实例化view中的listview
        lv_query.addFooterView(view,null,false);//把view视图加载 主listview的后面
        lv_add.setAdapter(adapter_laolai);
        lv_query.setAdapter(adapter_common);
    }

    private void getHttp() {
        TXWLProgressDialog.createDialog(QueryResultActivity.this);
        TXWLProgressDialog.setMessage("查询结果中.....");
        String url = Url.QUERY_URL;
        Log.d("QueryResult_url ------>", url);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("way", way);
        client.setTimeout(15000);
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("QueryResult_url ------>", new String(bytes));
                try {
                    QueryResultBean queryResultBean = new GsonBuilder().create().fromJson(new String(bytes), QueryResultBean.class);
                    if (queryResultBean.getGetQueryResultListBean()==null&&queryResultBean.getQueryResultLaolaiItemBean()==null){
                        TXWLApplication.getInstance().showTextToast("未查询到不良信用记录");
                    }else {
                        int size_common=queryResultBean.getGetQueryResultListBean().length;
                        int size_laolai=queryResultBean.getQueryResultLaolaiItemBean().length;
                        for (int j = 0; j < size_common; j++) {
                            list_common.add(queryResultBean.getGetQueryResultListBean()[j]);
                        }
                        for (int j = 0; j < size_laolai; j++) {
                            list_laolai.add(queryResultBean.getQueryResultLaolaiItemBean()[j]);
                        }
                        tv_add_title.setVisibility(View.VISIBLE);
                        adapter_common.notifyDataSetChanged();
                        adapter_laolai.notifyDataSetChanged();
                        Measure_ForListView.setListViewHeightBasedOnChildren(lv_add);
                        TXWLProgressDialog.Dismiss();
                    }
                } catch (Exception e) {
                    TXWLProgressDialog.Dismiss();
                    TXWLApplication.getInstance().showErrorConnected(e);
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                TXWLProgressDialog.Dismiss();
                TXWLApplication.getInstance().showTextToast("网络错误");
            }
        });

    }
}
