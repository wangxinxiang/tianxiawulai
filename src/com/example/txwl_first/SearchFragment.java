package com.example.txwl_first;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.txwl_first.Util.TXWLApplication;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

/**
 * Created by Administrator on 2015/7/8 0008.
 */
public class SearchFragment extends Fragment {
    private static String TAG="SearchFragment";
    private Context mContext;
    private View view;
    private Button btn_query;
    private EditText edit_query;
    private TextView tv_servison;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        view=inflater.inflate(R.layout.query_layout,null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
        mContext=getActivity();
        initView();
        initListener();
    }


    public String getVersion() {
        PackageInfo info;
        try {
            info = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            // 当前应用的版本名称
            String versionName = info.versionName;
            // 当前版本的版本号
            int versionCode = info.versionCode;
            // 当前版本的包名
            String packageNames = info.packageName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "1.0";
    }

    private void initListener() {
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

        tv_servison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UmengUpdateAgent.update(mContext);
            }
        });

        UmengUpdateAgent.setUpdateAutoPopup(false);
        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
            @Override
            public void onUpdateReturned(int updateStatus,UpdateResponse updateInfo) {
                switch (updateStatus) {
                    case UpdateStatus.Yes: // has update
                        UmengUpdateAgent.showUpdateDialog(mContext, updateInfo);
                        break;
                    case UpdateStatus.No: // has no update
                        Toast.makeText(mContext, "已经更新到最新版", Toast.LENGTH_SHORT).show();
                        break;
                    case UpdateStatus.NoneWifi: // none wifi
                        Toast.makeText(mContext, "没有wifi连接， 只在wifi下更新", Toast.LENGTH_SHORT).show();
                        break;
                    case UpdateStatus.Timeout: // time out
                        Toast.makeText(mContext, "网络链接超时", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private void initView() {
        btn_query = (Button) view.findViewById(R.id.btn_pay_money);
        edit_query = (EditText) view.findViewById(R.id.et_input);
        tv_servison= (TextView) view.findViewById(R.id.tv_servison);

        tv_servison.setText("V."+getVersion());
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
        MobclickAgent.onPageStart(TAG); //统计页面
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "oPause");
        MobclickAgent.onPageEnd(TAG);
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
