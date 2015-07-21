package com.example.txwl_first;

import android.util.Log;
import android.widget.Toast;
import com.example.txwl_first.Util.DataVeri;

/**
 * Created by Administrator on 2015/7/20.
 */
public class OtherLoanActivity extends LoanActivity {

    @Override
    protected void setLoanTime() {
        LocateDatePikerItem(8,loan_datetime);

    }

    @Override
    protected void setPaymentTime() {
        LocateDatePikerItem(9,paymenttime);
    }

    @Override
    protected void registToInternet() {
        super.registToInternet();
        LocateDatePikerItem(8,loan_datetime);
    }


    @Override
    protected void initView() {
        super.initView();
        fill_LinearLayout("其它",creditowner,credit_image_selectors);
    }

    @Override
    protected void checkData() {
        super.checkData();
    }
}
