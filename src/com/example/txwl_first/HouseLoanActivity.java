package com.example.txwl_first;

import android.util.Log;
import android.widget.Toast;
import com.example.txwl_first.Util.DataVeri;
import com.example.txwl_first.Util.TXWLApplication;
import com.loopj.android.http.RequestParams;


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
    protected void initView() {
        super.initView();
        fill_LinearLayout("房贷", houseowner, house_image_selectors);
    }

    @Override
    protected void checkData() {
        super.checkData();
        for (int i = 0; i < 5; i++) {
            if (image_url[i] == null  || "".equals(image_url[i])) {
                isSubmit = false;
                TXWLApplication.getInstance().showTextToast("图片不能为空");
            }
        }

        if (DataVeri.compare_date(check_data_str[9], check_data_str[10])) {
            isSubmit = false;
        }

        if (!DataVeri.isIDNum(check_data_str[5])) {
            isSubmit = false;
        }
    }

    @Override
    protected int putParams(RequestParams params) {
        params.put("personid", check_data_str[5]);
        params.put("province", check_data_str[6]);
        params.put("address", check_data_str[7]);
        params.put("appearanceimg", image_url[0]);
        params.put("goodsidimg", image_url[1]);
        params.put("owneridimg", image_url[2]);
        params.put("ownerheadimg", image_url[3]);
        params.put("contractimg", image_url[4]);

        params.put("appearancedesc", image_remark[0]);
        params.put("goodsiddesc", image_remark[1]);
        params.put("owneriddesc", image_remark[2]);
        params.put("ownerheaddesc", image_remark[3]);
        params.put("contractdesc", image_remark[4]);
        params.put("registtype", 2 );

        return 8;
    }
}
