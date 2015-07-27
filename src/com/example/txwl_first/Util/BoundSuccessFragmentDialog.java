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
                getDialog().dismiss();
                getActivity().finish();
            }
        });

        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if(keyCode==KeyEvent.KEYCODE_BACK){
                    getDialog().dismiss();
                    getActivity().finish();
                }
                return false;
            }
        });

        return view;
    }

}
