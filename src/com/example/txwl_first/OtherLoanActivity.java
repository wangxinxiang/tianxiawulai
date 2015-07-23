package com.example.txwl_first;

import android.util.Log;
import android.widget.Toast;
import com.example.txwl_first.Util.DataVeri;
import com.example.txwl_first.Util.TXWLApplication;
import com.loopj.android.http.RequestParams;

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
    protected void initView() {
        super.initView();
        fill_LinearLayout("其它",creditowner,credit_image_selectors);
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
        params.put("carid", check_data_str[5]);
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
        params.put("registtype", 3 );

        return 6;
    }
}
