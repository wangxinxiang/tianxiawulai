package com.example.txwl_first;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.txwl_first.Util.DataVeri;
import com.example.txwl_first.Util.TXWLApplication;
import com.example.txwl_first.Util.Url;
import com.example.txwl_first.bean.GetQueryDetailResultBean;
import com.example.txwl_first.bean.QueryDetailResultBean;
import com.example.txwl_first.business.LoaderBusiness;
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
                    addView(queryDetailResultBean);
                    setTopItem(queryDetailResultBean);
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
    }

    private void addView(QueryDetailResultBean queryDetailResultBean) {
        //动态填充两个LinearLayout布局
        switch (from_button){
            //从接口获取网络数据填充
            case "query_listview_car_item":
                layout_item_three.setVisibility(View.VISIBLE);
                image_show = car_owner_show;
                fill_LinearLayout("详情",carowner,car_own_images_show, queryDetailResultBean);
                break;
            case "query_listview_house_item":
                layout_item_three.setVisibility(View.VISIBLE);
                image_show = house_owner_show;
                fill_LinearLayout("详情",houseowner,car_own_images_show, queryDetailResultBean);
                break;
            case "query_listview_credit_item":
                layout_item_three.setVisibility(View.VISIBLE);
                image_show = credit_owner_show;
                fill_LinearLayout("详情",creditowner,car_own_images_show, queryDetailResultBean);
                break;
            case "query_listview_other_item":
                layout_item_three.setVisibility(View.VISIBLE);
                image_show = credit_owner_show;
                fill_LinearLayout("详情",creditowner,car_own_images_show, queryDetailResultBean);
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

    private void setTopItem(QueryDetailResultBean queryDetailResultBean) {
        ImageView headImg = (ImageView) findViewById(R.id.img_user_head);
        TextView name = (TextView) findViewById(R.id.tv_username);
        TextView tv_user_phone_num = (TextView) findViewById(R.id.tv_user_phone_num);
        TextView loan_type = (TextView) findViewById(R.id.loan_type);
        TextView tv_money_count = (TextView) findViewById(R.id.tv_money_count);
        TextView tv_manager = (TextView) findViewById(R.id.tv_manager);
        TextView tv_year = (TextView) findViewById(R.id.tv_year);
        TextView over_day = (TextView) findViewById(R.id.over_day);

        LoaderBusiness.loadImage(getIntent().getStringExtra("headImage"), headImg);
        name.setText(queryDetailResultBean.getName());
        tv_user_phone_num.setText(queryDetailResultBean.getMobile());
        loan_type.setText(getLoan_type(queryDetailResultBean.getRegisttype()));
        tv_money_count.setText(queryDetailResultBean.getAccount());
        tv_manager.setText("经办人" + getIntent().getStringExtra("realname"));
        tv_year.setText("利率:" +queryDetailResultBean.getAnnualrate() + "%");
        over_day.setText(getIntent().getStringExtra("data"));
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
}
