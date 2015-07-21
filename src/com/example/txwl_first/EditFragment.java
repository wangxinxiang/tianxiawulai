package com.example.txwl_first;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

/**
 * Created by Administrator on 2015/7/8 0008.
 */
public class EditFragment extends Fragment {
    private static String TAG="EditFragment";
    private View view;
    private TextView tv_title;
    private Button btn_house_loan,btn_car_loan,btn_cridet_loan,btn_other_loan;
    private static final String HOUSE_LOAN_BUTTON = "house_loan_button";
    private static final String CAR_LOAN_BUTTON = "car_loan_button";
    private static final String CREDIT_LOAN_BUTTON = "credit_loan_button";
    private static final String OTHER_LOAN_BUTTON = "other_loan_button";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.registration_layout,null);
        Log.d(TAG, "onCreateView");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
        initView();
        setClickListener();
    }

    private void setClickListener() {
        btn_house_loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),HouseLoanActivity.class);
                startActivity(intent);
            }
        });
        btn_car_loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent car_loan_intent = new Intent(getActivity(),CarLoanActivity.class);
                startActivity(car_loan_intent);
            }
        });
        btn_cridet_loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent credit_loan_intent = new Intent(getActivity(),CreditLoanActivity.class);
                startActivity(credit_loan_intent);
            }
        });
        btn_other_loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent other_loan_intent = new Intent(getActivity(),OtherLoanActivity.class);
                startActivity(other_loan_intent);
            }
        });

    }

    private void initView() {
        tv_title= (TextView) view.findViewById(R.id.tv_title);
        btn_car_loan = (Button) view.findViewById(R.id.btn_car_loan);
        btn_cridet_loan = (Button) view.findViewById(R.id.btn_credit_loan);
        btn_house_loan = (Button) view.findViewById(R.id.btn_house_loan);
        btn_other_loan = (Button) view.findViewById(R.id.btn_other_loan);
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
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "oPause");
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
