package com.example.txwl_first;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.txwl_first.Util.PreferenceUtils;
import com.example.txwl_first.Util.TXWLApplication;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2015/7/8 0008.
 */
public class EditFragment extends Fragment implements View.OnClickListener{
    private static String TAG="EditFragment";
    private View view;
    private TextView tv_title;
    private Button btn_house_loan,btn_car_loan,btn_credit_loan,btn_other_loan;
    private static final String HOUSE_LOAN_BUTTON = "house_loan_button";
    private static final String CAR_LOAN_BUTTON = "car_loan_button";
    private static final String CREDIT_LOAN_BUTTON = "credit_loan_button";
    private static final String OTHER_LOAN_BUTTON = "other_loan_button";
    private Intent intent;
    public static final int REQUSET = 1; //请求码


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.tab_edit_fragment,null);
        Log.d(TAG, "onCreateView");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
        initView();//初始化视图 并绑定点击事件

    }

    @Override
    public void onClick(View view) {
        intent=new Intent();
        switch (view.getId()){
        //如果已经登录 直接进入登记界面 否则跳转到登录界面
            case R.id.btn_house_loan:
                if (isLogin()){
                    intent.setClass(getActivity(), HouseLoanActivity.class);
                }else {
                    TXWLApplication.getInstance().showTextToast("登录用户才能登记信息");
                    intent.setClass(getActivity(), LoginActivity.class);

                }
                startActivityForResult(intent,REQUSET);
                break;

            case R.id.btn_car_loan:
                if (isLogin()){
                    intent.setClass(getActivity(), CarLoanActivity.class);
                }else {
                    TXWLApplication.getInstance().showTextToast("登录用户才能登记信息");
                    intent.setClass(getActivity(), LoginActivity.class);
                }
                startActivityForResult(intent, REQUSET);
                break;

            case R.id.btn_credit_loan:
                if (isLogin()){
                    intent.setClass(getActivity(), CreditLoanActivity.class);
                }else {
                    TXWLApplication.getInstance().showTextToast("登录用户才能登记信息");
                    intent.setClass(getActivity(), LoginActivity.class);
                }
                startActivityForResult(intent, REQUSET);
                break;

            case R.id.btn_other_loan:
                if (isLogin()){
                    intent.setClass(getActivity(),OtherLoanActivity.class);
                }else {
                    TXWLApplication.getInstance().showTextToast("登录用户才能登记信息");
                    intent.setClass(getActivity(), LoginActivity.class);

                }
                startActivityForResult(intent, REQUSET);
                break;
        }

    }

    private boolean isLogin() {
        return PreferenceUtils.getIsLogin();
    }


    private void initView() {
        tv_title= (TextView) view.findViewById(R.id.tv_title);
        btn_car_loan = (Button) view.findViewById(R.id.btn_car_loan);
        btn_credit_loan = (Button) view.findViewById(R.id.btn_credit_loan);
        btn_house_loan = (Button) view.findViewById(R.id.btn_house_loan);
        btn_other_loan = (Button) view.findViewById(R.id.btn_other_loan);

        btn_car_loan.setOnClickListener(this);
        btn_credit_loan.setOnClickListener(this);
        btn_house_loan.setOnClickListener(this);
        btn_other_loan.setOnClickListener(this);

        tv_title.setText("登记");

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        MobclickAgent.onPageStart(TAG); //统计页面
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "oPause");
        MobclickAgent.onPageEnd(TAG);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

}
