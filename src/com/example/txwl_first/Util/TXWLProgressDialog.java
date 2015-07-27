package com.example.txwl_first.Util;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;
import com.example.txwl_first.R;


/**
 * Created by 104520 on 2015/4/1.
 */
public class TXWLProgressDialog extends Dialog {
    private Context context = null;
    private static TXWLProgressDialog TXWLProgressDialog = null;

    //方法重写一
    public TXWLProgressDialog(Context context) {
        super(context);
        this.context = context;
    }

    //方法重写二
    public TXWLProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    //创建一个自定义的progressDialog
    public static TXWLProgressDialog createDialog(Context context) {
        TXWLProgressDialog = new TXWLProgressDialog(context, R.style.progress_dialog);
        TXWLProgressDialog.setCancelable(true);
        TXWLProgressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TXWLProgressDialog.show();
        //setContent一定要在show()方法后
        TXWLProgressDialog.setContentView(R.layout.dialog);
        return TXWLProgressDialog;
    }

    //设置显示的文字
    public static TXWLProgressDialog setMessage(String message) {
        TextView msg = (TextView) TXWLProgressDialog.findViewById(R.id.id_tv_loadingmsg);
        if (msg != null) {
            msg.setText(message);
        }
        return TXWLProgressDialog;
    }

    public static TXWLProgressDialog Dismiss() {
        TXWLProgressDialog.dismiss();
        return TXWLProgressDialog;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (!hasFocus) {
            dismiss();
        }
    }
}
