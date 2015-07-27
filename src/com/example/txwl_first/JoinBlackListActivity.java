package com.example.txwl_first;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.txwl_first.Util.DataVeri;
import com.example.txwl_first.Util.PreferenceUtils;
import com.example.txwl_first.Util.TXWLApplication;
import com.example.txwl_first.Util.Url;
import com.example.txwl_first.bean.GetQueryDetailResultBean;
import com.example.txwl_first.bean.QueryDetailResultBean;
import com.example.txwl_first.business.LoaderBusiness;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;
import org.apache.http.Header;

public class JoinBlackListActivity extends Activity{
    private static final String TAG = "JoinBlackListActivity";
    private TextView tv_title,tv_right;
    private ImageButton ibtn_title_back,ibtn_sub;
    private QueryDetailResultBean queryDetailResultBean;
    private GetQueryDetailResultBean getQueryDetailResultBean;
    private EditText et_contact, et_mobilephone, et_reward_money;
    private Button btn_pay_money;

    ImageView headImg ;
    TextView name ;
    TextView tv_user_phone_num ;
    TextView loan_type ;
    TextView tv_money_count ;
    TextView tv_manager;
    TextView tv_year ;
    TextView over_day ;
    TextView tv_black_reward ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_blacklist_layout);
        initView();
        setOnClickListener();
        getDataFromInternet();
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
            public void onClick(View view) {
                addBlackPerson();
            }
        });
    }

    private void initView() {
        Intent intent = getIntent();

        tv_title = (TextView) findViewById(R.id.tv_title);
        ibtn_title_back = (ImageButton) findViewById(R.id.ibtn_title_back);
        ibtn_sub = (ImageButton) findViewById(R.id.ibtn_sub);
        ibtn_title_back.setVisibility(View.VISIBLE);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_right.setVisibility(View.GONE);
        tv_title.setText("加入黑名单");

        headImg = (ImageView) findViewById(R.id.img_user_head);
        name = (TextView) findViewById(R.id.tv_username);
        tv_user_phone_num = (TextView) findViewById(R.id.tv_user_phone_num);
        loan_type = (TextView) findViewById(R.id.loan_type);
        tv_money_count = (TextView) findViewById(R.id.tv_money_count);
        tv_manager = (TextView) findViewById(R.id.tv_manager);
        tv_year = (TextView) findViewById(R.id.tv_year);
        over_day = (TextView) findViewById(R.id.over_day);
        tv_black_reward = (TextView) findViewById(R.id.tv_black_reward);

        et_contact = (EditText) findViewById(R.id.et_contact);
        et_mobilephone = (EditText) findViewById(R.id.et_mobilephone);
        et_reward_money = (EditText) findViewById(R.id.et_reward_money);
        btn_pay_money = (Button) findViewById(R.id.btn_pay_money);
    }

    private void setData() {
        LoaderBusiness.loadImage(getIntent().getStringExtra("headImage"),headImg);
        name.setText(queryDetailResultBean.getName());
        name.setText(queryDetailResultBean.getName());
        tv_user_phone_num.setText(queryDetailResultBean.getMobile());
        loan_type.setText(getLoan_type(queryDetailResultBean.getRegisttype()));
        tv_money_count.setText(connetText(1,queryDetailResultBean.getAccount(), "", ""));
        tv_manager.setText("经办人" + queryDetailResultBean.getRealname());
        tv_year.setText("利率:" + queryDetailResultBean.getAnnualrate() + "%");
        over_day.setText(getQueryDetailResultBean.getData());
        switch (queryDetailResultBean.getStatus()) {
            case "1":
                over_day.setText(connetText(0, getQueryDetailResultBean.getData(), "", ""));
                break;
            case "2":
                over_day.setTextColor(getResources().getColor(R.color.orange_text));
                break;
            case "3":
                //隐藏右边控件
                tv_manager.setVisibility(View.GONE);
                over_day.setVisibility(View.GONE);

                tv_black_reward.setVisibility(View.VISIBLE);

                tv_money_count.setTextColor(getResources().getColor(R.color.midblack));
                tv_black_reward.setTextColor(getResources().getColor(R.color.midblack));
                tv_year.setTextColor(getResources().getColor(R.color.midblack));

                if (!"5".equals(queryDetailResultBean.getRegisttype())) {
                    tv_black_reward.setText(connetText(2, queryDetailResultBean.getRegistcompany(), queryDetailResultBean.getContactname(), queryDetailResultBean.getRewardaccount() ));
                    tv_year.setText(queryDetailResultBean.getContactname() + "电话：" + queryDetailResultBean.getContactmobile());
                } else {
                    tv_black_reward.setVisibility(View.GONE);
                    tv_year.setVisibility(View.GONE);
                }
                tv_money_count.setText("恶意拖欠" + queryDetailResultBean.getRegistcompany() + DataVeri.getMoneyFromDouble(queryDetailResultBean.getAccount()) + "元");
                break;
        }
    }

    private String getLoan_type(String i) {
        switch (i) {
            case "1":
                return "车贷";
            case "2":
                return "房贷";
            case "3":
                return "信用贷";
            default:
                return "其他";
        }
    }

    private SpannableStringBuilder connetText(int mode,String one,String two,String three) {
        //mode 值为判断返回字段类型
        //0  剩余天数
        //1 为第2行 赏金字段

        SpannableStringBuilder builder=new SpannableStringBuilder();

        if (one==null ||"".equals(one)||one.length()==0){
            one=" ";
        }

        if (two==null || "".equals(two)||two.length()==0){
            two=" ";
        }

        if (three==null || "".equals(three)||three.length()==0){
            three=" ";
        }

        int start;
        int end;
        switch (mode){
            case 0:
                builder.append(one);
                start = one.indexOf(":") + 1;
                end = one.length() - 1;
                builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.orange_text)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case 1:
                start= 5;
                one = DataVeri.getMoneyFromDouble(one);
                end=start+one.length();
                builder.append("借款金额:");
                builder.append(one);
                builder.append("元");
                builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.orange_text)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case 2:
                start=one.length()+two.length()+2;
                three = DataVeri.getMoneyFromDouble(three);
                end=start+three.length();
                builder.append(one);
                builder.append(two);
                builder.append("悬赏");
                builder.append(three);
                builder.append("元催收");
                builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.orange_text)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
        }
        return builder;
    }



    private void getDataFromInternet() {
        String registid = getIntent().getStringExtra("registid");
        String url = Url.QUERY_DETAIL_URL;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("registid", registid);
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("QueryDetailActivity -->", new String(bytes));
                try {
                    getQueryDetailResultBean = new GsonBuilder().create().fromJson(new String(bytes), GetQueryDetailResultBean.class);
                    queryDetailResultBean = getQueryDetailResultBean.getQueryDetailResultBean();
                    setData();
                } catch (Exception e) {
                    TXWLApplication.getInstance().showErrorConnected(e);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                TXWLApplication.getInstance().showTextToast("请求失败");
            }
        });

    }

    private void addBlackPerson() {
        String url = Url.ADD_BLACK_URL;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("userid", PreferenceUtils.getUserId());
        params.put("registid", queryDetailResultBean.getRegistid());
        params.put("contactname", et_contact.getText());
        params.put("contactmobile", et_mobilephone.getText());
        params.put("reward", et_reward_money.getText());

        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("addBlackPerson ----->", new String(bytes));

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                TXWLApplication.getInstance().showTextToast("提交失败");
            }
        });

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
}
