package com.example.txwl_first;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.txwl_first.bean.BankListBean;
import com.google.gson.GsonBuilder;

/**
 * Created by Administrator on 2015/3/28.
 */
//选择银行卡界面
public class BankSelectionActivity extends Activity {

    private LinearLayout ll_card;
    private Context mContext;
    private BankListBean[] bankListBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_selection);
        initView();
        initListener();
        getAllCards();
    }

    private void initListener()
    {

    }

    private void initView()
    {
        mContext = this;
        ll_card = (LinearLayout)findViewById(R.id.ll_card_list);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void getAllCards() {
//        String url = URL.getBankListURL();
//        DataWebService dataWebService = DataWebService.getInstance();
//        dataWebService.getData(url, new DataWebCallBack() {
//            @Override
//            public void onSuccess(String responseBody, String successMsg) {
//                Log.d("CardSelectionActivity", responseBody);
//                String strBody = "{\"GetBankList\":" + responseBody + "}";
//                try {
//                    GetBankList getBankList = new GsonBuilder().create().fromJson(strBody, GetBankList.class);
//                    bankListBeans = getBankList.getBankListBeans();
//                    addCardList();
//                }catch (Exception ex) {
//                    SLHApplication.getInstance().showErrorConnected(ex);
//                }
//            }
//
//            @Override
//            public void onFailure(String responseBody, String failureMsg) {
//
//            }
//        });
    }

    private void addCardList() {
//        for (int j = 0; j < bankListBeans.length; j++) {
//            final BankListBean bankListBean = bankListBeans[j];
//            LinearLayout ll_card_item = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.bank_item,null);
//            TextView tv_bankname = (TextView)ll_card_item.findViewById(R.id.tv_bankname);
//            tv_bankname.setText(bankListBean.getBankName());
//            ll_card_item.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent();
//                    intent.putExtra("id", String.valueOf(bankListBean.getId()));
//                    intent.putExtra("BankName", bankListBean.getBankName());
//                    setResult(Strings.ResultCode, intent);
//                    finish();
//                }
//            });
//            ll_card.addView(ll_card_item);
//        }
    }
}
