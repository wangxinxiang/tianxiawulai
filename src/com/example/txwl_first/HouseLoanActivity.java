package com.example.txwl_first;

import android.util.Log;
import android.widget.Toast;
import com.example.txwl_first.Util.DataVeri;


public class HouseLoanActivity extends LoanActivity {

    @Override
    protected void setLoanTime() {
        LocateDatePikerItem(9,loan_datetime);

    }

    @Override
    protected void setPaymentTime() {
        LocateDatePikerItem(10,paymenttime);
    }

    @Override
    protected void registToInternet() {
        super.registToInternet();
        LocateDatePikerItem(9,loan_datetime);
    }

    @Override
    protected void initView() {
        super.initView();
        image_show = house_image_selectors;
        fill_LinearLayout("房贷", houseowner, image_show);
    }

    @Override
    protected void checkData() {
        super.checkData();
        for (int i = 0; i < check_data_str.length; i++) {
            //检测房贷字段
            switch (i) {
                case 5:
                    if (!DataVeri.isIDNum(check_data_str[5])) {
                        Toast.makeText(getApplicationContext(), "身份号输入有误", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }
}
