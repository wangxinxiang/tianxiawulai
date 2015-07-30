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

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Administrator on 2015/3/28.
 */
//选择银行卡界面
public class BankSelectionActivity extends Activity {

    private LinearLayout ll_card;
    private Context mContext;
    private TextView tv_title;
    private ImageButton ibtn_title_back;
    private HashMap<String, String> bankName;

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
        bankName= new HashMap<>();
        bankName.put("中国工商银行", "ICBC_D_B2C");
        bankName.put("中国农业银行", "ABC_D_B2C");
        bankName.put("中国建设银行", "CCB_D_B2C");
        bankName.put("民生银行", "CMBCD_D_B2C");
        bankName.put("中国银行", "BOCSH_D_B2C");
        bankName.put("兴业银行", "CIB_D_B2C");
        bankName.put("光大银行", "CEB_D_B2C");
        bankName.put("中信银行", "CNCB_D_B2C");
        bankName.put("平安银行", "PINGAN_D_B2C");
        bankName.put("中国邮政", "POSTGC_D_B2C");
        bankName.put("交通银行", "COMM_D_B2C");
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
        Set<String> set = bankName.keySet();
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            String name= iterator.next();
            LinearLayout ll_card_item = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.bank_item,null);
            TextView tv_bankname = (TextView)ll_card_item.findViewById(R.id.tv_bankname);
            tv_bankname.setText(name);
            ll_card_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("BankName", name);
                    intent.putExtra("bank_code", bankName.get(name));
                    setResult(Constant.ResultCode, intent);
                    finish();
                }
            });
            ll_card.addView(ll_card_item);
        }

    }
}
