package com.example.txwl_first;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.txwl_first.Util.PreferenceUtils;
import com.example.txwl_first.Util.TXWLApplication;
import com.example.txwl_first.Util.Url;
import com.example.txwl_first.bean.GetMyGroupBean;
import com.example.txwl_first.bean.MyGroupBean;
import com.example.txwl_first.business.LoaderBusiness;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;
import org.apache.http.Header;

import java.util.ArrayList;

public class MyGroupActivity extends Activity{
    private static final String TAG ="MyGroupActivity" ;
    private TextView tv_title,tv_right;
    private ImageButton ibtn_title_back,ibtn_sub,ibtn_add;
    private ArrayList<MyGroupBean> myGroupBeans = new ArrayList<>();
    private ListView lv_my_group;
    private HolderView holderView;
    private MyGroupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_group_layout);
        initView();
        setOnClickListener();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        MobclickAgent.onResume(this);
        getGroupData();
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

        ibtn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddActivity.class);
                startActivity(intent);
            }
        });

        ibtn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        ibtn_title_back = (ImageButton) findViewById(R.id.ibtn_title_back);
        ibtn_sub = (ImageButton) findViewById(R.id.ibtn_sub);
        ibtn_add = (ImageButton) findViewById(R.id.ibtn_add);
        ibtn_title_back.setVisibility(View.VISIBLE);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_right.setVisibility(View.GONE);
        ibtn_add.setVisibility(View.VISIBLE);
        ibtn_sub.setVisibility(View.VISIBLE);
        tv_title.setText("我的团队");

        lv_my_group = (ListView) findViewById(R.id.lv_my_group);
        adapter = new MyGroupAdapter();
        lv_my_group.setAdapter(adapter);
    }

    private void getGroupData() {
        String url = Url.GROUP_URL;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("userid", PreferenceUtils.getUserId());

        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("getGroupData---->", new String(bytes));
                try {
                    myGroupBeans.clear();
                    GetMyGroupBean getMyGroupBean = new GsonBuilder().create().fromJson(new String(bytes), GetMyGroupBean.class);
                    for (MyGroupBean myGroupBean: getMyGroupBean.getMyGroupBeans()) {
                        myGroupBeans.add(myGroupBean);
                    }
                    if (myGroupBeans.size() == 0) {
                        TXWLApplication.getInstance().showTextToast("没有团队成员信息");
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                }catch (Exception e) {
                    TXWLApplication.getInstance().showErrorConnected(e);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                TXWLApplication.getInstance().showTextToast("网络错误");

            }
        });
    }

    private class MyGroupAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return myGroupBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                holderView = new HolderView();
                convertView = LayoutInflater.from(MyGroupActivity.this).inflate(R.layout.group_item, null);
                holderView.group_item_headimage = (ImageView) convertView.findViewById(R.id.group_item_headimage);
                holderView.group_item_name = (TextView) convertView.findViewById(R.id.group_item_name);

                convertView.setTag(holderView);
            }else {
                holderView = (HolderView) convertView.getTag();
            }

            MyGroupBean myGroupBean = myGroupBeans.get(position);
            LoaderBusiness.loadImage(myGroupBean.getHeadimage(), holderView.group_item_headimage);
            holderView.group_item_name.setText(myGroupBean.getRealname());

            return convertView;
        }
    }

    class HolderView {
        private ImageView group_item_headimage;
        private TextView group_item_name;
    }
}
