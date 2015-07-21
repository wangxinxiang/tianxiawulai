package com.example.txwl_first;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import java.util.PriorityQueue;

/**
 * Created by licheng on 18/7/15.
 */
public class PersonalInfoActivity extends Activity implements AddItem {
    private TextView tv_title,tv_right;
    private ImageButton ibtn_title_back,ibtn_sub;
    private LinearLayout ll_my_loan_detail,ll_person_detail;

    private final static String[] person = new String[]{"头像","姓名","手机号"};
    private final static String[] company = new String[]{"公司名称","电话","所在地区","详细地址"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);
        initView();
        setOnClickListener();
    }

    private void setOnClickListener() {
        ibtn_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * paras data1 : 静态字段
     * paras data2 : 从服务端获取的字段，需要处理成数组
     */
    @Override
    public void AddItems(LinearLayout linearLayout, String data1, String data2) {
        RelativeLayout ll_detail = (RelativeLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_one, null);
        TextView left = (TextView) ll_detail.findViewById(R.id.left);
        TextView right = (TextView) ll_detail.findViewById(R.id.right);
        ImageButton btn_head_image = (ImageButton) ll_detail.findViewById(R.id.btn_entry);

        right.setVisibility(View.VISIBLE);

        left.setText(data1);
        right.setText(data2);

        if(data1.equals("头像")){
            right.setVisibility(View.GONE);
            btn_head_image.setVisibility(View.VISIBLE);
            //TODO；头像的地址需要单独设置
            btn_head_image.setImageResource(R.drawable.defaul_head_image);
            ll_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(),"点击事件",Toast.LENGTH_SHORT).show();
                }
            });
        }

        linearLayout.addView(ll_detail);


    }


    @Override
    public void fill_LinearLayout(String title_name, String[] owner, Integer[] selectors) {

    }

    @Override
    public void fill_LinearLayout(String title_name, String[] owner, String[] selectors) {

    }

    private void initView() {
        ll_person_detail = (LinearLayout) findViewById(R.id.ll_person_detail);
        ll_my_loan_detail = (LinearLayout) findViewById(R.id.ll_my_loan_detail);
        tv_title = (TextView) findViewById(R.id.tv_title);
        ibtn_title_back = (ImageButton) findViewById(R.id.ibtn_title_back);
        ibtn_sub = (ImageButton) findViewById(R.id.ibtn_sub);
        ibtn_title_back.setVisibility(View.VISIBLE);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_right.setVisibility(View.GONE);
        tv_title.setText("个人信息");

        //清空布局
        ll_person_detail.removeAllViews();
        ll_my_loan_detail.removeAllViews();

        for(int i = 0;i<person.length;i++){
            AddItems(ll_my_loan_detail, person[i], "test");
        }

        for(int i = 0;i<company.length;i++){
            AddItems(ll_person_detail,company[i],"test");
        }
    }
}
