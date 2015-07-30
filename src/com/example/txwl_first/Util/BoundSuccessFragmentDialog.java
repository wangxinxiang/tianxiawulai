package com.example.txwl_first.Util;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.txwl_first.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

/**
 * Created by licheng on 31/5/15.
 */
public class BoundSuccessFragmentDialog extends DialogFragment {
    Button btn_bound_success;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bound_success_dialog,null);
        btn_bound_success = (Button) view.findViewById(R.id.btn_bound_sucess);
        btn_bound_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SLHApplication.getInstance().backTop();
                getPaymentOrderOk();

            }
        });

        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if(keyCode==KeyEvent.KEYCODE_BACK){
                    getPaymentOrderOk();
                }
                return false;
            }
        });

        return view;
    }

    //将结果交给服务器
    public void getPaymentOrderOk() {
        String url = Url.RechargeOK_URL;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("out_trade_no", getActivity().getIntent().getStringExtra("billno"));
        params.put("customerId", PreferenceUtils.getUserId());
        params.put("registid", getActivity().getIntent().getStringExtra("registid"));

        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                if (new String(bytes).contains("success")) {
                    TXWLApplication.getInstance().showTextToast("已将记录交给服务器");
                    getDialog().dismiss();
                    TXWLApplication.getInstance().finishStack();
                } else {
                    TXWLApplication.getInstance().showTextToast("支付成功，将记录交给服务器失败,再按次确定");
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                TXWLApplication.getInstance().showTextToast("支付成功，网络错误,再按次确定");
            }
        });
    }

}
