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
import com.example.txwl_first.Adapter.LaolaiListAdapter;
import com.example.txwl_first.Util.Measure_ForListView;
import com.example.txwl_first.Util.TXWLApplication;
import com.example.txwl_first.Util.Url;
import com.example.txwl_first.bean.GetLaolaiListBean;
import com.example.txwl_first.bean.GetLaolaiListItemBean;
import com.example.txwl_first.bean.QueryResultBean;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/30 0030.
 */
public class GetLaolaiListActivity extends Activity {
    private String TAG="GetLaolaiListActivity";
    private ListView lv_detail;
    private TextView tv_title;
    private ImageButton ibtn_title_back;

    private String name,cardnum;

    private GetLaolaiListBean bean;
    private ArrayList<GetLaolaiListItemBean> list;
    private LaolaiListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.querylist_detail_laolai_layout);
        name=getIntent().getStringExtra("name");
        cardnum=getIntent().getStringExtra("cardnum");
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

        lv_detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent=new Intent(GetLaolaiListActivity.this,GetLaolaiDetailActivity.class);
                intent.putExtra("laolaiid", bean.getList()[position].getLaolaiid());
                startActivity(intent);
            }
        });
    }

    private void getHttp() {
        String url = Url.GetLaoLaiSecondList_URL;
        Log.d("GetUserDetail_URL ------>", url);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("name", name);
        params.put("cardnum", cardnum);
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d(TAG+"onSuccess ------>", new String(bytes));
                try {
                    bean= new GsonBuilder().create().fromJson(new String(bytes), GetLaolaiListBean.class);
                    int size=bean.getList().length;
                    if(size==0){
                        TXWLApplication.getInstance().showTextToast("没有数据");
                    }else {
                        for (int j = 0; j < size; j++) {
                            list.add(bean.getList()[j]);
                        }
                        adapter.notifyDataSetChanged();
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

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        ibtn_title_back = (ImageButton) findViewById(R.id.ibtn_title_back);
        lv_detail = (ListView) findViewById(R.id.lv_detail);
        ibtn_title_back.setVisibility(View.VISIBLE);
        tv_title.setText("查询结果");

        list=new ArrayList<GetLaolaiListItemBean>();
        adapter=new LaolaiListAdapter(GetLaolaiListActivity.this,list);
        lv_detail.setAdapter(adapter);
    }
}
