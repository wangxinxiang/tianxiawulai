package com.example.txwl_first;

import com.example.txwl_first.Util.TXWLApplication;
import com.loopj.android.http.RequestParams;

/**
 * Created by Administrator on 2015/7/20.
 */
public class CarLoanActivity extends LoanActivity{

    private static int registtype = 1;

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
        fill_LinearLayout("车贷",carowner,car_image_selectors);
    }

    @Override
    protected void checkData() {
        super.checkData();

        for (String url : image_url) {
            if ("".equals(url)) {
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
        params.put("registtype", registtype );

        return 6;
    }
}
