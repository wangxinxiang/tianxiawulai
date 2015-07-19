package com.example.txwl_first;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.example.txwl_first.Adapter.QueryResultAdapter;
import com.example.txwl_first.bean.LoanBean;

import java.util.ArrayList;

public class QueryResultActivity extends Activity {
    private TextView tv_title,tv_right;
    private ImageButton ibtn_title_back,ibtn_sub;
    private ListView lv_query;
    private QueryResultAdapter adapter;
    private ArrayList<LoanBean> loanBeans;
    private LoanBean loanBean;
    private final static  String QUERY_LISTVIEW_CAR_ITEM="query_listview_car_item";
    private final static  String QUERY_LISTVIEW_HOUSE_ITEM="query_listview_house_item";
    private final static  String QUERY_LISTVIEW_CREDIT_ITEM="query_listview_credit_item";
    private final static  String QUERY_LISTVIEW_OTHER_ITEM="query_listview_other_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.querylist_layout);
        loanBeans = new ArrayList<LoanBean>();
        //TODO:从网络接口得到数据
        loanBeans.add(new LoanBean("ss","1212","eee","dd","dd","dd"));
        loanBeans.add(new LoanBean("ss", "1212", "eee", "dd", "dd", "dd"));
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
                Intent intent = new Intent(getApplicationContext(),HouseLoanActivity.class);
                if("车贷".equals(loanBeans.get(position).getDebt_type())){
                    intent.putExtra("fromButton", QUERY_LISTVIEW_CAR_ITEM);
                }
                if ("房贷".equals(loanBeans.get(position).getDebt_type())){
                    intent.putExtra("fromButton",QUERY_LISTVIEW_HOUSE_ITEM);
                }
                if ("信用贷".equals(loanBeans.get(position).getDebt_type())){
                    intent.putExtra("fromButton",QUERY_LISTVIEW_CREDIT_ITEM);
                }
                if ("其它".equals(loanBeans.get(position).getDebt_type())){
                    intent.putExtra("fromButton",QUERY_LISTVIEW_OTHER_ITEM);
                }
                intent.putExtra("fromButton", QUERY_LISTVIEW_HOUSE_ITEM);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        ibtn_title_back = (ImageButton) findViewById(R.id.ibtn_title_back);
        lv_query = (ListView) findViewById(R.id.lv_query);
        ibtn_sub = (ImageButton) findViewById(R.id.ibtn_sub);
        ibtn_title_back.setVisibility(View.VISIBLE);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_right.setVisibility(View.GONE);
        tv_title.setText("查询结果");

        adapter = new QueryResultAdapter(getApplicationContext(),loanBeans);
        lv_query.setAdapter(adapter);
    }
}
