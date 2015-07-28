package com.example.txwl_first;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.txwl_first.Util.Constant;
import com.example.txwl_first.bean.BankListBean;
import com.google.gson.GsonBuilder;

/**
 * Created by Administrator on 2015/3/28.
 */
//选择银行卡界面
public class BankSelectionActivity extends Activity {

    private LinearLayout ll_card;
    private Context mContext;
    private TextView tv_title;
    private ImageButton ibtn_title_back;
    private String[] bankName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_selection);
        initBankName();
        initView();
        initListener();
        addCardList();
    }

    private void initBankName() {
        bankName= new String[]{"中国工商银行",
                "中国农业银行",
                "中国建设银行",
                "中国交通银行",
                "中国银行",
                "招商银行",
                "浦发银行"
                , "中国光大银行"
                , "中国民生银行"
                , "中信银行"
                , "华夏银行"
                , "兴业银行"
                , "深圳发展银行"
                , "广东发展银行"
                , "广州银行"
                , "广州农村商业银行"
                , "北京银行"
                , "北京农商银行"
                , "上海银行"
                , "上海农村商业银行"
                , "渤海银行"
                , "南京银行"
                , "东亚银行"
                , "宁波银行"
                , "杭州银行"
                , "平安银行"
                , "徽商银行"
                , "浙商银行"
                , "中国邮政储蓄银行"
                , "江苏银行"
                , "大连银行"
                , "中国银联"};
    }

    private void initListener()
    {

    }

    private void initView()
    {
        mContext = this;
        tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText("选择银行卡");
        ibtn_title_back= (ImageButton) findViewById(R.id.ibtn_title_back);
        ll_card = (LinearLayout)findViewById(R.id.ll_card_list);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    private void addCardList() {
        int size=bankName.length;
        for (int i = 0; i < size; i++) {
            String name=bankName[i];
            LinearLayout ll_card_item = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.bank_item,null);
            TextView tv_bankname = (TextView)ll_card_item.findViewById(R.id.tv_bankname);
            tv_bankname.setText(name);
            ll_card_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("BankName", name);
                    setResult(Constant.ResultCode, intent);
                    finish();
                }
            });
            ll_card.addView(ll_card_item);
        }
    }
}
