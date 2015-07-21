package com.example.txwl_first;

import android.util.Log;
import android.widget.Toast;
import com.example.txwl_first.Util.DataVeri;

/**
 * Created by Administrator on 2015/7/20.
 */
public class CarLoanActivity extends LoanActivity{


    @Override
    protected void setLoanTime() {
        LocateDatePikerItem(7,loan_datetime);

    }

    @Override
    protected void setPaymentTime() {
        LocateDatePikerItem(8,paymenttime);
    }

    @Override
    protected void initView() {
        super.initView();
        fill_LinearLayout("车贷",carowner,image_show);
    }

    @Override
    protected void checkData() {
        super.checkData();
    }
}
