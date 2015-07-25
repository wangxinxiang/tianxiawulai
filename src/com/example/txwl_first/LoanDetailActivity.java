package com.example.txwl_first;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
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

private GetQueryDetailResultBean getQueryDetailResultBean;

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
                    getQueryDetailResultBean = new GsonBuilder().create().fromJson(new String(bytes), GetQueryDetailResultBean.class);
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
                TXWLApplication.getInstance().showTextToast("请求失败");
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
        TextView tv_black_reward = (TextView) findViewById(R.id.tv_black_reward);

        LoaderBusiness.loadImage(getIntent().getStringExtra("headImage"), headImg);
        name.setText(queryDetailResultBean.getName());
        tv_user_phone_num.setText(queryDetailResultBean.getMobile());
        loan_type.setText(getLoan_type(queryDetailResultBean.getRegisttype()));
        tv_money_count.setText(connetText(1,queryDetailResultBean.getAccount(), "", ""));
        tv_manager.setText("经办人" + queryDetailResultBean.getRealname());
        tv_year.setText("利率:" +queryDetailResultBean.getAnnualrate() + "%");
        over_day.setText(getQueryDetailResultBean.getData());
        switch (getIntent().getStringExtra("status2")) {
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

                tv_black_reward.setText(connetText(2, queryDetailResultBean.getRegistcompany(), queryDetailResultBean.getContactname(), queryDetailResultBean.getRewardaccount() ));
                tv_year.setText(queryDetailResultBean.getContactname() + "电话：" + queryDetailResultBean.getContactmobile());
                tv_money_count.setText("恶意拖欠" + queryDetailResultBean.getRegistcompany() + queryDetailResultBean.getAccount() + "元");
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

        if ("".equals(one)||one.length()==0||one==null){
            one=" ";
        }
        if ("".equals(two)||two.length()==0||two==null){
            two=" ";
        }

        if ("".equals(three)||three.length()==0||three==null){
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
                end=start+one.length() - 1;
                builder.append("借款金额:");
                builder.append(one);
                builder.append("元");
                builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.orange_text)),start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case 2:
                start=one.length()+two.length()+2;
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
}
