package com.example.txwl_first;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.txwl_first.Util.TXWLApplication;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2015/5/31.
 */
public class RechargeSuccessActivity extends Activity{

    private Button recharge_success_commit;
    private String recipientsdisease,recipientsname,confirm_recharge_money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recharge_success);
        TXWLApplication.getInstance().pushStack(this);
        initView();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        recharge_success_commit = (Button) findViewById(R.id.recharge_success_commit);
        recipientsdisease = getIntent().getStringExtra("recipientsdisease");
        recipientsname = getIntent().getStringExtra("recipientsname");

        TextView  recharge_success_order = (TextView) findViewById(R.id.recharge_success_order);
        TextView  recharge_success_time = (TextView) findViewById(R.id.recharge_success_time);
        TextView recharge_success_method = (TextView) findViewById(R.id.recharge_success_method);
        TextView   recharge_success_status = (TextView) findViewById(R.id.recharge_success_status);
        TextView   tv_recipientsdisease = (TextView) findViewById(R.id.tv_recipientsdisease);
        TextView   tv_recipientsname = (TextView) findViewById(R.id.tv_recipientsname);
        TextView confirm_recharge_money = (TextView) findViewById(R.id.confirm_recharge_money);

        if("donation".equals(getIntent().getStringExtra("aim"))){
            //设置带有finish栈功能的setTitle
//            setTitleAndFinishStack("捐款成功");
            tv_recipientsdisease.setText(recipientsdisease);
            tv_recipientsname.setText("受助人："+recipientsname);
            recharge_success_order.setText(getIntent().getStringExtra("billno"));
            confirm_recharge_money.setText(getIntent().getStringExtra("recharge_money"));
            recharge_success_method.setText("贝付");
            recharge_success_status.setText("捐款成功");
            recharge_success_time.setText(getCurrentTime());
        }else{
            //设置带有finish栈功能的setTitle
//            setTitleAndFinishStack("充值成功");
            tv_recipientsdisease.setVisibility(View.GONE);
            recharge_success_order.setText(getIntent().getStringExtra("billno"));
            recharge_success_method.setText("贝付");
            recharge_success_status.setText("充值成功");
            confirm_recharge_money.setText(getIntent().getStringExtra("cz_money"));
            recharge_success_time.setText(getCurrentTime());
        }

    }

    private void initListener() {
        recharge_success_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TXWLApplication.getInstance().finishStack();
            }
        });
    }

    //获取当前时间
    private String getCurrentTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());
    }

    @Override
    public void onBackPressed() {
        //返回键按下
        super.onBackPressed();
        TXWLApplication.getInstance().finishStack();
    }
}
