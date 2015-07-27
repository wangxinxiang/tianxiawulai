package com.example.txwl_first;

import android.util.Log;
import android.widget.Toast;
import com.example.txwl_first.Util.DataVeri;
import com.example.txwl_first.Util.TXWLApplication;
import com.loopj.android.http.RequestParams;

/**
 * Created by Administrator on 2015/7/20.
 */
public class CreditLoanActivity extends LoanActivity {

    @Override
    protected void setLoanTime() {
        LocateDatePikerItem(8,loan_datetime);

    }

    @Override
    protected void setPaymentTime() {
        LocateDatePikerItem(9,paymenttime);
    }

    

    @Override
    protected void initView() {
        super.initView();
        fill_LinearLayout("信用贷",creditowner,credit_image_selectors);
    }

    @Override
    protected void checkData() {
        super.checkData();
        for (int i = 0; i < 3; i++) {
            if (image_url[i] == null || "".equals(image_url[i])) {
                isSubmit = false;
                TXWLApplication.getInstance().showTextToast("图片不能为空");
            }
        }

        if (DataVeri.compare_date(check_data_str[8], check_data_str[9])) {
            isSubmit = false;
        }


    }

    @Override
    protected int putParams(RequestParams params) {
        params.put("province", check_data_str[5]);
        params.put("address", check_data_str[6]);
        params.put("owneridimg", image_url[0]);
        params.put("ownerheadimg", image_url[1]);
        params.put("contractimg", image_url[2]);

        params.put("owneriddesc", image_remark[0]);
        params.put("ownerheaddesc", image_remark[1]);
        params.put("contractdesc", image_remark[2]);
        params.put("registtype", 3 );

        return 7;
    }
}
