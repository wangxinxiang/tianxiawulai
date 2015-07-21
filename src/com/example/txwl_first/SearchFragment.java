package com.example.txwl_first;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.txwl_first.Util.TXWLApplication;

/**
 * Created by Administrator on 2015/7/8 0008.
 */
public class SearchFragment extends Fragment {
    private static String TAG="SearchFragment";
    private View view;
    private Button btn_query;
    private EditText edit_query;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        view=inflater.inflate(R.layout.query_layout,null);
        btn_query = (Button) view.findViewById(R.id.btn_pay_money);
        edit_query = (EditText) view.findViewById(R.id.et_input);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),QueryResultActivity.class);
                String query_text = edit_query.getText().toString();
                if ("".equals(query_text)) {
                    TXWLApplication.getInstance().showTextToast("关键字不能为空");
                    return;
                }
                intent.putExtra("key", query_text);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "oPause");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
