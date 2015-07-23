package com.example.txwl_first;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.example.txwl_first.Adapter.BlackListAdapter;
import com.example.txwl_first.Util.TXWLApplication;
import com.example.txwl_first.Util.Url;
import com.example.txwl_first.bean.BlackListBean;
import com.example.txwl_first.bean.BlackListItemBean;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.apache.http.Header;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/8 0008.
 */
public class MenuFragment extends Fragment {
    private static String TAG="MenuFragment";
    private View view;
    private Resources resources;
    private TextView tv_add_deadbeater;
    private EditText et_search;
    private ImageButton ibtn_search;
    private ListView lv;

    private BlackListAdapter adapter;
    private BlackListBean blackListBean;
    private ArrayList<BlackListItemBean> list;
    private ArrayList<BlackListItemBean> byAllList;
    private ArrayList<BlackListItemBean> byWayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        view=inflater.inflate(R.layout.blacklist_layout,null);
        resources = getResources();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();//初始化视图和控件
        initListener();//初始化 监听器
        getHttpByQueryAllBlackPerson();//联网获取所有黑名单
        Log.d(TAG, "onActivityCreated");
    }

    private void initListener() {
        et_search.addTextChangedListener(new TextWatcher() {
            //搜索框的监听 处理
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("et_search", "beforeTextChanged");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("et_search", "onTextChanged");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("et_search", "afterTextChanged");
                //当有输入时 显示清空按钮 实现点击清空输入
                if (editable.length() == 0 || et_search.equals("")) {
                    ibtn_search.setVisibility(View.GONE);
                } else {
                    ibtn_search.setVisibility(View.VISIBLE);
                }
            }
        });

        //edittext的软键盘的搜索按钮监听
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //隐藏软键盘
                    if (textView.length() == 0 || textView.equals("")) {
                        Toast.makeText(getActivity(), getActivity().toString() + "输入框为空", Toast.LENGTH_LONG).show();
                        return true;
                    } else {
                        ((InputMethodManager) et_search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                                        InputMethodManager.HIDE_NOT_ALWAYS);
                        //实现跳转
//                    Intent intent=new Intent();
//                    intent.setClass(getActivity(),)
//                    startActivity(intent);
                        Toast.makeText(getActivity(), getActivity().toString() + "点击了软键盘的搜索", Toast.LENGTH_LONG).show();
                        getHttpByWayBlackPerson(et_search.getText().toString().trim());
                        return true;
                    }

                }
                return false;
            }
        });

        ibtn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "点击了搜索按钮", Toast.LENGTH_SHORT).show();
                getHttpByWayBlackPerson(et_search.getText().toString().trim());
            }
        });

        //点击添加老赖 实现跳转
        tv_add_deadbeater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddOldBeaterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getHttpByWayBlackPerson(String way) {
        //通过关键字查找黑名单
        AsyncHttpClient client=new AsyncHttpClient();
        client.setTimeout(10000);
        //查询所有黑名单接口 目前没有参数上传
        final RequestParams params = new RequestParams();
        params.put("way", way);//搜索内容

        client.post(Url.QueryBlackPersonFromWay_URL, params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                Log.d(TAG, "开始联网");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d(TAG, "联网成功");
                Log.d(TAG + "  onSuccess-->", new String(responseBody));//打印网络访问结果

                try {
                    blackListBean = new GsonBuilder().create().fromJson(new String(responseBody), BlackListBean.class);
                    if ((blackListBean != null) && ("success".equals(blackListBean.getStatus()))) {
                        //网络请求成功
                        byWayList.clear();
                        int size = blackListBean.getBlackListItemBeans().length;
                        for (int i = 0; i < size; i++) {
                            byWayList.add(blackListBean.getBlackListItemBeans()[i]);//添加list里面的item项目 到arraylist
                        }
                        list.clear();
                        list.addAll(byWayList);
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), "网络错误，请检查网络", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    TXWLApplication.getInstance().showErrorConnected(e);
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.d(TAG, "联网失败");
            }
        });

    }


    private void getHttpByQueryAllBlackPerson() {
        AsyncHttpClient client=new AsyncHttpClient();
        client.setTimeout(10000);
        //查询所有黑名单接口 目前没有参数上传
        final RequestParams params = new RequestParams();
//        params.put("opcode", "");//应该有一个一次网络请求 的数量

        client.post(Url.QueryAllBlackPerson_URL, params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                Log.d(TAG,"开始联网");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d(TAG, "联网成功");
                Log.d(TAG + "  onSuccess-->", new String(responseBody));//打印网络访问结果

                try {
                    blackListBean=new GsonBuilder().create().fromJson(new String(responseBody),BlackListBean.class);
                    if ((blackListBean!=null)&&("success".equals(blackListBean.getStatus()))){
                        //网络请求成功
                        int size=blackListBean.getBlackListItemBeans().length;
                        byAllList.clear();
                        for (int i = 0  ; i < size ; i++) {
                            byAllList.add(blackListBean.getBlackListItemBeans()[i]);//添加list里面的item项目 到arraylist
                        }
                        list.clear();
                        list.addAll(byAllList);
                        adapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(getActivity(),"网络错误，请检查网络",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    TXWLApplication.getInstance().showErrorConnected(e);
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.d(TAG,"联网失败");
            }
        });

    }

    private void initView() {
        tv_add_deadbeater = (TextView) view.findViewById(R.id.btn_text_add_deadbeater);
        et_search= (EditText) view.findViewById(R.id.et_search);
        ibtn_search= (ImageButton) view.findViewById(R.id.ibtn_search);


        lv= (ListView) view.findViewById(R.id.lv_blacklist);
        list=new ArrayList<BlackListItemBean>();
        byAllList = new ArrayList<BlackListItemBean>();
        byWayList=new ArrayList<BlackListItemBean>();
        adapter=new BlackListAdapter(getActivity(),list,resources);
        lv.setAdapter(adapter);

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
