package com.example.txwl_first;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DetailActivity extends Activity {
    LinearLayout rl_my_loan_detail,ll_person_detail,ll_loan_company_detail;
    private String[] des = new String[]{"年龄","性别","手机号","工作单位","所有地区","详情地址","借款日","还款日"};
    private String[] loan = new String[]{"借贷公司","电话","所在地区","详细地址","联系人","联系人电话"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);

        initView();

        for(int i = 0;i<des.length;i++){
            AddItems(ll_person_detail,des[i], "test");
        }

        for(int j = 0;j<loan.length;j++){
            AddItems(ll_loan_company_detail,loan[j],"test");
        }

    }

    private void initView() {
        ll_loan_company_detail = (LinearLayout) findViewById(R.id.ll_loan_company_detail);
        ll_person_detail = (LinearLayout) findViewById(R.id.ll_person_detail);
        rl_my_loan_detail = (LinearLayout) findViewById(R.id.ll_my_loan_detail);
    }

    //动态添加Item填充数据
    public void AddItems(LinearLayout layout,String describ,String datas){
        RelativeLayout ll_detail = (RelativeLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_one,null);
        TextView left = (TextView) ll_detail.findViewById(R.id.left);
        TextView right = (TextView) ll_detail.findViewById(R.id.right);
        if(describ.equals("手机号")||describ.equals("联系人电话")){
            right.setTextColor(getResources().getColor(R.color.orange_main));
        }
        left.setText(describ);
        right.setText(datas);
        layout.addView(ll_detail);
    }
}
