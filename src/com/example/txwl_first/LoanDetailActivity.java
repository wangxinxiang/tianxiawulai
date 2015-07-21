package com.example.txwl_first;

import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.example.txwl_first.Util.DataVeri;
import com.example.txwl_first.Util.TXWLApplication;
import com.example.txwl_first.Util.Url;
import com.example.txwl_first.bean.GetQueryDetailResultBean;
import com.example.txwl_first.bean.QueryDetailResultBean;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

/**
 * Created by Administrator on 2015/7/20.
 */
public class LoanDetailActivity extends LoanActivity{



    private void getDataFromInternet() {
        String registid = getIntent().getStringExtra("registid");
        String url = Url.QUERY_DETAIL_URL;

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("registid", registid);
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("QueryDetailActivity -->" , new String(bytes));
                try {
                    GetQueryDetailResultBean getQueryDetailResultBean = new GsonBuilder().create().fromJson(new String(bytes), GetQueryDetailResultBean.class);
                    QueryDetailResultBean queryDetailResultBean = getQueryDetailResultBean.getQueryDetailResultBean();
                    getInfo(queryDetailResultBean);
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

    @Override
    protected void initView() {
        super.initView();
        getDataFromInternet();
        //动态填充两个LinearLayout布局
        switch (from_button){
            //从接口获取网络数据填充
            case "query_listview_car_item":
                layout_item_three.setVisibility(View.VISIBLE);
                image_show = car_owner_show;
                fill_LinearLayout("详情",carowner,car_own_images_show);
                break;
            case "query_listview_house_item":
                layout_item_three.setVisibility(View.VISIBLE);
                image_show = house_owner_show;
                fill_LinearLayout("详情",houseowner,car_own_images_show);
                break;
            case "query_listview_credit_item":
                layout_item_three.setVisibility(View.VISIBLE);
                image_show = credit_owner_show;
                fill_LinearLayout("详情",creditowner,car_own_images_show);
                break;
            case "query_listview_other_item":
                layout_item_three.setVisibility(View.VISIBLE);
                image_show = credit_owner_show;
                fill_LinearLayout("详情",creditowner,car_own_images_show);
                break;
        }
    }

    private void getInfo(QueryDetailResultBean queryDetailResultBean) {
        switch (queryDetailResultBean.getRegisttype()) {
            case "1":
            case "2":
                car_own_images_show[0] = queryDetailResultBean.getAppearanceimg();
                car_owner_des_show[0] = queryDetailResultBean.getAppearancedesc();
                car_own_images_show[1] = queryDetailResultBean.getGoodsidimg();
                car_owner_des_show[1] = queryDetailResultBean.getGoodsiddesc();
                car_own_images_show[2] = queryDetailResultBean.getOwneridimg();
                car_owner_des_show[2] = queryDetailResultBean.getOwneriddesc();
                car_own_images_show[3] = queryDetailResultBean.getOwnerheadimg();
                car_owner_des_show[3] = queryDetailResultBean.getOwnerheaddesc();
                car_own_images_show[4] = queryDetailResultBean.getContractimg();
                car_owner_des_show[4] = queryDetailResultBean.getContractdesc();
                break;
            case "3":
            case "4":
                car_own_images_show = new String[3];
                car_own_images_show[0] = queryDetailResultBean.getOwneridimg();
                car_owner_des_show[0] = queryDetailResultBean.getOwneriddesc();
                car_own_images_show[1] = queryDetailResultBean.getOwnerheadimg();
                car_owner_des_show[1] = queryDetailResultBean.getOwnerheaddesc();
                car_own_images_show[2] = queryDetailResultBean.getContractimg();
                car_owner_des_show[2] = queryDetailResultBean.getContractdesc();
        }
    }
}
