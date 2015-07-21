package com.example.txwl_first;

import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.example.txwl_first.Util.DataVeri;

/**
 * Created by Administrator on 2015/7/20.
 */
public class LoanDetailActivity extends LoanActivity{

    @Override
    protected void registToInternet() {
        String registid = getIntent().getStringExtra("registid");


    }

    @Override
    protected void initView() {
        super.initView();
        //动态填充两个LinearLayout布局
        switch (from_button){
            //从接口获取网络数据填充
            case "query_listview_car_item":
                layout_item_three.setVisibility(View.VISIBLE);
                fill_LinearLayout("详情",carowner,car_own_images_show);
                break;
            case "query_listview_house_item":
                layout_item_three.setVisibility(View.VISIBLE);
                fill_LinearLayout("详情",houseowner,house_own_image_show);
                break;
            case "query_listview_credit_item":
                layout_item_three.setVisibility(View.VISIBLE);
                fill_LinearLayout("详情",creditowner,credit_own_image_show);
                break;
            case "query_listview_other_item":
                layout_item_three.setVisibility(View.VISIBLE);
                fill_LinearLayout("详情",creditowner,other_own_image_show);
                break;
        }
    }
}
