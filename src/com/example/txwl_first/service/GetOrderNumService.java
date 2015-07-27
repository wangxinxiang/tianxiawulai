package com.example.txwl_first.service;

import android.app.ProgressDialog;
import android.content.Context;
import com.example.txwl_first.Util.TXWLApplication;
import com.google.gson.GsonBuilder;

/**
 * Created by Administrator on 2015/5/30.
 */
public class GetOrderNumService {
    TXWLApplication application;
    private static GetOrderNumService  getOrderNumService;
    private String order;
    private ProgressDialog pd;

    public GetOrderNumService() {
        this.application = TXWLApplication.getInstance();
    }

    public static GetOrderNumService getInstance() {
        if(getOrderNumService==null){
            getOrderNumService = new GetOrderNumService();
        }
        return getOrderNumService;
    }

    public void getOrderNum(String money, Context context) {
        pd = ProgressDialog.show(context, "正在加载数据,请稍后...", "请等待", true);
//
    }

    public String getOrder() {
        return order;
    }
}
