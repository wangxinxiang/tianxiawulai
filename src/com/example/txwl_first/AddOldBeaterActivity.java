package com.example.txwl_first;

import android.util.Log;
import android.widget.Toast;
import com.example.txwl_first.Util.DataVeri;
import com.example.txwl_first.Util.TXWLApplication;
import com.loopj.android.http.RequestParams;

/**
 * Created by Administrator on 2015/7/20.
 */
public class AddOldBeaterActivity extends LoanActivity{
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
        fill_LinearLayout("添加老赖",creditowner,credit_image_selectors);
    }

    @Override
    protected void checkData() {
        super.checkData();
        for (int i = 0; i < 3; i++) {
            if ("".equals(image_url[i])) {
                isSubmit = false;
                TXWLApplication.getInstance().showTextToast("图片不能为空");
            }
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
        params.put("registtype", 5);

        return 7;
    }
}
